package com.haiyi.utils;

import javax.servlet.http.HttpSession;

import com.haiyi.domain.User;
import com.haiyi.domain.base.LoginInfo;

public class AgentUtil {
	public static final String TYPE ="type";
	
	public static final String AGENT ="agent";
	public static final String USER ="user";
	
	/**
	 * 获取代理id
	 * @param session
	 * @return
	 */
	public static Integer getAgentId(HttpSession session){
		LoginInfo loginInfo = SessionUtil.getCurrentLoginInfo(session);
		if(AGENT.equals(loginInfo.getType())){
			return loginInfo.getId();
		}else{
			return ((User)loginInfo).getAgentId();
		}
	}

	/**
	 * 获取当前登录类型
	 * @param session
	 * @return
	 */
	public static String getLoginType(HttpSession session){
		return SessionUtil.getCurrentLoginInfo(session).getType();
	}
	
	/**
	 * 获取代理Name
	 * @param session
	 * @return
	 */
	public static String getAgentName(HttpSession session){
		LoginInfo loginInfo = SessionUtil.getCurrentLoginInfo(session);
		if(AGENT.equals(loginInfo.getType())){
			return loginInfo.getName();
		}else{
			return ((User)loginInfo).getAgentName();
		}
	}
}
