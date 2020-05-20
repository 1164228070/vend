package com.haiyi.controller;

import com.haiyi.domain.Member;
import com.haiyi.netty.packet.MemberInfoResponsePacket;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.service.MemberService;
import com.haiyi.utils.WeChatUtil;
import com.haiyi.weChat.WeChatConfig;
import com.maizi.utils.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class TXController {
    @Autowired
    MemberService memberService;

    @RequestMapping(value="/MP_verify_vfoBGLXDW3mnuNEJ.txt",method= RequestMethod.GET)
    public void weChatCheck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();//创建输出流
        printWriter.println("vfoBGLXDW3mnuNEJ");
    }



}
