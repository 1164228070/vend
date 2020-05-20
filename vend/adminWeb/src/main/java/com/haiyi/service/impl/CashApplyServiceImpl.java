package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyi.dao.CashApplyMapper;
import com.haiyi.domain.Agent;
import com.haiyi.domain.CashApply;
import com.haiyi.query.CashApplyQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.CashApplyService;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;

import java.math.BigDecimal;

@Service
public class CashApplyServiceImpl implements CashApplyService{
	
	@Autowired
	AgentService agentService;
	
	@Autowired
	private CashApplyMapper cashApplyMapper;
 	
	@Override
	public CashApply findById(String id) {
		return cashApplyMapper.findById(id);
	}

	@Override
	public PageInfo<CashApply> findBySelective(CashApplyQuery e) {
    	if(e.isPagination()){
    		PageHelper.startPage(e.getPageStart(),e.getPageSize());
    		Page<CashApply> list = (Page<CashApply>)this.cashApplyMapper.findBySelective(e);
    		return list.toPageInfo();
    	}
    	return new PageInfo<>(this.cashApplyMapper.findBySelective(e));
	}

	@Override
	public int updateById(CashApply t) throws KPException {
		return cashApplyMapper.update(t);
	}

	/**
	 * 审核
	 */
	@Override
	public int verify(Integer id, Byte status,String remark) {
		CashApply cashApply = cashApplyMapper.findById(id+"");
		
		if(cashApply==null){
			throw new KPException(ExceptionKind.PARAM_E,"申请记录不存在");
		}
		
		if(StatusConstant.CASH_APPLY_STATUS_WAITING!=cashApply.getStatus()){
			throw new KPException(ExceptionKind.PARAM_E,"无效申请");
		}
		
		Agent agent = agentService.findById(cashApply.getAgentId()+"");
		
		agent.getLockMoney();
		
		Agent tempAgent = new Agent();
		tempAgent.setId(agent.getId());
		tempAgent.setLockMoney(agent.getLockMoney().subtract(cashApply.getMoney()));
		agentService.updateById(tempAgent);
		
		CashApply tempCashApply = new CashApply();
		BigDecimal agentRate = agent.getRate();
		BigDecimal adminMoney=cashApply.getMoney().multiply(agentRate);
		BigDecimal income=cashApply.getMoney().subtract(adminMoney);
		tempCashApply.setIncome(income);
		tempCashApply.setAdminMoney(adminMoney);
		tempCashApply.setId(cashApply.getId());
		tempCashApply.setStatus(status);
		tempCashApply.setHandlerDate(DateUtil.getCurrentDate());
		tempCashApply.setRemark(remark);
		return cashApplyMapper.update(tempCashApply);
	}
}