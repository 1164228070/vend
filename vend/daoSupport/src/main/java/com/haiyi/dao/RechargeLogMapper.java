package com.haiyi.dao;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.RechargeLog;
import com.haiyi.query.RechargeLogQuery;
import com.maizi.exception.KPException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeLogMapper extends BaseDao<RechargeLog, RechargeLogQuery> {
    List<RechargeLog> findByMemberId(Integer memberId);
    RechargeLog findByRechargelog(String rechargelog);
    int updateStatus(@Param("id") Integer id, @Param("payStatus") Byte payStatus) throws KPException;
    int update(RechargeLog rechargeLog);
    int add(RechargeLog rechargeLog);


}
