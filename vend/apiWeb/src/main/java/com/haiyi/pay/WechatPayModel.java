package com.haiyi.pay;


import com.maizi.utils.StringUtil;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "XML")
public class WechatPayModel {

    private String appid;
    private String mch_id;
    private String nonce_str;
    private String body;
    private String out_trade_no;
    private String total_fee;
    private String spbill_create_ip;
    private String notify_url;
    private String trade_type;
    private String openid;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void sign(SignMD5 encoder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appid=").append(getAppid());
        stringBuilder.append("&body=").append(getBody());
        stringBuilder.append("&mch_id=").append(getMch_id());
        stringBuilder.append("&nonce_str=").append(getNonce_str());
        stringBuilder.append("&notify_url=").append(getNotify_url());
        if(!StringUtil.isEmpty(getOpenid())){
        	stringBuilder.append("&openid=").append(getOpenid());
        }
        stringBuilder.append("&out_trade_no=").append(getOut_trade_no());
        stringBuilder.append("&spbill_create_ip=").append(getSpbill_create_ip());
        stringBuilder.append("&total_fee=").append(getTotal_fee());
        stringBuilder.append("&trade_type=").append(getTrade_type());
        stringBuilder.append("&key=").append(Configure.getKey());
        this.sign = encoder.encode(stringBuilder.toString());
    }

    @Override
    public String toString() {
        return "<xml>" +
                "<appid><![CDATA[" + appid + "]]></appid>" +
                "<body><![CDATA[" + body + "]]></body>" +
                "<mch_id><![CDATA[" + mch_id + "]]></mch_id>" +
                "<nonce_str><![CDATA[" + nonce_str + "]]></nonce_str>" +
                "<notify_url><![CDATA[" + notify_url + "]]></notify_url>" +
                "<openid><![CDATA[" + openid + "]]></openid>" +
                "<out_trade_no><![CDATA[" + out_trade_no + "]]></out_trade_no>" +
                "<spbill_create_ip><![CDATA[" + spbill_create_ip + "]]></spbill_create_ip>" +
                "<trade_type><![CDATA[" + trade_type + "]]></trade_type>" +
                "<total_fee><![CDATA[" + total_fee + "]]></total_fee>" +
                "<sign><![CDATA[" + sign + "]]></sign>" +
                "</xml>";
    }
}