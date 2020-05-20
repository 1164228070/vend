package com.haiyi.netty.packet;


import com.haiyi.netty.packet.util.BasePacket;
import com.haiyi.netty.packet.util.Command;

/**
 * 心跳请求数据包
 * @author Administrator
 *
 */
public class HeartBeatRequestPacket extends BasePacket {
	 
	@Override
	public Byte getCommand() {
		return Command.HEAT_BEAN_REQUEST;
	}
}