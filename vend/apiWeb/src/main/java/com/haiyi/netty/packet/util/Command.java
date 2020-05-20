package com.haiyi.netty.packet.util;

import com.haiyi.netty.packet.*;

public class Command {
	//登录请求
	public static final Byte LOGIN_REQUEST = 1;
	//登录响应
	public static final Byte LOGIN_RESPONSE = 2;

	//心跳包
	public static final Byte HEAT_BEAN_REQUEST = 3;
	public static final Byte HEAT_BEAN_RESPONSE = 4;

	//会员信息
	public static final Byte MEMBER_INFO_REQUEST = 5;
	public static final Byte MEMBER_INFO_RESPONSE = 6;

	//洗车结算
	public static final Byte CARWASH_COST_REQUEST = 7;
	public static final Byte CARWASH_COST_RESPONSE = 8;

	//余额信息
	public static final Byte BALANCE_INFO_REQUEST = 9;
	public static final Byte BALANCE_INFO_RESPONSE = 10;

	//锁
	public static final Byte LOCK_REQUEST = 11;
	public static final Byte LOCK_RESPONSE = 12;

	//泡沫状态
	public static final Byte FROTH_REQUEST = 13;
	public static final Byte FROTH_RESPONSE = 14;
	//售货机购买推送
	public static final Byte BUYPUSH_REQUEST = 15;
	public static final Byte BUYPUSH_RESPONSE = 16;

	//上报出货信息
	public static final Byte REPORTE_REQUEST = 17;
	public static final Byte REPORTE_RESPONSE = 18;







	public static Class<? extends BasePacket> getRequestType(Byte command){
		if (command.byteValue()==LOGIN_REQUEST) {
			return LoginPacket.class;
		}else if(command.byteValue()==LOGIN_RESPONSE){
			return LoginResponsePacket.class;
		}else if(command.byteValue()==HEAT_BEAN_REQUEST){
			return HeartBeatRequestPacket.class;
		}else if(command.byteValue()==HEAT_BEAN_RESPONSE){
			return HeartBeatResponsePacket.class;
		}else if(command.byteValue()==MEMBER_INFO_RESPONSE){
			return MemberInfoResponsePacket.class;
		}else if(command.byteValue()==CARWASH_COST_REQUEST){
			return CarWashCostPacket.class;
		}else if(command.byteValue()==CARWASH_COST_RESPONSE){
			return CarWashCostResponsePacket.class;
		}else if(command.byteValue()==BALANCE_INFO_REQUEST){
			return BalancePacket.class;
		}else if(command.byteValue()==BALANCE_INFO_RESPONSE){
			return BalanceResponsePacket.class;
		}else if(command.byteValue()==LOCK_RESPONSE){
			return LockResponsePacket.class;
		}else if(command.byteValue()==FROTH_REQUEST){
			return FrothRequestPacket.class;
		}else if(command.byteValue()==FROTH_RESPONSE){
			return FrothResponsePacket.class;
		}else if(command.byteValue()==REPORTE_REQUEST){
			return ReportePacket.class;
		}else if(command.byteValue()==REPORTE_RESPONSE){
			return ReporteResponsePacket.class;
		}else {
			return null;
		}
	}
}