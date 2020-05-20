package com.haiyi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.AccountLog;
import com.haiyi.query.AccountLogQuery;
import com.haiyi.service.AccountLogService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;

@Controller
@RequestMapping("/accountLogs")
@ControllerAnno(addUI = "/accountLogs/save", detailUI = "/accountLogs/detail", editUI = "/accountLogs/save", listUI = "/accountLogs/list")
public class AccountLogController extends BaseController<AccountLog,AccountLogQuery>{
	
	@Autowired
	public void setAccountLogService(AccountLogService accountLogService) {
		this.baseService = accountLogService;
	}

	@Override
	public AccountLog beforeSave(ModelMap modelMap, AccountLog t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}