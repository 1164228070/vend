package com.haiyi.netty.server.handler;

import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.Dev;
import com.haiyi.domain.Member;
import com.haiyi.domain.User;
import com.haiyi.netty.packet.CarWashCostPacket;
import com.haiyi.netty.packet.CarWashCostPacket;
import com.haiyi.netty.packet.CarWashCostResponsePacket;
import com.haiyi.netty.server.handler.auth.Session;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.netty.utils.LoginUtil;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.DevService;
import com.haiyi.service.MemberService;
import com.haiyi.service.UserService;
import com.haiyi.service.impl.MemberServiceImpl;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.SpringUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.utils.DateUtil;
import com.maizi.utils.LogUtils;
import com.maizi.utils.StringUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class CarWashCostHandle extends SimpleChannelInboundHandler<CarWashCostPacket>{



    private static final CarWashCostHandle carWashCostHandle = new CarWashCostHandle();
    private CarWashCostHandle() {
    }

    public static CarWashCostHandle getInstance() {
        return carWashCostHandle;
    }
    /**
     * 当消息到达时
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CarWashCostPacket carWashCostPacket) throws Exception {
        LogUtils.logInfo("收到客户端[{}]消费请求",carWashCostPacket.getAccount());
        String account=carWashCostPacket.getAccount();
        BigDecimal consumption=carWashCostPacket.getConsumption();
        String devNum=carWashCostPacket.getDevNum();
        String orderId=carWashCostPacket.getOrderId();
        Integer time=carWashCostPacket.getTime();
        String loginName=carWashCostPacket.getLoginName();
        //调用Service
        DevService devService=SpringUtil.getBean("devServiceImpl");
        Dev dev = devService.findByNum(devNum);
        devService.updateDevStates(dev.getId(), StatusConstant.DEV_STATUS_FREE);
        UserService userService=SpringUtil.getBean("userServiceImpl");
        User user = userService.findById(dev.getUserId());
        user.setLeftMoney(user.getLeftMoney().add(consumption));
        userService.update(user);
        MemberService memberService =   SpringUtil.getBean("memberServiceImpl");
        Member member=null;
        if(account!=null && !"".equals(account)){
            member = memberService.findByAccount(account);
        }else if(loginName!=null && !"".equals(loginName)){
            member = memberService.findByLoginName(loginName);
        }
        if(member==null){
            LogUtils.logInfo("未找到[{}]该用户",carWashCostPacket.getAccount());
            CarWashCostResponsePacket larWashCostResponsePacket = new CarWashCostResponsePacket();
            larWashCostResponsePacket.setSuccess(false);
            larWashCostResponsePacket.setMsg("未找到该用户");
            ctx.channel().writeAndFlush(larWashCostResponsePacket);
            return;
        }else{
            try{
                memberService.updateBalance(member.getBalance().subtract(consumption),member.getAccount());
            }catch (Exception e){
                LogUtils.logInfo("[{}]该用户扣款失败",carWashCostPacket.getAccount());
                CarWashCostResponsePacket larWashCostResponsePacket = new CarWashCostResponsePacket();
                larWashCostResponsePacket.setSuccess(false);
                larWashCostResponsePacket.setMsg("扣款失败");
                ctx.channel().writeAndFlush(larWashCostResponsePacket);
                return;
            }
            //扣款

            //添加消费记录
            ComsumeLogService comsumeLogService =   SpringUtil.getBean("comsumeLogServiceImpl");
            ComsumeLog comsumeLog=null;
            try {
                comsumeLog=comsumeLogService.findByOrderId(orderId);
                comsumeLog.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
                Integer min=time/60;
                Integer sec=time%60;
                comsumeLog.setTime(min.toString()+"分"+sec.toString()+"秒");
                comsumeLog.setPayAmount(consumption);
                comsumeLogService.update(comsumeLog);
                CarWashCostResponsePacket larWashCostResponsePacket = new CarWashCostResponsePacket();
                larWashCostResponsePacket.setSuccess(true);
                larWashCostResponsePacket.setMsg("结算成功");
                LogUtils.logInfo("收到客户端[{}]消费请求，结算成功",carWashCostPacket.getAccount());
                ctx.channel().writeAndFlush(larWashCostResponsePacket);
            }catch (Exception e){
                LogUtils.logInfo("收到客户端[{}]消费请求，但修改订单出错",carWashCostPacket.getAccount());
                CarWashCostResponsePacket larWashCostResponsePacket = new CarWashCostResponsePacket();
                larWashCostResponsePacket.setSuccess(false);
                larWashCostResponsePacket.setMsg("修改订单出错");
                ctx.channel().writeAndFlush(larWashCostResponsePacket);
                return;
            }
        }


    }




}
