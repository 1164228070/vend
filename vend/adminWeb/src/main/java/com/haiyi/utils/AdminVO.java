package com.haiyi.utils;

import com.haiyi.domain.base.LoginInfo;

public final class AdminVO extends LoginInfo{
	public static final String LOGIN_NAME="admin";
	public static final String PASSWORD="123456";

	public AdminVO(String loginName,String password){
		this.loginName =  loginName;
		this.password = password;
	}
	
	public static AdminVO login(String loginName,String password){
		if(LOGIN_NAME.equals(loginName) && PASSWORD.equals(password)){
			return new AdminVO(loginName,password);
		}else{
			return null;
		}
	}




}
