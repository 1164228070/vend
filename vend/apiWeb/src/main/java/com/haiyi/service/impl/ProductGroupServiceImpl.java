package com.haiyi.service.impl;

import com.haiyi.dao.ProductGroupMapper;
import com.haiyi.domain.ProductGroup;
import com.haiyi.service.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductGroupServiceImpl implements ProductGroupService {
    @Autowired
    ProductGroupMapper productGroupMapper;
    @Override
    public ProductGroup findByDevNum(String devNum) {
        return productGroupMapper.findByDevNum(devNum);
    }

    @Override
    public ProductGroup findById(String productGroupId) {
        return productGroupMapper.findById(productGroupId);
    }
}
