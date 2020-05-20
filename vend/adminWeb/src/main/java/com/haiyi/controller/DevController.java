package com.haiyi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.haiyi.utils.RequestUtil;
import com.haiyi.utils.SessionUtil;
import com.maizi.utils.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.haiyi.domain.Dev;
import com.haiyi.query.DevQuery;
import com.haiyi.service.UserService;
import com.haiyi.service.DevService;
import com.haiyi.utils.SysConfigUtil;
import com.maizi.anno.AuthAnno;
import com.maizi.exception.KPException;
/**
 * 设备控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/devs")
public class DevController{
	@Autowired
	DevService devServcie;
	
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
	@AuthAnno(verifyLogin=true)
	public @ResponseBody Map<String,Object> list(DevQuery e, HttpServletRequest request) throws KPException{
		Map<String,Object> result = new HashMap<String,Object>();
		PageInfo<Dev> results = devServcie.findBySelective(e);
		result.put("success", true);
		result.put("msg","查询设备成功");  
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
	@AuthAnno(verifyLogin=true)
    public String list(HttpServletRequest request,ModelMap modelMap) throws KPException{
		return "/devs/list";
	}
	
	/**
	 * 查看详情信息
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	@AuthAnno
	public String findById(@PathVariable String id,ModelMap modelMap){
		Dev dev = devServcie.findById(id);
		if(dev==null){
			//重定向到列表页面
			return "redirect:/devs";
		}
		modelMap.addAttribute(dev);
		return "/devs/detail";
	}


	@RequestMapping(value={"/{devId}/status"}, method={RequestMethod.PUT},headers="X-Requested-With=XMLHttpRequest")
	@AuthAnno
	public @ResponseBody
	JsonModel updateStatus(@PathVariable("devId") Integer devId, @RequestParam(required=true) String sign){
		JsonModel jsonModel = new JsonModel();

		if("y".equals(sign)){
			devServcie.updateStatus(devId,1);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("开锁成功");
		}else if("n".equals(sign)){
			devServcie.updateStatus(devId,2);
			jsonModel.setSuccess(true);
			jsonModel.setMsg("关锁成功");
		}
		return jsonModel;
	}
}