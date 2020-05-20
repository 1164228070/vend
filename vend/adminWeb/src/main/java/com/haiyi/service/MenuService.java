package com.haiyi.service;

import java.util.List;
import java.util.Map;

import com.haiyi.domain.Menu;
import com.haiyi.query.MenuQuery;
import com.haiyi.service.base.BaseService;
import com.maizi.exception.KPException;
public interface MenuService extends BaseService<Menu,MenuQuery> {

	List<Menu> findByParent(Integer parentId);
	
	List<Integer> findByRole(Integer roleId);
	
	void updateAuth(Integer roleId,Integer[] privIds) throws KPException;
	
    int clearGrant(Map<String,Integer> param);

	List<Menu> findByRoleIds(Integer[] roleId);

	List<Menu> findLevelMenu(); 
}