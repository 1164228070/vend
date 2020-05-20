package com.haiyi.service;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.UserCashApply;
import com.haiyi.query.UserCashApplyQuery;
import com.maizi.exception.KPException;

public interface UserCashApplyService {
	
	int verify(Integer id, Byte status, String remark);
	
	  //根据id查询分页
    UserCashApply findById(String id);
     
    //多添加查询
    PageInfo<UserCashApply> findBySelective(UserCashApplyQuery e);

    //更新
    int updateById(UserCashApply t) throws KPException;
}