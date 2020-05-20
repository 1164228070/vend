package com.haiyi.service.impl;

import com.haiyi.dao.PayModeMapper;
import com.haiyi.domain.PayMode;
import com.haiyi.service.PayModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayModeServicelmpl implements PayModeService{
    @Autowired
    PayModeMapper payModeMapper;


    @Override
    public List<PayMode> findBySelective() {
        return payModeMapper.findBySelective(null);
    }


}
