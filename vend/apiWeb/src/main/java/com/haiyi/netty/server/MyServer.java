package com.haiyi.netty.server;


import com.haiyi.netty.code.PacketHandler;
import com.haiyi.netty.frame.Spliter;
import com.haiyi.netty.server.handler.*;
import com.haiyi.netty.server.handler.auth.AuthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Hello world!
 *
 */
public class MyServer {

	public MyServer() {

		ServerBootstrap serverBootstrap = new ServerBootstrap();

		// boss组
		EventLoopGroup bosserGroup = null;
		// work组
		EventLoopGroup workerGroup = null;
		bosserGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();

		// 设置boss和worker
		serverBootstrap.group(bosserGroup, workerGroup);

		// 指定IO模型
		serverBootstrap.channel(NioServerSocketChannel.class);

		// 这里定义的是每个连接的处理逻辑
		// NioServerSocketChannel 相当于是ServerSocket
		// NioSocketChannel相当于是Socket
		serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
			@Override
			protected void initChannel(NioSocketChannel ch) throws Exception {

				//空闲检测
				ch.pipeline().addLast(new IMIdleStateHandler());

				//添加拆包器,会屏蔽非本协议连接
				ch.pipeline().addLast(new Spliter());
				
				//注册解码器
				ch.pipeline().addLast(PacketHandler.getInstance());



				//注册登录handler
				ch.pipeline().addLast(LoginHandler.getInstance());

				//心跳包
				ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);

				//添加验证Handler
				ch.pipeline().addLast(new AuthHandler());


				//洗车结算handler
				ch.pipeline().addLast(CarWashCostHandle.getInstance());

				//余额推送handler
				ch.pipeline().addLast(BalanceHandle.getInstance());
				//泡沫状态handler
				ch.pipeline().addLast(FrothHandle.getInstance());
				//上报出货信息handler
				ch.pipeline().addLast(ReporteHandle.getInstance());
			}
		});

		// 监听服务端事件
		serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
			@Override
			protected void initChannel(NioServerSocketChannel ch) throws Exception {
				System.out.println("服务端启动中.....");
			}
		});

		// 设置NioSocketChannel属性
		// serverBootstrap.attr(AttributeKey.newInstance("serverName"),"nettyServer");

		// 设置每一条连接属性
		// serverBootstrap.childAttr(childKey, value)

		// 设置NioSocketChannel一些tcp属性
		serverBootstrap.option(ChannelOption.SO_BACKLOG,2018);

		// 设置每一条tcp属性
		//设置是否开启TCP底层心跳机制
		//serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
		//serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);

		// 启动
		serverBootstrap.bind(9999);
	}
}
