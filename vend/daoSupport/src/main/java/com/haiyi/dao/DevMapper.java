package com.haiyi.dao;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
public interface DevMapper extends BaseDao<Dev,DevQuery> {
	
     Dev devLoginInfo(@Param("userId") Integer userId,@Param("status") Byte status);
     
     Dev devLoginInfoByObtain(@Param("userId") Integer userId,@Param("obtain") Integer obtain);
     
     List<Dev> selectDevNum();

	 Map<String,Object> getPayInfo(Integer productId);

	Dev findByNumAndToken(@Param("num") String num,@Param("token")String token);
	int productCount(@Param("id") Integer id);
    int updateStatus(@Param("devId")Integer devId,@Param("Obtain") Integer Obtain);
    int updateStates(@Param("devId")Integer devId,@Param("state") Integer state);
    int updateDevStates(@Param("devId")Integer devId,@Param("devStatus") Integer devStatus);
    int updateFrothStatus(@Param("devId")Integer devId,@Param("frothStatus") Integer frothStatus);
}