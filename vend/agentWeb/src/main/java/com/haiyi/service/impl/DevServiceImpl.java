package com.haiyi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haiyi.domain.DevAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.DevMapper;
import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;
import com.haiyi.service.DevAuthService;
import com.haiyi.service.DevService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;

@Service
public class DevServiceImpl extends BaseServiceImpl<Dev, DevQuery> implements DevService{

	@Autowired
	DevAuthService devAuthService;

	@Autowired
	public void setDevMapper(DevMapper devMapper){
		this.daoMapper = devMapper;
	}

	@Override
	public int add(Dev t) throws KPException {
		//t.setCommodity(0);

		//判断售货机的编号是否可用,并且使用当前代理的
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("devNum",t.getNum());
		param.put("userId",t.getUserId());
		param.put("status",StatusConstant.DEVAUTH_STATUS_UNUSED);
		devAuthService.updateStatus((int)StatusConstant.DEVAUTH_STATUS_USED,t.getNum()+"");
		return super.add(t);
		/*if(devAuthService.check(param)==1){
			//更新已使用状态
			devAuthService.updateStatus((int)StatusConstant.DEVAUTH_STATUS_USED,t.getUserId(),t.getNum()+"");
			return super.add(t);
		}else{
			throw new KPException(ExceptionKind.PARAM_E,"设备编号错误");
		}*/
	}

	@Override
	public List<Dev> selectDevNum() {
		return ((DevMapper)daoMapper).selectDevNum();
	}

	@Override
	public boolean onLine(Integer devId,Integer userId) {
		Dev dev= new Dev();
		dev.setId(devId);
		dev.setUserId(userId);
		dev.setObtain(StatusConstant.DEV_OBTAIN_OPEN);
		return updateById(dev)==1;
	}

	@Override
	public boolean offLine(Integer devId,Integer userId) {
		Dev dev= new Dev();
		dev.setId(devId);
		dev.setUserId(userId);
		dev.setObtain(StatusConstant.DEV_OBTAIN_CLOSE);
		return updateById(dev)==1;
	}

	@Override
	public boolean onLive(Integer devId, Integer userId) {
		Dev dev= new Dev();
		dev.setId(devId);
		dev.setUserId(userId);
		dev.setState(StatusConstant.DEV_STATUS_ONLINE);
		return updateById(dev)==1;
	}

	@Override
	public boolean offLive(Integer devId, Integer userId) {
		Dev dev= new Dev();
		dev.setId(devId);
		dev.setUserId(userId);
		dev.setState(StatusConstant.DEV_STATUS_OFFLINE);
		return updateById(dev)==1;
	}

	@Override
	public int deleteById(Integer userId, String id) throws KPException {
		//恢复设备编号
		Dev dev = findById(id);
		if(dev==null){
			throw new KPException(ExceptionKind.DELETE_E,"设备数据不存在");
		}
		if(!dev.getUserId().equals(userId)){
			throw new KPException(ExceptionKind.DELETE_E,"请注意您的行为!");
		}
		devAuthService.updateStatus((int)StatusConstant.DEVAUTH_STATUS_UNUSED,dev.getNum()+"");
		devAuthService.updateUserNull(dev.getNum()+"");
		return deleteById(id);
	}

	@Override
	public int deleteByIds(Integer userId, String... id) {
		List<Dev> devs = findByIds(id);
		for(Dev dev : devs){
			if(dev==null){
				throw new KPException(ExceptionKind.DELETE_E,"设备数据不存在");
			}
			if(!dev.getUserId().equals(userId)){
				throw new KPException(ExceptionKind.DELETE_E,"请注意您的行为!");
			}
			devAuthService.updateStatus((int)StatusConstant.DEVAUTH_STATUS_UNUSED,dev.getNum()+"");
			devAuthService.updateUserNull(dev.getNum()+"");
		}
		return deleteByIds(id);
	}
}