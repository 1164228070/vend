package com.haiyi.pay;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PayConfig {
    
    /**
     * 交易密钥
     */
    public static String key ;
    public static String mchPrivateKey;
    public static String platPublicKey;
    /**
     * 商户号
     */
    public static String mch_id;
    
    /**
     * 请求url
     */
    public static String req_url;
    
    /**
     * 通知url
     */
    public static String notify_url;

    /**
     * appId
     */
    public static String appId;

    /**
     * appSecret
     */
    public static String appSecret;




    public static String alipayAPPID;
    public static String ALIPAY_PUBLIC_KEY;
    public static String alipay_gateway;
    public static String alipay_notify_url;
    public static String alipay_return_url;
    public static String RSA_private_key;



    public static String zx_mch_id;
    public static String zx_req_url;
    public static String zx_notify_url;
    public static String zx_mchPrivateKey;
    public static String zx_platPublicKey;

    static{
        Properties prop = new Properties();
        InputStream in = PayConfig.class.getResourceAsStream("/properties/pay.properties");
        try {
            prop.load(in);
            key = prop.getProperty("key").trim();
            mchPrivateKey = prop.getProperty("mchPrivateKey").trim();
            platPublicKey = prop.getProperty("platPublicKey").trim();
            mch_id = prop.getProperty("mch_id").trim();
            req_url = prop.getProperty("req_url").trim();
            notify_url = prop.getProperty("notify_url").trim();
            appId = prop.getProperty("appId").trim();
            appSecret = prop.getProperty("appSecret").trim();
            alipayAPPID = prop.getProperty("alipayAPPID").trim();
            ALIPAY_PUBLIC_KEY = prop.getProperty("ALIPAY_PUBLIC_KEY").trim();
            alipay_gateway = prop.getProperty("alipay_gateway").trim();
            alipay_notify_url = prop.getProperty("alipay_notify_url").trim();
            alipay_return_url = prop.getProperty("alipay_return_url").trim();
            RSA_private_key = prop.getProperty("RSA_private_key").trim();
            zx_mch_id = prop.getProperty("zx_mch_id").trim();
            zx_req_url = prop.getProperty("zx_req_url").trim();
            zx_notify_url = prop.getProperty("zx_notify_url").trim();
            zx_mchPrivateKey = prop.getProperty("zx_mchPrivateKey").trim();
            zx_platPublicKey = prop.getProperty("zx_platPublicKey").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
