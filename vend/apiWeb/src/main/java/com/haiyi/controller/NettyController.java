package com.haiyi.controller;

import com.haiyi.netty.packet.BuyPushResponsePacket;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.maizi.utils.JsonModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/netty")
public class NettyController {
    @RequestMapping(value="/isOnline",method= RequestMethod.POST)
    @ResponseBody
    public JsonModel IsOnline(String devNum) {
        JsonModel jsonModel = new JsonModel();
        if(SessionUtil.getChannel(devNum)==null){
            jsonModel.setMsg("设备不在线");
            jsonModel.setSuccess(false);
        }else {
            jsonModel.setMsg("设备在线");
            jsonModel.setSuccess(true);
        }


        jsonModel.setData("");
        return jsonModel;
    }

    @RequestMapping(value="/buyPush",method= RequestMethod.POST)
    @ResponseBody
    public JsonModel buyPush(String devNum, String orderId, BigDecimal price,Integer orderNum) {
        //购买推送信息
        BuyPushResponsePacket buyPushResponsePacket=new BuyPushResponsePacket();
        JsonModel jsonModel = new JsonModel();
        Map<String,Object> param = new HashMap<>();
        param.put("buyCount",1);
        param.put("orderId",orderId);
        param.put("price",price);
        param.put("orderNum",orderNum);
        buyPushResponsePacket.setData(param);
        buyPushResponsePacket.setSuccess(true);
        SessionUtil.dispatcherBuyInfo(SessionUtil.getChannel(devNum), buyPushResponsePacket);


        jsonModel.setSuccess(true);
        return jsonModel;
    }


}
