package com.haiyi.netty.server.handler.auth;

public class Session {
	private String devNum;

	public String getDevNum() {
		return devNum;
	}

	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}

	public Session(String devNum) {
		this.devNum = devNum;
	}
}
