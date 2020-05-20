package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.MemberMapper;
import com.haiyi.domain.Member;
import com.haiyi.service.MemberService;
import com.maizi.utils.MD5;

import java.math.BigDecimal;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberMapper memberMapper;

	/**
	 * 登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Override
	public Member login(String loginName, String password) {
		return memberMapper.login(loginName, MD5.GetMD5Code(password));
	}

	@Override
	public Member getByOpenId(String openid) {
		return memberMapper.getByOpenId(openid);
	}

	/**
	 * 根据Id查找
	 * @param memberId
	 * @return
	 */
	@Override
	public Member findById(String memberId) {
		return memberMapper.findById(memberId);
	}

	@Override
	public Member findByAccount(String account) {
		return memberMapper.findByAccount(account);
	}

	@Override
	public Member findByLoginName(String loginName) {
		return memberMapper.findByLoginName(loginName);
	}

	@Override
	public boolean registerByOpenId(Member member) {
		memberMapper.registerByOpenId(member);
		return true;
	}

	@Override
	public int add(Member member) {
		return memberMapper.add(member);
	}


	@Override
	public void updatePassword(String passwordNew,Integer memberId) {
		this.memberMapper.updatePWD(MD5.GetMD5Code(passwordNew),memberId);
	}

	@Override
	public int updateBalance(BigDecimal balance, String account) {
		return memberMapper.updateBalance(balance,account);
	}
}