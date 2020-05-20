package com.yunwan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/callback")
public class CallbackController {
	 
	private static final String SUCCESS = "commom/success";
	  
	@RequestMapping(value = "/wx/{orderId}", method = RequestMethod.GET)
	public String WXsuccess(@PathVariable String orderId, ModelMap modelMap){
		modelMap.addAttribute("orderId",orderId);
		return SUCCESS;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(){
		return "commom/test";
	}

}