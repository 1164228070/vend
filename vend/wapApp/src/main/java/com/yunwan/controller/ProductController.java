package com.yunwan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/products")
public class ProductController {

    @RequestMapping(value = "/toProduct",method= RequestMethod.GET)
    public String toProduct(String devNum, Model model){
        model.addAttribute("devNum",devNum);
        return "product/product";
    }
    @RequestMapping(value = "/toPayUI",method= RequestMethod.GET)
    public String toPayUI(String productId,String price,String devNum,String productName, Model model){
        model.addAttribute("productId",productId);
        model.addAttribute("price",price);
        model.addAttribute("devNum",devNum);
        model.addAttribute("productName",productName);
        return "product/payUI";
    }
    @RequestMapping(value = "/toHttpPayUI",method= RequestMethod.GET)
    public String toHttpPayUI(String devNum,String orderId,String price, Model model){
        model.addAttribute("devNum",devNum);
        model.addAttribute("orderId",orderId);
        model.addAttribute("price",price);
        return "product/httpPayUI";
    }
}
