package com.haiyi.pay;

public class AlipayResp {
    private String out_trade_no;     //我方订单号
    private String trade_no;         //支付宝订单号
    private String trade_status;     //状态
    private String total_fee;        //支付金额

    private String subject;          //商品名称\订单名称
    private String body;             //商品描述
    private String buyer_email;      //买家支付账号

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }


    @Override
    public String toString() {
        return "AlipayResp{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", trade_status='" + trade_status + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", buyer_email='" + buyer_email + '\'' +
                '}';
    }
}
