package com.haiyi.controller;

import com.haiyi.domain.*;
import com.haiyi.domain.weChat.AccessToken;
import com.haiyi.netty.packet.MemberInfoResponsePacket;
import com.haiyi.netty.server.handler.auth.SessionUtil;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.*;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.HttpUtil;
import com.haiyi.utils.MenuUtil;
import com.haiyi.utils.SpringUtil;
import com.haiyi.utils.WeChatUtil;
import com.maizi.utils.DateUtil;
import com.maizi.utils.JsonModel;
import com.maizi.utils.SnowflakeIdFactory;
import com.maizi.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.haiyi.utils.WeChatUtil.getAccessToken;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	MemberService memberService;
	@Autowired
	MealService mealService;
	@Autowired
	UserService userService;
	@Autowired
	DevService devService;

	@Autowired
	ComsumeLogService comsumeLogService;

	@Autowired
	RechargeLogService rechargeLogService;
	@Autowired
	ProductService productService;
	@Autowired
	DevAuthService devAuthService;
	@Autowired
	InsideComsumeService insideComsumeService;


	private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);

	
	/**
	 * 返回消费记录
	 * @return
	 */
	@RequestMapping(value="account",method=RequestMethod.GET)

	public @ResponseBody JsonModel listAccountLog(){
		JsonModel jsonModel = new JsonModel();
		InsideComsume insideComsume=new InsideComsume();
		insideComsume.setOrderId("1251045835878436864");
		insideComsume.setProductId(138);
		insideComsume.setProductName("lang");
		insideComsume.setUserId(58);
		insideComsume.setUserName("赵六");
		insideComsume.setAgentId(21);
		insideComsume.setAgentName("李四");
		insideComsume.setDevNum("1000104");
		insideComsume.setDevName("00设备");
		insideComsume.setPrice(new BigDecimal(0.02));
		insideComsume.setCost(new BigDecimal(0.01));
		insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNGOING);
		Date currentDate = DateUtil.getCurrentDate();
		insideComsume.setCreateDate(currentDate);
		insideComsumeService.add(insideComsume);





		jsonModel.setSuccess(true);
		jsonModel.setData(insideComsume);






		return jsonModel;
	}

	@RequestMapping(value="session",method=RequestMethod.GET)
	public void  session()  {
		InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
		insideComsumeQuery.setOrderId("1251045835878436864");
		insideComsumeQuery.setProductId(138);
		List<InsideComsume> insideComsumeList = insideComsumeService.findBySelective(insideComsumeQuery).getList();
		int success=1;
		int size=insideComsumeList.size();
		if(success>0){
			for(int i=0;i<success;i++){
				System.out.println("成功第"+i+"条记录");
				InsideComsume insideComsume=insideComsumeList.get(i);
				System.out.println("成功的id:"+insideComsume.getId());
				insideComsume.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
				insideComsumeService.update(insideComsume);
			}
		}
		if(size>success){
			for(int j=success;j<size;j++){
				System.out.println("失败第"+j+"条记录");
				InsideComsume insideComsume=insideComsumeList.get(j);
				System.out.println("失败的id:"+insideComsume.getId());
				insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
				insideComsumeService.update(insideComsume);
			}
		}
	}


	@RequestMapping(value="log",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel  log(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Dev login = devService.login("1000102", "12345678");
		JsonModel jsonModel = new JsonModel();
		jsonModel.setMsg("充值记录");
		jsonModel.setSuccess(true);
		jsonModel.setData(login);
		return jsonModel;
	}
	@RequestMapping(value="meal",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel  meal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonModel jsonModel = new JsonModel();

		jsonModel.setMsg("充值记录");
		jsonModel.setSuccess(true);
		jsonModel.setData(mealService.findAll());
		return jsonModel;
	}




	@RequestMapping(value="add",method=RequestMethod.GET)

	public void   add() throws IOException {
		//信息
		for(int i=0;i<=22;i++){
			ComsumeLog comsumeLog=new ComsumeLog();
			comsumeLog.setComsumeLog(String.valueOf(snow.nextId()));
			comsumeLog.setOrderId(String.valueOf(snow.nextId()));
			comsumeLog.setDevNum("1000111");
			Dev dev=devService.findByNum("1000111");
			User user = userService.findById(dev.getUserId());
			comsumeLog.setUserName(user.getName());
			comsumeLog.setAgentName(user.getAgentName());
			comsumeLog.setUserId(dev.getUserId());
			comsumeLog.setAgentId(dev.getAgentId());
			comsumeLog.setProductId(15);
			Product product = productService.findById(15);
			comsumeLog.setProductName(product.getName());
			comsumeLog.setPayStatus(StateConfig.PAYSTATUS_SUCCESS);
			comsumeLog.setDevName(dev.getName());
			comsumeLog.setPayType(StateConfig.PAYTYPE_XW);
			comsumeLog.setTradeType(StateConfig.TRADETYPE_COMSUME);
			comsumeLog.setPayAmount(product.getPrice());
			comsumeLog.setCost(product.getCost());
			Date currentDate = DateUtil.getCurrentDate();
			comsumeLog.setCreateDate(currentDate);
			comsumeLogService.add(comsumeLog);
		}

	}
	@RequestMapping(value="update",method=RequestMethod.GET)
	public void   update()   {
		devService.updateDevStates(62,1);
	}
	@RequestMapping(value="test",method=RequestMethod.GET)
	public void   test() throws IOException {
		Workbook wk=new HSSFWorkbook();//创建工作薄
		Sheet sh=wk.createSheet();//创建sheet页
		Row row=sh.createRow(0);//创建第一行
		row.createCell(0).setCellValue(1);
		row.createCell(1).setCellValue("字符串");
		row.createCell(2).setCellValue(true);
		row.createCell(3).setCellValue(new Date());
		row.createCell(4).setCellValue(HSSFCell.ENCODING_COMPRESSED_UNICODE);
		row.createCell(5).setCellValue(false);

		FileOutputStream out=new FileOutputStream("F://处理不同格式的单元格.xls");
		wk.write(out);
		out.close();
	}



	@RequestMapping(value="addRecharge",method=RequestMethod.GET)
	@ResponseBody
	public JsonModel   addRecharge() throws IOException, ParseException {
		Member member = memberService.findById("63");

		//信息
		RechargeLog rechargeLog=new RechargeLog();
		rechargeLog.setMemberId(member.getId());
		rechargeLog.setMemberName(member.getName());
		rechargeLog.setMemberAccount(member.getAccount());
		rechargeLog.setRecharge_momey(new BigDecimal(10.55));
		rechargeLog.setGift(new BigDecimal("5.45"));
		rechargeLog.setPayStatus((byte) 1);
		rechargeLog.setPayType(StateConfig.PAYTYPE_XW);
		rechargeLog.setBalance(member.getBalance().add(rechargeLog.getRecharge_momey().add(rechargeLog.getGift())));
		memberService.updateBalance(rechargeLog.getBalance(),member.getAccount());
		rechargeLog.setRemark("测试充值");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat log = new SimpleDateFormat("yyyyMMddHHmmss");
		rechargeLog.setRechargelog(log.format(d));
		String dateNowStr = sdf.format(d);
		rechargeLog.setCreateDate(sdf.parse(dateNowStr));
		rechargeLogService.add(rechargeLog);

		RechargeLog rechargelog = rechargeLogService.findByRechargelog(rechargeLog.getRechargelog());
		rechargeLogService.updateStatus(rechargelog.getId(), (byte) 1);
		List<RechargeLog> rechargeLog2=rechargeLogService.findByMemberId(member.getId());
		JsonModel jsonModel = new JsonModel();
		jsonModel.setMsg("充值记录");
		jsonModel.setSuccess(true);
		jsonModel.setData(rechargeLog2);
		return jsonModel;
	}
	@RequestMapping(value="money",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel   money() {
		JsonModel jsonModel=new JsonModel();
		/*final DevAuth devAuth = devAuthService.findDevAuthByDevNum("1000104");
		System.out.println(devAuth.getIp());
		String url="http://localhost:80/apiWeb/netty/buyPush";
		String param="devNum=1000104&orderId=1210793305533845504&price=0.01&orderNum=10";
		String mysrsult = HttpUtil.sendPost(url, param);
		Map json = (Map) JSONObject.parse(mysrsult);
		Boolean isOnline = (Boolean)json.get("success");
		if(isOnline==true){
			jsonModel.setMsg("设备在线");
			jsonModel.setSuccess(true);
		}else {
			jsonModel.setMsg("设备不在线");
			jsonModel.setSuccess(false);
		}*/

		System.out.println("1111111111111");

		return jsonModel;
	}

	@RequestMapping(value="ttt",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel   ttt() {
		JsonModel jsonModel=new JsonModel();
		/*final DevAuth devAuth = devAuthService.findDevAuthByDevNum("1000104");
		System.out.println(devAuth.getIp());
		String url="http://localhost:80/apiWeb/netty/buyPush";
		String param="devNum=1000104&orderId=1210793305533845504&price=0.01&orderNum=10";
		String mysrsult = HttpUtil.sendPost(url, param);
		Map json = (Map) JSONObject.parse(mysrsult);
		Boolean isOnline = (Boolean)json.get("success");
		if(isOnline==true){
			jsonModel.setMsg("设备在线");
			jsonModel.setSuccess(true);
		}else {
			jsonModel.setMsg("设备不在线");
			jsonModel.setSuccess(false);
		}*/

		System.out.println("222222222222");

		return jsonModel;
	}


	@RequestMapping(value="menu",method=RequestMethod.GET)
	public void  menu(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		AccessToken token = WeChatUtil.getAccessToken();
		String menuStr = new JSONObject(MenuUtil.initMenu()).toString();
		int result=MenuUtil.createMenu(token.getToken(), menuStr);
		if(result==0){
			System.out.println("创建菜单成功");
		}else{
			System.out.println("错误码："+result);
		}

	}




}