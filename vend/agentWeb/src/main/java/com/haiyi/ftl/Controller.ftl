package com.haiyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.haiyi.anno.ControllerAnno;
import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.${className};
import com.haiyi.exception.KPException;
import com.haiyi.query.${className}Query;
import com.haiyi.service.${className}Service;

@Controller
@RequestMapping("/${req}")
@ControllerAnno(addUI = "/${req}/save", detailUI = "/${req}/detail", editUI = "/${req}/save", listUI = "/${req}/list")
public class ${className}Controller extends BaseController<${className},${className}Query>{
	
	@Autowired
	public void set${className}Service(${className}Service ${smallClassName}Service) {
		this.baseService = ${smallClassName}Service;
	}

	@Override
	public ${className} beforeSave(ModelMap modelMap, ${className} t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}