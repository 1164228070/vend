package com.haiyi.state;

public class StateConfig {
    //微信支付（支付方式）
    public static final  Byte PAYTYPE_XW=1;
    //支付宝支付（支付方式）
    public static final  Byte PAYTYPE_ZFB=2;
    //中信微信支付（支付方式）
    public static final  Byte PAYTYPE_ZX_XW=3;
    //中信支付宝支付（支付方式）
    public static final  Byte PAYTYPE_ZX_ZFB=4;
    //消费（交易类型）
    public static final  Byte TRADETYPE_COMSUME=1;
    //充值（交易类型）
    public static final  Byte TRADETYPE_RECHARGE=2;
    //退款（交易类型）
    public static final  Byte TRADETYPE_REFUND=3;


    //成功（支付状态）
    public static final  Byte PAYSTATUS_SUCCESS=1;
    //异常（支付状态）
    public static final  Byte PAYSTATUS_UNUSUAL=2;
    //未支付（支付状态）
    public static final  Byte PAYSTATUS_UNGOING=3;
    //未完成（支付状态）
    public static final  Byte PAYSTATUS_UNDONE=4;



    //状态
    public static final Byte STATUS_ACTIVATE=1;
    public static final Byte STATUS_FREEZE=2;
    //状态
    public static final Byte TCP_REQUEST=1;
    public static final Byte HTTP_REQUEST=2;
}
