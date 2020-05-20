package com.haiyi.service;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.AccountLog;
import com.haiyi.query.AccountLogQuery;
import com.maizi.exception.KPException;
public interface AccountLogService{
    //添加
    int add(AccountLog accountLog) throws KPException;
    
    //根据id查询分页
    AccountLog findById(String id);
    
    //多添加查询
    PageInfo<AccountLog> findBySelective(AccountLogQuery e);
}