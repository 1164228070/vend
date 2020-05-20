package com.haiyi.dao;
import java.util.List;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.DictVal;
import com.haiyi.query.DictValQuery;
public interface DictValMapper extends BaseDao<DictVal,DictValQuery> {
	void clearByDict(Integer dictId);
	
	int batchInsert(List<DictVal> dictVals);
}