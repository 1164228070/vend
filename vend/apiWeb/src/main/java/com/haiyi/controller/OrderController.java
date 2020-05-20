package com.haiyi.controller;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.*;
import com.haiyi.query.ComsumeLogQuery;
import com.haiyi.service.*;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.SessionUtil;
import com.haiyi.utils.StatusConstant;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.KPException;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.SnowflakeIdFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	ComsumeLogService comsumeLogService;
	@Autowired
	MemberService memberService;
	@Autowired
	DevService devService;
	@Autowired
	UserService userService;
	@Autowired
	ProductService productService;

	@Autowired
	InsideComsumeService insideComsumeService;




	@Autowired
	RechargeLogService rechargeLogService;




	private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);



	@RequestMapping(value="/{orderId}",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody JsonModel detail(@PathVariable String orderId){
		JsonModel jsonModel = new JsonModel();
		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		if(comsumeLog!=null){
			jsonModel.setSuccess(true);
			jsonModel.setData(comsumeLog);
			return jsonModel;
		}else{
			jsonModel.setSuccess(false);
			jsonModel.setMsg("参数错误");
			return jsonModel;
		}
	}

	/**
	 * 统一下单
	 * @param session
	 * @return
	 */

	@RequestMapping(value="/unifiedOrder",method=RequestMethod.POST)
	public @ResponseBody JsonModel unifiedorder(@RequestParam Integer productId,@RequestParam String devNum, @RequestParam String price, HttpSession session){
		JsonModel jsonModel = new JsonModel();
		//TODO 参数校验
		//创建外部表
		ComsumeLog comsumeLog=new ComsumeLog();
		String orderId=String.valueOf(snow.nextId());
		comsumeLog.setOrderId(orderId);
		comsumeLog.setDevNum(devNum);
		Dev dev=devService.findByNum(devNum);
		User user = userService.findById(dev.getUserId());
		comsumeLog.setUserName(user.getName());
		comsumeLog.setAgentName(user.getAgentName());
		comsumeLog.setUserId(dev.getUserId());
		comsumeLog.setAgentId(dev.getAgentId());
		comsumeLog.setProductId(productId);
		comsumeLog.setPayAmount(new BigDecimal(price));
		Product product = productService.findById(productId);
		comsumeLog.setProductName(product.getName());
		comsumeLog.setCost(product.getCost());
		comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNGOING);
		comsumeLog.setDevName(dev.getName());
		comsumeLog.setTradeType(StateConfig.TRADETYPE_COMSUME);
		comsumeLog.setReqType(StateConfig.TCP_REQUEST);
		Date currentDate = DateUtil.getCurrentDate();
		comsumeLog.setCreateDate(currentDate);
		comsumeLogService.add(comsumeLog);

		//创建内部表
		InsideComsume insideComsume=new InsideComsume();
		insideComsume.setOrderId(orderId);
		insideComsume.setProductId(productId);
		insideComsume.setProductName(product.getName());
		insideComsume.setUserId(dev.getUserId());
		insideComsume.setUserName(user.getName());
		insideComsume.setAgentId(user.getAgentId());
		insideComsume.setAgentName(user.getAgentName());
		insideComsume.setDevNum(devNum);
		insideComsume.setDevName(dev.getName());
		insideComsume.setPrice(new BigDecimal(price));
		insideComsume.setCost(product.getCost());
		insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNGOING);
		insideComsume.setCreateDate(currentDate);
		insideComsumeService.add(insideComsume);


		try {
			jsonModel.setSuccess(true);
			jsonModel.setMsg("下单成功");
			jsonModel.setData(comsumeLog);
		} catch (KPException e) {
			jsonModel.setMsg(e.getSelfMessage());
		}
		return jsonModel;
	}

	/**
	 * 手动催促出单
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value="/{orderId}/outting",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody JsonModel outting(@PathVariable String orderId){
		JsonModel jsonModel = new JsonModel();
		return jsonModel;
	}

	@RequestMapping(value="/{orderId}/status",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	public @ResponseBody JsonModel status(@PathVariable String orderId){
		JsonModel jsonModel = new JsonModel();
		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		if(comsumeLog!=null){
			jsonModel.setSuccess(true);
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("payStatus",comsumeLog.getPayStatus());
			comsumeLog = null;
			jsonModel.setData(result);
			return jsonModel;
		}else{
			jsonModel.setSuccess(false);
			jsonModel.setMsg("参数错误");
			return jsonModel;
		}
	}

	/**
	 * 返回消费记录
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET,headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody JsonModel listComsumeLog(ComsumeLogQuery comsumeLogQuery,HttpSession session){
		JsonModel jsonModel = new JsonModel();
		comsumeLogQuery.setMemberId(SessionUtil.getCurrentLoginInfoId(session));
		PageInfo<ComsumeLog> comsumeLogs = comsumeLogService.findBySelective(comsumeLogQuery);
		jsonModel.setData(comsumeLogs.getList());
		jsonModel.setSuccess(true);
		return jsonModel;
	}
}