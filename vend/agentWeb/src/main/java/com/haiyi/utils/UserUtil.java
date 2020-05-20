package com.haiyi.utils;

import com.haiyi.domain.User;
import com.haiyi.domain.base.LoginInfo;

import javax.servlet.http.HttpSession;

public class UserUtil {
	public static final String TYPE ="type";
	
	public static final String AGENT ="agent";
	public static final String USER ="user";
	
	/**
	 * 获取代理id
	 * @param session
	 * @return
	 */
	public static Integer getUserId(HttpSession session){
		LoginInfo loginInfo = SessionUtil.getCurrentLoginInfo(session);
		if(USER.equals(loginInfo.getType())){
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
	public static String getUserName(HttpSession session){
		LoginInfo loginInfo = SessionUtil.getCurrentLoginInfo(session);
		if(USER.equals(loginInfo.getType())){
			return loginInfo.getName();
		}else{
			return ((User)loginInfo).getAgentName();
		}
	}
}
