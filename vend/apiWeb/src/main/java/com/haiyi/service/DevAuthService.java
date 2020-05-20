package com.haiyi.service;

import com.haiyi.domain.DevAuth;

public interface DevAuthService {
    DevAuth findDevAuthByDevNum(String devNum);

}
