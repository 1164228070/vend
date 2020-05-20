package com.haiyi.service;

import com.haiyi.domain.User;
import com.haiyi.query.UserQuery;
import com.haiyi.service.base.BaseService;
public interface UserService extends BaseService<User,UserQuery> {

	User login(String loginName,String password);

	boolean updatePassword(Integer userId, String password,Integer agentId);
}