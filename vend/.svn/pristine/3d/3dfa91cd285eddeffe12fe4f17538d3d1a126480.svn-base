package com.haiyi.netty.server.handler;

import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.netty.utils.LoginUtil;
import com.maizi.utils.LogUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 5;
    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.MINUTES);
    }
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        LogUtils.logWarn(READER_IDLE_TIME + "分内未读到数据，关闭连接");
        //关闭连接
        ctx.channel().close();
    }
}