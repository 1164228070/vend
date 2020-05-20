package com.haiyi.utils;

import com.haiyi.pay.PayConfig;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonUtil;
import com.maizi.utils.LogUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class WXUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    // 临时票据API
    public static String TICKET_API = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    // 微信OPENID API
    public static String OPENID_API = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取用户的OpenId
     * @param code
     * @return
     */
    public static String takeOpenId(String code) {

        //开始
        LogUtils.logInfo("调用微信接口,获取Code[{}]对应的OpenId",code);

        StringBuilder openidBuilder = new StringBuilder();
        openidBuilder.append(OPENID_API);
        openidBuilder.append("?appid=").append(PayConfig.appId);
        openidBuilder.append("&secret=").append(PayConfig.appSecret);
        openidBuilder.append("&code=").append(code);
        openidBuilder.append("&grant_type=authorization_code");
        // 获取 OPENID
        String res = restTemplate.getForObject(openidBuilder.toString(), String.class);

        //响应
        LogUtils.logInfo("调用微信接口,获取Code[{}]对应的OpenId[{}]",code,res);
        Map<String, Object> map;
        try {
            map = JsonUtil.jsonToMap(res);
            return String.valueOf(map.get("openid"));
        } catch (KPException e) {
            LogUtils.logWarn("JSON装换Map异常",e);
            return null;
        }
    }

    public static String zx_takeOpenId(String code) {

        //开始
        LogUtils.logInfo("调用微信接口,获取Code[{}]对应的OpenId",code);

        StringBuilder openidBuilder = new StringBuilder();
        openidBuilder.append(OPENID_API);
        openidBuilder.append("?appid=").append(PayConfig.appId);
        openidBuilder.append("&secret=").append(PayConfig.appSecret);
        openidBuilder.append("&code=").append(code);
        openidBuilder.append("&grant_type=authorization_code");
        // 获取 OPENID
        String res = restTemplate.getForObject(openidBuilder.toString(), String.class);

        //响应
        LogUtils.logInfo("调用微信接口,获取Code[{}]对应的OpenId[{}]",code,res);
        Map<String, Object> map;
        try {
            map = JsonUtil.jsonToMap(res);
            return String.valueOf(map.get("openid"));
        } catch (KPException e) {
            LogUtils.logWarn("JSON装换Map异常",e);
            return null;
        }
    }


}
