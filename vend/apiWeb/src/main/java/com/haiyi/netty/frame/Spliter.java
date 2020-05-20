package com.haiyi.netty.frame;
import com.haiyi.netty.code.CodeUtil;
import com.maizi.utils.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 拆包器
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

	private static final int LENGTH_FIELD_OFFSET = 7;
	private static final int LENGTH_FIELD_LENGTH = 4;
 
	public Spliter() {
		super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// 屏蔽非本协议的客户端
		int result = in.getInt(in.readerIndex());
		LogUtils.logInfo("拆包器,拆包数据数据大小[{}]",result);

		if (result != CodeUtil.MAGIC_NUMBER) {
			LogUtils.logInfo("连接被强制关闭");
			ctx.channel().close();
			return null;
		}
		return super.decode(ctx, in);
	}
}
