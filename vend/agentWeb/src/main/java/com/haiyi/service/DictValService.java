package com.haiyi.service;

import java.util.List;

import com.haiyi.domain.DictVal;
import com.haiyi.query.DictValQuery;
import com.haiyi.service.base.BaseService;
public interface DictValService extends BaseService<DictVal,DictValQuery> {
	void clearByDict(Integer dictId);

	int batchInsert(List<DictVal> dictVals);
}