package com.haiyi.service.impl;

import com.haiyi.dao.AgentMapper;
import com.haiyi.dao.ProductGroupMapper;
import com.haiyi.domain.Dev;
import com.haiyi.domain.ProductGroup;
import com.haiyi.domain.User;
import com.haiyi.query.ProductGroupQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.UserService;
import com.haiyi.service.DevService;
import com.haiyi.service.ProductGroupService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductGroupServiceImpl extends BaseServiceImpl<ProductGroup, ProductGroupQuery> implements ProductGroupService {
	
	@Autowired
    UserService userService;
	@Autowired
	AgentService agentService;
	
	
	@Autowired
    DevService devService;
	
	
	@Autowired
	public void setProductGroupMapper(ProductGroupMapper productGroupMapper){
		this.daoMapper = productGroupMapper;
	}
	
	@Override
	public int add(ProductGroup t) throws KPException {
		User user=userService.findById(t.getUser().getId()+"");
		String userName = user.getName();
		t.setUserName(userName);
		t.setAgent(agentService.findById(user.getAgentId()+""));
		t.setAgentName(user.getAgentName());
		if(t.getDev()!=null){
			Dev dev	 = devService.findById(t.getDev().getId()+"");
			if(!dev.getUserId().equals(t.getUser().getId())){
				throw new KPException(ExceptionKind.PARAM_E,"选中的设备不属于当前商户");
			}
			String devNum = dev.getNum()+"";
			t.setDevNum(devNum);
		}
		
		return super.add(t);
	}
	
	@Override
	public int updateById(ProductGroup t) throws KPException {
		User user=userService.findById(t.getUser().getId()+"");
		String userName = user.getName();
		t.setUserName(userName);
		t.setAgent(agentService.findById(t.getUser().getAgentId()+""));
		t.setAgentName(user.getAgentName());
		
		if(t.getDev()!=null){
			Dev dev	 = devService.findById(t.getDev().getId()+"");
			if(!dev.getUserId().equals(t.getUser().getId())){
				throw new KPException(ExceptionKind.PARAM_E,"选中的设备不属于当前商户");
			}
			String devNum = dev.getNum()+"";
			t.setDevNum(devNum);
		}
		return super.updateById(t);
	}

	@Override
	public ProductGroup findByDevNum(String devNum) {
		return ((ProductGroupMapper)this.daoMapper).findByDevNum(devNum);
	}
}