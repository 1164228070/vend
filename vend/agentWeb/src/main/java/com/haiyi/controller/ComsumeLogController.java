package com.haiyi.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.Dev;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.DevQuery;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.InsideComsumeService;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.UserUtil;
import com.maizi.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haiyi.controller.base.BaseController;
import com.haiyi.domain.ComsumeLog;
import com.haiyi.query.ComsumeLogQuery;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.utils.AgentUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.anno.ControllerAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.JsonModel;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comsumeLogs")
@ControllerAnno(addUI = "/comsumeLogs/save", detailUI = "/comsumeLogs/detail", editUI = "/comsumeLogs/save", listUI = "/comsumeLogs/list")
public class ComsumeLogController extends BaseController<ComsumeLog,ComsumeLogQuery>{
	
	@Autowired
	public void setComsumeLogService(ComsumeLogService comsumeLogService) {
		this.baseService = comsumeLogService;
	}

	@Autowired
	ComsumeLogService comsumeLogService;
	@Autowired
	InsideComsumeService insideComsumeService;
	
	@Override
	@AuthAnno
	public Map<String, Object> list(ComsumeLogQuery e, HttpServletRequest request) throws KPException {
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		return super.list(e, request);
	}

	
	@AuthAnno(verifyValid=false)
	@Override
	public String add(ComsumeLog t, ModelMap modelMap) throws KPException {
		return null;
	}
	 
	@AuthAnno(verifyValid=false)
	@Override
	public JsonModel delete(String ids) {
		return null;
	}
	
	@AuthAnno(verifyValid=false)
	@Override
	public String edit(String id, ModelMap modelMap, ComsumeLog t) throws KPException {
		return null;
	}

	@Override
	public ComsumeLog beforeSave(ModelMap modelMap, ComsumeLog t) throws KPException {
		return null;
	}

	@Override
	public void beforeSaveUI(ModelMap modelMap, String id) throws KPException {
	}

	@Override
	protected void beforeDelete(String[] ids) throws KPException {
	}

	@RequestMapping(value={"/getComsumeCounts"}, method={RequestMethod.POST})
	public @ResponseBody
	JsonModel getComsumeCounts(InsideComsumeQuery e, HttpServletRequest request){
		JsonModel jsonModel = new JsonModel();
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		e.setPagination(false);
		PageInfo<InsideComsume> comsumeLogPageInfo = insideComsumeService.findBySelective(e);
		List<InsideComsume> comsumeLogs = comsumeLogPageInfo.getList();
		Map<String,Integer> counts=new HashMap<>();
		Integer finisheds=0;
		Integer unusuals=0;
		Integer unfinisheds=0;
		Integer allOrders=0;
		Date currentDate = DateUtil.getCurrentDate();

		for(InsideComsume comsumeLog:comsumeLogs){
			if(comsumeLog.getPayStatus() == 1){
				String finishedDate=DateUtil.dateToString(comsumeLog.getCreateDate(), "yyyy-MM-dd");
				finisheds++;
			}else if(comsumeLog.getPayStatus() == 2){
				unusuals++;
			}else if(comsumeLog.getPayStatus() == 3){
				unfinisheds++;
			}
			allOrders++;
		}
		counts.put("allOrders",allOrders);
		counts.put("finisheds",finisheds);
		counts.put("unusuals",unusuals);
		counts.put("unfinisheds",unfinisheds);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("订单状态数查询成功");
		jsonModel.setData(counts);
		return jsonModel;
	}


