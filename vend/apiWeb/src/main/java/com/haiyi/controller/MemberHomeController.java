package com.haiyi.controller;

import com.haiyi.domain.ComsumeLog;
import com.haiyi.domain.Dev;
import com.haiyi.domain.Member;
import com.haiyi.domain.User;
import com.haiyi.netty.packet.MemberInfoResponsePacket;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.DevService;
import com.haiyi.service.MemberService;
import com.haiyi.service.UserService;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.StatusConstant;
import com.maizi.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/MemberHome")
public class MemberHomeController {
    @Autowired
    MemberService memberService;
    @Autowired
    ComsumeLogService comsumeLogService;
    @Autowired
    DevService devService;
    @Autowired
    UserService userService;


    private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);


    @ResponseBody
    @RequestMapping(value="/register",method = RequestMethod.POST)
    public JsonModel register(Member member, HttpServletRequest req, Model model){
        String password=member.getPassword();
        member.setPassword(MD5.GetMD5Code(member.getPassword()));
        memberService.registerByOpenId(member);
        Member member2 = memberService.login(member.getLoginName(), password);
        HttpSession session=req.getSession();
        session.setAttribute("member", member2);
        JsonModel jsonmodel=new JsonModel();
        jsonmodel.setSuccess(true);
        jsonmodel.setData(member2);
        jsonmodel.setMsg(session.getId());
        return jsonmodel;
    }



    @ResponseBody
    @RequestMapping(value="/updatePassword",method = RequestMethod.POST)
    public JsonModel updatePassword(String loginName,String oldPassword,String newPassword, HttpServletRequest req,Model model){
        Member member=memberService.login(loginName,oldPassword);
        JsonModel jsonmodel=new JsonModel();
        if(member!=null){
            memberService.updatePassword(newPassword,member.getId());
            Member member2 = memberService.login(member.getLoginName(), newPassword);
            HttpSession session=req.getSession();
            session.setAttribute("member", member2);
            jsonmodel.setSuccess(true);
            jsonmodel.setData(member2);
            jsonmodel.setMsg(session.getId());
        }else{
            jsonmodel.setSuccess(false);
            jsonmodel.setMsg("旧密码错误");
        }
        return jsonmodel;
    }
    @ResponseBody
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public JsonModel login(String loginName,String password, HttpServletRequest req,Model model){
        Member member=memberService.login(loginName,password);
        JsonModel jsonmodel=new JsonModel();
        if(member!=null){
            HttpSession session=req.getSession();
            session.setAttribute("member", member);
            jsonmodel.setSuccess(true);
            jsonmodel.setData(member);
            jsonmodel.setMsg(session.getId());
        }else{
            jsonmodel.setSuccess(false);
            jsonmodel.setMsg("用户名或密码错误");
        }
        return jsonmodel;
    }
    @ResponseBody
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public JsonModel logout(HttpServletRequest req){
        JsonModel jsonmodel=new JsonModel();
        HttpSession session = req.getSession();
        session.invalidate();
        jsonmodel.setMsg("退出成功");
        jsonmodel.setSuccess(true);
        return jsonmodel;
    }
    @ResponseBody
    @RequestMapping(value="/carWash",method = RequestMethod.GET)
    public JsonModel carWash(HttpServletRequest req,String devNum){

        JsonModel jsonmodel=new JsonModel();
        Dev dev=devService.findByNum(devNum);
        if(dev.getDevStatus()== StatusConstant.DEV_STATUS_FREE){
            devService.updateDevStates(dev.getId(),StatusConstant.DEV_STATUS_BUSY);
            HttpSession session=req.getSession();
            Member member = (Member)session.getAttribute("member");
            ComsumeLog comsumeLog=new ComsumeLog();
            comsumeLog.setOrderId(String.valueOf(snow.nextId()));
            comsumeLog.setMemberId(member.getId());
            comsumeLog.setMemberName(member.getName());

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
            MemberInfoResponsePacket memberInfoResponsePacket= new MemberInfoResponsePacket();
            Map<String,Object> param = new HashMap<>();
            param.put("account",member.getAccount());
            param.put("balance",member.getBalance());
            param.put("orderId",comsumeLog.getOrderId());
            memberInfoResponsePacket.setData(param);
            memberInfoResponsePacket.setSuccess(true);
            SessionUtil.dispatcherMemberInfo(SessionUtil.getChannel(devNum), memberInfoResponsePacket);
            LogUtils.logInfo("收到会员[{}]扫码请求，成功返回",member.getName());
            jsonmodel.setMsg("点击洗车成功，您可以开始洗车了！");
            jsonmodel.setSuccess(true);
        }else{
            jsonmodel.setSuccess(false);
            jsonmodel.setMsg("设备已被使用，请耐心等待");
        }


        return jsonmodel;
    }




}
