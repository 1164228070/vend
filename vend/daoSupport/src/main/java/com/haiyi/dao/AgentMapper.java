package com.haiyi.dao;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Agent;
import com.haiyi.domain.Menu;
import com.haiyi.query.AgentQuery;
public interface AgentMapper extends BaseDao<Agent,AgentQuery> {
	List<Menu> quaeryAllMemus(Integer agentId);

	int grant(Map<String,Object> param);

	Agent login(@Param("loginName")String loginName,@Param("password")String password);

	int updatePassword(@Param("agentId") Integer agentId,@Param("password") String password);

	int updateLeftMoney(@Param("changeMoney")BigDecimal changeMoney,@Param("agentId") Integer agentId);
	int updateRateById(@Param("rate")BigDecimal rate,@Param("id") String id);
	int updatePayModeById(@Param("payMode")Byte payMode,@Param("id") String id);
	int updateOpenIdById(@Param("openId")String openId,@Param("id") String id);
    Agent findByOrderId(@Param("orderId") String orderId);
    Agent findByOpenId(@Param("openId") String openId);

}