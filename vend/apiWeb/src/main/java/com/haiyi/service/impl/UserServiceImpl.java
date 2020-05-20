package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.UserMapper;
import com.haiyi.domain.User;
import com.haiyi.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User login(String loginName, String password) {
		return  this.userMapper.login(loginName,password);
	}

	@Override
	public User findById(Integer userId) {
		return this.userMapper.findById(userId+"");
	}

	@Override
	public int update(User user) {
		return this.userMapper.update(user);
	}

}