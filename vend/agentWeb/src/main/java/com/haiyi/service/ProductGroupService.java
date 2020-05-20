package com.haiyi.service;

import com.haiyi.domain.ProductGroup;
import com.haiyi.query.ProductGroupQuery;
import com.haiyi.service.base.BaseService;

public interface ProductGroupService extends BaseService<ProductGroup, ProductGroupQuery> {
    ProductGroup findByDevNum(String devNum);
}