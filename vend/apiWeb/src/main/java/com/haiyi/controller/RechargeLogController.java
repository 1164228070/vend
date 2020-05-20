package com.haiyi.controller;

import com.haiyi.domain.RechargeLog;
import com.haiyi.service.RechargeLogService;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/rechargeLogs")
public class RechargeLogController {
    @Autowired
    RechargeLogService rechargeLogService;

    @ResponseBody
    @RequestMapping(value="/queryRechargeLog/{memberId}",method= RequestMethod.POST)
    public JsonModel queryRechargeLog(@PathVariable Integer memberId){
        List<RechargeLog> rechargeLogs = rechargeLogService.findByMemberId(memberId);

        if(rechargeLogs!=null){
            JsonModel model = new JsonModel();
            model.setMsg("查询成功");
            model.setSuccess(true);
            model.setData(rechargeLogs);
            model.setTotal(rechargeLogs.size());
            return model;
        }else{
            JsonModel model = new JsonModel();
            model.setMsg("查询失败");
            model.setSuccess(false);
            return model;
        }

    }
}
