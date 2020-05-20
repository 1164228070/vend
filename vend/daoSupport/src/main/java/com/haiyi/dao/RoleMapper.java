package com.haiyi.dao;
import org.apache.ibatis.annotations.Param;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Role;
import com.haiyi.query.RoleQuery;
import com.maizi.exception.KPException;
public interface RoleMapper extends BaseDao<Role,RoleQuery> {
	
	Integer [] findIdsByUserId(Integer userId);
	
	void bind(@Param("roleIds") Integer[] roleIds,@Param("userId") Integer usreId) throws KPException;
	
    void unbind(Integer userId);
}