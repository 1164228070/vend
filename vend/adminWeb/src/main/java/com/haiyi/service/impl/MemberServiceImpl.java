package com.haiyi.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.haiyi.domain.User;
import com.haiyi.service.MemberService;
import com.haiyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haiyi.dao.MemberMapper;

import com.haiyi.domain.Member;

import com.haiyi.query.MemberQuery;

import com.haiyi.service.base.impl.BaseServiceImpl;
import com.haiyi.utils.StatusConstant;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.MD5;
import com.maizi.utils.StringUtil;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, MemberQuery> implements MemberService {



	@Autowired
	UserService userService;

	@Autowired
	public void setMemberMapper(MemberMapper memberMapper){
		this.daoMapper = memberMapper;
	}

	@Override
	public int add(Member t) throws KPException {

		return super.add(t);
	}

	@Override
	public boolean activeStatus(Integer memberId) {
		Member member= new Member();
		member.setId(memberId);
		member.setStatus(StatusConstant.MEMBER_STATUS_ACTIVE);
		return updateById(member)==1;
	}

	@Override
	public boolean deactivateStatus(Integer memberId) {
		Member member= new Member();
		member.setId(memberId);
		member.setStatus(StatusConstant.MEMBER_STATUS_DEACTIVATE);
		return updateById(member)==1;
	}

	@Override
	public boolean updatePassword(Integer memberId, String defaultPassword) {
		return ((MemberMapper)this.daoMapper).updatePWD(MD5.GetMD5Code(defaultPassword),memberId)==1;
	}

	@Override
	public void updateCredit(Integer memberId, int creditType, BigDecimal creditValue, Integer userId) {
		Member member = findById(memberId+"");
		if(member==null){
			throw new KPException(ExceptionKind.PARAM_E,"会员id不存在");
		}
		User user = userService.findById(userId+"");
		if(user==null){
			throw new KPException(ExceptionKind.PARAM_E,"员工id不存在");
		}

		Member tempMember = new Member();
		tempMember.setId(member.getId());

		User tempUser = new User();
		tempUser.setId(user.getId());
		tempUser.setAgentId(user.getAgentId());

		String errorMsg=null;
		if(creditType==3){
			//积分
			/*if(user.getScore()<creditValue.intValue()){
				errorMsg="积分不足";
			}else{
				tempUser.setScore(user.getScore()-creditValue.intValue());
				tempMember.setScore(member.getScore()+creditValue.intValue());
			}*/
		}
		if(errorMsg!=null){
			throw new KPException(ExceptionKind.BUSINESS_E,errorMsg);
		}
		tempUser.setRoles(null);
		userService.updateById(tempUser);
		updateById(tempMember);
	}
}