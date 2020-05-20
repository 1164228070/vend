package com.haiyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.UserMapper;
import com.haiyi.domain.Role;
import com.haiyi.domain.User;
import com.haiyi.query.UserQuery;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.KPException;
import com.maizi.utils.MD5;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserQuery> implements UserService{

	@Autowired
	public void setUserMapper(UserMapper userMapper){
		this.daoMapper = userMapper;
	}
	
	@Autowired
	RoleService roleService;
	
	@Override
	public int add(User t) throws KPException {
		t.setPassword(MD5.GetMD5Code(t.getPassword()));
		System.out.println(t.toString());
		int effectCount = super.add(t);
		destributeRole(t);
		return effectCount;
	}
	
	@Override
	public int updateById(User t) throws KPException {
		roleService.unbind(t.getId());
		destributeRole(t);
		return super.updateById(t);
	}

	@Override
	public User login(String loginName, String password) {
		return ((UserMapper)this.daoMapper).login(loginName, MD5.GetMD5Code(password));
	}
	
	private void destributeRole(User user){
		List<Role> roles = user.getRoles();
		if(roles==null || roles.size()==0){
			return;
		}
		int size = roles.size();
		Integer roleIds [] = new Integer[size]; 
		for(int i=0;i<size;i++){
			roleIds[i] = roles.get(i).getId();
		}
		roleService.bind(roleIds, user.getId());
	}

	@Override
	public boolean updatePassword(Integer userId, String password,Integer agentId) {
		return ((UserMapper)this.daoMapper).updatePassword(userId,password,agentId)==1;
	}
}