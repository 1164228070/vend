package com.haiyi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.DictVal;
import com.haiyi.query.DictValQuery;
import com.haiyi.service.DictValService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;

@Controller
@RequestMapping("/dictVals")
@ControllerAnno(addUI = "/dictVals/save", detailUI = "/dictVals/detail", editUI = "/dictVals/save", listUI = "/dictVals/list")
public class DictValController extends BaseController<DictVal,DictValQuery>{
	
	@Autowired
	public void setDictValService(DictValService dictValService) {
		this.baseService = dictValService;
	}

	@Override
	public DictVal beforeSave(ModelMap modelMap, DictVal t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
	//TODO 
	//编辑和添加和列表需要隐藏都不需要
}