package com.haiyi.service.impl;

import org.springframework.stereotype.Service;
import com.haiyi.dao.ComsumeLogMapper;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.query.ComsumeLogQuery;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ComsumeLogServiceImpl extends BaseServiceImpl<ComsumeLog, ComsumeLogQuery> implements ComsumeLogService{

	@Autowired
	public void setComsumeLogMapper(ComsumeLogMapper comsumeLogMapper){
		this.daoMapper = comsumeLogMapper;
	}
}