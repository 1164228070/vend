package com.haiyi.service.impl;

import com.haiyi.dao.UserCashApplyMapper;
import com.haiyi.domain.Role;
import com.haiyi.domain.User;
import com.haiyi.domain.UserCashApply;
import com.haiyi.query.UserCashApplyQuery;
import com.haiyi.service.RoleService;
import com.haiyi.service.UserCashApplyService;
import com.haiyi.service.UserService;
import com.haiyi.service.base.impl.BaseServiceImpl;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class UserCashApplyServiceImpl extends BaseServiceImpl<UserCashApply, UserCashApplyQuery> implements UserCashApplyService{

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@Autowired
	public void setUserCashApplyMapper(UserCashApplyMapper userCashApplyMapper){
		this.daoMapper = userCashApplyMapper;
	}

	@Override
	public int add(UserCashApply t) throws KPException {
		User user = userService.findById(t.getUserId()+"");
		List<Integer> roleIds = Arrays.asList(roleService.findIdsByUserId(Integer.parseInt(t.getUserId()+"")));
		for(Integer roleId : roleIds){
			user.getRoles().add(new Role(roleId));
		}
		System.out.println("最开始的权限"+user.getRoles());
		//判断资金
		if(t.getMoney().compareTo(new BigDecimal(ConstantUtil.MIN_MONEY))==-1){
			throw new KPException(ExceptionKind.BUSINESS_E,"最小提现金额不能小于"+ConstantUtil.MIN_MONEY);
		}
		if(user.getLeftMoney().compareTo(t.getMoney())==-1){
			throw new KPException(ExceptionKind.BUSINESS_E,"余额不足");
		}
		User tempUser = new User();
		tempUser.setId(user.getId());
		tempUser.setRoles(user.getRoles());
		tempUser.setLeftMoney(user.getLeftMoney().subtract(t.getMoney()));  //可用

		LogUtils.logInfo("提现前余额[{}]元,提现[{}]元",user.getLeftMoney(),t.getMoney());

		//冻结资金
		tempUser.setLockMoney(user.getLockMoney().add(t.getMoney()));

		userService.updateById(tempUser);

		return super.add(t);
	}
}