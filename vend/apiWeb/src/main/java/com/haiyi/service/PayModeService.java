package com.haiyi.service;

import com.haiyi.domain.PayMode;

import java.util.List;

public interface PayModeService {
    List<PayMode> findBySelective();

}
