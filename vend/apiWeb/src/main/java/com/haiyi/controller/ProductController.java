package com.haiyi.controller;


import com.haiyi.domain.Product;
import com.haiyi.domain.ProductGroup;
import com.haiyi.service.ProductGroupService;
import com.haiyi.service.ProductService;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductGroupService productGroupService;
    @Autowired
    ProductService productService;


    @RequestMapping(value="/AllProducts",method= RequestMethod.POST)
    @ResponseBody
    public JsonModel AllProducts(String devNum) {
        JsonModel jsonModel = new JsonModel();
        ProductGroup productGroup = productGroupService.findByDevNum(devNum);
        List<Product> products = productService.findByProductGroupId(productGroup.getId());
        jsonModel.setMsg("商品搜索成功");
        jsonModel.setSuccess(true);
        jsonModel.setData(products);
        return jsonModel;
    }

}
