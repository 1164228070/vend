package com.haiyi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haiyi.domain.Agent;
import com.haiyi.domain.Menu;
import com.haiyi.service.AgentService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.MenuUtil;
import com.haiyi.utils.SessionUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import com.maizi.utils.RegexUtil;

@Controller
@RequestMapping("/agents")
public class AgentController{
	
	@Autowired
	AgentService agentService;
 
	/**
	 * 获取当期代理下的所有权限
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value={"/menus"},method=RequestMethod.GET)
	@AuthAnno(verifyToken=false)
	@ResponseBody
	public JsonModel queryAllMemus(HttpSession session){
		Integer agentId = AgentUtil.getAgentId(session);
		//获取当前代理下的所有权限
		List<Menu> mymenus = agentService.queryAllMemus(agentId);
		List<Menu> menus=new ArrayList<>();
		for (Menu menu:mymenus){
			menu.setURL(null);
			menus.add(menu);
		}

		//构造菜单树
		MenuUtil.buildMenuTree(menus);

		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setData(menus);
		return jsonModel;
	}

	/**
	 *修改代理名字
	 * @param memberId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value={"/name"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyToken=false)
	public @ResponseBody JsonModel updateName(@RequestParam(required=true) String name,HttpSession session){
		Integer agentId = SessionUtil.getCurrentLoginInfoId(session);
		 
		//记录修改密码
		LogUtils.logInfo("代理商[{}]修改名字",agentId);
		
		JsonModel jsonModel = new JsonModel();
		//TODO 判断name的规则
		Agent agent = new Agent();
		agent.setId(agentId);
		agent.setName(name);
		agentService.updateById(agent);
		SessionUtil.getCurrentLoginInfo(session).setName(name);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("修改成功!");
		return jsonModel;
	}
	
	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/password", method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(verifyToken=false)
	public @ResponseBody JsonModel resetPassword(HttpSession session,@RequestParam String oldPassword,@RequestParam String newPassword){
		
		Integer agentId = SessionUtil.getCurrentLoginInfo(session).getId();
		
		//记录修改密码
		LogUtils.logInfo("代理商[{}]修改密码",agentId);
		
		JsonModel jsonModel = new JsonModel();
		  
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
		Agent agent =  agentService.login(((Agent) SessionUtil.getCurrentLoginInfo(session)).getLoginName(),oldPassword);
		if(agent==null){
			jsonModel.setSuccess(false);
			jsonModel.setMsg("旧密码错误!");
			return jsonModel;
		}
		boolean sign = agentService.updatePassword(agentId,newPassword);
		jsonModel.setSuccess(sign);
		jsonModel.setMsg(sign?"重置成功":"重置密码失败");
		return jsonModel;
	}
}