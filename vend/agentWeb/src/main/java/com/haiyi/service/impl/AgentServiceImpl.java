package com.haiyi.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.AgentMapper;
import com.haiyi.domain.Agent;
import com.haiyi.domain.Menu;
import com.haiyi.domain.User;
import com.haiyi.query.AgentQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.MenuService;
import com.haiyi.service.UserService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.LogUtils;
import com.maizi.utils.MD5;
import com.maizi.utils.SnowflakeIdFactory;

@Service
public class AgentServiceImpl extends BaseServiceImpl<Agent, AgentQuery> implements AgentService{
	@Autowired
	MenuService menuService;
	
	@Autowired
	UserService userService;

	
	@Autowired
	public void setAgentMapper(AgentMapper agentMapper){
		this.daoMapper = agentMapper;
	}

	/**
	 * 查找指定代理下的所有权限
	 */
	@Override
	public List<Menu> queryAllMemus(Integer agentId) {
		return ((AgentMapper)this.daoMapper).quaeryAllMemus(agentId);
	}

	@Override
	public Agent login(String loginName, String password) {
		return ((AgentMapper)this.daoMapper).login(loginName, MD5.GetMD5Code(password));
	}
 

	
	@Override
	public boolean updatePassword(Integer agentId, String password) {
		return ((AgentMapper)this.daoMapper).updatePassword(agentId,MD5.GetMD5Code(password))==1;
	}

	@Override
	public Agent findByOpenId(String openId) {
		return ((AgentMapper)this.daoMapper).findByOpenId(openId);
	}

	@Override
	public int updateOpenIdById(String openId, String id) {
		return ((AgentMapper)this.daoMapper).updateOpenIdById(openId,id);
	}
}