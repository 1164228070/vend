package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.InsideComsumeService;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/insideComsumes")
@ControllerAnno(addUI = "/insideComsumes/save", detailUI = "/insideComsumes/detail", editUI = "/insideComsumes/save", listUI = "/insideComsumes/list")
public class InsideComsumeController extends BaseController<InsideComsume,InsideComsumeQuery>{

	@Autowired
	InsideComsumeService insideComsumeService;

	@Autowired
	public void setInsideComsumeService(InsideComsumeService insideComsumeService) {
		this.baseService = insideComsumeService;
	}

	
	@AuthAnno(verifyValid=false)
	@Override
	public String add(InsideComsume t, ModelMap modelMap,Errors errors) throws KPException {
		return null;
	}
	 
	 
	@AuthAnno(verifyValid=false)
	@Override
	public JsonModel ajaxDelete(String ids) {
		return null;
	}
	
	@AuthAnno(verifyValid=false)
	@Override
	public String edit(String id, ModelMap modelMap, InsideComsume t) throws KPException {
		return null;
	}

	@Override
	public InsideComsume beforeSave(ModelMap modelMap, InsideComsume t) throws KPException {
		return null;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}

	@RequestMapping(value={"/toInsideComsumes/{orderId}"}, method={RequestMethod.GET})
	public String toInsideComsumes(@PathVariable String orderId, Model model){
		System.out.println(orderId);
		model.addAttribute("orderId",orderId);
		return "/insideComsumes/list";
	}



}