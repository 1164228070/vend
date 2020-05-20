package com.haiyi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.AccountLog;
import com.haiyi.query.AccountLogQuery;
import com.haiyi.service.AccountLogService;
import com.haiyi.utils.SessionUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;

@Controller
@RequestMapping("/accountLogs")
public class AccountLogController {
	
	@Autowired
	AccountLogService accountLogoService;
	
	/**
	 * 返回消费记录
	 * @param accountLogQuery
	 * @param session
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel listAccountLog(AccountLogQuery accountLogQuery,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		accountLogQuery.setMemberId(SessionUtil.getCurrentLoginInfoId(session));
		PageInfo<AccountLog> accountLogs = accountLogoService.findBySelective(accountLogQuery);
		jsonModel.setData(accountLogs.getList());
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	
	/**
	 * 查看账单明细
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel listAccountLog(@PathVariable String id,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		AccountLog accountLog = accountLogoService.findById(id);
		if(!accountLog.getMemberId().equals(SessionUtil.getCurrentLoginInfoId(session))){
			throw new KPException(ExceptionKind.PARAM_E,"账单参数错误");
		}
		jsonModel.setData(accountLog);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
}