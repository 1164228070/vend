package com.haiyi.service.impl;

import org.springframework.stereotype.Service;
import com.haiyi.dao.OrderAccountMapper;
import com.haiyi.domain.OrderAccount;
import com.haiyi.query.OrderAccountQuery;
import com.haiyi.service.OrderAccountService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderAccountServiceImpl extends BaseServiceImpl<OrderAccount, OrderAccountQuery> implements OrderAccountService{

	@Autowired
	public void setOrderAccountMapper(OrderAccountMapper orderAccountMapper){
		this.daoMapper = orderAccountMapper;
	}
}