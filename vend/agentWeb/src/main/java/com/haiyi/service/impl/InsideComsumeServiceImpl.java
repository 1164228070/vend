package com.haiyi.service.impl;

import com.haiyi.dao.InsideComsumeMapper;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.InsideComsumeService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsideComsumeServiceImpl extends BaseServiceImpl<InsideComsume, InsideComsumeQuery> implements InsideComsumeService{

	@Autowired
	public void setInsideComsumeMapper(InsideComsumeMapper insideComsumeMapper){
		this.daoMapper = insideComsumeMapper;
	}
}