package com.haiyi.service;

import com.haiyi.domain.Product;
import com.haiyi.domain.vo.ProductVo;
import com.haiyi.query.ProductQuery;
import com.haiyi.service.base.BaseService;

import java.util.List;

public interface ProductService extends BaseService<Product, ProductQuery> {



	ProductVo findDetailById(Integer productId);

	 List<Product> findEmergencyProduct(Integer paramInteger);
}