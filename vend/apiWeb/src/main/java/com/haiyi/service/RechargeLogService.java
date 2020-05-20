package com.haiyi.service;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.RechargeLog;
import com.haiyi.query.RechargeLogQuery;
import com.maizi.exception.KPException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RechargeLogService {
    PageInfo<RechargeLog> findBySelective(RechargeLogQuery e);
    RechargeLog findById(String id);
    RechargeLog findByRechargelog(String rechargelog);
    List<RechargeLog> findByMemberId(Integer memberId);
    int updateStatus(Integer id, Byte payStatus) throws KPException;
    int update(RechargeLog rechargeLog);
    int add(RechargeLog rechargeLog);


    //----------------------------------支付回调-----------------------------------//
    String nofificationWXPay(Map<String,String> notifyParam);
}
