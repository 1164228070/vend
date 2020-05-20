package com.haiyi.controller;

import com.haiyi.domain.Agent;
import com.haiyi.service.AgentService;
import com.haiyi.service.UserService;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/works")
public class WorkController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AgentService agentService;


	
	private final String CENTER_UI = "works/centerUI";
	private final String CHECK_INFO_UI = "works/checkInfoUI";
	 
	/**
	 * 工作台
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping(value="",method = RequestMethod.GET)
	@AuthAnno
	public String center(ModelMap modelMap,HttpSession session){
		return CENTER_UI;
	}

	


	@RequestMapping(value={"/checkInfo"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	@AuthAnno(verifyToken=false)
	public String checkInfo(ModelMap modelMap, HttpSession session)
	{
		//TODO
		//verifyStatus(session);

		return "works/checkInfoUI";
	}



	/**
	 * 判断代理商状态
	 * @param session
	 */
	private void verifyStatus(HttpSession session){
		Agent agent = (Agent) SessionUtil.getCurrentLoginInfo(session);
		if(StatusConstant.AGENT_STATUS_VERIFIED!=agent.getStatus()){
			throw new KPException(ExceptionKind.BUSINESS_E,"未审核通过,请联系运营商!");
		}
	}
}