package com.haiyi.service.impl;

import com.haiyi.dao.ProductGroupMapper;
import com.haiyi.dao.ProductMapper;
import com.haiyi.domain.Product;
import com.haiyi.domain.ProductGroup;
import com.haiyi.service.ProductGroupService;
import com.haiyi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;


    @Override
    public List<Product> findByProductGroupId(Integer productGroupId) {
        return productMapper.findByProductGroupId(productGroupId);
    }

    @Override
    public Product findByDevNumAndProductId(Integer devNum, Integer productId) {
        return productMapper.findByDevNumAndProductId(devNum,productId);
    }

    @Override
    public int update(Product product) {
        return productMapper.update(product);
    }

    @Override
    public Product findById(Integer productId) {
        return productMapper.findById(productId+"");
    }
}
