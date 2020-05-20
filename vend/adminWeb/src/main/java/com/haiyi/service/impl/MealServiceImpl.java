package com.haiyi.service.impl;

import com.haiyi.dao.DevMapper;
import com.haiyi.dao.MealMapper;
import com.haiyi.domain.Meal;
import com.haiyi.query.MealQuery;
import com.haiyi.service.MealService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealServiceImpl extends BaseServiceImpl<Meal, MealQuery> implements MealService {
    @Autowired
    public void setDevMapper(MealMapper mealMapper){
        this.daoMapper = mealMapper;
    }

}
