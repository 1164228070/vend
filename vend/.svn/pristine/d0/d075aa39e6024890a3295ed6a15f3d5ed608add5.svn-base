package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.CountingLogMapper;
import com.haiyi.domain.CountingLog;
import com.haiyi.query.CountingLogQuery;
import com.haiyi.service.CountingLogService;
import com.haiyi.service.base.impl.BaseServiceImpl;

@Service
public class CountingLogServiceImpl extends BaseServiceImpl<CountingLog, CountingLogQuery> implements CountingLogService{

	@Autowired
	public void setCountingLogMapper(CountingLogMapper countingLogMapper){
		this.daoMapper = countingLogMapper;
	}
}