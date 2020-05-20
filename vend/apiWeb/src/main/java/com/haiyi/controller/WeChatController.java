package com.haiyi.controller;

import com.haiyi.domain.Member;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.pay.CheckUtil;
import com.haiyi.pay.PayConfig;
import com.haiyi.pay.SignMD5;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.DevService;
import com.haiyi.service.MemberService;
import com.haiyi.service.UserService;
import com.haiyi.utils.WeChatUtil;
import com.haiyi.weChat.WeChatConfig;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import com.maizi.utils.SnowflakeIdFactory;
import com.maizi.utils.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/weChat")
public class WeChatController {
    @Autowired
    MemberService memberService;
    @Autowired
    ComsumeLogService comsumeLogService;
    @Autowired
    DevService devService;
    @Autowired
    UserService userService;



    SignMD5 encoder = new SignMD5();

    private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);
    @RequestMapping(value="",method= RequestMethod.GET)
    public void weChatCheck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String signature=req.getParameter("signature");
        String timestamp=req.getParameter("timestamp");
        String nonce=req.getParameter("nonce");
        String echostr=req.getParameter("echostr");
        PrintWriter out=resp.getWriter();
        if(WeChatUtil.checkSignature(signature, timestamp, nonce)){
            out.print(echostr);
        }
    }
    @RequestMapping(value="/auth",method= RequestMethod.GET)
    public void AuthGet(String role,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!StringUtil.isEmpty(role)){
            String backUrl=WeChatConfig.webUrl+req.getContextPath()+"/weChat/callBall?role="+role;
            String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APPID +
                    "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                    "&response_type=code" +
                    "&scope=snsapi_userinfo" +
                    "&state=STATE#wechat_redirect";
            //System.out.println(url);
            resp.sendRedirect(url);
        }else {
            System.out.println("设备编号为空");
            //TODO
        }
    }

    @RequestMapping(value="/callBall",method=RequestMethod.GET)
    public void UserResult(String role,HttpServletRequest request, HttpServletResponse response) throws IOException

    {
        String code=request.getParameter("code");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeChatConfig.APPID+
                "&secret=" +WeChatConfig.APPSECRET+
                "&code=" +code+
                "&grant_type=authorization_code";
        JSONObject jsonObject = WeChatUtil.doGetStr(url);
        String openid=jsonObject.get("openid").toString();
        String token=jsonObject.get("access_token").toString();
        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        JSONObject userInfo = WeChatUtil.doGetStr(infoUrl);

        if(!StringUtil.isEmpty(role)){
            //response.sendRedirect(WeChatConfig.webUrl+request.getContextPath()+"/weChat/scanResult?devNum="+devNum);
            if("user".equals(role)){
                response.sendRedirect("http://www.peshion.com/agentWeb/session/user/new?openId="+openid);
            }else if("agent".equals(role)){
                response.sendRedirect("http://www.peshion.com/agentWeb/session/new?openId="+openid);
            }
        }else{
            System.out.println("设备编号为空");
            //TODO
        }

    }
    @RequestMapping(value="/scanResult",method=RequestMethod.GET)
    public void scanResult(String devNum, Model model, HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        if(SessionUtil.getChannel(devNum)==null){
            LogUtils.logInfo("收到设备号为[{}]扫码请求,但设备不在线",devNum);
            resp.sendRedirect("http://www.peshion.com/wapApp/tofail?MSG="+URLEncoder.encode("该设备不在线","utf-8"));
            return;
        }else{
            LogUtils.logInfo("收到设备号为[{}]扫码请求，成功返回",devNum);
            resp.sendRedirect("http://www.peshion.com/wapApp/products/toProduct?devNum="+devNum);
            return;
        }

    }

    @RequestMapping(value="/JsonPerson",method=RequestMethod.GET)
    @ResponseBody
    public JsonModel JsonPerson(HttpServletRequest req,String memberId){
        Member member=null;
        if(memberId!=null && memberId !=""){
            member=memberService.findById(memberId);
        }else {
            HttpSession session=req.getSession();
            Member member1 = (Member)session.getAttribute("member");
            member=memberService.findById(member1.getId().toString());
            session.setAttribute("member",member);
        }
        JsonModel jsonmodel=new JsonModel();
        jsonmodel.setSuccess(true);
        jsonmodel.setMsg("成功");
        jsonmodel.setData(member);
        return jsonmodel;
    }



}
