package com.haiyi.service;

import java.util.List;

import com.haiyi.domain.Agent;
import com.haiyi.domain.Menu;
import com.haiyi.query.AgentQuery;
import com.haiyi.service.base.BaseService;
public interface AgentService extends BaseService<Agent,AgentQuery> {
	List<Menu> queryAllMemus(Integer agentId);

	Agent login(String loginName, String password);


	boolean updatePassword(Integer agentId, String newPassword);
	Agent findByOpenId(String openId);
	int updateOpenIdById( String openId, String id);
}