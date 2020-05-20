package com.haiyi.netty.server.handler.auth;

import com.haiyi.netty.attr.Attributes;
import com.haiyi.netty.packet.BuyPushResponsePacket;
import com.haiyi.netty.packet.LockResponsePacket;
import com.haiyi.netty.packet.MemberInfoResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
	private static final Map<String, Channel> devChannelMap = new ConcurrentHashMap<>();

	public static void bindSession(Session session, Channel channel) {
		//TOTO 验证
		devChannelMap.put(session.getDevNum(), channel);
		 
		channel.attr(Attributes.SESSION).set(session);
	}

	public static void unBindSession(Channel channel) {
		if (hasLogin(channel)) {
			devChannelMap.remove(getSession(channel).getDevNum());
			channel.attr(Attributes.SESSION).set(null);
		}
	}

	public static boolean hasLogin(Channel channel) {
		return channel.hasAttr(Attributes.SESSION);
	}

	public static Session getSession(Channel channel) {
		return channel.attr(Attributes.SESSION).get();
	}

	public static Channel getChannel(String devNum) {
		return devChannelMap.get(devNum);
	}



	/**
	 * 推送会员相关信息
	 * @param channel
	 */
	public static void dispatcherMemberInfo(Channel channel, MemberInfoResponsePacket memberInfoResponsePacket){
		channel.writeAndFlush(memberInfoResponsePacket);
	}
	/**
	 * 推送会员相关信息
	 * @param channel
	 */
	public static void dispatcherBuyInfo(Channel channel,  BuyPushResponsePacket buyPushResponsePacket){
		channel.writeAndFlush(buyPushResponsePacket);
	}
	/**
	 * 推送会员相关信息
	 * @param channel
	 */
	public static void dispatcherLockInfo(Channel channel, LockResponsePacket lockResponsePacket){
		channel.writeAndFlush(lockResponsePacket);
	}
}