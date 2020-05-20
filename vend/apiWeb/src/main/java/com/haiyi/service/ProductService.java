package com.haiyi.service;

import com.haiyi.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findByProductGroupId(Integer productGroupId);
    Product findById(Integer productId);
    Product findByDevNumAndProductId(Integer devNum, Integer productId);
    int update(Product product);


}