package com.haiyi.netty.server.handler;

import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.Dev;
import com.haiyi.domain.Member;
import com.haiyi.domain.User;
import com.haiyi.netty.packet.BalancePacket;
import com.haiyi.netty.packet.BalancePacket;
import com.haiyi.netty.packet.BalanceResponsePacket;
import com.haiyi.netty.server.handler.auth.Session;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.netty.utils.LoginUtil;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.DevService;
import com.haiyi.service.MemberService;
import com.haiyi.service.UserService;
import com.haiyi.service.impl.MemberServiceImpl;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.MD5;
import com.haiyi.utils.SpringUtil;
import com.maizi.utils.DateUtil;
import com.maizi.utils.LogUtils;
import com.maizi.utils.SnowflakeIdFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class BalanceHandle extends SimpleChannelInboundHandler<BalancePacket>{

    private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);

    private static final BalanceHandle carWashCostHandle = new BalanceHandle();
    private BalanceHandle() {
    }

    public static BalanceHandle getInstance() {
        return carWashCostHandle;
    }

    /**
     * 当消息到达时
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BalancePacket balancePacket) throws Exception {
        LogUtils.logInfo("收到客户端[{}]余额请求",balancePacket.getLoginName());

        String loginName=balancePacket.getLoginName();
        String password=balancePacket.getPassword();
        String devNum=balancePacket.getDevNum();

        //调用Service
        MemberService memberService =   SpringUtil.getBean("memberServiceImpl");
        //推送余额

        Member member = memberService.login(loginName,password);
        if(member==null){
            LogUtils.logInfo("收到客户端[{}]余额请求,但用户名或密码错误",balancePacket.getLoginName());
            BalanceResponsePacket larBalanceResponsePacket = new BalanceResponsePacket();
            larBalanceResponsePacket.setSuccess(false);
            larBalanceResponsePacket.setMsg("用户名或密码错误");
            ctx.channel().writeAndFlush(larBalanceResponsePacket);
            return;
        }
        ComsumeLogService comsumeLogService=SpringUtil.getBean("comsumeLogServiceImpl");
        DevService devService=SpringUtil.getBean("devServiceImpl");
        ComsumeLog comsumeLog=new ComsumeLog();
        comsumeLog.setOrderId(String.valueOf(snow.nextId()));
        comsumeLog.setMemberId(member.getId());
        comsumeLog.setMemberName(member.getName());
        Dev dev=devService.findByNum(devNum);
        UserService userService=SpringUtil.getBean("userServiceImpl");
        User user = userService.findById(dev.getUserId());
        comsumeLog.setUserName(user.getName());
        comsumeLog.setAgentName(user.getAgentName());
        comsumeLog.setUserId(dev.getUserId());
        comsumeLog.setAgentId(dev.getAgentId());
        comsumeLog.setDevNum(devNum);
        comsumeLog.setDevName(dev.getName());
        comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNGOING);
        comsumeLog.setPayType(StateConfig.PAYTYPE_XW);
        comsumeLog.setTradeType(StateConfig.TRADETYPE_COMSUME);
        Date currentDate = DateUtil.getCurrentDate();
        comsumeLog.setCreateDate(currentDate);
        comsumeLogService.add(comsumeLog);
        BalanceResponsePacket larBalanceResponsePacket = new BalanceResponsePacket();
        Map<String,Object> param = new HashMap<>();
        param.put("account",member.getAccount());
        param.put("balance",member.getBalance());
        param.put("orderId",comsumeLog.getOrderId());
        larBalanceResponsePacket.setSuccess(true);
        larBalanceResponsePacket.setData(param);
        larBalanceResponsePacket.setMsg("查询成功");
        LogUtils.logInfo("[{}]余额请求查询成功",balancePacket.getLoginName());
        //TODO
        // 登录响应
        ctx.channel().writeAndFlush(larBalanceResponsePacket);
    }




}
