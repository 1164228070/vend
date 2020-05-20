package com.haiyi.query;
import com.haiyi.query.base.BaseQuery;
public class AgentQuery extends BaseQuery{
	private Integer agentId;
	private String name;
	
	//状态
	private Byte status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String getOrder() {
		return null;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}

	@Override
	public String toString() {
		return "AgentQuery{" +
				"agentId=" + agentId +
				", name='" + name + '\'' +
				", status=" + status +
				'}';
	}
}
