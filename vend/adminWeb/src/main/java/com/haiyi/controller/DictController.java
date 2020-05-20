package com.haiyi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Dict;
import com.haiyi.domain.DictVal;
import com.haiyi.query.DictQuery;
import com.haiyi.query.DictValQuery;
import com.haiyi.service.DictService;
import com.haiyi.service.DictValService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.StringUtil;

@Controller
@RequestMapping("/dicts")
@ControllerAnno(addUI = "/dicts/save", detailUI = "/dicts/detail", editUI = "/dicts/save", listUI = "/dicts/list")
public class DictController extends BaseController<Dict,DictQuery>{
	
	@Autowired
	DictValService dictValService;
	
	@Autowired
	public void setDictService(DictService dictService) {
		this.baseService = dictService;
	}

	@Override
	public Dict beforeSave(ModelMap modelMap, Dict t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		if(!StringUtil.isEmpty(id)){
			DictValQuery dictValQuery = new DictValQuery();
			dictValQuery.setDictId(Integer.parseInt(id));
			dictValQuery.setPagination(false);
			List<DictVal> dictVals =  dictValService.findBySelective(dictValQuery).getList();
			modelMap.addAttribute("dictVals", dictVals);
		}
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}