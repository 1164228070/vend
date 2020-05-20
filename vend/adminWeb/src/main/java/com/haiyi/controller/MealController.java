package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Meal;
import com.haiyi.query.MealQuery;
import com.haiyi.service.MealService;
import com.haiyi.service.MemberService;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meals")
@ControllerAnno(addUI = "/meals/save", detailUI = "/meals/detail", editUI = "/meals/save", listUI = "/meals/list")
public class MealController extends BaseController<Meal, MealQuery> {

    @Autowired
    public void setMemberService(MealService mealService) {
        this.baseService = mealService;
    }

    @Override
    public Meal beforeSave(ModelMap modelMap, Meal meal) throws KPException {
        return meal;
    }

    @Override
    public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {

    }

    @Override
    protected void beforeDelete(String[] ids) throws KPException {

    }
}
