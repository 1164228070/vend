package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Product;
import com.haiyi.domain.ProductGroup;
import com.haiyi.domain.vo.ProductVo;
import com.haiyi.query.ProductGroupQuery;
import com.haiyi.query.ProductQuery;
import com.haiyi.service.DevService;
import com.haiyi.service.ProductGroupService;
import com.haiyi.service.ProductService;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//代理才能添加商品


@Controller
@RequestMapping("/products")
@ControllerAnno(addUI = "/products/save", detailUI = "/products/detail", editUI = "/products/save", listUI = "/products/list")
public class ProductController extends BaseController<Product, ProductQuery> {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
    ProductGroupService productGroupService;

	@Autowired
    DevService devService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.baseService = productService;
	}

	@Override
	@AuthAnno
	public String list(HttpServletRequest request, ModelMap modelMap) throws KPException {
		ProductGroupQuery productGroupQuery = new ProductGroupQuery();
		List<ProductGroup> productGroups = productGroupService.findBySelective(productGroupQuery).getList();
        request.setAttribute("productGroups",productGroups);
		return super.list(request, modelMap);
	}

	@Override
	public Map<String, Object> list(ProductQuery e, HttpServletRequest request) throws KPException {
		return super.list(e, request);
	}

	@Override
	public Product beforeSave(ModelMap modelMap, Product t) throws KPException {
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {

	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}



	
	@Override
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	@AuthAnno
	public String findById(@PathVariable String id, ModelMap modelMap){
		ProductVo productVo = ((ProductService)baseService).findDetailById(Integer.parseInt(id));
		if(productVo == null){
			throw new KPException(ExceptionKind.PARAM_E);  
		}
		modelMap.put("product",productVo);
		return detailUI;
	}
}