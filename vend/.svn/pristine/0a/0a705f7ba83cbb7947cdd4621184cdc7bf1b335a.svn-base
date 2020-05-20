package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.CountingLogMapper;
import com.haiyi.domain.CountingLog;
import com.haiyi.service.CountingLogService;

@Service
public class CountingLogServiceImpl implements CountingLogService{
	
	@Autowired
	CountingLogMapper comsumeLogMapper;

	@Override
	public int add(CountingLog countingLog) {
		return comsumeLogMapper.add(countingLog);
	}
}