package com.haiyi.netty.server.handler;

import com.haiyi.domain.Dev;
import com.haiyi.netty.packet.LoginPacket;
import com.haiyi.netty.packet.LoginResponsePacket;
import com.haiyi.netty.server.handler.auth.Session;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.netty.utils.LoginUtil;
import com.haiyi.service.DevService;
import com.haiyi.utils.SpringUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.utils.LogUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginPacket>{

	private static final LoginHandler loginHandler = new LoginHandler();
	private LoginHandler() {
	}

	public static LoginHandler getInstance() {
		return loginHandler;
	}
	/**
	 * 当消息到达时
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginPacket loginPacket) throws Exception {
		LogUtils.logInfo("收到客户端[{}]登录请求",loginPacket.getDevNum());

		LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
		if(SessionUtil.getChannel(loginPacket.getDevNum())!=null){
			loginResponsePacket.setSuccess(false);
			//重复登录
			ctx.channel().writeAndFlush(loginResponsePacket);
			return;
		}
		Dev dev = login(loginPacket);
		if (dev!=null) {
			//保存Session
			Session session = new Session(loginPacket.getDevNum());
			SessionUtil.bindSession(session, ctx.channel());

			LoginUtil.markAsLogin(ctx.channel());

			Map<String,Object> data = new HashMap<>();

			//查找商品总数量
			//data.put("commodity",devService.productCount(dev.getId()));

			data.put("num",dev.getNum());
			data.put("token",dev.getToken());
			data.put("CLS",dev.getCSL());
			//购买速度
			/*data.put("speed1",dev.getSpeed1());
			//退货速度
			data.put("speed2",dev.getSpeed2());
			//购买脉冲
			data.put("KD",dev.getKD());
			//退货脉冲
			data.put("KD2",dev.getKD2());
			//感应退款金额


			//是否可退货(1为可退，2为不可退）
			data.put("TF",dev.getTF());
			//退货模式(0为高电平，1为代电平）
			data.put("TFType",dev.getTFType());
			//退货比例
			data.put("TBPercentage",dev.getTBPercentage());
			//购买比例
			data.put("TFMS",dev.getTFMS());
			//绑定商品数量
			data.put("commodity",dev.getCommodity());
			//支付类型
			data.put("tradeWay",dev.getTradeWay());*/
			loginResponsePacket.setData(data);
			DevService devService=SpringUtil.getBean("devServiceImpl");
			Dev dev1 = devService.findByNum(loginPacket.getDevNum());
			devService.updateDevStates(dev1.getId(), StatusConstant.DEV_STATUS_FREE);
			loginResponsePacket.setSuccess(true);
			LogUtils.logInfo("客户端[{}]登录成功",loginPacket.getDevNum());
		} else {
			loginResponsePacket.setSuccess(false);
			LogUtils.logInfo("客户端[{}]登录失败，账号密码校验失败",loginPacket.getDevNum());
		}
		// 登录响应
		ctx.channel().writeAndFlush(loginResponsePacket);
	}
	//用户掉线清除session
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SessionUtil.unBindSession(ctx.channel());
	}
	/**
	 * 设备登录
	 * @param loginPacket
	 * @return
	 */
	private Dev login(LoginPacket loginPacket) {
		DevService devService = SpringUtil.getBean("devServiceImpl");
		String token = loginPacket.getToken();
		String devNum = loginPacket.getDevNum();
		return devService.login(devNum,token);
	}
}
