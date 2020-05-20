package com.haiyi.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.AgentMapper;
import com.haiyi.domain.Agent;
import com.haiyi.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService{
	
	@Autowired
	private AgentMapper agentMapper;
	
	@Override
	public Agent login(String loginName, String password) {
		Agent agent = this.agentMapper.login(loginName,password);
		return agent;
	}

	@Override
	public int updateNumber(Agent agent) {
		return this.agentMapper.update(agent);
	}

	@Override
	public int updateLeftMoney(BigDecimal changeMoney, Integer agentId) {
		return this.agentMapper.updateLeftMoney(changeMoney,agentId);
	}

	@Override
	public Agent findByOrderId(String orderId) {
		return this.agentMapper.findByOrderId(orderId);
	}

	@Override
	public Agent findById(Integer agentId) {
		return this.agentMapper.findById(agentId+"");
	}
}
