package com.haiyi.service.impl;

import com.haiyi.dao.UserMapper;
import com.haiyi.domain.Role;
import com.haiyi.domain.User;
import com.haiyi.query.UserQuery;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.KPException;
import com.maizi.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
		int effectCount = super.add(t);
		destributeRole(t);
		return effectCount;
	}
	
	@Override
	public int updateById(User t) throws KPException {
		//忽略修改密码
		t.setPassword(null);
		System.out.println("刚进来权限========"+t.getRoles());
		if(t.getRoles()!=null){
			System.out.println("解绑前权限权限========"+t.getRoles());
			roleService.unbind(t.getId());
			System.out.println("解绑后权限权限========"+t.getRoles());
			destributeRole(t);
		}
		return super.updateById(t);
	}

	@Override
	public User login(String loginName, String password) {
		return ((UserMapper)this.daoMapper).login(loginName, MD5.GetMD5Code(password));
	}
	
	private void destributeRole(User user){
		List<Role> roles = user.getRoles();
		System.out.println("要分配的权限===="+roles);
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
		return ((UserMapper)this.daoMapper).updatePassword(userId,MD5.GetMD5Code(password),agentId)==1;
	}

	@Override
	public boolean deactivateStatus(Integer userId,Integer agentId) {
		User user= new User();
		user.setId(userId);
		user.setAgentId(agentId);
		user.setStatus(StatusConstant.USER_STATUS_DEACTIVATE);
		return updateById(user)==1;
	}

	@Override
	public boolean activeStatus(Integer userId, Integer agentId) {
		User user= new User();
		user.setId(userId);
		user.setAgentId(agentId);
		user.setStatus(StatusConstant.USER_STATUS_ACTIVE);
		return updateById(user)==1;
	}

	@Override
	public void updateAppropriate(User user) {
		super.updateById(user);
	}
	
	@Override
	public void updateName(Integer agentId,Integer userId,String name) {
		User user = new User();
		user.setId(userId);
		user.setAgentId(agentId);
		user.setName(name);
		super.updateById(user);
	}

	@Override
	public int updateRateById(BigDecimal rate, String id) {
		return ((UserMapper)this.daoMapper).updateRateById(rate,id);
	}

	@Override
	public int updateOpenIdById(String openId, String id) {
		return ((UserMapper)this.daoMapper).updateOpenIdById(openId,id);
	}

	@Override
	public User findByOpenId(String openId) {
		return ((UserMapper)this.daoMapper).findByOpenId(openId);
	}
}