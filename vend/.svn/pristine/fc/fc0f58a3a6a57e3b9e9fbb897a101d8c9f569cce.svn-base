package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Member;
import com.haiyi.domain.User;
import com.haiyi.query.MemberQuery;
import com.haiyi.service.AgentService;
import com.haiyi.service.MemberService;
import com.haiyi.service.UserService;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/members")
@ControllerAnno(addUI = "/members/save", detailUI = "/members/detail", editUI = "/members/save", listUI = "/members/list")
public class MemberController extends BaseController<Member,MemberQuery>{

	@Autowired
	AgentService agentService;
	@Autowired
	UserService userService;



	@Autowired
	public void setMemberService(MemberService memberService) {
		this.baseService = memberService;
	}


	@Override
	public Member beforeSave(ModelMap modelMap, Member t) throws KPException {
		//添加
		if(t.getId()==null){
			//默认状态
			t.setStatus(StatusConstant.MEMBER_STATUS_ACTIVE);
			//设置默认密码
			t.setPassword(ConstantUtil.DEFAULT_PASSWORD);

			t.setScore(0);
			t.setRegisteDate(DateUtil.getCurrentDate());
			return t;
		}else{
			Member member  = new Member();
			member.setName(t.getName());
			member.setLoginName(t.getLoginName());
			member.setId(t.getId());
			return member;
		}
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {

	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}

	/**
	 *修改会员状态
	 * @param memberId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value={"/{memberId}/status"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel updateStatus(@PathVariable("memberId") Integer memberId,@RequestParam(required=true) String sign){
		JsonModel jsonModel = new JsonModel();
		if("y".equals(sign)){
			jsonModel.setSuccess(((MemberService)this.baseService).activeStatus(memberId));
			jsonModel.setMsg(ConstantUtil.ACTIVE_SUCCESS);
		}else if("n".equals(sign)){
			jsonModel.setSuccess(((MemberService)this.baseService).deactivateStatus(memberId));
			jsonModel.setMsg(ConstantUtil.DEACTIVATE_SUCCESS);
		}
		return jsonModel;
	}

	/**
	 * 重置员工密码
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value={"/{id}/initiation/password"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel initiationPassword(@PathVariable("id") Integer memberId,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		((MemberService)(this.baseService)).updatePassword(memberId,ConstantUtil.DEFAULT_PASSWORD_MW);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("重置密码成功");
		return jsonModel;
	}


	@RequestMapping(value={"/{id}/credit"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(token = "credit")
	public @ResponseBody JsonModel credit(@PathVariable("id") Integer memberId,Integer creditType,BigDecimal creditValue,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		if(1<=creditType && creditType<=5 && creditValue.compareTo(BigDecimal.ZERO)==1){
			try{
				((MemberService)(this.baseService)).updateCredit(memberId,creditType,creditValue,SessionUtil.getCurrentLoginInfoId(session));
				jsonModel.setSuccess(true);

				User tempUser = userService.findById(SessionUtil.getCurrentLoginInfoId(session)+"");
				User user = (User) SessionUtil.getCurrentLoginInfo(session);
				user.setScore(tempUser.getScore());
			}catch (KPException e){
				jsonModel.setMsg(e.getSelfMessage());
			}
		}else{
			jsonModel.setMsg("参数错误");
		}
		return jsonModel;
	}
}