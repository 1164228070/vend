package com.haiyi.controller;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.User;
import com.haiyi.domain.UserCashConfig;
import com.haiyi.query.UserCashConfigQuery;
import com.haiyi.service.UserCashConfigService;
import com.haiyi.service.UserService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.SysConfigUtil;
import com.haiyi.utils.UserUtil;
import com.maizi.anno.ControllerAnno;
import com.maizi.enums.CashTypeEnum;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userCashConfigs")
@ControllerAnno(addUI = "", detailUI = "", editUI = "", listUI = "")
public class UserCashConfigController {
	private final String MSG = "商户提现配置";
	
	@Autowired
	UserCashConfigService userCashConfigService;
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
	public @ResponseBody Map<String,Object> list(UserCashConfigQuery e, HttpServletRequest request,HttpSession session) throws KPException{
		Map<String,Object> result = new HashMap<String,Object>();
		e.setUserId(UserUtil.getUserId(session));
		PageInfo<UserCashConfig> results = userCashConfigService.findBySelective(e);
		result.put("success", true);
		result.put("msg","查询"+MSG+"成功");  
		result.put(SysConfigUtil.getValue("json-total")+"", results.getTotal());
		result.put(SysConfigUtil.getValue("json-data")+"", results.getList());
		return result;  
	}
	
	/**
	 * 添加
	 * @param t
	 * @param modelMap
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="/",method = RequestMethod.POST,headers="X-Requested-With=XMLHttpRequest")
	//@AuthAnno(token="add")
	@ResponseBody
	public JsonModel add(UserCashConfig userCashConfig,@RequestParam int type,HttpSession session) throws KPException {
		JsonModel jsonModel = new JsonModel();
		
		String typeName = CashTypeEnum.getNameByCode(type);
		if(typeName==null){
			throw new KPException(ExceptionKind.PARAM_E,"提取现方式参数异常");
		}
		
		userCashConfig.setType(typeName);  
		userCashConfig.setCreateDate(DateUtil.getCurrentDate());
		userCashConfig.setUserId(UserUtil.getUserId(session));
		User user=userService.findById(UserUtil.getUserId(session)+"");
		userCashConfig.setAgentId(user.getAgentId());
		userCashConfigService.add(userCashConfig);
		
		jsonModel.setSuccess(true);
		jsonModel.setMsg("添加"+MSG+"成功");
		return jsonModel;
	}
	
	/**
	 * 更新
	 * @param id
	 * @param modelMap
	 * @param t
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value={"/{id}"}, method=RequestMethod.PUT,headers="X-Requested-With=XMLHttpRequest")
	//@AuthAnno(token="edit")
	@ResponseBody
	public JsonModel edit(@PathVariable("id") String id,UserCashConfig userCashConfig,HttpSession session)throws KPException{
		JsonModel jsonModel = new JsonModel();
		if(userCashConfig.getAccNo()!=null && userCashConfig.getAccNo()!=null){
			userCashConfig.setAgentId(AgentUtil.getAgentId(session));
			userCashConfig.setType(CashTypeEnum.getNameByCode(Integer.parseInt(userCashConfig.getType())));
			userCashConfigService.updateById(userCashConfig);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("更新"+MSG+"成功");
		}else {
			UserCashConfig config = userCashConfigService.findById(id);
			jsonModel.setSuccess(true);
			jsonModel.setData(config);
			jsonModel.setMsg("回显"+MSG+"成功");
		}
		return jsonModel;
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value={"/{ids}"}, method=RequestMethod.DELETE,headers="X-Requested-With=XMLHttpRequest")
	//@AuthAnno(token="delete")
	public @ResponseBody JsonModel delete(@PathVariable("ids") String ids){
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			int result;
			if (id.length > 1) {
				// 批量删除
				result = userCashConfigService.deleteByIds(id);
			} else {
				//单个删除
				result = userCashConfigService.deleteById(ids);
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
	}
	
	/**
	 * 根据ID查询  
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	//@AuthAnno
	public @ResponseBody JsonModel findById(@PathVariable String id){
		JsonModel jsonModel = new JsonModel();
				
		UserCashConfig userCashConfig = userCashConfigService.findById(id);
		if(userCashConfig==null){
			jsonModel.setMsg("数据不存在");
		}else{
			jsonModel.setSuccess(true);
			jsonModel.setData(userCashConfig);
		}
		return jsonModel;
	}
}