package com.haiyi.service.impl;

import com.haiyi.dao.RechargeLogMapper;
import com.haiyi.domain.RechargeLog;
import com.haiyi.query.RechargeLogQuery;
import com.haiyi.service.RechargeLogService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeServiceImpl extends BaseServiceImpl<RechargeLog, RechargeLogQuery> implements RechargeLogService{

	@Autowired
	public void setRechargeLogMapper(RechargeLogMapper rechargeLogMapper){
		this.daoMapper = rechargeLogMapper;
	}
}