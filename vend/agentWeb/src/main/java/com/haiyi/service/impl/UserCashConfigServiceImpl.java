package com.haiyi.service.impl;

import com.haiyi.dao.UserCashConfigMapper;
import com.haiyi.domain.UserCashConfig;
import com.haiyi.query.UserCashConfigQuery;
import com.haiyi.service.UserCashConfigService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCashConfigServiceImpl extends BaseServiceImpl<UserCashConfig, UserCashConfigQuery> implements UserCashConfigService {

	@Autowired
	public void setUserCashConfigMapper(UserCashConfigMapper usercashConfigMapper){
		this.daoMapper = usercashConfigMapper;
	}
}