package com.haiyi.service;

import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;

import java.util.List;
import java.util.Map;

public interface DevService{
	
	Dev devLoginInfo(Integer agentId);
	
	Dev devLoginInfoByObtain(Integer agentId);

	int updateObtain(Integer devId, Integer obtain);
	int updateStates(Integer devId, Integer state);
	int updateDevStates(Integer devId, Integer devStatus);
	int updateFrothStatus(Integer devId, Integer frothStatus);

	List<Dev> findBySelective(DevQuery devQuery);

	Map<String,Object> getPayInfo(Integer productId);
	
	Dev findById(Integer devId);

	//login
	Dev login(String devNum,String token);

	Dev findByNumAndToken(String num,String token);
	

	


	Dev findByNum(String devNum);
}