package com.haiyi.service;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.CashApply;
import com.haiyi.query.CashApplyQuery;
import com.maizi.exception.KPException;
public interface CashApplyService{
	
	int verify(Integer id,Byte status,String remark);
	
	  //根据id查询分页
    CashApply findById(String id);
     
    //多添加查询
    PageInfo<CashApply> findBySelective(CashApplyQuery e);

    //更新
    int updateById(CashApply t) throws KPException;
}