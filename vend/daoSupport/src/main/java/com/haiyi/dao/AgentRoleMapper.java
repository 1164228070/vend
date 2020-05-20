package com.haiyi.dao;
import java.util.List;
import java.util.Map;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.AgentRole;
import com.haiyi.query.AgentRoleQuery;
public interface AgentRoleMapper extends BaseDao<AgentRole,AgentRoleQuery> {
	
    int clearGrant(Map<String,Integer> param);

	int grant(Map<String, Integer> param);

	List<Integer> findMenuIds(Integer agentRoleId); 
	
	List<Map<String,Integer>> findUserMenuIdsByAgentRoleId(Integer agentRoleId); 
}