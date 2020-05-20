package com.haiyi.controller;

import com.alipay.api.AlipayApiException;
import com.haiyi.pay.AlipayResp;
import com.haiyi.pay.CheckUtil;
import com.haiyi.pay.WechatPayService;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.utils.SignUtil;
import com.haiyi.utils.XmlUtils;
import com.maizi.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/callback")
public class CallbackController {

	@Autowired
	ComsumeLogService comsumeLogService;

	/**
	 * 微信支付异步通知
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/wx/notification", method = RequestMethod.POST)
	public void nofificationWXPay(HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-type", "text/html;charset=UTF-8");

		String resString = XmlUtils.parseRequst(req);
		LogUtils.logInfo("请求的内容：" + resString);
		String respString = "error";
		if(resString != null && !"".equals(resString)){
			Map<String,String> map = XmlUtils.toMap(resString.getBytes(), "utf-8");
			String res = XmlUtils.toXml(map);
			LogUtils.logInfo("请求的内容：" + res);
			respString = comsumeLogService.nofificationWXPay(map);
			/*if(map.containsKey("sign")){
				if(!SignUtil.verifySign(reSign, sign_type, map)){
					res = "验证签名不通过2";
					respString = "error";
					LogUtils.logInfo("请求的内容2：" + res);
				}else{
					String status = map.get("status");
					if(status != null && "0".equals(status)){
						String result_code = map.get("result_code");
						if(result_code != null && "SUCCESS".equals(result_code)){
							//TODO 业务处理
							respString = comsumeLogService.nofificationWXPay(map);
						}
					}else {
						LogUtils.logInfo("status不为0" );
					}
				}
			}*/
		}
		resp.getWriter().write(respString);
		} catch (Exception e) {
			LogUtils.logError("操作失败，原因：",e);
		}
	}

	@RequestMapping(value = "/wx/refund")
	public void refund(HttpServletResponse response, HttpServletRequest request) {
		try {
			String retStr = null;
			try {
				retStr = new String(readInput(request.getInputStream()), "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			LogUtils.logInfo("退款回调信息：" + retStr);
			Map<String, String> map = CheckUtil.xmlToMap(retStr);
			if (map.get("return_code").toString().equalsIgnoreCase("SUCCESS")) {
				//对加密串req_info进行解密，这部分根据项目需求来，若不需要，可不获取解密数据
				/*String req_info = map.get("req_info");
				String data = AESUtil.decryptData(req_info);
				LogUtils.logInfo("返回的加密字段", data);
				//判断退款结果是否已经处理，处理过后直接返回通知
				Map<String, String> map1 = CheckUtil.xmlToMap(data);
				String refund_status = map1.get("refund_status");//退款状态
				String outTradeNo = map1.get("out_trade_no");
				if (refund_status.equals("SUCCESS")) {
					//TODO 退款状态为成功后，对数据进行处理
				}*/
				//告诉微信已收到通知
				response.setContentType("text/xml");
				String xml = "<xml>"
						+ "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>"
						+ "</xml>";
				response.getWriter().print(xml);
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] readInput(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
		return out.toByteArray();
	}



	/**
	 * 支付宝支付回调通知支付结果: <br>
	 * @param
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException
	 */
	@RequestMapping(value = "/alipay/notification", method = RequestMethod.POST)
	public @ResponseBody
	String notifyAliPay(HttpServletRequest request, AlipayResp alipayResp) throws UnsupportedEncodingException, AlipayApiException {
		//获取支付宝GET过来反馈信息
		LogUtils.logInfo("支付宝支付回调通知支付结果============");
		WechatPayService wechatPayService=new WechatPayService();
		Map<String, String> params = new HashMap<String, String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		LogUtils.logInfo("支付宝支付回调通知支付结果参数====="+params);
		return comsumeLogService.nofificationAliPay(params, alipayResp);
	}

	/**
	 * 支付宝支付同步通知
	 * @param request
	 * @param response
	 * @param alipayResp
	 * @throws AlipayApiException
	 * @throws IOException
	 */
	@RequestMapping(value = "/alipay", method = RequestMethod.GET)
	public void AliPayReturnUrl(HttpServletRequest request,HttpServletResponse response,AlipayResp alipayResp) throws AlipayApiException, IOException{
		LogUtils.logInfo("alipayResp================"+alipayResp);

		Map<String, String> params = new HashMap<String, String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		WechatPayService wechatPayService=new WechatPayService();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		//boolean sign = wechatPayService.checkRSA(params);
		LogUtils.logInfo("params=================="+params);
		String orderId = alipayResp.getOut_trade_no(); // 我方订单ID
        response.sendRedirect("http://www.peshion.com/wapApp/callback/wx/"+orderId);
		//LogUtils.logInfo("sign========="+sign);

		/*PrintWriter printWriter = response.getWriter();
		if(sign){
			//重定向到等待出货界面
			response.sendRedirect("http://www.peshion.com/wapApp/callback/wx/"+orderId);
			//支付成功
			//printWriter.write("支付成功success");
		}else{
			//TODO 重定向到重复支付界面
			response.sendRedirect("http://www.peshion.com/wapApp/callback/wx/"+orderId);
		}*/
	}



	@RequestMapping(value = "/zx/notification", method = RequestMethod.POST)
	public void nofificationZX_WXPay(HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			resp.setHeader("Content-type", "text/html;charset=UTF-8");

			String resString = XmlUtils.parseRequst(req);
			LogUtils.logInfo("请求的内容：" + resString);

			String respString = "error";
			if(resString != null && !"".equals(resString)){
				Map<String,String> map = XmlUtils.toMap(resString.getBytes(), "utf-8");
				String res = XmlUtils.toXml(map);
				String sign_type = map.get("sign_type");
				String reSign = map.get("sign");
				LogUtils.logInfo("请求的内容：" + res);

				if(map.containsKey("sign")){
					if(!SignUtil.verifySign(reSign, sign_type, map)){
						res = "验证签名不通过";
						respString = "error";
						LogUtils.logInfo("请求的内容2：" + res);
					}else{
						String status = map.get("status");
						if(status != null && "0".equals(status)){
							String result_code = map.get("result_code");
							if(result_code != null && "0".equals(result_code)){
								//TODO 业务处理
								respString = comsumeLogService.nofificationZX_WXPay(map);
							}
						}
					}
				}
			}
			resp.getWriter().write(respString);
		} catch (Exception e) {
			LogUtils.logError("操作失败，原因：",e);
		}
	}








}