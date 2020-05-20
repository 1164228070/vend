package com.haiyi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Role;
import com.haiyi.query.RoleQuery;
import com.haiyi.service.MenuService;
import com.haiyi.service.RoleService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.RequestUtil;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;

@Controller
@RequestMapping("/roles")
@ControllerAnno(addUI = "/roles/save", detailUI = "/roles/detail", editUI = "/roles/save", listUI = "/roles/list")
public class RoleController extends BaseController<Role,RoleQuery>{
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.baseService = roleService;
	}
	
	@Autowired
	MenuService menuService;
	
	@Override
	public Map<String, Object> list(RoleQuery e, HttpServletRequest request) throws KPException {
		e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		return super.list(e, request);
	}
	

	/**
	 * 
	 * @Title: menuService 
	 * @Description:    获取指定角色的权限信息
	 * @param @param roleId
	 * @param @return     
	 * @return JsonModel     
	 * @throws
	 */
    @RequestMapping(value="/{roleId}/menus",method = RequestMethod.POST)
	public @ResponseBody JsonModel roleMenuIds(@PathVariable String roleId){
		JsonModel jsonModel = new JsonModel();
		List<Integer> result = menuService.findByRole(Integer.parseInt(roleId));
		System.out.println(result);
		jsonModel.setData(result);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	
	
	/**
	 * 授权
	 * @param roleId
	 * @param ids
	 * @return
	 * @throws KPException
	 */
    @RequestMapping(value="/{roleId}/menus",method = RequestMethod.PUT)
	public @ResponseBody JsonModel grant(@PathVariable Integer roleId,Integer [] menuIds) throws KPException{
    	if(roleId==null){
    		throw new KPException(ExceptionKind.PARAM_E);
    	}
		JsonModel jsonModel = new JsonModel();
		menuService.updateAuth(roleId,menuIds);
		jsonModel.setMsg("成功赋值权限");
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	

	@Override
	public Role beforeSave(ModelMap modelMap, Role t) throws KPException {
		if(t.getId()==null){
			t.setAgentId(AgentUtil.getAgentId(RequestUtil.getRequest().getSession()));
		}
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}