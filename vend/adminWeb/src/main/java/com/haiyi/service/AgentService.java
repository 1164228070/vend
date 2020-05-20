package com.haiyi.service;

import com.haiyi.domain.Agent;
import com.haiyi.query.AgentQuery;
import com.haiyi.service.base.BaseService;

import java.math.BigDecimal;

public interface AgentService extends BaseService<Agent,AgentQuery> {
    int updateRateById(BigDecimal rate,String id);
    int updatePayModeById(Byte payMode,String id);
}