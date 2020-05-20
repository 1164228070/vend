package com.haiyi.service;

import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.Dev;
import com.haiyi.domain.Member;
import com.haiyi.domain.User;

import java.math.BigDecimal;

public interface ApiService {
/*	*//**
	 * 上传退货记录
	 * 方法用途: <br>
	 *//*
	public Integer upload(Member member,String queryType,BigDecimal number,ComsumeLog comsumelog,Product product);*/
	/**
	 * 充值
	 * 方法用途: <br>
	 */
	public Integer proceeds(Member member, String queryType, BigDecimal number,ComsumeLog comsumeLog,User user);
	 
/*	*//**
	 * 上传购买记录
	 * 方法用途: <br>
	 * @param member
	 * @param queryType
	 * @param number
	 * @param comsumeLog
	 *//*
	public Integer buy(Member member, String queryType, BigDecimal number,ComsumeLog comsumeLog,Product product);*/
	
	/**
	 * 退款
	 * 方法用途: <br>
	 * @param member
	 * @param queryType
	 * @param number
	 * @param comsumeLog
	 */
	public Integer refund(Member member, String queryType, BigDecimal number,ComsumeLog comsumeLog,User user);
	
	
	public Dev validateToken(Integer token,Integer devNum);
	
	
	public Dev validateDevNum(Integer devNum);

	public Integer pay(ComsumeLog comsumeLog);
}
