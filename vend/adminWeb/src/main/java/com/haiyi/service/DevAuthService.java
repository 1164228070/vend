package com.haiyi.service;

import java.util.List;

import com.haiyi.domain.DevAuth;
import com.haiyi.query.DevAuthQuery;
import com.haiyi.service.base.BaseService;
public interface DevAuthService extends BaseService<DevAuth,DevAuthQuery> {
	public List<DevAuth> findDevAuthByUserId(Integer userId);

	DevAuth findDevAuthByDevNum(String devNum);

	public void batchAddCardNum(String[] split, DevAuth t);

	int updateIPById(String ip, String id);
}