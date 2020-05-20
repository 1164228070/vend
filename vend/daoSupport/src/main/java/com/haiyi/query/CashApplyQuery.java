package com.haiyi.query;
import com.haiyi.query.base.BaseQuery;
public class CashApplyQuery extends BaseQuery{
	
	private Integer agentId;
	private String agentName;
	private String accNo;
	private String accUser;
	private String area;
	private Byte status;

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccUser() {
		return accUser;
	}

	public void setAccUser(String accUser) {
		this.accUser = accUser;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	@Override
	public String getOrder() {
		return null;
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}
}
