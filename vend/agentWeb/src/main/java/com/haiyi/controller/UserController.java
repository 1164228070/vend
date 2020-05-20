package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Agent;
import com.haiyi.domain.Role;
import com.haiyi.domain.User;
import com.haiyi.query.RoleQuery;
import com.haiyi.query.UserQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.DictUtil;
import com.haiyi.utils.RequestUtil;
import com.haiyi.utils.SessionUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ClassInfoAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
@ControllerAnno(addUI = "/users/save", detailUI = "/users/detail", editUI = "/users/save", listUI = "/users/list")
@ClassInfoAnno(msg = "员工")
public class UserController extends BaseController<User,UserQuery>{

	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	@Autowired
	AgentService agentService;

	@Autowired
	public void setUserService(UserService userService) {
		this.baseService = userService;
	}

	@Override
	public User beforeSave(ModelMap modelMap, User t) throws KPException {
		if(t.getId()==null){ //添加
			t.setAgentId(AgentUtil.getAgentId(RequestUtil.getRequest().getSession()));
			t.setAgentName(AgentUtil.getAgentName(RequestUtil.getRequest().getSession()));
		}else{
			t.setAgentId(AgentUtil.getAgentId(RequestUtil.getRequest().getSession()));
		}
		return t;
	}

	@Override
	public Map<String, Object> list(UserQuery userQuery, HttpServletRequest request) throws KPException {
		userQuery.setAgentId(AgentUtil.getAgentId(request.getSession()));
		return super.list(userQuery, request);
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Agent agent1 = agentService.findById(AgentUtil.getAgentId(request.getSession()) + "");
		modelMap.addAttribute("agentRate",agent1.getRate());
		/*User user1=userService.findById(id);
		Agent agent1=agentService.findById(user1.getAgentId()+"");
		modelMap.addAttribute("agentRate", agent1.getRate());*/
		RoleQuery roleQuery=new RoleQuery();
		roleQuery.setAgentId(AgentUtil.getAgentId(request.getSession()));
		List<Role> roles = roleService.findBySelective(roleQuery).getList();
		modelMap.addAttribute("roles",roles);

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

	/**
	 *修改用户状态
	 * @param memberId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value={"/{userId}/status"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel updateStatus(@PathVariable("userId") Integer userId,@RequestParam(required=true) String sign){
		JsonModel jsonModel = new JsonModel();
		if("y".equals(sign)){
			jsonModel.setSuccess(((UserService)this.baseService).activeStatus(userId,AgentUtil.getAgentId(RequestUtil.getRequest().getSession())));
			jsonModel.setMsg(ConstantUtil.ACTIVE_SUCCESS);
		}else if("n".equals(sign)){
			jsonModel.setSuccess(((UserService)this.baseService).deactivateStatus(userId,AgentUtil.getAgentId(RequestUtil.getRequest().getSession())));
			jsonModel.setMsg(ConstantUtil.DEACTIVATE_SUCCESS);
		}
		return jsonModel;
	}

	/**
	 *修改用户名字
	 * @param memberId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value={"/name"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyToken=false)
	public @ResponseBody JsonModel updateName(@RequestParam(required=true) String name,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		Integer userId = SessionUtil.getCurrentLoginInfoId(session);
		//TODO 判断name的规则
		((UserService)this.baseService).updateName(AgentUtil.getAgentId(session),userId,name);
		SessionUtil.getCurrentLoginInfo(session).setName(name);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("修改成功!");
		return jsonModel;
	}

	/**
	 * 重置密码
	 * @param userId
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/password", method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyToken=false)
	public @ResponseBody JsonModel resetPassword(HttpSession session,@RequestParam String oldPassword,@RequestParam String newPassword){
		JsonModel jsonModel = new JsonModel();

		Integer userId = SessionUtil.getCurrentLoginInfo(session).getId();

		//判断密码参数合法性
		if(!RegexUtil.password(oldPassword) || !RegexUtil.password(newPassword)){
			jsonModel.setMsg("密码由[a-zA-Z0-9_.]组成的6到10位!");
			return jsonModel;
		}

		if(oldPassword.equals(newPassword)){
			jsonModel.setSuccess(false);
			jsonModel.setMsg("新密码不能和旧密码相同!");
			return jsonModel;
		}
		User user =  ((UserService)this.baseService).login(((User) SessionUtil.getCurrentLoginInfo(session)).getLoginName(),oldPassword);
		if(user==null){
			jsonModel.setSuccess(false);
			jsonModel.setMsg("旧密码错误!");
			return jsonModel;
		}
		boolean sign = ((UserService)this.baseService).updatePassword(userId,newPassword,AgentUtil.getAgentId(session));
		jsonModel.setSuccess(sign);
		jsonModel.setMsg(sign?"重置成功":"重置密码失败");
		return jsonModel;
	}

	/**
	 * 重置员工密码
	 * @param userId
	 * @return
	 */
	@RequestMapping(value={"/{id}/initiation/password"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel initiationPassword(@PathVariable("id") Integer userId,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		((UserService)(this.baseService)).updatePassword(userId,ConstantUtil.DEFAULT_PASSWORD_MW,AgentUtil.getAgentId(session));
		jsonModel.setSuccess(true);
		jsonModel.setMsg("重置密码成功");
		return jsonModel;
	}

	@RequestMapping(value={"/updateRate/{ids}"}, method={RequestMethod.PUT})
	@AuthAnno
	public @ResponseBody JsonModel UpateRate(@PathVariable("ids") String ids,String rate){
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			int result=0;
			if (id.length > 1) {
				// 批量删除
				for(String newId : id){
					result = userService.updateRateById(new BigDecimal(rate),newId);
					result=result+1;
				}
			} else {
				//单个删除
				result = userService.updateRateById(new BigDecimal(rate),ids);
			}
			if(result==0){
				jsonModel.setSuccess(false);
				jsonModel.setMsg("更新失败");
			}else{
				jsonModel.setSuccess(true);
				jsonModel.setMsg("成功更新" + result + "条记录");
			}
		} catch (Exception e) {
			LogUtils.logError(e.toString());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("更新费率失败,请重新刷新数据再更新");
		}
		return jsonModel;
	}


	@RequestMapping("/toUpdateRate/{ids}")
	@AuthAnno
	public String toUpdateRate(@PathVariable("ids") String ids, Model model, HttpServletRequest request){
		model.addAttribute("ids",ids);
		Agent agent = agentService.findById(AgentUtil.getAgentId(request.getSession()) + "");
		model.addAttribute("agentRate",agent.getRate());
		return "/users/updateRate";
	}

}