	@RequestMapping(value={"/getCurrentMonthCounts"}, method={RequestMethod.POST})
	public @ResponseBody
	JsonModel getCurrentMonthCounts(ComsumeLogQuery e, HttpServletRequest request){
		JsonModel jsonModel = new JsonModel();
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		e.setPagination(false);
		PageInfo<ComsumeLog> comsumeLogPageInfo = comsumeLogService.findBySelective(e);
		List<ComsumeLog> comsumeLogs = comsumeLogPageInfo.getList();
		Map<String,List<Integer>> counts=new HashMap<>();
		List<Integer> monthAllOrders=new ArrayList<>();
		List<Integer> monthFinisheds=new ArrayList<>();
		List<Integer> monthUnusuals=new ArrayList<>();
		for(int i=0;i<5;i++){
			monthAllOrders.add(0);
			monthFinisheds.add(0);
			monthUnusuals.add(0);
		}
		Date currentDate = DateUtil.getCurrentDate();

		for(ComsumeLog comsumeLog:comsumeLogs){
			int interval = DateUtil.getInterval(comsumeLog.getCreateDate(), currentDate);
			if(28<=interval && interval<35){
				monthAllOrders.set(0,monthAllOrders.get(0)+1);
				if(comsumeLog.getPayStatus() == 1){
					monthFinisheds.set(0,monthFinisheds.get(0)+1);
				}else if(comsumeLog.getPayStatus() == 2){
					monthUnusuals.set(0,monthUnusuals.get(0)+1);
				}
			}else if(21<=interval && interval<28){
				monthAllOrders.set(1,monthAllOrders.get(1)+1);
				if(comsumeLog.getPayStatus() == 1){
					monthFinisheds.set(1,monthFinisheds.get(1)+1);
				}else if(comsumeLog.getPayStatus() == 2){
					monthUnusuals.set(1,monthUnusuals.get(1)+1);
				}
			}else if(14<=interval && interval<21){
				monthAllOrders.set(2,monthAllOrders.get(2)+1);
				if(comsumeLog.getPayStatus() == 1){
					monthFinisheds.set(2,monthFinisheds.get(2)+1);
				}else if(comsumeLog.getPayStatus() == 2){
					monthUnusuals.set(2,monthUnusuals.get(2)+1);
				}
			}else if(7<=interval && interval<14){
				monthAllOrders.set(3,monthAllOrders.get(3)+1);
				if(comsumeLog.getPayStatus() == 1){
					monthFinisheds.set(3,monthFinisheds.get(3)+1);
				}else if(comsumeLog.getPayStatus() == 2){
					monthUnusuals.set(3,monthUnusuals.get(3)+1);
				}
			}else if(0<=interval && interval<7){
				monthAllOrders.set(4,monthAllOrders.get(4)+1);
				if(comsumeLog.getPayStatus() == 1){
					monthFinisheds.set(4,monthFinisheds.get(4)+1);

				}else if(comsumeLog.getPayStatus() == 2){
					monthUnusuals.set(4,monthUnusuals.get(4)+1);
				}
			}
		}
		counts.put("monthAllOrders",monthAllOrders);
		counts.put("monthFinisheds",monthFinisheds);
		counts.put("monthUnusuals",monthUnusuals);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("月统计成功");
		jsonModel.setData(counts);
		return jsonModel;
	}


	@RequestMapping(value={"/getCurrentWeedCounts"}, method={RequestMethod.POST})
	public @ResponseBody
	JsonModel getCurrentWeedCounts(ComsumeLogQuery e, HttpServletRequest request){
		JsonModel jsonModel = new JsonModel();
		String type= SessionUtil.getCurrentLoginInfo(request.getSession()).getType();
		if("user".equals(type)){
			e.setUserId(UserUtil.getUserId(request.getSession()));
		}else{
			e.setAgentId(AgentUtil.getAgentId(request.getSession()));
		}
		e.setPayStatus((byte) 1);
		e.setPagination(false);
		PageInfo<ComsumeLog> comsumeLogPageInfo = comsumeLogService.findBySelective(e);
		List<ComsumeLog> comsumeLogs = comsumeLogPageInfo.getList();
		Map<String,List<Integer>> counts=new HashMap<>();
		List<Integer> weedFinisheds=new ArrayList<>();
		for(int i=0;i<7;i++){
			weedFinisheds.add(0);
		}
		Date currentDate = DateUtil.getCurrentDate();
		for(ComsumeLog comsumeLog:comsumeLogs){
			int interval = DateUtil.getInterval(comsumeLog.getCreateDate(), currentDate);
			if(interval==0){
				weedFinisheds.set(0,weedFinisheds.get(0)+1);
			}else if(interval==1){
				weedFinisheds.set(1,weedFinisheds.get(1)+1);
			}else if(interval==2){
				weedFinisheds.set(2,weedFinisheds.get(2)+1);
			}else if(interval==3){
				weedFinisheds.set(3,weedFinisheds.get(3)+1);
			}else if(interval==4){
				weedFinisheds.set(4,weedFinisheds.get(4)+1);
			}else if(interval==5){
				weedFinisheds.set(5,weedFinisheds.get(5)+1);
			}else if(interval==6){
				weedFinisheds.set(6,weedFinisheds.get(6)+1);
			}
		}
		counts.put("weedFinisheds",weedFinisheds);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("周统计成功");
		jsonModel.setData(counts);
		return jsonModel;
	}



}