package com.haiyi.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.haiyi.domain.*;
import com.haiyi.netty.server.handler.ReporteHandle;
import com.haiyi.pay.CheckUtil;
import com.haiyi.pay.PayConfig;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.*;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.MoneyUtil;
import com.haiyi.utils.SignUtil;
import com.haiyi.utils.StatusConstant;
import com.haiyi.utils.XmlUtils;
import com.maizi.utils.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;

/**
 * HTTP协议控制器
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/link")
public class HttpController {

    @Autowired
    DevService devService;
    @Autowired
    ProductGroupService productGroupService;
    @Autowired
    ProductService productService;
    @Autowired
    ComsumeLogService comsumeLogService;
    @Autowired
    UserService userService;
    @Autowired
    InsideComsumeService insideComsumeService;


    private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);


    @RequestMapping(value="/login",method= RequestMethod.GET)
    @ResponseBody
    public void login(@RequestParam String devNum, @RequestParam String token, HttpServletResponse response) throws IOException {
        Dev dev = devService.findByNumAndToken(devNum, token);
        response.setContentType("text/html;charset=utf-8");// 加上这个处理问号
        StringBuilder result=new StringBuilder();
        if(dev==null){
            result.append("state=2;");
        }else {
            result.append("state=1;");
            ProductGroup productGroup = productGroupService.findByDevNum(devNum);
            List<Product> products = productService.findByProductGroupId(productGroup.getId());
            for(Product product:products ){
                result.append("status="+product.getStatus()+",productId="+product.getId()+",name="+product.getName()+",price="+product.getPrice()+",img=http://118.190.52.54:9090/images/"+product.getImg()
                        +",orderNum="+product.getOrderNum()+",storeCount="+product.getStoreCount()+";");
            }
        }
        PrintWriter out = response.getWriter();
        out.write(result.toString());
        out.close();
    }


    @RequestMapping(value="/buyInfo/{devNum}",method= RequestMethod.GET)
    @ResponseBody
    public String buyInfo(@PathVariable String devNum,@RequestParam String info) {
        System.out.println(info);
        BigDecimal price=new BigDecimal(0);
        BigDecimal cost=new BigDecimal(0);
        StringBuilder productName1=new StringBuilder();
        Integer productId=0;
        StringBuilder result=new StringBuilder();

        String orderId=String.valueOf(snow.nextId());
        Date currentDate = DateUtil.getCurrentDate();
        Dev dev=devService.findByNum(devNum);
        User user = userService.findById(dev.getUserId());

        try{
            String[] context = info.split("@");
            for(int i=0;i<context.length;i++){
                System.out.println(context[i]);
                String[] split = context[i].split(",");
                productId=Integer.valueOf(split[0]);
                BigDecimal money=new BigDecimal(split[1]);
                BigDecimal buyCount=new BigDecimal(split[2]);
                price=price.add(money.multiply(buyCount));
                Product product = productService.findById(productId);
                cost=cost.add(product.getCost().multiply(buyCount));
                productName1.append(product.getName()+"+");

                //创建内部表
                for(int k=0;k<buyCount.intValue();k++){
                    InsideComsume insideComsume=new InsideComsume();
                    insideComsume.setOrderId(orderId);
                    insideComsume.setProductId(productId);
                    insideComsume.setProductName(product.getName());
                    insideComsume.setUserId(dev.getUserId());
                    insideComsume.setUserName(user.getName());
                    insideComsume.setAgentId(user.getAgentId());
                    insideComsume.setAgentName(user.getAgentName());
                    insideComsume.setDevNum(devNum);
                    insideComsume.setDevName(dev.getName());
                    insideComsume.setPrice(money);
                    insideComsume.setCost(product.getCost());
                    insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNGOING);
                    insideComsume.setCreateDate(currentDate);
                    insideComsumeService.add(insideComsume);
                }



            }
        }catch (Exception e){
            result.append("error");
            return result.toString();
        }

        String productName=productName1.toString();
        productName = productName.substring(0, productName.length() - 1);
        System.out.println(cost);
        System.out.println(productName);
        ComsumeLog comsumeLog=new ComsumeLog();
        comsumeLog.setOrderId(orderId);
        comsumeLog.setDevNum(devNum);

        comsumeLog.setUserName(user.getName());
        comsumeLog.setAgentName(user.getAgentName());
        comsumeLog.setUserId(dev.getUserId());
        comsumeLog.setAgentId(dev.getAgentId());
        comsumeLog.setProductId(productId);
        comsumeLog.setPayAmount(price);
        comsumeLog.setProductName(productName);
        comsumeLog.setCost(cost);
        comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNGOING);
        comsumeLog.setDevName(dev.getName());
        comsumeLog.setTradeType(StateConfig.TRADETYPE_COMSUME);
        comsumeLog.setReqType(StateConfig.HTTP_REQUEST);

        comsumeLog.setCreateDate(currentDate);
        comsumeLogService.add(comsumeLog);






        result.append("orderId="+comsumeLog.getOrderId()+",payUrl=http://www.peshion.com/wapApp/products/toHttpPayUI?devNum="+devNum+"&orderId="+comsumeLog.getOrderId()+"&price="+price);
        return result.toString();
    }


    @RequestMapping(value="/verify",method= RequestMethod.GET)
    @ResponseBody
    public String verify(@RequestParam String devNum, @RequestParam String token,@RequestParam String orderId) {
        Dev dev = devService.findByNumAndToken(devNum, token);
        StringBuilder result=new StringBuilder();
        if(dev==null){
            result.append("state=2;");
        }else {
            result.append("state=1;");
            ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
            if(comsumeLog.getPayStatus()==StateConfig.PAYSTATUS_UNDONE){
                result.append("statue=1");
            }else {
                if(comsumeLog.getPayStatus()==StateConfig.PAYSTATUS_UNGOING){
                    result.append("statue=2");
                }else {
                    result.append("statue=1");
                }

            }
        }
        return result.toString();
    }


    @RequestMapping(value="/shipInfo/{orderId}",method= RequestMethod.GET)
    @ResponseBody
    public String shipInfo(@PathVariable String orderId,String productInfo) throws Exception{
        StringBuilder respon=new StringBuilder();
        ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
        LogUtils.logInfo("收到客户端[{}]上报出货信息请求",comsumeLog.getDevNum());
        if(comsumeLog.getPayStatus()==StateConfig.PAYSTATUS_SUCCESS || comsumeLog.getPayStatus()==StateConfig.PAYSTATUS_UNUSUAL){
            //订单已支付成功或已退款
            respon.append("state=2;");
            return respon.toString();
        }else {
            BigDecimal returnPrice=new BigDecimal("0");

            try{

                String[] context = productInfo.split("@");
                for(int i=0;i<context.length;i++){
                    String[] split = context[i].split(",");
                    int success=Integer.valueOf(split[1]);
                    BigDecimal fail=new BigDecimal(split[2]);
                    System.out.println(fail);
                    Product product=productService.findById(Integer.valueOf(split[0]));


                    if(success!=0){
                        product.setStoreCount(product.getStoreCount()-success);
                        if(product.getStoreCount()<=product.getThreshold()){
                            product.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
                        }else{
                            product.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
                        }
                        productService.update(product);
                    }


                    //修改内部表
                    InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
                    insideComsumeQuery.setOrderId(orderId);
                    insideComsumeQuery.setProductId(product.getId());
                    List<InsideComsume> insideComsumeList = insideComsumeService.findBySelective(insideComsumeQuery).getList();
                    int size=insideComsumeList.size();
                    if(success>0){
                        for(int y=0;y<success;y++){
                            InsideComsume insideComsume=insideComsumeList.get(y);
                            insideComsume.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
                            insideComsumeService.update(insideComsume);
                        }
                    }
                    if(size>success){
                        for(int j=success;j<size;j++){
                            InsideComsume insideComsume=insideComsumeList.get(j);
                            insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                            insideComsumeService.update(insideComsume);
                        }
                    }




                    if(fail.intValue()!=0){
                        returnPrice=returnPrice.add(product.getPrice().multiply(fail));
                    }

                }


            }catch (Exception e){
                respon.append("error");
                return respon.toString();
            }



            if(returnPrice.compareTo(BigDecimal.ZERO)==1){
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
                    String finalReturnPrice = MoneyUtil.changeY2F(returnPrice);
                    map.put("out_trade_no", outTradeNo);//微信订单号  内部订单号和微信订单号二选一
                    //商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
                    //todo 用户退款单号 先暂用时间戳
                    String outRefundNo = String.valueOf(snow.nextId());
                    map.put("out_refund_no", outRefundNo);
                    map.put("total_fee", totalFee);
                    //退款总金额，单位为分，只能为整数（不能大于订单总金额）
                    map.put("refund_fee", finalReturnPrice);
                    map.put("refund_desc", "机器繁忙，未能出货");
                    //异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
                    //如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效
                    map.put("notify_url", "http://www.peshion.com/apiWeb/callback/wx/refund");
                    LogUtils.logInfo("MAP======"+map);
            StringBuilder paySign = new StringBuilder();
            // 生成退款签名
            paySign.append("appId=").append(PayConfig.appId);
            paySign.append("&nonceStr=").append(map.get("nonce_str"));
            paySign.append("&mch_id=").append(PayConfig.mch_id);
            paySign.append("&out_trade_no=").append(outTradeNo);
            paySign.append("&out_refund_no=").append(map.get("out_refund_no"));
            paySign.append("&total_fee=").append(map.get("total_fee"));
            paySign.append("&refund_fee=").append(map.get("refund_fee"));
            paySign.append("&refund_desc=").append(map.get("refund_desc"));
            paySign.append("&notify_url=").append(map.get("notify_url"));
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

                            //outTradeNo = refounResult.get("out_trade_no");//
                            respon.append("state=1;");
                            LogUtils.logInfo("[{}]设备未出货,退款成功",comsumeLog.getDevNum());
                        } else {
                            String msg = refounResult.get("return_msg");
                            LogUtils.logInfo("退款失败==========" + msg);
                            respon.append("state=1");
                            LogUtils.logInfo("[{}]设备未出货,退款失败",comsumeLog.getDevNum());

                        }
                    } catch (Exception e) {
                        LogUtils.logInfo("退款失败" + e.getMessage());
                        respon.append("state=2");
                        LogUtils.logInfo("[{}]设备未出货,退款失败",comsumeLog.getDevNum());

                    }
                }else if(comsumeLog.getPayType()==2){
                    //申请支付宝退款
                    AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", PayConfig.alipayAPPID, PayConfig.RSA_private_key, "json", ConstantUtil.CHARSET, PayConfig.ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
                    AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
                    refundModel.setOutTradeNo(comsumeLog.getOrderId());
                    refundModel.setRefundAmount(returnPrice+"");
                    refundModel.setRefundReason("商品退款");
                    //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
                    AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
                    request.setBizModel(refundModel);
                    comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
                    comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
                    //设置支付方式
                    comsumeLog.setPayType(StateConfig.PAYTYPE_ZFB);
                    comsumeLogService.update(comsumeLog);
                    try{
                        LogUtils.logInfo("发送退款请求===========");
                        alipayClient.execute(request);
                    }catch(Exception e){
                        respon.append("state=1");
                        //LogUtils.logInfo("[{}]设备未出货,退款失败",reportePacket.getDevNum());
                        LogUtils.logInfo("[{}]设备未出货,退款成功",comsumeLog.getDevNum());
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
                    String finalReturnPrice = MoneyUtil.changeY2F(returnPrice);
                    map.put("total_fee",totalFee);
                    map.put("refund_fee",finalReturnPrice);
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
                        //outTradeNo = refounResult.get("out_trade_no");//
                        respon.append("state=1");
                        LogUtils.logInfo("[{}]设备未出货,退款成功",comsumeLog.getDevNum());

                    }else{
                        result.put("status", "500");
                        result.put("msg", res);
                        LogUtils.logInfo("退款失败" );
                        respon.append("state=1");
                        LogUtils.logInfo("[{}]设备未出货,退款失败",comsumeLog.getDevNum());
                    }
                }
            }else {
                //出货成功
                comsumeLog.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
                comsumeLogService.update(comsumeLog);
                respon.append("state=1");
                LogUtils.logInfo("[{}]设备出货成功",comsumeLog.getDevNum());

            }
        }



        return respon.toString();
    }

























}
