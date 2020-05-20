package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.DictVal;
import com.haiyi.domain.RechargeLog;
import com.haiyi.query.RechargeLogQuery;
import com.haiyi.service.RechargeLogService;
import com.haiyi.utils.DictUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/rechargeLogs")
@ControllerAnno(addUI = "/rechargeLogs/save", detailUI = "/rechargeLogs/detail", editUI = "/rechargeLogs/save", listUI = "/rechargeLogs/list")
public class RechargeLogController extends BaseController<RechargeLog,RechargeLogQuery>{
	@Autowired
	public void setRechargeLogService(RechargeLogService rechargeLogService) {
		this.baseService = rechargeLogService;
	}

	
	@AuthAnno(verifyValid=false)
	@Override
	public String add(RechargeLog t, ModelMap modelMap,Errors errors) throws KPException {
		return null;
	}
	 
	 
	@AuthAnno(verifyValid=false)
	@Override
	public JsonModel ajaxDelete(String ids) {
		return null;
	}
	
	@AuthAnno(verifyValid=false)
	@Override
	public String edit(String id, ModelMap modelMap, RechargeLog t) throws KPException {
		return null;
	}

	@Override
	public RechargeLog beforeSave(ModelMap modelMap, RechargeLog t) throws KPException {
		return null;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}


	@RequestMapping(value={"selectStatus"},method= RequestMethod.POST)
	@AuthAnno(verifyLogin=true)
	public @ResponseBody
	JsonModel selectAgentId() throws KPException{
		List<DictVal> dictVals = DictUtil.getDictValListByType(15);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setData(dictVals);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
}