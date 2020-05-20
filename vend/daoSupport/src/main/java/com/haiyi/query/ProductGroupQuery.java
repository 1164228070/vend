package com.haiyi.query;
import com.haiyi.domain.Agent;
import com.haiyi.domain.User;
import com.haiyi.query.base.BaseQuery;

public class ProductGroupQuery extends BaseQuery {
	private String name;
	
	private Agent agent;
	private String agentName;
	private User user;
	private String userName;
	private String num;
	
	private Integer devId;
	private String devNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getDevNum() {
		return devNum;
	}

	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}





	@Override
	public String getOrder() {
		return null;
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}

	@Override
	public String toString() {
		return "ProductGroupQuery{" +
				"name='" + name + '\'' +
				", agent=" + agent +
				", agentName='" + agentName + '\'' +
				", user=" + user +
				", userName='" + userName + '\'' +
				", num='" + num + '\'' +
				", devId=" + devId +
				", devNum='" + devNum + '\'' +
				'}';
	}
}
