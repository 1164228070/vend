package com.haiyi.controller;

import com.haiyi.domain.Dev;
import com.haiyi.netty.packet.LockResponsePacket;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.query.DevQuery;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.DevService;
import com.maizi.utils.JsonModel;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: DevController
 * @Company: 麦子科技
 * @Description: 会员控制器
 * @author 技术部-谢维乐
 * @date 2016年4月19日下午11:50:08
 *
 */
@Controller
@RequestMapping("/devs")
public class DevController {
	@Autowired
	DevService devService;


	@Autowired
	ComsumeLogService comsumeLogService;




	/**
	 * 获取当前已经支付记录
	 * @param devNum
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/{devNum}/comsumeLog",method=RequestMethod.GET)
	public @ResponseBody JsonModel obtainDevTask(@PathVariable String devNum,@RequestParam String token){
		JsonModel jsonModel = new JsonModel();
/*
	   Dev dev = devService.findByNum(devNum);
		if(dev==null){
            jsonModel.setMsg("设备不存在");
            return jsonModel;
		}
        List<ComsumeDetail> comsumeDetails = comsumeLogService.findOuttingOrderByDevNum(devNum);
		if(comsumeDetails==null){
            jsonModel.setMsg("没有最新购买的订单");
            return jsonModel;
		}else{
		    jsonModel.setSuccess(true);
		    jsonModel.setData(comsumeDetails);
            return jsonModel;
        }*/
		return null;
	}

	/**
	 *  出货上报接口
	 * @param devNum    设备编号
	 * @param orderId   订单Id
	 * @param token     token
	 * @param orderNum    商品Id
	 * @param count     数量
	 * @return
	 */
	@RequestMapping(value="/{devNum}/comsumeLogs/{orderId}",method=RequestMethod.GET)
	public @ResponseBody JsonModel outputCallback(@PathVariable Integer devNum,@PathVariable String orderId,@RequestParam String token,@RequestParam Integer orderNum,@RequestParam Integer count){
		//查询订单
		/*ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		JsonModel jsonModel = new JsonModel();
		if(comsumeLog==null){
		    jsonModel.setMsg("无效订单");
		    return jsonModel;
		}
		if(StatusConstant.PAY_STATUS_PAIED==comsumeLog.getPayStatus()){
            int effect= comsumeDetailService.updateOutputInfo(orderId,orderNum,count);
            if(effect==1){
                jsonModel.setSuccess(true);
                jsonModel.setMsg("出货成功");
            }else{
                jsonModel.setMsg("出货失败");
            }
		}else{
            jsonModel.setMsg("没有可出货的订单");
		}
		return jsonModel;*/

		return null;
	}

	/*@RequestMapping(value={"/{devId}/status"}, method={RequestMethod.GET})
	public @ResponseBody JsonModel updateStatus(@PathVariable("devId") Integer devId,@RequestParam(required=true) String sign){
		JsonModel jsonModel = new JsonModel();
		LockResponsePacket lockResponsePacket=new LockResponsePacket();
		lockResponsePacket.setDevNum(devService.findById(devId).getNum().toString());
		Map<String,Object> param = new HashMap<>();
		param.put("devNum",lockResponsePacket.getDevNum());

		if("y".equals(sign)){
			param.put("lock","1");
			lockResponsePacket.setData(param);
			lockResponsePacket.setSuccess(true);
			jsonModel.setMsg("设备开锁成功");
			jsonModel.setSuccess(true);
		}else if("n".equals(sign)){
			param.put("lock","2");
			lockResponsePacket.setData(param);
			lockResponsePacket.setSuccess(false);
			jsonModel.setMsg("设备开锁失败");
			jsonModel.setSuccess(false);
		}
		SessionUtil.dispatcherLockInfo(SessionUtil.getChannel(lockResponsePacket.getDevNum()),lockResponsePacket);
		return jsonModel;
	}*/
	@RequestMapping(value={"/getStatus/{devNum}"}, method={RequestMethod.POST})
	public @ResponseBody JsonModel getStatus(@PathVariable("devNum") String devNum){
		JsonModel jsonModel = new JsonModel();
		Channel channe = SessionUtil.getChannel(devNum);
		if(channe!=null){
			jsonModel.setSuccess(true);
			jsonModel.setMsg("在线");
		}else {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("离线");
		}
		return jsonModel;
	}

	@RequestMapping(value={"/getStatus"}, method={RequestMethod.POST})
	public @ResponseBody JsonModel getAllStatus(DevQuery devQuery){
		JsonModel jsonModel = new JsonModel();
		List<Dev> devs = devService.findBySelective(devQuery);
		Map<String,Integer> data=new HashMap<String,Integer>();
		Integer allLine=0;
		Integer liveLine=0;
		Integer offLine=0;
		for(Dev dev:devs){
			allLine++;
			Channel channe = SessionUtil.getChannel(dev.getNum()+"");
			if(channe!=null){
				devService.updateStates(dev.getId(),1);
				liveLine++;
			}else {
				devService.updateStates(dev.getId(),2);
				offLine++;
			}
		}
		data.put("allLine",allLine);
		data.put("liveLine",liveLine);
		data.put("offLine",offLine);
		jsonModel.setData(data);
		jsonModel.setSuccess(true);
		return jsonModel;
	}
	@RequestMapping(value={"/getObtain/{devId}"}, method={RequestMethod.POST})
	public @ResponseBody JsonModel getObtain(@PathVariable Integer devId){
		JsonModel jsonModel = new JsonModel();
		try {
			Dev dev = devService.findById(devId);
			LockResponsePacket lockResponsePacket=new LockResponsePacket();
			Map<String,Object> param = new HashMap<>();
			param.put("devNum",dev.getNum());
			param.put("lock",1);
			lockResponsePacket.setData(param);
			lockResponsePacket.setSuccess(true);
			SessionUtil.dispatcherLockInfo(SessionUtil.getChannel(dev.getNum()+""),lockResponsePacket);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("开锁成功");
		}catch (Exception e){
			jsonModel.setSuccess(false);
			jsonModel.setMsg("开锁失败,请确认设备是否已经登录");
		}
		return jsonModel;
	}








}
