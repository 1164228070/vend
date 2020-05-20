package com.haiyi.service.impl;

import com.haiyi.dao.DevAuthMapper;
import com.haiyi.domain.DevAuth;
import com.haiyi.query.DevAuthQuery;
import com.haiyi.service.DevAuthService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.KPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevAuthServiceImpl extends BaseServiceImpl<DevAuth, DevAuthQuery> implements DevAuthService{



	@Autowired
	public void setDevAuthMapper(DevAuthMapper devAuthMapper){
		this.daoMapper = devAuthMapper;
	}

	@Override
	public List<DevAuth> findDevAuthByUserId(Integer userId) {
		return ((DevAuthMapper)this.daoMapper).findDevAuthByUserId(userId);
	}

	@Override
	public DevAuth findDevAuthByDevNum(String devNum) {
		return ((DevAuthMapper)this.daoMapper).findDevAuthByDevNum(devNum);
	}

	@Override
	public int updateStatus(Integer status, String devNum) {
		return ((DevAuthMapper)this.daoMapper).updateStatus(status, devNum);
	}

	@Override
	public int updateUserNull(String devNum) {
		return ((DevAuthMapper)this.daoMapper).updateUserNull(devNum);
	}
}