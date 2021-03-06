package com.haiyi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haiyi.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.AgentMapper;
import com.haiyi.domain.Agent;
import com.haiyi.domain.Role;
import com.haiyi.query.AgentQuery;
import com.haiyi.query.RoleQuery;
import com.haiyi.service.AgentRoleService;
import com.haiyi.service.AgentService;
import com.haiyi.service.MenuService;
import com.haiyi.service.RoleService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;

@Service
public class AgentServiceImpl extends BaseServiceImpl<Agent, AgentQuery> implements AgentService{
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	AgentRoleService agentRoleService;
	 
	@Autowired
	public void setAgentMapper(AgentMapper agentMapper){
		this.daoMapper = agentMapper;
	}
	
	@Override
	public int updateById(Agent t) throws KPException {
		Agent agent = findById(t.getId()+"");
		if(agent==null){
			throw new KPException(ExceptionKind.PARAM_E);
		}
		//原始的代理角色Id
		Integer oldAgentRoleId = agent.getAgentRoleId();
		
		int result= super.updateById(t);
		
		//需要删除当前代理下员工的权限信息
		Integer agentRoleId = t.getAgentRoleId();
		
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setAgentId(t.getId());
		roleQuery.setPagination(false);
		List<Role> roles = roleService.findBySelective(roleQuery).getList();
		
		List<Integer> roleIds = new ArrayList<Integer>();
		for(Role role : roles){
			roleIds.add(role.getId());
		}
		
		if(agentRoleId==null){
			//清空当前代理角色下的所有权限
			for(Integer roleId : roleIds){
				Map<String,Integer> param = new HashMap<String,Integer>();
				param.put("roleId",roleId);
				menuService.clearGrant(param);
			}
		}else{
			
			if(oldAgentRoleId!=null){
				//获取原来的权限
				List<Integer> oldMenuIds= agentRoleService.findMenuIds(oldAgentRoleId);
				 
				//获取当前代理的所有权限
				List<Integer> menuIds= agentRoleService.findMenuIds(agentRoleId);
				
				List<Integer> deletedMenuIds = new ArrayList<Integer>();
				for(Integer oldMenuId :oldMenuIds){
				    if(!menuIds.contains(oldMenuId)){
				    	deletedMenuIds.add(oldMenuId);
				    }
				}
				
				for(Integer roleId : roleIds){
					for(Integer deletedMenuId : deletedMenuIds){
						Map<String,Integer> param = new HashMap<String,Integer>();
						param.put("roleId",roleId);
						param.put("menuId",deletedMenuId);
						menuService.clearGrant(param);
					}
				}
			}
		}
		return result;
	}

	@Override
	public int updateRateById(BigDecimal rate, String id) {
		return ((AgentMapper)this.daoMapper).updateRateById(rate,id);
	}

	@Override
	public int updatePayModeById(Byte payMode, String id) {
		return ((AgentMapper)this.daoMapper).updatePayModeById(payMode,id);
	}

}