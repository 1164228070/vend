package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.User;
import com.haiyi.domain.Product;
import com.haiyi.domain.ProductGroup;
import com.haiyi.domain.vo.ProductVo;
import com.haiyi.query.ProductGroupQuery;
import com.haiyi.query.ProductQuery;
import com.haiyi.service.DevService;
import com.haiyi.service.ProductGroupService;
import com.haiyi.service.ProductService;
import com.haiyi.utils.*;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

		User user = new User();
		productGroupQuery.setUser(user);

		List<ProductGroup> productGroups = productGroupService.findBySelective(productGroupQuery).getList();
        request.setAttribute("productGroups",productGroups);
		return super.list(request, modelMap);
	}

	@Override
	public Map<String, Object> list(ProductQuery e, HttpServletRequest request) throws KPException {
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		System.out.println(e.toString());
		return super.list(e, request);
	}

	@Override
	public Product beforeSave(ModelMap modelMap, Product t) throws KPException {
		//添加
	    if(t.getId()==null){
	    	//默认状态(下架)
	    	t.setStatus(StatusConstant.PRODUCT_STATUS_OFFLINE);
	    }
	    t.setUserId(UserUtil.getUserId(RequestUtil.getRequest().getSession()));
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		Integer userId = UserUtil.getUserId(httpServletRequest.getSession());

		//获取当前代理下的商品组
		ProductGroupQuery productGroupQuery = new ProductGroupQuery();
		User user = new User();
		user.setId(userId);
		productGroupQuery.setUser(user);
		productGroupQuery.setPagination(false);
		List<ProductGroup> productGroups = productGroupService.findBySelective(productGroupQuery).getList();
		modelMap.addAttribute("productGroups",productGroups);
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}

	/**
	 *修改商品状态
	 * @param productId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value={"/{productId}/status"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody
    JsonModel updateStatus(@PathVariable("productId") Integer productId, @RequestParam(required=true) String sign){
		JsonModel jsonModel = new JsonModel();
		if("y".equals(sign)){
			jsonModel.setSuccess(((ProductService)this.baseService).onLine(productId, UserUtil.getUserId(RequestUtil.getRequest().getSession())));
			jsonModel.setMsg(ConstantUtil.ONLINE_SUCCESS);
		}else if("n".equals(sign)){
			jsonModel.setSuccess(((ProductService)this.baseService).offLine(productId, UserUtil.getUserId(RequestUtil.getRequest().getSession())));
			jsonModel.setMsg(ConstantUtil.OFFLINE_SUCCESS);
		}
		return jsonModel;
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