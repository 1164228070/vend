package com.haiyi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.AgentRole;
import com.haiyi.query.AgentRoleQuery;
import com.haiyi.service.AgentRoleService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;

@Controller
@RequestMapping("/agentRoles")
@ControllerAnno(addUI = "/agentRoles/save", detailUI = "/agentRoles/detail", editUI = "/agentRoles/save", listUI = "/agentRoles/list")
public class AgentRoleController extends BaseController<AgentRole,AgentRoleQuery>{
	
	@Autowired
	public void setAgentRoleService(AgentRoleService agentRoleService) {
		this.baseService = agentRoleService;
	}

	@Override
	public AgentRole beforeSave(ModelMap modelMap, AgentRole t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
	
	/**
	 * 获取指定角色的权限信息
	 * @param agentRoleId
	 * @return
	 */
    @RequestMapping(value="/{agentRoleId}/menus",method = RequestMethod.POST)
	public @ResponseBody JsonModel roleMenuIds(@PathVariable Integer agentRoleId){
		JsonModel jsonModel = new JsonModel();
		List<Integer> result = ((AgentRoleService)this.baseService).findMenuIds(agentRoleId);
		jsonModel.setData(result);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	
	/**
	 * 授权
	 * @param agentRoleId
	 * @param ids
	 * @return
	 * @throws KPException
	 */
    @RequestMapping(value="/{agentRoleId}/menus",method = RequestMethod.PUT)
	public @ResponseBody JsonModel grant(@PathVariable Integer agentRoleId,Integer [] menuIds) throws KPException{
    	if(agentRoleId==null){
    		throw new KPException(ExceptionKind.PARAM_E);
    	}
		JsonModel jsonModel = new JsonModel();
		//代理角色和菜单绑定关系
		((AgentRoleService)this.baseService).updateAuth(agentRoleId,menuIds);	
		jsonModel.setMsg("成功赋值权限");
		jsonModel.setSuccess(true);
		return jsonModel;
	}
}