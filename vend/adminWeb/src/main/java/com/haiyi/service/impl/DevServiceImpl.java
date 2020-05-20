package com.haiyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.DevMapper;
import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;
import com.haiyi.service.DevService;
import com.haiyi.service.base.impl.BaseServiceImpl;

@Service
public class DevServiceImpl extends BaseServiceImpl<Dev, DevQuery> implements DevService{

	@Autowired
	public void setDevMapper(DevMapper devMapper){
		this.daoMapper = devMapper;
	}

	@Override
	public List<Dev> selectDevNum() {
		return ((DevMapper)daoMapper).selectDevNum();
	}

	@Override
	public int updateStatus(Integer devId, Integer Obtain) {
		return ((DevMapper)daoMapper).updateStatus(devId,Obtain);
	}


}