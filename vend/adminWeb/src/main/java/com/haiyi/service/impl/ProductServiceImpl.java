package com.haiyi.service.impl;

import com.haiyi.dao.ProductMapper;
import com.haiyi.domain.Product;
import com.haiyi.domain.ProductGroup;
import com.haiyi.domain.User;
import com.haiyi.domain.vo.ProductVo;
import com.haiyi.query.ProductQuery;
import com.haiyi.service.ProductGroupService;
import com.haiyi.service.ProductService;
import com.haiyi.service.UserService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, ProductQuery> implements ProductService {
	
	@Autowired
    ProductGroupService productGroupService;
	@Autowired
	UserService userService;

	@Autowired
	public void setProductMapper(ProductMapper productMapper){
		this.daoMapper = productMapper;
	}
	

	

	@Override
	public ProductVo findDetailById(Integer productId) {
		return ((ProductMapper)this.daoMapper).findDetailById(productId);
	}

	public List<Product> findEmergencyProduct(Integer userId)
	{
		return ((ProductMapper)this.daoMapper).findEmergencyProduct(userId);
	}
}