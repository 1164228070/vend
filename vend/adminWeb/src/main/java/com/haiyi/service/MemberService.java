package com.haiyi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.haiyi.domain.Member;
import com.haiyi.query.MemberQuery;
import com.haiyi.service.base.BaseService;
public interface MemberService extends BaseService<Member,MemberQuery> {
    boolean activeStatus(Integer memberId);
    boolean deactivateStatus(Integer memberId);

    boolean updatePassword(Integer memberId, String defaultPassword);

    void updateCredit(Integer memberId, int creditType, BigDecimal creditValue, Integer userId);
}