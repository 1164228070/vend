package com.haiyi.netty.server.handler;

import com.haiyi.domain.Dev;
import com.haiyi.netty.packet.FrothRequestPacket;
import com.haiyi.netty.packet.FrothResponsePacket;
import com.haiyi.service.DevService;
import com.haiyi.utils.SpringUtil;
import com.maizi.utils.LogUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class FrothHandle extends SimpleChannelInboundHandler<FrothRequestPacket> {
    private static final FrothHandle frothHandle = new FrothHandle();
    private FrothHandle() {
    }

    public static FrothHandle getInstance() {
        return frothHandle;
    }
    
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FrothRequestPacket frothRequestPacket) throws Exception {
        LogUtils.logInfo("收到客户端[{}]设备泡沫状态",frothRequestPacket.getDevNum());
        String devNum = frothRequestPacket.getDevNum();
        Integer frothStatus=frothRequestPacket.getFrothStatus();
        FrothResponsePacket frothResponsePacket=new FrothResponsePacket();
        try {
            DevService devService=SpringUtil.getBean("devServiceImpl");
            Dev dev = devService.findByNum(devNum);
            devService.updateDevStates(dev.getId(),frothStatus);
            frothResponsePacket.setSuccess(true);
            LogUtils.logInfo("收到客户端[{}]设备泡沫状态,更改成功",frothRequestPacket.getDevNum());
            ctx.channel().writeAndFlush(frothResponsePacket);
        }catch (Exception e){
            frothResponsePacket.setSuccess(false);
            LogUtils.logInfo("收到客户端[{}]设备泡沫状态，更改失败",frothRequestPacket.getDevNum());
            ctx.channel().writeAndFlush(frothResponsePacket);
        }

    }
}
