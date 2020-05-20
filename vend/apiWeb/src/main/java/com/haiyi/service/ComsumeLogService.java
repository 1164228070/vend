package com.haiyi.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.pay.AlipayResp;
import com.haiyi.query.ComsumeLogQuery;
import com.maizi.exception.KPException;

public interface ComsumeLogService{
	PageInfo<ComsumeLog> findBySelective(ComsumeLogQuery e);
	
	ComsumeLog findByOrderId(String orderId);

    
    //更新订单状态
    int updateStatus(String orderId,Byte payStatus) throws KPException;

	int update(ComsumeLog comsumeLog);
	int add(ComsumeLog comsumeLog);

    //Map<String,Object> unifiedorder(String memberId, Integer[] referes,Integer[] counts,String devNum) throws KPException;

    //----------------------------------支付回调-----------------------------------//
    String nofificationWXPay(Map<String,String> notifyParam);
    String nofificationAliPay(Map<String,String> params, AlipayResp alipayResp);
    String nofificationZX_WXPay(Map<String,String> notifyParam);
}