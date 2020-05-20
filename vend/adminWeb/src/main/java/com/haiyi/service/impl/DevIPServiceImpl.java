package com.haiyi.service.impl;

import com.haiyi.dao.DevIPMapper;
import com.haiyi.domain.DevIP;
import com.haiyi.query.DevIPQuery;
import com.haiyi.service.DevIPService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevIPServiceImpl extends BaseServiceImpl<DevIP, DevIPQuery> implements DevIPService {
    @Autowired
    public void setDevMapper(DevIPMapper devIPMapper){
        this.daoMapper = devIPMapper;
    }

}
