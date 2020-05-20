package com.haiyi.service.impl;

import com.haiyi.dao.DevAuthMapper;
import com.haiyi.domain.DevAuth;
import com.haiyi.service.DevAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevAuthServicelmpl implements DevAuthService{
    @Autowired
    DevAuthMapper devAuthmapper;


    @Override
    public DevAuth findDevAuthByDevNum(String devNum) {
        return devAuthmapper.findDevAuthByDevNum(devNum);
    }
}
