package com.haiyi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.haiyi.domain.DictVal;
import com.haiyi.utils.DictUtil;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.CashApply;
import com.haiyi.domain.CashConfig;
import com.haiyi.query.CashApplyQuery;
import com.haiyi.query.CashConfigQuery;
import com.haiyi.service.CashApplyService;
import com.haiyi.service.CashConfigService;
import com.haiyi.utils.AgentUtil;
import com.haiyi.utils.StatusConstant;
import com.haiyi.utils.SysConfigUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.ExceptionKind;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.LogUtils;
    
@Controller
@RequestMapping("/cashApplys")
public class CashApplyController{
	private static final String MSG = "提现申请";
	
	@Autowired
	private CashApplyService cashApplyService;
	
	@Autowired
	private CashConfigService cashConfigService;
	
	/**
	 * 分页查询[返回JSON]
	 * @param e
	 * @param request
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="", method=RequestMethod.GET, headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody Map<String,Object> list(CashApplyQuery e, HttpServletRequest request) throws KPException{
		e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		Map<String,Object> result = new HashMap<String,Object>();
		PageInfo<CashApply> results = cashApplyService.findBySelective(e);
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
		return "/cashApplys/list";
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
		
		CashConfigQuery cashConfigQuery = new CashConfigQuery();
		cashConfigQuery.setPagination(false);
		cashConfigQuery.setAgentId(AgentUtil.getAgentId(session));

		List<CashConfig> cashConfigs = cashConfigService.findBySelective(cashConfigQuery).getList();

		modelMap.addAttribute("cashConfigs",cashConfigs);
		   
	    modelMap.addAttribute(new CashApply());
		return "/cashApplys/save";
	}
	/**
	 * 添加
	 * @param t
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="",method = { RequestMethod.POST })
	@AuthAnno(token="add")
	public String add(CashApply t,@RequestParam Integer cashConfigId,HttpSession session) throws KPException {
		
		Integer agentId = AgentUtil.getAgentId(session);
		//记录
		LogUtils.logInfo("代理商[{}]申请提现开始", agentId);
		
		CashConfig cashConfig = cashConfigService.findById(cashConfigId+"");
		if(cashConfig==null || !cashConfig.getAgentId().equals(AgentUtil.getAgentId(session))){
			throw new KPException(ExceptionKind.PARAM_E,"提现配置信息错误");
		}
		t.setAgentId(agentId);
		t.setAgentName(AgentUtil.getAgentName(session));
		t.setTypeName(cashConfig.getType());
		
		t.setAccNo(cashConfig.getAccNo());
		t.setAccUser(cashConfig.getAccUser());
		t.setArea(cashConfig.getArea());
		t.setCreateDate(DateUtil.getCurrentDate());
		
		t.setStatus(StatusConstant.CASH_APPLY_STATUS_WAITING);
		this.cashApplyService.add(t);
		 
		LogUtils.logInfo("代理商[{}]申请提现结束", agentId);
		 
		return "redirect:/cashApplys";
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
				result = this.cashApplyService.deleteByIds(id);
			} else {
				//单个删除
				result = this.cashApplyService.deleteById(ids);
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