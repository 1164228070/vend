package com.haiyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.DictMapper;
import com.haiyi.domain.Dict;
import com.haiyi.domain.DictVal;
import com.haiyi.query.DictQuery;
import com.haiyi.service.DictService;
import com.haiyi.service.DictValService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.KPException;

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict, DictQuery> implements DictService{

	
	@Autowired
	DictValService dictValService;
	
	@Autowired
	public void setDictMapper(DictMapper dictMapper){
		this.daoMapper = dictMapper;
	}
	
	@Override
	public int add(Dict t) throws KPException {
		int effectCount =  super.add(t);
		List<DictVal> dictVals = t.getDictVals();
		if(dictVals!=null){
			for(DictVal dictVal : dictVals){
				dictVal.setDict(t);
			}
		}
		//批量添加
		int count = dictValService.batchInsert(dictVals);
		return effectCount;
	}
	
	@Override
	public int updateById(Dict t) throws KPException {
		dictValService.clearByDict(t.getId());
		
		List<DictVal> dictVals = t.getDictVals();
		if(dictVals!=null){
			for(DictVal dictVal : dictVals){
				dictVal.setDict(t);
			}
		}
		//批量添加
		int count = dictValService.batchInsert(dictVals);
		return super.updateById(t);
	}

	@Override
	public List<Dict> selectDict() {
		return ((DictMapper)this.daoMapper).selectDict();
	}
}