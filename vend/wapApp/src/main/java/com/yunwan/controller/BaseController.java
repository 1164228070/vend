package com.yunwan.controller;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yunwan.bean.BaseBean;
import com.yunwan.bean.JsonModel;
import com.yunwan.query.BaseQuery;
import com.yunwan.service.BaseService;

public class BaseController <T extends BaseBean,E extends BaseQuery>{
	@Autowired
	BaseService<T,E> baseService;

	@RequestMapping(value="",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel<T> query(E e){
		JsonModel<T> jsonModel = new JsonModel<T>();
		PageInfo<T> pageInfo = baseService.query(e);
		jsonModel.setSuccess(true);
		jsonModel.setRows(pageInfo.getList());
		jsonModel.setMsg("查找成功");
		jsonModel.setTotal(pageInfo.getTotal());
		return jsonModel;
	}
	

}
