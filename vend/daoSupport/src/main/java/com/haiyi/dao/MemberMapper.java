package com.haiyi.dao;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Member;
import com.haiyi.query.MemberQuery;
public interface MemberMapper extends BaseDao<Member,MemberQuery> {
	Member login(@Param("loginName")String loginName,@Param("password")String password);
	Member getByOpenId(String openid);
	Member findByAccount(String account);
	Member findByLoginName(String loginName);
	int add(Member member);  //获取微信用户信息写进数据库专用
	boolean registerByOpenId(Member member);
	int updateBalance(@Param("balance") BigDecimal balance,@Param("account") String account);

	//Member queryBalance(@Param("cardNum")String cardNum);//TODO
	int updatePWD(@Param("password") String password,@Param("memberId") Integer memberId);
}