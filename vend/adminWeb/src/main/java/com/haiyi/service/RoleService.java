package com.haiyi.service;

import com.haiyi.domain.Role;
import com.haiyi.query.RoleQuery;
import com.haiyi.service.base.BaseService;
import com.maizi.exception.KPException;
public interface RoleService extends BaseService<Role,RoleQuery> {
	Integer [] findIdsByUserId(Integer userId);
	
	void bind(Integer[] roleIds,Integer usreId) throws KPException;
	
    void unbind(Integer userId);
}