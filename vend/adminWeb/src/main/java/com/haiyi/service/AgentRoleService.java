package com.haiyi.service;

import java.util.List;
import java.util.Map;

import com.haiyi.domain.AgentRole;
import com.haiyi.query.AgentRoleQuery;
import com.haiyi.service.base.BaseService;
public interface AgentRoleService extends BaseService<AgentRole,AgentRoleQuery> {

	void updateAuth(Integer agentRoleId, Integer[] menuIds);

	int clearGrant(Map<String, Integer> param);

	List<Integer> findMenuIds(Integer agentRoleId);
	
	List<Map<String,Integer>> findUserMenuIdsByAgentRoleId(Integer agentRoleId); 
}