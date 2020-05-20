package com.yunwan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pays")
public class PayController {

	private String WX_PAY="pays/wx_pay";

    private String ZX_WX_PAY="pays/zx_wx_pay";

    //支付界面
    private String ZX_ZFB_PAY="pays/zx_zfb_pay";
   /**
    * 微信授权
    * @param modelMap
    * @return
    */
   @RequestMapping(value = "/code",method=RequestMethod.GET)
   public String WXAuth(ModelMap modelMap,@RequestParam String code,@RequestParam("state") String orderId){
	   modelMap.addAttribute("code",code);
	   //orderId为订单id
	   modelMap.addAttribute("orderId",orderId);
	   //重定向到下单界面
	   return WX_PAY;
   }


    @RequestMapping(value = "/zxCode",method=RequestMethod.GET)
    public String zxCode(ModelMap modelMap,String code,String auth_code,@RequestParam("state") String orderId){
        modelMap.addAttribute("orderId",orderId);
        if(!"".equals(auth_code)&&auth_code!=null){
            modelMap.addAttribute("code",auth_code);
            return ZX_ZFB_PAY;
        }else{
            modelMap.addAttribute("code",code);
            return ZX_WX_PAY;
        }

    }

   @RequestMapping(value = "/toPay",method=RequestMethod.GET)
   public String toPay(){

	   return "pays/pay";
   }
}