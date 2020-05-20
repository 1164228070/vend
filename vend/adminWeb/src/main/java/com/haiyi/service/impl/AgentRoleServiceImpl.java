package com.haiyi.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.AgentRoleMapper;
import com.haiyi.domain.AgentRole;
import com.haiyi.query.AgentRoleQuery;
import com.haiyi.service.AgentRoleService;
import com.haiyi.service.AgentService;
import com.haiyi.service.MenuService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;

@Service
public class AgentRoleServiceImpl extends BaseServiceImpl<AgentRole, AgentRoleQuery> implements AgentRoleService{

	@Autowired
        AgentService agentService;	
	
	@Autowired
	MenuService menuService;	
	
	@Autowired
	public void setAgentRoleMapper(AgentRoleMapper agentRoleMapper){
		this.daoMapper = agentRoleMapper;
	}
	
	@Override
	public int clearGrant(Map<String,Integer> param) {
		return((AgentRoleMapper)this.daoMapper).clearGrant(param);
	}

	@Override
	public void updateAuth(Integer agentRoleId, Integer[] menuIds) {
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("roleId",agentRoleId);
		//清空已有的权限数据
		clearGrant(param);
		
		if(menuIds!=null){
			int menuCount = menuIds.length;
			int effectCount=0;
			for(int i=0;i<menuCount;i++){
				param.put("menuId",menuIds[i]);
				effectCount +=((AgentRoleMapper)this.daoMapper).grant(param);  //TODO 建议批量添加
			}
			if(effectCount!=menuCount){
				throw new KPException(ExceptionKind.PARAM_E);
			}
		}
		//修改关联代理的用户的巨额
		if(menuIds==null){
			//清除所有权限
			List<Map<String,Integer>> ownMenus = findUserMenuIdsByAgentRoleId(agentRoleId);
			for(Map<String,Integer> ownMenu : ownMenus){
				menuService.clearGrant(ownMenu);
			}
		}else{
			List<Integer> temp = Arrays.asList(menuIds);
			List<Map<String,Integer>> ownMenus = findUserMenuIdsByAgentRoleId(agentRoleId);
			for(Map<String,Integer> ownMenu : ownMenus){
				if(!temp.contains(ownMenu.get("menuId"))){
					menuService.clearGrant(ownMenu);
				}
			}
		}
	}

	/**
	 * 查找当前代理角色下的菜单id集合
	 */
	@Override
	public List<Integer> findMenuIds(Integer agentRoleId) {
		return((AgentRoleMapper)this.daoMapper).findMenuIds(agentRoleId);
	}

	@Override
	public List<Map<String,Integer>> findUserMenuIdsByAgentRoleId(Integer agentRoleId) {
		return((AgentRoleMapper)this.daoMapper).findUserMenuIdsByAgentRoleId(agentRoleId);
	}
}