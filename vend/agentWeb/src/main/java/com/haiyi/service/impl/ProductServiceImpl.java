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
	ProductService productService;

	@Autowired
	public void setProductMapper(ProductMapper productMapper){
		this.daoMapper = productMapper;
	}
	
	@Override
	public int add(Product t) throws KPException {
		ProductGroup productGroup = t.getProductGroup();
		//判断当前代理商品组是否属于当前代理
		productGroup = productGroupService.findById(productGroup.getId()+"");
		if(productGroup == null || !productGroup.getUser().getId().equals(t.getUserId())){
			//参数错误
			throw new KPException(ExceptionKind.PARAM_E,"错误商品组信息");
		}
		t.setProductGroup(productGroup);
		User user = userService.findById(t.getUserId() + "");
		t.setUserName(user.getName());
		t.setAgentId(user.getAgentId());
		t.setAgentName(user.getAgentName());
		if(t.getStoreCount()<=t.getThreshold()){
			System.out.println("添加，缺货============");
			t.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
		}else {
			System.out.println("添加，充足============");
			t.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
		}
		return super.add(t);
	}
	
	@Override
	public int updateById(Product t) throws KPException {
		ProductGroup productGroup = t.getProductGroup();
		if(productGroup!=null){
			//判断当前代理商品组是否属于当前商户
			productGroup = productGroupService.findById(productGroup.getId()+"");
			if(productGroup == null || !productGroup.getUser().getId().equals(t.getUserId())){
				//参数错误
				throw new KPException(ExceptionKind.PARAM_E,"错误商品组信息");
			}
		}
		t.setProductGroup(productGroup);
		User user = userService.findById(t.getUserId() + "");
		t.setUserName(user.getName());
		t.setAgentId(user.getAgentId());
		t.setAgentName(user.getAgentName());
		if(t.getStoreCount()<=t.getThreshold()){
			System.out.println("更新，缺货============");
			t.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
		}else {
			System.out.println("更新，充足============");
			t.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
		}
		return super.updateById(t);
	}

	@Override
	public boolean onLine(Integer productId,Integer userId) {
		Product product=productService.findById(productId+"");
		product.setId(productId);
		product.setUserId(userId);
		product.setStatus(StatusConstant.PRODUCT_STATUS_ONLINE);
		return updateById(product)==1;
	}

	@Override
	public boolean offLine(Integer productId,Integer userId) {
		Product product=productService.findById(productId+"");
		product.setId(productId);
		product.setUserId(userId);
		product.setStatus(StatusConstant.PRODUCT_STATUS_OFFLINE);
		return updateById(product)==1;
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