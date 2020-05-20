package com.haiyi.dao;
import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.ProductGroup;
import com.haiyi.query.ProductGroupQuery;

public interface ProductGroupMapper extends BaseDao<ProductGroup, ProductGroupQuery> {
    ProductGroup findByDevNum(String devNum);
}