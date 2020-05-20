package com.haiyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.RoleMapper;
import com.haiyi.domain.Role;
import com.haiyi.query.RoleQuery;
import com.haiyi.service.RoleService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.KPException;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleQuery> implements RoleService{

	@Autowired
	public void setRoleMapper(RoleMapper roleMapper){
		this.daoMapper = roleMapper;
	}

	@Override
	public Integer[] findIdsByUserId(Integer userId) {
		return ((RoleMapper)this.daoMapper).findIdsByUserId(userId);
	}

	@Override
	public void bind(Integer[] roleIds, Integer usreId) throws KPException {
		((RoleMapper)this.daoMapper).bind(roleIds,usreId);
	}

	@Override
	public void unbind(Integer userId) {
		((RoleMapper)this.daoMapper).unbind(userId);
	}
}