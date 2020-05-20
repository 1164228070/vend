package com.haiyi.netty.server.handler.auth;

import com.haiyi.netty.utils.LoginUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("---------AuthHandler-----------------------------------");
		if (!LoginUtil.hasLogin(ctx.channel())) {
			ctx.channel().close();
		} else {
			// 一行代码实现逻辑的删除
			ctx.pipeline().remove(this);
			super.channelRead(ctx, msg);
		}
	}



	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		if (LoginUtil.hasLogin(ctx.channel())) {
			System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
		} else {
			Channel channel = ctx.channel();
			System.out.println("无登录验证，强制关闭连接!状态:"+channel.isActive());
		}
	}




	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("逻辑处理器被添加：handlerAdded()");
		super.handlerAdded(ctx);
	}
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel 绑定到线程(NioEventLoop)：channelRegistered()");
		super.channelRegistered(ctx);
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel 准备就绪：channelActive()");
		super.channelActive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		System.out.println("channel 某次数据读完：channelReadComplete()");
		super.channelReadComplete(ctx);
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel 被关闭：channelInactive()");
		super.channelInactive(ctx);
	}
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		System.out.println("channel 取消线程(NioEventLoop) 的绑定:channelUnregistered()");
		super.channelUnregistered(ctx);
	}

}