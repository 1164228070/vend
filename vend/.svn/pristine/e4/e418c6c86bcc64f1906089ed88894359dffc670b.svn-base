package com.haiyi.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.CashApplyMapper;
import com.haiyi.domain.Agent;
import com.haiyi.domain.CashApply;
import com.haiyi.query.CashApplyQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.CashApplyService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.LogUtils;

@Service
public class CashApplyServiceImpl extends BaseServiceImpl<CashApply, CashApplyQuery> implements CashApplyService{
	
	@Autowired
	AgentService agentService;

	@Autowired
	public void setCashApplyMapper(CashApplyMapper cashApplyMapper){
		this.daoMapper = cashApplyMapper;
	}
	
	@Override
	public int add(CashApply t) throws KPException {
		Agent agent = agentService.findById(t.getAgentId()+"");
        
		//判断资金
		if(t.getMoney().compareTo(new BigDecimal(ConstantUtil.MIN_MONEY))==-1){
			throw new KPException(ExceptionKind.BUSINESS_E,"最小提现金额不能小于"+ConstantUtil.MIN_MONEY);
		}
		if(agent.getLeftMoney().compareTo(t.getMoney())==-1){
			throw new KPException(ExceptionKind.BUSINESS_E,"余额不足");
		}
		Agent tempAgent = new Agent();
		tempAgent.setId(agent.getId());
		tempAgent.setLeftMoney(agent.getLeftMoney().subtract(t.getMoney()));  //可用
		
		LogUtils.logInfo("提现前余额[{}]元,提现[{}]元",agent.getLeftMoney(),t.getMoney());
		
		//冻结资金
		tempAgent.setLockMoney(agent.getLockMoney().add(t.getMoney()));
		agentService.updateById(tempAgent);
		
		return super.add(t);
	}
}