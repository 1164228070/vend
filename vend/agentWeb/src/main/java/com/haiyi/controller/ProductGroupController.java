package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Agent;
import com.haiyi.domain.User;
import com.haiyi.domain.Dev;
import com.haiyi.domain.ProductGroup;
import com.haiyi.query.DevQuery;
import com.haiyi.query.ProductGroupQuery;
import com.haiyi.service.UserService;
import com.haiyi.service.DevService;
import com.haiyi.service.ProductGroupService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.UserUtil;
import com.haiyi.utils.RequestUtil;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productGroups")
@ControllerAnno(addUI = "/productGroups/save", detailUI = "/productGroups/detail", editUI = "/productGroups/save", listUI = "/productGroups/list")
public class ProductGroupController extends BaseController<ProductGroup, ProductGroupQuery> {
	
	@Autowired
    UserService userService;
	@Autowired
    DevService devService;
	@Autowired
	ProductGroupService productGroupService;
	
	@Autowired
	public void setProductGroupService(ProductGroupService productGroupService) {
		this.baseService = productGroupService;
	}
	
	@Override
	public Map<String, Object> list(ProductGroupQuery e, HttpServletRequest request) throws KPException {
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			User user = new User();
			user.setId(UserUtil.getUserId(request.getSession()));
			e.setUser(user);
		}else{
			Agent agent = new Agent();
			agent.setId(AgentUtil.getAgentId(request.getSession()));
			e.setAgent(agent);
		}
		return super.list(e, request);
	}

	@Override
	public ProductGroup beforeSave(ModelMap modelMap, ProductGroup t) throws KPException {
		User user = new User();
		user.setId(UserUtil.getUserId(RequestUtil.getRequest().getSession()));
		t.setUser(user);
		System.out.println("save111111111111"+t.toString());
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		//列出属于当前代理的设备
		DevQuery devQuery = new DevQuery();
		devQuery.setUserId(UserUtil.getUserId(RequestUtil.getRequest().getSession()));
		devQuery.setPagination(false);
		List<Dev> devs = devService.findBySelective(devQuery).getList();
		for(int i = devs.size() - 1; i >= 0; i--){
			ProductGroup productGroup = productGroupService.findByDevNum(devs.get(i).getNum() + "");
			if(productGroup!=null){
				devs.remove(devs.get(i));
			}
		}
		modelMap.addAttribute("devs", devs);
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
}