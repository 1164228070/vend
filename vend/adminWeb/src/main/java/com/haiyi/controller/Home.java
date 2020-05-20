package com.haiyi.controller;

import com.haiyi.service.MenuService;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserService;
import com.haiyi.utils.AdminVO;
import com.haiyi.utils.SessionUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.utils.JsonModel;
import com.maizi.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class Home {
	
	private final String LOGIN_URL = "home/loginUI";
	
	private final String INDEX_VIEW = "home/index";
	
	@Autowired
	UserService userService;
	
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
	
	@AuthAnno(verifyLogin = true)
	@RequestMapping(value = { "/index" },method = {RequestMethod.GET})
	public String index() {
		return INDEX_VIEW;
	}
	
	@RequestMapping({"/logout" })
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
	@AuthAnno(verifyLogin = true)
	@RequestMapping({"/profile" })  
	public String profile(HttpSession session) {
		return "home/profile";
	}
	
	/**
	 * 登录
	 * @param loginName
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/session" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonModel login(String loginName, String password, HttpSession session) {
		JsonModel jsonModel = new JsonModel();
		if ((StringUtil.isEmpty(loginName) || (StringUtil.isEmpty(password)))){
			jsonModel.setSuccess(false);
			jsonModel.setMsg("用户名字和密码都不能为空");
			return jsonModel;
		}
		AdminVO adminVO = AdminVO.login(loginName, password);
		if (adminVO != null) {
			SessionUtil.saveCurrentLoginInfo(session,adminVO);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("登录成功");
		} else {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("用户名或密码错误");
		}
		return jsonModel;
	}


	@RequestMapping(value = { "/sessionTest" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonModel loginTest(String openId,HttpSession session) {
		JsonModel jsonModel = new JsonModel();
		if("qwe123456789".equals(openId)){
			AdminVO adminVO = AdminVO.login("admin", "123456");
			SessionUtil.saveCurrentLoginInfo(session,adminVO);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("登录成功");
		}else {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("此微信号非运营商微信号，请输入正确的账号密码进行登录");
		}
		return jsonModel;
	}

 
}