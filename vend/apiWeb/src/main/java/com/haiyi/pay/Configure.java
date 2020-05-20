package com.haiyi.pay;


import java.util.Calendar;
import java.util.Date;

import com.maizi.utils.StringUtil;


public class Configure {

    private static String token = null;
    private static Date tokenTime = null;
    private static String jsapiTicket = null;
    private static Date jsapiTicketTime = null;

    public static String MD5 = "MD5";
    public static String EMPTY = "";
    public static String SUCCESS = "SUCCESS";
    public static String HEX_FORMAT = "%02x";


    public static String JS_TRADE_TYPE = "JSAPI";  //公众号支付
    public static String H5_TRADE_TYPE = "MWEB";   //H5支付

    public static String MIDDLE_LINE = "-";
    public static String CHARTSET_UTF8 = "UTF-8";

    public static String NOTIFY_SUCCESS = "<xml>\n<return_code><![CDATA[SUCCESS]]></return_code>\n<return_msg><![CDATA[OK]]></return_msg>\n</xml>";
    public static String NOTIFY_FAIL = "<xml>\n<return_code><![CDATA[FAIL]]></return_code>\n<return_msg><![CDATA[ERROR]]></return_msg>\n</xml>";

    private static String key = "werijdkfloirori1295094896fvmvQld";
    // 微信分配的公众号ID（开通公众号之后可以获取到）
    private static String appID = "wx7262448316d3e202";

    //private static String appSecret = "be30487db9129a299aa4d4ad720df8b9";
    private static String appSecret = "fe7240a77dba69b493b0f4c4b96b775e";
    // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    private static String mchID = "1546607121";
    // 以下是几个API的路径：

    // 统一下单
    public static String UNIFIEDORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //  access_token API
    public static String TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    // 临时票据API
    public static String TICKET_API = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    // 微信OPENID API
    public static String OPENID_API = "https://api.weixin.qq.com/sns/oauth2/access_token";

    //支付成功界面
    public static String SUCCESS_URL = "http://www.peshion.com/apiWeb/callback/wx/notification";

    public static void setKey(String key) {
        Configure.key = key;
    }

    public static void setAppID(String appID) {
        Configure.appID = appID;
    }

    public static void setMchID(String mchID) {
        Configure.mchID = mchID;
    }

    public static String getKey(){
        return key;
    }

    public static String getAppid(){
        return appID;
    }

    public static String getMchid(){
        return mchID;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static void setAppSecret(String appSecret) {
        Configure.appSecret = appSecret;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Configure.token = token;
        Configure.tokenTime = new Date();
    }

    public static String getJsapiTicket() {
        return jsapiTicket;
    }

    public static void setJsapiTicket(String jsapiTicket) {
        Configure.jsapiTicket = jsapiTicket;
        Configure.jsapiTicketTime = new Date();
    }

    public static boolean checkToken() {
        if (!StringUtil.isEmpty(Configure.token)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tokenTime);
            calendar.add(Calendar.SECOND, 7200);
            return calendar.before(new Date());
        }
        return true;
    }

    public static boolean checkJsapiTicket() {
        if (!StringUtil.isEmpty(Configure.jsapiTicket)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(jsapiTicketTime);
            calendar.add(Calendar.SECOND, 7200);
            return calendar.before(new Date());
        }
        return true;
    }
}