package com.haiyi.controller;

import com.haiyi.service.MealService;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/meal")
public class MealController {
    @Autowired
    MealService mealService;

    @RequestMapping(value="/AllMeal",method= RequestMethod.POST)
    @ResponseBody
    public JsonModel AllMeal() {
        JsonModel jsonModel = new JsonModel();
        jsonModel.setMsg("套餐搜索成功");
        jsonModel.setSuccess(true);
        jsonModel.setData(mealService.findAll());
        return jsonModel;
    }
}
