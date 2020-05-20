package com.haiyi.dao;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Meal;
import com.haiyi.query.MealQuery;

import java.util.List;

public interface MealMapper  extends BaseDao<Meal, MealQuery> {
    List<Meal> findAll();


}
