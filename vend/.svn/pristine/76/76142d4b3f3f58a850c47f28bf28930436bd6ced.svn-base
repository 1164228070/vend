package com.haiyi.controller;

import java.util.Arrays;
import java.util.List;

import com.haiyi.domain.Agent;
import com.haiyi.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Role;
import com.haiyi.domain.User;
import com.haiyi.query.UserQuery;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserService;
import com.haiyi.utils.DictUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.MD5;
import com.maizi.utils.StringUtil;
  
@Controller
@RequestMapping("/users")
@ControllerAnno(addUI = "/users/save", detailUI = "/users/detail", editUI = "/users/save", listUI = "/users/list")
public class UserController extends BaseController<User,UserQuery>{
	
	@Autowired
	RoleService roleService;

	@Autowired
	AgentService agentService;


	@Autowired
	public void setUserService(UserService userService) {
		this.baseService = userService;
	}

	@Override
	public User beforeSave(ModelMap modelMap, User t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		List<Role> roles = roleService.findAll();
		modelMap.addAttribute("roles",roles);
		List<Agent> agents=agentService.findAll();
		System.out.println(agents);
		modelMap.addAttribute("agents",agents);
		//查找是否管理员字典
		modelMap.addAttribute("statusList",DictUtil.getDictVal());
		
		if(!StringUtil.isEmpty(id)){
			User user = (User) modelMap.get("user");
			List<Integer> roleIds = Arrays.asList(roleService.findIdsByUserId(Integer.parseInt(id)));
			for(Integer roleId : roleIds){
				user.getRoles().add(new Role(roleId));
			}
			for(Role role : roles){
				if(roleIds.contains(role.getId())){
					user.getRoles().add(role);
				}
			}
		}
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}