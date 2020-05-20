package com.haiyi.pay;

import com.haiyi.utils.SignUtil;
import com.haiyi.utils.WXUtil;
import com.haiyi.utils.XmlUtils;
import com.maizi.utils.LogUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

public class GateWayService {

    public final static String NOTIFY_FAIL="fail";
    public final static String NOTIFY_SUCCESS="success";

    private final static String version = "1.0";
    private final static String charset = "UTF-8";
    /**
     * 支付请求
     * @param ip  发起支付ip
     * @param total  支付金额,单位分
     * @param body  商品描述
     * @param orderId  订单id
     * @param code  用于获取关注用户的openId
     * @throws ServletException
     * @throws IOException
     */
    public static Map<String,String> pay(String ip,int total,String body,String orderId,String code){

        LogUtils.logInfo("开始支付请求...");

        SortedMap<String,String> map = new TreeMap<String,String>();

        //map.put("service", "pay.weixin.jspay");
        map.put("version", version);
        //map.put("charset", charset);

        //map.put("is_raw", "1");
        map.put("mch_id", PayConfig.mch_id);
        map.put("notify_url", PayConfig.notify_url);
        map.put("nonce_str", String.valueOf(new Date().getTime()));

        String openId = WXUtil.takeOpenId(code);
        map.put("openid",openId);
        map.put("appid",PayConfig.appId);

        //商品描述 TODO
        map.put("body",body);
        map.put("spbill_create_ip",ip);
        map.put("total_fee",total+"");
        map.put("fee_type","CNY");
        map.put("out_trade_no",orderId);
        map.put("sign_type","MD5");
        map.put("trade_type","JSAPI");

        Map<String, String> params = SignUtil.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        SignUtil.buildPayParams(buf, params, false);
        String preStr = buf.toString();
        LogUtils.logInfo("preStr======================"+preStr);
        map.put("sign", SignUtil.getSign(map.get("sign_type")+"", preStr));
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        Map<String, String> resultMap = null;
        try {
            HttpPost httpPost = new HttpPost( PayConfig.req_url);

            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "text/xml;utf-8");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            LogUtils.logInfo("response=================="+response);
            LogUtils.logInfo("response.getEntity()=================="+response.getEntity());
            if (response != null && response.getEntity() != null) {
                resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                LogUtils.logInfo("resultMap=================="+resultMap);
                String reSign = resultMap.get("sign");
                LogUtils.logInfo("reSign=================="+reSign);
                res = XmlUtils.toXml(resultMap);
                LogUtils.logInfo("");
                LogUtils.logInfo("res解析XML信息========================"+res);
                if ("SUCCESS".equals(resultMap.get("result_code"))) {
                    String pay_info = resultMap.get("pay_info");
                    res = "ok";
                }
                /*if (resultMap.containsKey("sign") && !SignUtil.verifySign(reSign, resultMap.get("sign_type"), resultMap)) {
                    res = "验证签名不通过1";
                } else {
                    if ("SUCCESS".equals(resultMap.get("result_code"))) {
                        String pay_info = resultMap.get("pay_info");
                        res = "ok";
                    }
                }*/
            } else {
                res = "操作失败";
            }
        } catch (Exception e) {
            LogUtils.logError("操作失败，原因"+e.getMessage());
            res = "系统异常";
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String,String> result = new HashMap<String,String>();

        if("ok".equals(res)){
            result = resultMap;
            result.put("status","0");
        }else{
            result.put("status", "500");
            result.put("msg", res);
        }
        return result;
    }

    public static Map<String,String> wechatPay(Map<String,String> res){
        Map<String,String> result = new HashMap<String,String>();
        result.put("",res.get(""));

        return result;
    }




    public static Map<String,String> zxPay(String ip,int total,String body,String orderId,String code,String payType,String userId){

        LogUtils.logInfo("开始支付请求...");

        SortedMap<String,String> map = new TreeMap<String,String>();
        if ("wxpay".equals(payType)) {
            map.put("service", "pay.weixin.jspay");
            map.put("version", version);
            map.put("charset", charset);

            map.put("is_raw", "1");
            map.put("mch_id", PayConfig.zx_mch_id);
            map.put("notify_url", PayConfig.zx_notify_url);
            map.put("nonce_str", String.valueOf(new Date().getTime()));

            String openId = WXUtil.zx_takeOpenId(code);
            map.put("sub_openid",openId);
            map.put("sub_appid",PayConfig.appId);

            //商品描述 TODO
            map.put("body",body);
            map.put("mch_create_ip",ip);
            map.put("total_fee",total+"");
            map.put("out_trade_no",orderId);
            map.put("sign_type","RSA_1_256");

        } else if ("alipay".equals(payType)) {
            //TODO 支付宝支付
            map.put("service","pay.alipay.jspay");
            map.put("version", version);
            map.put("charset", charset);
            map.put("mch_id", PayConfig.zx_mch_id);
            map.put("sign_type","RSA_1_256");
            map.put("out_trade_no",orderId);
            map.put("buyer_id",userId);
            map.put("body",body);
            map.put("mch_create_ip",ip);
            map.put("total_fee",total+"");
            map.put("notify_url", PayConfig.zx_notify_url);
            map.put("nonce_str", String.valueOf(new Date().getTime()));
        }
        Map<String, String> params = SignUtil.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        SignUtil.buildPayParams(buf, params, false);
        String preStr = buf.toString();
        map.put("sign", SignUtil.getSign(map.get("sign_type")+"", preStr));

        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        Map<String, String> resultMap = null;
        HttpPost httpPost =null;
        try {
            if ("wxpay".equals(payType)) {
                httpPost = new HttpPost( PayConfig.zx_req_url);

            } else if ("alipay".equals(payType)) {
                //TODO 支付宝支付
                httpPost = new HttpPost( "https://payapi.citicbank.com/pay/gateway");
            }
            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "text/xml;utf-8");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            if (response != null && response.getEntity() != null) {
                resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                String reSign = resultMap.get("sign");
                res = XmlUtils.toXml(resultMap);

                if (resultMap.containsKey("sign") && !SignUtil.verifySign(reSign, resultMap.get("sign_type"), resultMap)) {
                    res = "验证签名不通过";
                } else {
                    if ("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))) {
                        String pay_info = resultMap.get("pay_info");
                        res = "ok";
                    }
                }
            } else {
                res = "操作失败";
            }
        } catch (Exception e) {
            LogUtils.logError("操作失败，原因"+e.getMessage());
            res = "系统异常";
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String,String> result = new HashMap<String,String>();

        if("ok".equals(res)){
            result = resultMap;
        }else{
            result.put("status", "500");
            result.put("msg", res);
        }
        return result;
    }







}