package com.haiyi.netty.server.handler;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.InsideComsume;
import com.haiyi.domain.Product;
import com.haiyi.netty.packet.ReportePacket;
import com.haiyi.netty.packet.ReporteResponsePacket;
import com.haiyi.pay.CheckUtil;
import com.haiyi.pay.PayConfig;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.InsideComsumeService;
import com.haiyi.service.ProductService;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.*;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.LogUtils;
import com.maizi.utils.SnowflakeIdFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;

//1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class ReporteHandle extends SimpleChannelInboundHandler<ReportePacket>{
    private static final ReporteHandle reporteHandle = new ReporteHandle();
    private ReporteHandle() {
    }
    public static ReporteHandle getInstance() {
        return reporteHandle;
    }

    private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);

    /**
     * 当消息到达时
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReportePacket reportePacket) throws Exception {
        LogUtils.logInfo("收到客户端[{}]上报出货信息请求",reportePacket.getDevNum());
        ReporteResponsePacket reporteResponsePacket=new ReporteResponsePacket();
        String success=reportePacket.getSuccess();
        ComsumeLogService comsumeLogService= SpringUtil.getBean("comsumeLogServiceImpl");
        InsideComsumeService insideComsumeService=SpringUtil.getBean("insideComsumeServiceImpl");
        String orderId=reportePacket.getOrderId();
        ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
        if(comsumeLog.getPayStatus()==StateConfig.PAYSTATUS_SUCCESS || comsumeLog.getPayStatus()==StateConfig.PAYSTATUS_UNUSUAL){
            reporteResponsePacket.setResult(false);
            ctx.channel().writeAndFlush(reporteResponsePacket);
            return;
        }
        if("1".equals(success)){

            //Product product=productService.findByDevNumAndProductId(Integer.valueOf(comsumeLog.getDevNum()),comsumeLog.getProductId());

            comsumeLog.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
            //设置支付方式
            comsumeLogService.update(comsumeLog);

            //设置内部表支付状态
            InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
            insideComsumeQuery.setOrderId(orderId);
            InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
            insideComsume.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
            insideComsumeService.update(insideComsume);

            reporteResponsePacket.setResult(true);
            ctx.channel().writeAndFlush(reporteResponsePacket);
            LogUtils.logInfo("[{}]设备出货成功",reportePacket.getDevNum());
        }else {
            ProductService productService= SpringUtil.getBean("productServiceImpl");
            Product product=productService.findById(comsumeLog.getProductId());
            product.setStoreCount(product.getStoreCount()+1);
            if(product.getStoreCount()<=product.getThreshold()){
                product.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
            }else{
                product.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
            }
            productService.update(product);
            //开始退款申请
            if(comsumeLog.getPayType()==1){
                //申请微信退款
                Map<String, String> map = new HashMap<String, String>();
                //商户平台id
                map.put("mch_id", PayConfig.mch_id);
                //微信分配的公众账号ID（企业号corpid即为此appId）
                map.put("appid", PayConfig.appId);
                //随机字符串
                map.put("nonce_str", CheckUtil.generateNonceStr());
                //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
                // TODO 获取用户押金订单号（用户在微信支付时的订单号）
                String outTradeNo = orderId;
                //订单总金额，单位为分，只能为整数（用户在微信支付时的总金额）
                String totalFee = MoneyUtil.changeY2F(comsumeLog.getPayAmount());
                map.put("out_trade_no", outTradeNo);//微信订单号  内部订单号和微信订单号二选一
                //商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
                //todo 用户退款单号 先暂用时间戳
                String outRefundNo = String.valueOf(snow.nextId());
                map.put("out_refund_no", outRefundNo);
                map.put("total_fee", totalFee);
                //退款总金额，单位为分，只能为整数（不能大于订单总金额）
                map.put("refund_fee", totalFee);
                map.put("refund_desc", "机器繁忙，未能出货");
                //异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
                //如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效
                map.put("notify_url", "http://www.peshion.com/apiWeb/callback/wx/refund");
                LogUtils.logInfo("MAP======"+map);
           /* StringBuilder paySign = new StringBuilder();
            // 生成退款签名
            paySign.append("appId=").append(PayConfig.appId);
            paySign.append("&nonceStr=").append(map.get("nonce_str"));
            paySign.append("&mch_id=").append(PayConfig.mch_id);
            paySign.append("&out_trade_no=").append(outTradeNo);
            paySign.append("&out_refund_no=").append(map.get("out_refund_no"));
            paySign.append("&total_fee=").append(map.get("total_fee"));
            paySign.append("&refund_fee=").append(map.get("refund_fee"));
            paySign.append("&refund_desc=").append(map.get("refund_desc"));
            paySign.append("&notify_url=").append(map.get("notify_url"));*/
                //String sign = encoder.encode(paySign.toString());
                //签名
                String sign = CheckUtil.generateSignature(map, PayConfig.key, "MD5");
                map.put("sign", sign);
                String xml = CheckUtil.getXMLFromMap(map);
                LogUtils.logInfo("退款签名XML格式========"+xml);
                try {
                    //获取apiclient_cert.p12证书，以下内容是直接在微信提供的demo中摘取出来的
                    //InputStream certStream = request.getServletContext().getResourceAsStream("/WEB-INF/apiclient_cert.p12");
                    InputStream certStream = ReporteHandle.class.getResourceAsStream("/properties/apiclient_cert.p12");
                    char[] password = PayConfig.mch_id.toCharArray();
                    KeyStore ks = KeyStore.getInstance("PKCS12");
                    ks.load(certStream, password);
                    // 实例化密钥库 & 初始化密钥工厂
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    kmf.init(ks, password);
                    // 创建 SSLContext
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
                    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                            sslContext,
                            new String[]{"TLSv1"},
                            null,
                            new DefaultHostnameVerifier());
                    BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                            RegistryBuilder.<ConnectionSocketFactory>create()
                                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                                    .register("https", sslConnectionSocketFactory)
                                    .build(),
                            null,
                            null,
                            null
                    );
                    HttpClient httpClient = HttpClientBuilder.create()
                            .setConnectionManager(connManager)
                            .build();
                    String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
                    HttpPost httpPost = new HttpPost(url);
                    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
                    httpPost.setConfig(requestConfig);
                    StringEntity postEntity = new StringEntity(xml, "UTF-8");
                    httpPost.addHeader("Content-Type", "text/xml");
                    httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + PayConfig.mch_id);
                    httpPost.setEntity(postEntity);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    String data = EntityUtils.toString(httpEntity, "UTF-8");
                    Map<String, String> refounResult = CheckUtil.xmlToMap(data);
                    LogUtils.logInfo("退款返回的信息" + refounResult);
                    if ("SUCCESS".equals(refounResult.get("return_code")) && "SUCCESS".equals(refounResult.get("result_code"))) {
                        //TODO 获得退款详细信息，封装到数据库中
                        comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                        comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
                        //设置支付方式
                        comsumeLog.setPayType(StateConfig.PAYTYPE_XW);
                        comsumeLogService.update(comsumeLog);

                        //设置内部表支付状态
                        InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                        insideComsumeQuery.setOrderId(orderId);
                        InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
                        insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                        insideComsumeService.update(insideComsume);


                        //outTradeNo = refounResult.get("out_trade_no");//
                        reporteResponsePacket.setResult(true);
                        ctx.channel().writeAndFlush(reporteResponsePacket);
                        LogUtils.logInfo("[{}]设备未出货,退款成功",reportePacket.getDevNum());
                    } else {
                        String msg = refounResult.get("return_msg");
                        LogUtils.logInfo("退款失败==========" + msg);
                        reporteResponsePacket.setResult(true);
                        ctx.channel().writeAndFlush(reporteResponsePacket);
                        LogUtils.logInfo("[{}]设备未出货,退款失败",reportePacket.getDevNum());

                    }
                } catch (Exception e) {
                    LogUtils.logInfo("退款失败" + e.getMessage());
                    reporteResponsePacket.setResult(false);
                    ctx.channel().writeAndFlush(reporteResponsePacket);
                    LogUtils.logInfo("[{}]设备未出货,退款失败",reportePacket.getDevNum());

                }
            }else if(comsumeLog.getPayType()==2){
                //申请支付宝退款
                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", PayConfig.alipayAPPID, PayConfig.RSA_private_key, "json", ConstantUtil.CHARSET, PayConfig.ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
                AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
                refundModel.setOutTradeNo(comsumeLog.getOrderId());
                refundModel.setRefundAmount(comsumeLog.getPayAmount()+"");
                refundModel.setRefundReason("商品退款");
                //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
                 AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
                 request.setBizModel(refundModel);
                comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
                //设置支付方式
                comsumeLog.setPayType(StateConfig.PAYTYPE_ZFB);
                comsumeLogService.update(comsumeLog);

                //设置内部表支付状态
                InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                insideComsumeQuery.setOrderId(orderId);
                InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
                insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                insideComsumeService.update(insideComsume);

                try{
                    LogUtils.logInfo("发送退款请求===========");
                    alipayClient.execute(request);
                    reporteResponsePacket.setResult(true);
                    ctx.channel().writeAndFlush(reporteResponsePacket);
                    LogUtils.logInfo("[{}]设备未出货,退款成功",reportePacket.getDevNum());
                }catch(Exception e){
                    reporteResponsePacket.setResult(true);
                    ctx.channel().writeAndFlush(reporteResponsePacket);
                    //LogUtils.logInfo("[{}]设备未出货,退款失败",reportePacket.getDevNum());
                    LogUtils.logInfo("[{}]设备未出货,退款成功",reportePacket.getDevNum());
                }
                 //根据response中的结果继续业务逻辑处理
            }else if(comsumeLog.getPayType()==3 ||comsumeLog.getPayType()==4){
                //申请中信退款
                SortedMap<String,String> map = new TreeMap<String,String>();
                map.put("service", "unified.trade.refund");
                map.put("sign_type","RSA_1_256");
                map.put("mch_id",PayConfig.zx_mch_id);
                map.put("out_trade_no",orderId);
                map.put("out_refund_no",String.valueOf(snow.nextId()));
                String totalFee = MoneyUtil.changeY2F(comsumeLog.getPayAmount());
                map.put("total_fee",totalFee);
                map.put("refund_fee",totalFee);
                map.put("op_user_id",PayConfig.zx_mch_id);
                map.put("nonce_str",String.valueOf(new Date().getTime()));
                Map<String, String> params = SignUtil.paraFilter(map);
                StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
                SignUtil.buildPayParams(buf, params, false);
                String preStr = buf.toString();
                map.put("sign", SignUtil.getSign(map.get("sign_type")+"", preStr));
                CloseableHttpResponse response = null;
                CloseableHttpClient client = null;
                String res = null;
                try {
                    HttpPost httpPost = new HttpPost("https://payapi.citicbank.com/pay/gateway");
                    StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map),"utf-8");
                    httpPost.setEntity(entityParams);
                    httpPost.setHeader("Content-Type", "text/xml;utf-8");
                    client = HttpClients.createDefault();
                    response = client.execute(httpPost);
                    if(response != null && response.getEntity() != null){
                        Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                        String reSign = resultMap.get("sign");
                        res = XmlUtils.toXml(resultMap);
                        LogUtils.logInfo("请求结果：" + res);
                        System.out.println("请求结果：" + res);
                        if (resultMap.containsKey("sign") && !SignUtil.verifySign(reSign, map.get("sign_type"), resultMap)) {
                            res = "验证签名不通过";
                        }
                    }else{
                        res = "操作失败!";
                    }
                } catch (Exception e) {
                    LogUtils.logError("操作失败，原因：",e);
                    res = "操作失败";
                } finally {
                    if(response != null){
                        response.close();
                    }
                    if(client != null){
                        client.close();
                    }
                }
                Map<String,String> result = new HashMap<String,String>();
                if(res.startsWith("<")){
                    result.put("status", "200");
                    result.put("msg", "操作成功，请在日志文件中查看");
                    comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                    //comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
                    //设置支付方式
                    comsumeLogService.update(comsumeLog);

                    //设置内部表支付状态
                    InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                    insideComsumeQuery.setOrderId(orderId);
                    InsideComsume insideComsume = insideComsumeService.findBySelective(insideComsumeQuery).getList().get(0);
                    insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                    insideComsumeService.update(insideComsume);

                    //outTradeNo = refounResult.get("out_trade_no");//
                    reporteResponsePacket.setResult(true);
                    ctx.channel().writeAndFlush(reporteResponsePacket);
                    LogUtils.logInfo("[{}]设备未出货,退款成功",reportePacket.getDevNum());

                }else{
                    result.put("status", "500");
                    result.put("msg", res);
                    LogUtils.logInfo("退款失败" );
                    reporteResponsePacket.setResult(false);
                    ctx.channel().writeAndFlush(reporteResponsePacket);
                    LogUtils.logInfo("[{}]设备未出货,退款失败",reportePacket.getDevNum());
                }
            }
        }
        //TODO
        // 登录响应

    }




}
