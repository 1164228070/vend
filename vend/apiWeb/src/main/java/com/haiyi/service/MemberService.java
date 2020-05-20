package com.haiyi.service;
import com.haiyi.domain.Member;

import java.math.BigDecimal;

public interface MemberService{
	Member login(String loginName,String password);

	Member getByOpenId(String openid);

	Member findById(String memberId);
	Member findByAccount(String account);
	Member findByLoginName(String loginName);
	boolean registerByOpenId(Member member);
	int add(Member member);
	void updatePassword(String passwordNew,Integer memberId);
	int updateBalance(BigDecimal balance,String account);
}