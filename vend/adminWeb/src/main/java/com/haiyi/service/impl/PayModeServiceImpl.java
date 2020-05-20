package com.haiyi.service.impl;

import com.haiyi.dao.PayModeMapper;
import com.haiyi.domain.PayMode;
import com.haiyi.query.PayModeQuery;
import com.haiyi.service.PayModeService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayModeServiceImpl extends BaseServiceImpl<PayMode, PayModeQuery> implements PayModeService {
    @Autowired
    public void setDevMapper(PayModeMapper payModeMapper){
        this.daoMapper = payModeMapper;
    }

}
