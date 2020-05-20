package com.haiyi.netty.packet;


import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

/**
   * 登录数据包
 * @author Administrator
 *
 */
public class LoginPacket extends BasePacket {
	
	private String devNum;   //设备编号
	
	private String token; //设备token

	public String getDevNum() {
		return devNum;
	}

	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Byte getCommand() {
		return Command.LOGIN_REQUEST;
	}
}