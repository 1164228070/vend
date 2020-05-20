package com.haiyi.service;

import com.haiyi.domain.ProductGroup;

public interface ProductGroupService {
    ProductGroup findByDevNum(String devNum);
    ProductGroup findById(String productGroupId);
}