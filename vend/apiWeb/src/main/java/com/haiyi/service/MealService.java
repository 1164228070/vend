package com.haiyi.service;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.Meal;
import com.haiyi.query.MealQuery;
import com.maizi.exception.KPException;

import java.util.List;

public interface MealService {
    List<Meal> findAll();

}
