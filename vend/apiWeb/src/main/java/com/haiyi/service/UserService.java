package com.haiyi.service;

import com.haiyi.domain.User;

public interface UserService {
	User login(String loginName,String password);
	User findById(Integer userId);
	int update(User user);

}
