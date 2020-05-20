package com.haiyi.weChat;

import com.haiyi.pay.PayConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeChatConfig {
    public static String token;
    public static String APPID;
    public static String APPSECRET;
    public static String ACCESS_TOKEN_URL;
    public static String webUrl;

    static{
        Properties prop = new Properties();
        InputStream in = PayConfig.class.getResourceAsStream("/properties/weChat.properties");
        try {
            prop.load(in);
            token = prop.getProperty("token").trim();
            APPID = prop.getProperty("APPID").trim();
            APPSECRET = prop.getProperty("APPSECRET").trim();
            ACCESS_TOKEN_URL = prop.getProperty("ACCESS_TOKEN_URL").trim();
            webUrl = prop.getProperty("webUrl").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
