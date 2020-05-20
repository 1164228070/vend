package com.haiyi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.CountingLog;
import com.haiyi.query.CountingLogQuery;
import com.haiyi.service.CountingLogService;
import com.haiyi.utils.AgentUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;

@Controller
@RequestMapping("/countingLogs")
@ControllerAnno(addUI = "", detailUI = "", editUI = "", listUI = "/countingLogs/list")
public class CountingLogController extends BaseController<CountingLog,CountingLogQuery>{
	
	@Autowired
	public void setCountingLogService(CountingLogService comsumeLogService) {
		this.baseService = comsumeLogService;
	}
	
	@Override
	public Map<String, Object> list(CountingLogQuery e, HttpServletRequest request) throws KPException {
		e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		return super.list(e, request);
	}

	@AuthAnno(verifyValid=false)
	@Override
	public String add(CountingLog t, ModelMap modelMap) throws KPException {
		return null;
	}
	 
	@AuthAnno(verifyValid=false)
	@Override
	public JsonModel delete(String ids) {
		return null;
	}
	
	@AuthAnno(verifyValid=false)
	@Override
	public String edit(String id, ModelMap modelMap, CountingLog t) throws KPException {
		return null;
	}

	@Override
	public CountingLog beforeSave(ModelMap modelMap, CountingLog t) throws KPException {
		return null;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}