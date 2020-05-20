package com.haiyi.service.impl;

import org.springframework.stereotype.Service;
import com.haiyi.dao.CashConfigMapper;
import com.haiyi.domain.CashConfig;
import com.haiyi.query.CashConfigQuery;
import com.haiyi.service.CashConfigService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CashConfigServiceImpl extends BaseServiceImpl<CashConfig, CashConfigQuery> implements CashConfigService{

	@Autowired
	public void setCashConfigMapper(CashConfigMapper cashConfigMapper){
		this.daoMapper = cashConfigMapper;
	}
}