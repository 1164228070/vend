package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.InsideComsumeService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.UserUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/insideComsumes")
@ControllerAnno(addUI = "/insideComsumes/save", detailUI = "/insideComsumes/detail", editUI = "/insideComsumes/save", listUI = "/insideComsumes/list")
public class InsideComsumeController extends BaseController<InsideComsume,InsideComsumeQuery>{

	@Autowired
	InsideComsumeService insideComsumeService;

	@Autowired
	public void setInsideComsumeService(InsideComsumeService insideComsumeService) {
		this.baseService = insideComsumeService;
	}


	@Override
	@AuthAnno
	public Map<String, Object> list(InsideComsumeQuery e, HttpServletRequest request) throws KPException {
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		return super.list(e, request);
	}



	
	@AuthAnno(verifyValid=false)
	@Override
	public String edit(String id, ModelMap modelMap, InsideComsume t) throws KPException {
		return null;
	}

	@Override
	public InsideComsume beforeSave(ModelMap modelMap, InsideComsume t) throws KPException {
		return null;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}

	@RequestMapping(value={"/toInsideComsumes/{orderId}"}, method={RequestMethod.GET})
	public String toInsideComsumes(@PathVariable String orderId, Model model){
		System.out.println(orderId);
		model.addAttribute("orderId",orderId);
		return "/insideComsumes/list";
	}



}