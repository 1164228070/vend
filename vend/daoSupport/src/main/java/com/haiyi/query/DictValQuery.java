package com.haiyi.query;
import com.haiyi.query.base.BaseQuery;
public class DictValQuery extends BaseQuery{
	
	private Integer dictId;
	
	public Integer getDictId() {
		return dictId;
	}

	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	
	@Override
	public String getOrder() {
		return null;
	}

	@Override
	public void addQuery(String condition, Object... param) {
	}
}
