package com.haiyi.service.impl;

import com.github.pagehelper.PageInfo;
import com.haiyi.dao.MealMapper;
import com.haiyi.domain.Meal;
import com.haiyi.query.MealQuery;
import com.haiyi.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServicelmpl implements MealService{
    @Autowired
    MealMapper mealMapper;


    @Override
    public List<Meal> findAll() {
        return mealMapper.findAll();
    }


}
