package com.haiyi.dao;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Product;
import com.haiyi.domain.vo.ProductVo;
import com.haiyi.query.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends BaseDao<Product, ProductQuery> {
	Product findByNum(Integer productNum);
	
	ProductVo findDetailById(Integer productId);

	Product findByDevNumAndProductId(@Param("devNum") Integer devNum, @Param("productId") Integer productId);

	Product findByDevNumAndOrderNum(@Param("devNum") Integer devNum, @Param("orderNum") Integer orderNum);

    List<Product> findByOrderNums(@Param("devNum") String devNum, @Param("orderStrNums") String[] orderStrNums);

	List<Product> findEmergencyProduct(Integer userId);

	List<Product> findByProductGroupId(Integer productGroupId);
}