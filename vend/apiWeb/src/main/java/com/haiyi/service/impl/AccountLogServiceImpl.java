package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyi.dao.AccountLogMapper;
import com.haiyi.domain.AccountLog;
import com.haiyi.query.AccountLogQuery;
import com.haiyi.service.AccountLogService;
import com.maizi.exception.KPException;

@Service
public class AccountLogServiceImpl implements AccountLogService{
	
	@Autowired
	private AccountLogMapper accountLogMapper;

	@Override
	public int add(AccountLog accountLog) throws KPException {
		return accountLogMapper.add(accountLog);
	}

	@Override
	public AccountLog findById(String id) {
		return accountLogMapper.findById(id);
	}

	@Override
	public PageInfo<AccountLog> findBySelective(AccountLogQuery e) {
		if(e.isPagination()){
    		PageHelper.startPage(e.getPageStart(),e.getPageSize());
    		Page<AccountLog> list = (Page<AccountLog>)accountLogMapper.findBySelective(e);
    		return list.toPageInfo();
    	}
    	return new PageInfo<>(accountLogMapper.findBySelective(e));
	}
}