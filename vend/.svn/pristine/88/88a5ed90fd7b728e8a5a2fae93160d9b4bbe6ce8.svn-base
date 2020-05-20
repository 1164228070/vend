package com.haiyi.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Menu;
import com.haiyi.query.MenuQuery;
public interface MenuMapper extends BaseDao<Menu,MenuQuery> {
	List<Menu> findByParent(@Param("parentId") Integer parentId);
	
	List<Integer> findByRole(Integer roleId);
	
    int clearGrant(Map<String,Integer> param); 
	
    int grant(Map<String,Integer> param);

	List<Menu> findByRoleIds(Integer[] roleId);
	
	List<Menu> findLevelMenu(); 
}