package com.haiyi.controller;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.Agent;
import com.haiyi.domain.AgentRole;
import com.haiyi.domain.DictVal;
import com.haiyi.query.AgentQuery;
import com.haiyi.service.AgentRoleService;
import com.haiyi.service.AgentService;
import com.haiyi.utils.DictUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/agents")
@ControllerAnno(addUI = "/agents/save", detailUI = "/agents/detail", editUI = "/agents/save", listUI = "/agents/list")
public class AgentController extends BaseController<Agent,AgentQuery>{
	
	@Autowired
	AgentRoleService agentRoleService;
	@Autowired
	AgentService agentService;

	@Autowired
	public void setAgentService(AgentService agentService) {
		this.baseService = agentService;
	}

	@Override
	public Agent beforeSave(ModelMap modelMap, Agent t) throws KPException {
		if(t.getId()==null){
			t.setPassword(ConstantUtil.DEFAULT_PASSWORD);
			t.setLockMoney(BigDecimal.ZERO);
			t.setLeftMoney(BigDecimal.ZERO);
		}
		
		if(t.getAgentRoleId()!=null){
			AgentRole agentRole = agentRoleService.findById(t.getAgentRoleId()+"");
			if(agentRole!=null){
				t.setAgentRoleName(agentRole.getName());
			}else{
				throw new KPException(ExceptionKind.PARAM_E);
			}
		}
		return t;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
		List<DictVal> dictVals = DictUtil.getDictValListByType(9);
		modelMap.addAttribute("dictVals",dictVals);
		//查找
		List<AgentRole> agentRoles = agentRoleService.findAll();
		modelMap.put("agentRoles",agentRoles);
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}
	
	@RequestMapping(value={"selectStatus"},method=RequestMethod.POST)
	@AuthAnno(verifyLogin=true)
	public @ResponseBody JsonModel selectAgentId() throws KPException{
		List<DictVal> dictVals = DictUtil.getDictValListByType(9);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setData(dictVals);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	@RequestMapping(value={"/updateRate/{ids}"}, method={RequestMethod.PUT})
	@AuthAnno
	public @ResponseBody JsonModel UpateRate(@PathVariable("ids") String ids,String rate){
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			int result=0;
			if (id.length > 1) {
				// 批量删除
				for(String newId : id){
					result = agentService.updateRateById(new BigDecimal(rate),newId);
					result=result+1;
				}
			} else {
				//单个删除
				result = agentService.updateRateById(new BigDecimal(rate),ids);
			}
			if(result==0){
				jsonModel.setSuccess(false);
				jsonModel.setMsg("更新失败");
			}else{
				jsonModel.setSuccess(true);
				jsonModel.setMsg("成功更新" + result + "条记录");
			}
		} catch (Exception e) {
			LogUtils.logError(e.toString());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("更新失败,请重新刷新数据再更新");
		}
		return jsonModel;
	}


	@RequestMapping(value={"/updatePayMode/{ids}"}, method={RequestMethod.PUT})
	@AuthAnno
	public @ResponseBody JsonModel updatePayMode(@PathVariable("ids") String ids,Byte payMode){
		JsonModel jsonModel = new JsonModel();
		try {
			String id[] = ids.split(",");
			int result=0;
			if (id.length > 1) {
				// 批量删除
				for(String newId : id){

					result = agentService.updatePayModeById(payMode,newId);;
					result=result+1;
				}
			} else {
				//单个删除
				result = agentService.updatePayModeById(payMode,ids);
			}
			if(result==0){
				jsonModel.setSuccess(false);
				jsonModel.setMsg("更新失败");
			}else{
				jsonModel.setSuccess(true);
				jsonModel.setMsg("成功更新" + result + "条记录");
			}
		} catch (Exception e) {
			LogUtils.logError(e.toString());
			jsonModel.setSuccess(false);
			jsonModel.setMsg("更新失败,请重新刷新数据再更新");
		}
		return jsonModel;
	}


	@RequestMapping("/toUpdatePayMode/{ids}")
	@AuthAnno
	public String toUpdatePayMode(@PathVariable("ids") String ids, Model model){
		model.addAttribute("ids",ids);
		return "/agents/updatePayMode";
	}

	@RequestMapping("/toUpdateRate/{ids}")
	@AuthAnno
	public String toUpdateRate(@PathVariable("ids") String ids, Model model){
		model.addAttribute("ids",ids);
		return "/agents/updateRate";
	}








}