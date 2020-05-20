package com.haiyi.service;

import java.util.List;

import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;
import com.haiyi.service.base.BaseService;
public interface DevService extends BaseService<Dev,DevQuery> {
	List<Dev> selectDevNum();
	int updateStatus(Integer devId, Integer Obtain);
}