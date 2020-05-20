package com.haiyi.service;

import java.util.Date;
import java.util.List;

import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;
import com.haiyi.service.base.BaseService;
import com.maizi.exception.KPException;
public interface DevService extends BaseService<Dev,DevQuery> {
	List<Dev> selectDevNum();
	 
    int deleteById(Integer userId,String id) throws KPException;

	int deleteByIds(Integer userId,String ... id);

	boolean onLine(Integer devId, Integer userId);

	boolean offLine(Integer devId, Integer userId);

	boolean onLive(Integer devId, Integer userId);

	boolean offLive(Integer devId, Integer userId);
}