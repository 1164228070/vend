package com.haiyi.dao;
import org.apache.ibatis.annotations.Param;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.User;
import com.haiyi.query.UserQuery;

import java.math.BigDecimal;

public interface UserMapper extends BaseDao<User,UserQuery> {
	User findByOpenId(@Param("openId") String openId);
	User login(@Param("loginName")String loginName,@Param("password")String password);
	int updateRateById(@Param("rate") BigDecimal rate, @Param("id") String id);
	int updateOpenIdById(@Param("openId") String openId, @Param("id") String id);
	int updatePassword(@Param("userId")Integer userId,@Param("password") String password,@Param("agentId")Integer agentId);
}