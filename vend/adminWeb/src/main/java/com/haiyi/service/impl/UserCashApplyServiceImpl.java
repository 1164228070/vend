package com.haiyi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyi.dao.UserCashApplyMapper;
import com.haiyi.domain.Agent;
import com.haiyi.domain.Role;
import com.haiyi.domain.User;
import com.haiyi.domain.UserCashApply;
import com.haiyi.query.UserCashApplyQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserService;
import com.haiyi.service.UserCashApplyService;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class UserCashApplyServiceImpl implements UserCashApplyService{
	@Autowired
	UserService userService;
	@Autowired
	AgentService agentService;
	@Autowired
	RoleService roleService;
	@Autowired
	private UserCashApplyMapper userCashApplyMapper;
	@Override
	public UserCashApply findById(String id) {
		return userCashApplyMapper.findById(id);
	}

	@Override
	public PageInfo<UserCashApply> findBySelective(UserCashApplyQuery e) {
    	if(e.isPagination()){
    		PageHelper.startPage(e.getPageStart(),e.getPageSize());
    		Page<UserCashApply> list = (Page<UserCashApply>)this.userCashApplyMapper.findBySelective(e);
    		return list.toPageInfo();
    	}
    	return new PageInfo<>(this.userCashApplyMapper.findBySelective(e));
	}

	@Override
	public int updateById(UserCashApply t) throws KPException {
		return userCashApplyMapper.update(t);
	}

	/**
	 * 审核
	 */
	@Override
	public int verify(Integer id, Byte status,String remark) {
		UserCashApply userCashApply = userCashApplyMapper.findById(id+"");

		if(userCashApply==null){
			throw new KPException(ExceptionKind.PARAM_E,"申请记录不存在");
		}

		if(StatusConstant.CASH_APPLY_STATUS_WAITING!=userCashApply.getStatus()){
			throw new KPException(ExceptionKind.PARAM_E,"无效申请");
		}

		User user = userService.findById(userCashApply.getUserId()+"");
		List<Integer> roleIds = Arrays.asList(roleService.findIdsByUserId(Integer.parseInt(userCashApply.getUserId()+"")));
		for(Integer roleId : roleIds){
			user.getRoles().add(new Role(roleId));
		}
		Agent agent = agentService.findById(user.getAgentId() + "");

		user.getLockMoney();

		User tempUser = new User();
		tempUser.setId(user.getId());
		tempUser.setRoles(user.getRoles());
		tempUser.setLockMoney(user.getLockMoney().subtract(userCashApply.getMoney()));
		userService.updateById(tempUser);
		UserCashApply tempUserCashApply = new UserCashApply();
		BigDecimal userRate = user.getRate();
		BigDecimal agentRate = agent.getRate();
		tempUserCashApply.setId(userCashApply.getId());
		BigDecimal adminMoney=userCashApply.getMoney().multiply(agentRate);
		BigDecimal agentMoney=userCashApply.getMoney().multiply((userRate.subtract(agentRate)));
		BigDecimal income=userCashApply.getMoney().subtract(adminMoney).subtract(agentMoney);
		tempUserCashApply.setIncome(income);
		tempUserCashApply.setAgentMoney(agentMoney);
		tempUserCashApply.setAdminMoney(adminMoney);
		tempUserCashApply.setStatus(status);
		tempUserCashApply.setHandlerDate(DateUtil.getCurrentDate());
		tempUserCashApply.setRemark(remark);
		return userCashApplyMapper.update(tempUserCashApply);
	}
}