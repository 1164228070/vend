package com.haiyi.netty.code;

import java.util.List;

import com.haiyi.netty.packet.util.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

@ChannelHandler.Sharable
public class PacketHandler extends MessageToMessageCodec<ByteBuf, BasePacket> {
	private static final PacketHandler INSTANCE = new PacketHandler();

	private PacketHandler() {
	}
	
	public static PacketHandler getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
		out.add(CodeUtil.decode(byteBuf));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, BasePacket packet, List<Object> out) {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
		CodeUtil.encode(byteBuf, packet);
		out.add(byteBuf);
	}
}
