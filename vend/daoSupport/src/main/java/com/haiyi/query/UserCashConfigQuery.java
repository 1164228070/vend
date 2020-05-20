package com.haiyi.query;
import com.haiyi.query.base.BaseQuery;

public class UserCashConfigQuery extends BaseQuery{
	
	private Integer agentId;
	private Integer userId;

	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String getOrder() {
		return null;
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}
}
