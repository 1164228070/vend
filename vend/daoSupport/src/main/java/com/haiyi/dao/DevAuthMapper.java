package com.haiyi.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.DevAuth;
import com.haiyi.query.DevAuthQuery;
public interface DevAuthMapper extends BaseDao<DevAuth,DevAuthQuery> {
	List<DevAuth> findDevAuthByUserId(Integer userId);
	DevAuth findDevAuthByDevNum(String devNum);
	int updateStatus(@Param("status") Integer status,@Param("devNum") String devNum);
	int updateUserNull(String devNum);
	int updateIPById(@Param("ip")String ip,@Param("id") String id);
}