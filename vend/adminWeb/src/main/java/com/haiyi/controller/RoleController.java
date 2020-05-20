package com.haiyi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.Role;
import com.haiyi.query.RoleQuery;
import com.haiyi.service.RoleService;
import com.haiyi.utils.SysConfigUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.KPException;

@Controller
@RequestMapping("/roles")
public class RoleController{
	
	@Autowired
    RoleService roleServcie;
	 
	/**
	 * 分页查询[返回JSON]
	 * @param e
	 * @param request
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="", method=RequestMethod.GET, headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyLogin=true)
	public @ResponseBody Map<String,Object> list(RoleQuery e, HttpServletRequest request) throws KPException{
		Map<String,Object> result = new HashMap<String,Object>();
		PageInfo<Role> results = roleServcie.findBySelective(e);
		result.put("success", true);
		result.put("msg","查询角色成功");  
		result.put(SysConfigUtil.getValue("json-total")+"", results.getTotal());
		result.put(SysConfigUtil.getValue("json-data")+"", results.getList());
		return result;  
	}
	
	/**
	 * 跳转显示页面
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	@AuthAnno(verifyLogin=true)
    public String list(HttpServletRequest request,ModelMap modelMap) throws KPException{
		return "/roles/list";
	}
}