package com.haiyi.service;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.InsideComsumeQuery;

public interface InsideComsumeService {
	PageInfo<InsideComsume> findBySelective(InsideComsumeQuery e);
	
	int update(InsideComsume insideComsume);
	int add(InsideComsume insideComsume);
	
}