package com.haiyi.service.impl;

import java.util.List;

import com.haiyi.domain.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.DictValMapper;
import com.haiyi.domain.DictVal;
import com.haiyi.query.DictValQuery;
import com.haiyi.service.DictValService;
import com.haiyi.service.base.impl.BaseServiceImpl;

@Service
public class DictValServiceImpl extends BaseServiceImpl<DictVal, DictValQuery> implements DictValService{

	@Autowired
	public void setDictValMapper(DictValMapper dictValMapper){
		this.daoMapper = dictValMapper;
	}	
	
	@Override
	public void clearByDict(Integer dictId){
		((DictValMapper)this.daoMapper).clearByDict(dictId);
	}

	@Override
	public int batchInsert(List<DictVal> dictVals) {
		return ((DictValMapper)this.daoMapper).batchInsert(dictVals);
	}

}