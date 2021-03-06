package com.haiyi.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.DevAuthMapper;
import com.haiyi.domain.DevAuth;
import com.haiyi.query.DevAuthQuery;
import com.haiyi.service.DevAuthService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;

@Service
public class DevAuthServiceImpl extends BaseServiceImpl<DevAuth, DevAuthQuery> implements DevAuthService{






	@Autowired
	public void setDevAuthMapper(DevAuthMapper devAuthMapper){
		this.daoMapper = devAuthMapper;
	}

	@Override
	public List<DevAuth> findDevAuthByUserId(Integer userId) {
		return ((DevAuthMapper)this.daoMapper).findDevAuthByUserId(userId);
	}

	@Override
	public DevAuth findDevAuthByDevNum(String devNum) {
		return ((DevAuthMapper)this.daoMapper).findDevAuthByDevNum(devNum);
	}

	@Override
	public int updateById(DevAuth t) throws KPException {
		//授权了不能再次更新
		DevAuth devAuth = this.daoMapper.findById(t.getId()+"");
		if(devAuth == null ||StatusConstant.DEVAUTH_STATUS_USED==devAuth.status){
			throw new KPException(ExceptionKind.BUSINESS_E,"["+t.getDevNum()+"设备号已授权,不能修改]");
		}
		
		//判断是否已经存在
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id",t.getId());
		param.put("devNum",t.getDevNum());
		param.put("userId",devAuth.getUserId());
		int count = check(param);
		if(count>0){
			throw new KPException(ExceptionKind.UPDATE_E,"修改设备授权,"+t.getDevNum()+"设备号已经存在");
		}
		t.setUserId(null);  //代理号不能修改
		//t.setIp(t.getDevIP().getIp());
		System.out.println("uuuuuuuuuuu");
		System.out.println(t.toString());
		return super.updateById(t);
	}

	@Override
	public void batchAddCardNum(String[] devNums, DevAuth devAuth) {
		System.out.println("batchAddCardNum=========");
		devAuth.setStatus(StatusConstant.DEVAUTH_STATUS_UNUSED);
		//排除重复元素
		Set<String> temp = new HashSet<String>();
		for(String devNum : devNums){
			temp.add(devNum);
		}
		for(String devNum : temp){
			devAuth.setDevNum(devNum);
			//判断是否已经存在
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("devNum",devNum);
			param.put("userId",devAuth.getUserId());
			int count = check(param);
			if(count>0){
				throw new KPException(ExceptionKind.UPDATE_E,"修改设备授权,"+devNum+"设备号已经存在");
			}
			this.add(devAuth);
		}
	}

	@Override
	public int updateIPById(String ip, String id) {
		return ((DevAuthMapper)this.daoMapper).updateIPById(ip,id);
	}
}