package com.haiyi.controller;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.DictVal;
import com.haiyi.domain.User;
import com.haiyi.domain.UserCashApply;
import com.haiyi.domain.UserCashConfig;
import com.haiyi.query.UserCashApplyQuery;
import com.haiyi.query.UserCashConfigQuery;
import com.haiyi.service.UserCashApplyService;
import com.haiyi.service.UserCashConfigService;
import com.haiyi.service.UserService;
import com.haiyi.utils.*;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userCashApplys")
public class UserCashApplyController {
	private static final String MSG = "提现申请";
	
	@Autowired
	private UserCashApplyService userCashApplyService;
	
	@Autowired
	private UserCashConfigService userCashConfigService;
	@Autowired
	UserService userService;
	
	/**
	 * 分页查询[返回JSON]
	 * @param e
	 * @param request
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="", method=RequestMethod.GET, headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody Map<String,Object> list(UserCashApplyQuery e, HttpServletRequest request) throws KPException{
		Map<String,Object> result = new HashMap<String,Object>();
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		PageInfo<UserCashApply> results = userCashApplyService.findBySelective(e);
		result.put("success", true);
		result.put("msg","查询"+MSG+"成功");  
		result.put(SysConfigUtil.getValue("json-total")+"", results.getTotal());
		result.put(SysConfigUtil.getValue("json-data")+"", results.getList());
		return result;  
	}
		
    /**
     * 跳转显示页面
     * @param request
     * @param modelMap
     * @return
     * @throws KPException
     */
	@RequestMapping(value="", method=RequestMethod.GET)
	@AuthAnno
    public String list(HttpServletRequest request,ModelMap modelMap) throws KPException{
		return "/userCashApplys/list";
	}
	
	/**
	 * 添加界面
	 * @param modelMap
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value={"/new"}, method={RequestMethod.GET})
	@AuthAnno(token="add")
	public String addUI(ModelMap modelMap,HttpSession session) throws KPException {
		
		UserCashConfigQuery userCashConfigQuery = new UserCashConfigQuery();
		userCashConfigQuery.setPagination(false);
		userCashConfigQuery.setUserId(UserUtil.getUserId(session));

		List<UserCashConfig> userCashConfigs = userCashConfigService.findBySelective(userCashConfigQuery).getList();

		modelMap.addAttribute("userCashConfigs",userCashConfigs);
		   
	    modelMap.addAttribute(new UserCashApply());
		return "/userCashApplys/save";
	}
	/**
	 * 添加
	 * @param t
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="",method = { RequestMethod.POST })
	@AuthAnno(token="add")
	public String add(UserCashApply t,@RequestParam Integer userCashConfigId,HttpSession session) throws KPException {
		
		Integer userId = UserUtil.getUserId(session);
		//记录
		LogUtils.logInfo("代理商[{}]申请提现开始", userId);
		
		UserCashConfig userCashConfig = userCashConfigService.findById(userCashConfigId+"");
		if(userCashConfig==null || !userCashConfig.getUserId().equals(UserUtil.getUserId(session))){
			throw new KPException(ExceptionKind.PARAM_E,"提现配置信息错误");
		}
		User user=userService.findById(UserUtil.getUserId(session)+"");
		t.setUserId(userId);
		t.setUserName(UserUtil.getUserName(session));
		t.setAgentId(user.getAgentId());
		t.setAgentName(user.getAgentName());
		t.setTypeName(userCashConfig.getType());
		
		t.setAccNo(userCashConfig.getAccNo());
		t.setAccUser(userCashConfig.getAccUser());
		t.setArea(userCashConfig.getArea());
		t.setCreateDate(DateUtil.getCurrentDate());
		
		t.setStatus(StatusConstant.CASH_APPLY_STATUS_WAITING);
		this.userCashApplyService.add(t);
		 
		LogUtils.logInfo("代理商[{}]申请提现结束", userId);
		 
		return "redirect:/userCashApplys";
	}


	@RequestMapping(value={"selectStatus"},method=RequestMethod.POST)
	@AuthAnno(verifyLogin=true)
	public @ResponseBody
	JsonModel selectAgentId() throws KPException{
		List<DictVal> dictVals = DictUtil.getDictValListByType(14);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setData(dictVals);
		jsonModel.setSuccess(true);
		return jsonModel;
	}

	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	/*@RequestMapping(value={"/{ids}"}, method={RequestMethod.DELETE},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno(token="delete")
	public @ResponseBody JsonModel delete(@PathVariable("ids") String ids){
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			int result;
			if (id.length > 1) {
				// 批量删除
				result = this.userCashApplyService.deleteByIds(id);
			} else {
				//单个删除
				result = this.userCashApplyService.deleteById(ids);
			}
			if(result==0){
				jsonModel.setSuccess(false);
				jsonModel.setMsg("删除失败");
			}else{
				jsonModel.setSuccess(true);
				jsonModel.setMsg("成功删除" + result + "条记录");
			}
		} catch (Exception e) {
			LogUtils.logError(e.toString());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("删除" + MSG + "失败,请重新刷新数据再删除");
		}
		return jsonModel;
	}*/
}