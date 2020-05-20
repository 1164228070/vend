package com.haiyi.service;

import com.haiyi.domain.User;
import com.haiyi.query.UserQuery;
import com.haiyi.service.base.BaseService;

import java.math.BigDecimal;

public interface UserService extends BaseService<User,UserQuery> {

	User login(String loginName,String password);
 
	boolean updatePassword(Integer userId, String getMD5Code, Integer agentId);

	boolean activeStatus(Integer userId, Integer agentId);

	boolean deactivateStatus(Integer userId, Integer agentId);
	
	void updateAppropriate(User user);
 
	void updateName(Integer agentId, Integer userId, String name);
	int updateRateById(BigDecimal rate, String id);

	int updateOpenIdById( String openId, String id);

	User findByOpenId(String openId);

}