package com.haiyi.controller;

import com.haiyi.domain.Agent;
import com.haiyi.domain.Menu;
import com.haiyi.domain.User;
import com.haiyi.domain.base.LoginInfo;
import com.haiyi.service.*;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.anno.AuthAnno;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.RegexUtil;
import com.maizi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("")
public class Home {

	private final String LOGIN_URL = "home/loginUI";

	private final String LOGIN_USER_URL = "users/loginUI";  //员工登录入口

	private final String INDEX_VIEW = "home/index";

	@Autowired
	AgentService agentService;

	@Autowired
	UserService userService;

	@Autowired
	ComsumeLogService comsumeLogService;



	@Autowired
	DevService devService;

	@Autowired
	MenuService menuService;

	@Autowired
	RoleService roleService;

	@RequestMapping(value = {"session/new"},method = {RequestMethod.GET})
	public String loginUI(String openId, Model model) {
		if(openId!=null){
			model.addAttribute("openId",openId);
		}
		return LOGIN_URL;
	}

	/**
	 * 员工登录入口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"session/user/new"},method = {RequestMethod.GET})
	public String loginUserUI(String openId, Model model) {
		if(openId!=null){
			model.addAttribute("openId",openId);
		}
		return LOGIN_USER_URL;
	}

	@AuthAnno(verifyToken=false)
	@RequestMapping(value = { "/index" },method = {RequestMethod.GET})
	public String index(ModelMap modelMap,HttpSession session) {

		//今日订单数据(完成订单)
		Map<String,Object> orderParam = new HashMap<String,Object>();
		orderParam.put("agentId",AgentUtil.getAgentId(session));
		orderParam.put("payStatus",StatusConstant.PAY_STATUS_FINISH);
		orderParam.put("createDate",DateUtil.dateToString(DateUtil.getCurrentDate(),"yyyy-MM-dd"));
		int orderCount = comsumeLogService.count(orderParam);



		//会员数
		Map<String,Object> memberParam = new HashMap<String,Object>();
		memberParam.put("agentId",AgentUtil.getAgentId(session));
		memberParam.put("status",StatusConstant.MEMBER_STATUS_ACTIVE);

		//异常设备
		Map<String,Object> devParam = new HashMap<String,Object>();
		devParam.put("agentId",AgentUtil.getAgentId(session));
		devParam.put("status",StatusConstant.DEV_STATUS_OFFLINE);
		int devCount = devService.count(devParam);

		modelMap.put("orderCount", orderCount);
		modelMap.put("devCount", devCount);
		return INDEX_VIEW;
	}

	@RequestMapping({"/logout"})
	public String logout(HttpSession session) {
		SessionUtil.removeCurrentLoginInfo(session);
		session.invalidate();
		return "redirect:/session/new";
	}

	/**
	 * 跳转个人中心界面
	 * @param session
	 * @return
	 */
	@RequestMapping({"/profile" })
	@AuthAnno(verifyToken=false)
	public String profile(HttpSession session,Model model) {
		String type = AgentUtil.getLoginType(session);
		LoginInfo loginInfo = SessionUtil.getCurrentLoginInfo(session);
		if(AgentUtil.AGENT.equals(type)){
			Agent info = agentService.findById(loginInfo.getId() + "");
			model.addAttribute("info",info);
			return "agents/profile";
		}else{
			User info = userService.findById(loginInfo.getId() + "");
			model.addAttribute("info",info);
			return "users/profile";
		}
	}

	/**
	 * 登录
	 * @param loginName
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/session" }, method = { RequestMethod.POST})
	@ResponseBody
	public JsonModel login(String loginName, String password,String type,String openId, HttpSession session) {
		JsonModel jsonModel = new JsonModel();
		LoginInfo loginInfo = null;
		Map<String,String> map=new HashMap<String,String>();
		System.out.println("0000000000000000==="+openId);
		if(openId!=null && openId !=""){
			System.out.println("444444444444======="+openId);
			if ((StringUtil.isEmpty(loginName) && (StringUtil.isEmpty(password)))){
				if(AgentUtil.AGENT.equals(type)){//代理登陆
					System.out.println("333333333333==="+openId);
					Agent agent = agentService.findByOpenId(openId);
					if(agent!=null){
						loginInfo = agent;
					}else {
						map.put("openId",openId);
						jsonModel.setData(map);
						jsonModel.setSuccess(false);
						jsonModel.setMsg("此微信号未绑定代理商，请先通过用户名密码进行登录");
						return jsonModel;
					}

				}else{//员工登陆
					User user = userService.findByOpenId(openId);
					if(user!=null){
						loginInfo = user;
					}else {
						System.out.println("222222222222222==============="+openId);
						map.put("openId",openId);
						jsonModel.setData(map);
						jsonModel.setSuccess(false);
						jsonModel.setMsg("此微信号未绑定商户，请先通过用户名密码进行登录");
						return jsonModel;
					}
				}
			}else {
				System.out.println("11111111111111==============="+openId);
				if ((StringUtil.isEmpty(loginName) || (StringUtil.isEmpty(password)))){
					jsonModel.setMsg("用户名字和密码都不能为空");
					return jsonModel;
				}

				if(!RegexUtil.password(password)){
					jsonModel.setMsg("密码由[a-zA-Z0-9_.]组成的6到10位!");
					return jsonModel;
				}
				if(AgentUtil.AGENT.equals(type)){//代理登陆
					Agent agent=agentService.login(loginName,password);
					agentService.updateOpenIdById(openId,agent.getId()+"");
					loginInfo = agent;
				}else{//员工登陆
					User user = userService.login(loginName,password);
					userService.updateOpenIdById(openId,user.getId()+"");
					loginInfo = user;
				}
			}




		}else {
			if ((StringUtil.isEmpty(loginName) || (StringUtil.isEmpty(password)))){
				jsonModel.setMsg("用户名字和密码都不能为空");
				return jsonModel;
			}

			if(!RegexUtil.password(password)){
				jsonModel.setMsg("密码由[a-zA-Z0-9_.]组成的6到10位!");
				return jsonModel;
			}


			if(AgentUtil.AGENT.equals(type)){//代理登陆
				loginInfo = agentService.login(loginName,password);
			}else{//员工登陆
				loginInfo = userService.login(loginName,password);
			}
		}
		if (loginInfo != null) {
			Integer agentId= loginInfo.getId();
			if(AgentUtil.AGENT.equals(type)){//代理登陆

				if(StatusConstant.AGENT_STATUS_VERIFIED!=loginInfo.getStatus()){
					jsonModel.setSuccess(false);
					jsonModel.setMsg("登录失败[审核未通过]");
					return jsonModel;
				}
				loginInfo.setType(AgentUtil.AGENT);
				//加载当前代理的权限
				loginInfo.setMenus(agentService.queryAllMemus(agentId));
			}else{//员工登陆

				if(StatusConstant.USER_STATUS_DEACTIVATE==loginInfo.getStatus()){
					jsonModel.setSuccess(false);
					jsonModel.setMsg("登录失败[未激活]");
					return jsonModel;
				}

				loginInfo.setType(AgentUtil.USER);
				//加载当前员工的权限
				Integer [] roleIds = roleService.findIdsByUserId(loginInfo.getId());
				if(roleIds.length>0){
					List<Menu> menus = menuService.findByRoleIds(roleIds);
					loginInfo.setMenus(menus);
				}
			}
			SessionUtil.saveCurrentLoginInfo(session,loginInfo);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("登录成功");
		} else {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("用户名或密码错误");
		}
		return jsonModel;
	}





}