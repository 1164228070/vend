package com.haiyi.controller;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.CashApply;
import com.haiyi.domain.DictVal;
import com.haiyi.query.CashApplyQuery;
import com.haiyi.service.CashApplyService;
import com.haiyi.utils.DictUtil;
import com.haiyi.utils.StatusConstant;
import com.haiyi.utils.SysConfigUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
    
@Controller
@RequestMapping("/cashApplys")
public class CashApplyController{
	private static final String MSG = "提现申请";
	
	@Autowired
	private CashApplyService cashApplyService;
	 
	
	/**
	 * 分页查询[返回JSON]
	 * @param e
	 * @param request
	 * @return
	 * @throws KPException
	 */
	@RequestMapping(value="", method=RequestMethod.GET, headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody Map<String,Object> list(CashApplyQuery e, HttpServletRequest request) throws KPException{
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
	 *修改设备状态
	 * @param productId
	 * @param sign
	 * @return
	 */
	@RequestMapping(value={"/{cashApplyId}/status"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel updateStatus(@PathVariable("cashApplyId") Integer cashApplyId,@RequestParam(required=true) String sign,@RequestParam(required=true) String remark){
		JsonModel jsonModel = new JsonModel();
		if("y".equals(sign)){  //通过
			cashApplyService.verify(cashApplyId,StatusConstant.CASH_APPLY_STATUS_SUCCESS,remark);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("审核完成");
		}else if("n".equals(sign)){ //失败
			cashApplyService.verify(cashApplyId,StatusConstant.CASH_APPLY_STATUS_FAIL,remark);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("审核失败");
		}
		return jsonModel;
	}

	@RequestMapping(value={"selectStatus"},method=RequestMethod.POST)
	@AuthAnno(verifyLogin=true)
	public @ResponseBody JsonModel selectAgentId() throws KPException{
		List<DictVal> dictVals = DictUtil.getDictValListByType(14);
		JsonModel jsonModel = new JsonModel();
		jsonModel.setData(dictVals);
		jsonModel.setSuccess(true);
		return jsonModel;
	}

}