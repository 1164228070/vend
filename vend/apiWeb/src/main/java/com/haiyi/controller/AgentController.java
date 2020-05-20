package com.haiyi.controller;

import com.haiyi.domain.Agent;
import com.haiyi.service.AgentService;
import com.maizi.anno.AuthAnno;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/agents")
public class AgentController {

	@Autowired
	AgentService agentService;

	/**
	 * 根据消费id查找代理信息
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value="/orders/{orderId}",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody JsonModel listAccountLog(@PathVariable String orderId){
		Agent agent = agentService.findByOrderId(orderId);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setSuccess(true);
		jsonModel.setData(agent);
		return jsonModel;
	}
}