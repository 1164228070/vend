package com.haiyi.query;
import com.haiyi.query.base.BaseQuery;
public class AccountLogQuery extends BaseQuery{
	
	private Integer memberId;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Override
	public String getOrder() {
		return null;
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}
}
