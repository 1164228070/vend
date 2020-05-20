package com.haiyi.service.impl;

import org.springframework.stereotype.Service;
import com.haiyi.dao.AccountLogMapper;
import com.haiyi.domain.AccountLog;
import com.haiyi.query.AccountLogQuery;
import com.haiyi.service.AccountLogService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountLogServiceImpl extends BaseServiceImpl<AccountLog, AccountLogQuery> implements AccountLogService{

	@Autowired
	public void setAccountLogMapper(AccountLogMapper accountLogMapper){
		this.daoMapper = accountLogMapper;
	}
}