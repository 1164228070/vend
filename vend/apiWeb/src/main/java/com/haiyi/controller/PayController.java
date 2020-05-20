package com.haiyi.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.fasterxml.jackson.xml.XmlMapper;
import com.haiyi.domain.*;
import com.haiyi.netty.server.handler.ReporteHandle;
import com.haiyi.pay.*;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.*;
import com.haiyi.state.StateConfig;
import com.haiyi.utils.*;
import com.maizi.utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.management.resources.agent;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 支付控制器
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	RechargeLogService rechargeLogService;
	@Autowired
	ComsumeLogService comsumeLogService;
	@Autowired
	ProductService productService;
	@Autowired
	ProductGroupService productGroupService;
	@Autowired
	DevService devService;
	@Autowired
	PayModeService payModeService;
	@Autowired
	AgentService agentService;
	@Autowired
	InsideComsumeService insideComsumeService;


	private static SnowflakeIdFactory snow = new SnowflakeIdFactory(1, 2);







	/**
	 *
	 * 微信/支付宝H5支付统一下单
	 * @param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{payType}/unifiedorder/{orderId}", method = RequestMethod.POST, produces = "application/text;charset=utf-8")
	public void AliPayunifiedorder(@PathVariable String payType, @PathVariable String orderId, HttpServletResponse response,HttpServletRequest request) throws IOException{

		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		WechatPayService webchatPayService=new WechatPayService();
		LogUtils.logInfo("11111111111111111111111111111111111111");
		if (comsumeLog != null  && comsumeLog.getPayStatus()== StatusConstant.PAY_STATUS_INVALID) {
			String IP = IPUtil.getIp(request);
			if ("wxpay".equals(payType)) {
				String total = MoneyUtil.changeY2F(comsumeLog.getPayAmount()); // 单位分
				String productName = "test";
				String form="";
				form = webchatPayService.unifiedOrder(Configure.H5_TRADE_TYPE, null,productName,total, orderId,IP);
				XmlMapper xmlMapper = new XmlMapper();
				JsonNode node = xmlMapper.readTree(form);
				if (Configure.SUCCESS.equals(node.get("return_code").asText())) {
					form = node.get("mweb_url").asText()+ "&redirect_url=" + URLEncoder.encode(Configure.SUCCESS_URL+orderId, "UTF-8");;
					response.sendRedirect(form);
				}else{
					String errorMsg = node.get("return_msg").asText();
					LogUtils.logError("支付失败{}",errorMsg);
					//response.sendRedirect("http://www.peshion.com/wapApp/errors?msg=调用微信支付失败");
				}
			} else if ("alipay".equals(payType)) {
				try {
					LogUtils.logInfo("222222222222222222222222222222222222222");
					String form = webchatPayService.alipay(orderId,comsumeLog.getPayAmount().toString(),comsumeLog.getProductName());
					response.setContentType("text/html;charset=" + ConstantUtil.CHARSET);
					LogUtils.logInfo("4444444444444444444444444444444");
					response.getWriter().write(form);// 直接将完整的表单html输出到页面
					LogUtils.logInfo("555555555555555555555555555");
					response.getWriter().flush();
					response.getWriter().close();
				} catch (Exception e) {
					LogUtils.logError("调用支付宝支付失败{}",e.getMessage());
					//response.sendRedirect("http://www.peshion.com/wapApp/errors?msg=调用支付宝支付失败");
				}
			} else {
				LogUtils.logError("支付失败,参数payType[{}]错误",payType);
				//response.sendRedirect("http://www.peshion.com/wapApp/errors?msg=调用支付参数错误");
			}
		} else {
			LogUtils.logWarn("支付失败,查不找到订单[{}]平台信息",orderId);
			//response.sendRedirect("http://www.peshion.com/wapApp/errors?msg=无效订单,请联系管理员");
		}
	}


	@RequestMapping(value = "/unifiedorder/{orderId}")
	@ResponseBody
	public JsonModel unifiedorder( @PathVariable String orderId, @RequestParam String code, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {


		JsonModel jsonModel = new JsonModel();
		WechatPayService webchatPayService=new WechatPayService();
		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		if (comsumeLog == null) {
			jsonModel.setMsg("支付失败!不存在该订单");
			LogUtils.logWarn("支付失败,查不找到订单[{}]平台信息",orderId);
			return jsonModel;
		}

		if(comsumeLog.getPayStatus() != StatusConstant.PAY_STATUS_INVALID){
			jsonModel.setMsg("支付失败!该订单已经支付");
			LogUtils.logWarn("支付失败!该订单[{}]已经支付",orderId);
			return jsonModel;
		}

		String openId = WXUtil.takeOpenId(code);
		if (StringUtil.isEmpty(openId)) {
			jsonModel.setMsg("支付失败!无法获取用户信息");
			LogUtils.logWarn("支付失败!无法获取用户的openId信息");
			return jsonModel;
		}

		//Product product = productService.findById(comsumeLog.getProductId());

		// 商品描述
		String body = comsumeLog.getProductName();

		// 单位分
		String total = MoneyUtil.changeY2F(comsumeLog.getPayAmount());

		String ip = IPUtil.getIp(request);

		//调用微信统一下单
		String res = webchatPayService.unifiedOrder(Configure.JS_TRADE_TYPE, openId, body, total,orderId, ip);
		try {
			res = new String(res.getBytes("ISO8859-1"), "UTF-8");
			LogUtils.logWarn("统一下单失败{}",res);
		} catch (UnsupportedEncodingException e) {
			jsonModel.setMsg("服务器异常,预支付失败!");
			LogUtils.logWarn("支付失败,不支持编码方式!{}",e.getMessage());
			return jsonModel;
		}

		Map<String, Object> result = webchatPayService.wechatPay(res);
		if (result == null || result.isEmpty()) {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("支付失败!");
			LogUtils.logWarn("调用微信支付失败!");
			return jsonModel;
		}
		jsonModel.setSuccess(true);
		jsonModel.setData(result);
		return jsonModel;
	}






	@RequestMapping(value = "/{payType}/zx_unifiedorder/{orderId}")
	@ResponseBody
	public JsonModel unifiedorder(@PathVariable String payType, @PathVariable String orderId, @RequestParam String code, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
		JsonModel jsonModel = new JsonModel();
		String userId=WechatPayService.getApiUserId(code);
		LogUtils.logInfo("userId=============="+userId);
		//TODO
		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		//Product product = productService.findById(comsumeLog.getProductId());
		if ("wxpay".equals(payType)) {
			comsumeLog.setPayType(StateConfig.PAYTYPE_ZX_XW);
		} else if ("alipay".equals(payType)) {
			comsumeLog.setPayType(StateConfig.PAYTYPE_ZX_ZFB);
		}
		comsumeLogService.update(comsumeLog);
		String form = "";
		if (comsumeLog != null  && comsumeLog.getPayStatus()==3) {
			String total = MoneyUtil.changeY2F(comsumeLog.getPayAmount()); // 单位分
			String productName = comsumeLog.getProductName();
			//调用全付通统一下单
			String ip = IPUtil.getIp(request);
			String body = comsumeLog.getProductName();
			Map<String,String> resultMap = GateWayService.zxPay(ip,Integer.parseInt(total),body,orderId,code,payType,userId);
			LogUtils.logInfo("请求结果1"+resultMap);
			if("0".equals(resultMap.get("status"))){
				//初始化支付信息成功响应数据给客户端
				jsonModel.setSuccess(true);
				resultMap.put("orderId",orderId);
				jsonModel.setData(resultMap);
			}else{
				jsonModel.setSuccess(false);
			}
			/*if ("wxpay".equals(payType)) {

			} else if ("alipay".equals(payType)) {
				//TODO 支付宝支付

			} else {
				LogUtils.logError("支付失败,参数payType[{}]错误",payType);
				response.sendRedirect("http://www.xmxinmei.net/wapApp/errors?msg=调用支付参数错误");
			}*/
		} else {
			LogUtils.logWarn("支付失败,查不找到订单[{}]平台信息",orderId);
			response.sendRedirect("http://www.xmxinmei.net/wapApp/errors?msg=无效订单,请联系管理员");
		}
		LogUtils.logInfo("请求结果2"+jsonModel.getData());
		return jsonModel;
	}














	@RequestMapping(value = "/paySuccess/{orderId}",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel paySuccess(@PathVariable String orderId,HttpServletResponse response, HttpServletRequest request) {
		JsonModel jsonModel = new JsonModel();
		Map<String, Object> result =new HashMap<String, Object>();
		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		/*Product product = productService.findById(comsumeLog.getProductId());
		ProductGroup productGroup=productGroupService.findById(product.getProductGroup().id+"");*/
		Dev dev = devService.findByNum(comsumeLog.getDevNum());
		result.put("orderId",orderId);
		result.put("price",comsumeLog.getPayAmount());
		result.put("devNum",dev.getNum());
		result.put("phone",dev.getPhone());
		jsonModel.setData(result);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("查询支付信息成功");
		return jsonModel;
	}
	@RequestMapping(value = "/payStatus/{orderId}",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel payStatus(@PathVariable String orderId,HttpServletResponse response, HttpServletRequest request) {
		JsonModel jsonModel = new JsonModel();
		Map<String, Object> result =new HashMap<String, Object>();
		ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
		result.put("payStatus",comsumeLog.getPayStatus());
		jsonModel.setData(result);
		jsonModel.setSuccess(true);
		jsonModel.setMsg("支付状态查询成功");
		return jsonModel;
	}


	@RequestMapping(value = "/payReturn/{orderId}",method=RequestMethod.POST)
	@ResponseBody
	public JsonModel payReturn(@PathVariable String orderId) throws Exception {
		JsonModel jsonModel = new JsonModel();

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		Runnable runnable = new Runnable() {
			int times=0;
			//创建 run 方法
			public void run() {
				ComsumeLog comsumeLog = comsumeLogService.findByOrderId(orderId);
				if(times>=60){
					Product product=productService.findById(comsumeLog.getProductId());
					product.setStoreCount(product.getStoreCount()+1);
					if(product.getStoreCount()<=product.getThreshold()){
						product.setCountStatus(StatusConstant.PRODUCT_COUNT_STOCKOUT);
					}else{
						product.setCountStatus(StatusConstant.PRODUCT_COUNT_ENOUGH);
					}
					productService.update(product);
					if(comsumeLog.getPayType()==1){
						//申请微信退款
						Map<String, String> map = new HashMap<String, String>();
						//商户平台id
						map.put("mch_id", PayConfig.mch_id);
						//微信分配的公众账号ID（企业号corpid即为此appId）
						map.put("appid", PayConfig.appId);
						//随机字符串
						map.put("nonce_str", CheckUtil.generateNonceStr());
						//商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
						// TODO 获取用户押金订单号（用户在微信支付时的订单号）
						String outTradeNo = orderId;
						//订单总金额，单位为分，只能为整数（用户在微信支付时的总金额）
						String totalFee = MoneyUtil.changeY2F(comsumeLog.getPayAmount());
						map.put("out_trade_no", outTradeNo);//微信订单号  内部订单号和微信订单号二选一
						//商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
						//todo 用户退款单号 先暂用时间戳
						String outRefundNo = String.valueOf(snow.nextId());
						map.put("out_refund_no", outRefundNo);
						map.put("total_fee", totalFee);
						//退款总金额，单位为分，只能为整数（不能大于订单总金额）
						map.put("refund_fee", totalFee);
						map.put("refund_desc", "机器繁忙，未能出货");
						//异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
						//如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效
						map.put("notify_url", "http://www.peshion.com/apiWeb/callback/wx/refund");
						LogUtils.logInfo("MAP======"+map);
						//签名
						String sign = null;
						String xml=null;
						try {
							sign = CheckUtil.generateSignature(map, PayConfig.key, "MD5");
							map.put("sign", sign);
							xml = CheckUtil.getXMLFromMap(map);
						} catch (Exception e) {
							e.printStackTrace();
						}


						LogUtils.logInfo("退款签名XML格式========"+xml);
						try {
							//获取apiclient_cert.p12证书，以下内容是直接在微信提供的demo中摘取出来的
							//InputStream certStream = request.getServletContext().getResourceAsStream("/WEB-INF/apiclient_cert.p12");
							InputStream certStream = ReporteHandle.class.getResourceAsStream("/properties/apiclient_cert.p12");
							char[] password = PayConfig.mch_id.toCharArray();
							KeyStore ks = KeyStore.getInstance("PKCS12");
							ks.load(certStream, password);
							// 实例化密钥库 & 初始化密钥工厂
							KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
							kmf.init(ks, password);
							// 创建 SSLContext
							SSLContext sslContext = SSLContext.getInstance("TLS");
							sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
							SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
									sslContext,
									new String[]{"TLSv1"},
									null,
									new DefaultHostnameVerifier());
							BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
									RegistryBuilder.<ConnectionSocketFactory>create()
											.register("http", PlainConnectionSocketFactory.getSocketFactory())
											.register("https", sslConnectionSocketFactory)
											.build(),
									null,
									null,
									null
							);
							HttpClient httpClient = HttpClientBuilder.create()
									.setConnectionManager(connManager)
									.build();
							String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
							HttpPost httpPost = new HttpPost(url);
							RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
							httpPost.setConfig(requestConfig);
							StringEntity postEntity = new StringEntity(xml, "UTF-8");
							httpPost.addHeader("Content-Type", "text/xml");
							httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + PayConfig.mch_id);
							httpPost.setEntity(postEntity);
							HttpResponse httpResponse = httpClient.execute(httpPost);
							HttpEntity httpEntity = httpResponse.getEntity();
							String data = EntityUtils.toString(httpEntity, "UTF-8");
							Map<String, String> refounResult = CheckUtil.xmlToMap(data);
							LogUtils.logInfo("退款返回的信息" + refounResult);
							if ("SUCCESS".equals(refounResult.get("return_code")) && "SUCCESS".equals(refounResult.get("result_code"))) {
								//TODO 获得退款详细信息，封装到数据库中
								comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
								comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
								//设置支付方式
								comsumeLog.setPayType(StateConfig.PAYTYPE_XW);
								comsumeLogService.update(comsumeLog);

								//设置内部表支付状态
								InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
								insideComsumeQuery.setOrderId(orderId);
								List<InsideComsume> insideComsumeList = insideComsumeService.findBySelective(insideComsumeQuery).getList();
								for(InsideComsume insideComsume:insideComsumeList){
									insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
									insideComsumeService.update(insideComsume);
								}



								//outTradeNo = refounResult.get("out_trade_no");//

								LogUtils.logInfo("[{}]设备未出货,退款成功",comsumeLog.getDevNum());
							} else {
								String msg = refounResult.get("return_msg");
								LogUtils.logInfo("退款失败==========" + msg);

								LogUtils.logInfo("[{}]设备未出货,退款失败",comsumeLog.getDevNum());

							}
						} catch (Exception e) {
							LogUtils.logInfo("退款失败" + e.getMessage());

							LogUtils.logInfo("[{}]设备未出货,退款失败",comsumeLog.getDevNum());

						}
					}else if(comsumeLog.getPayType()==2){
						//申请支付宝退款
						AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", PayConfig.alipayAPPID, PayConfig.RSA_private_key, "json", ConstantUtil.CHARSET, PayConfig.ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
						AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
						refundModel.setOutTradeNo(comsumeLog.getOrderId());
						refundModel.setRefundAmount(comsumeLog.getPayAmount()+"");
						refundModel.setRefundReason("商品退款");
						//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
						AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
						request.setBizModel(refundModel);
						comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
						comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
						//设置支付方式
						comsumeLog.setPayType(StateConfig.PAYTYPE_ZFB);
						comsumeLogService.update(comsumeLog);

						//设置内部表支付状态
						InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
						insideComsumeQuery.setOrderId(orderId);
						List<InsideComsume> insideComsumeList = insideComsumeService.findBySelective(insideComsumeQuery).getList();
						for(InsideComsume insideComsume:insideComsumeList){
							insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
							insideComsumeService.update(insideComsume);
						}


						try{
							LogUtils.logInfo("发送退款请求===========");
							alipayClient.execute(request);
							LogUtils.logInfo("[{}]设备未出货,退款成功",comsumeLog.getDevNum());
						}catch(Exception e){
							//LogUtils.logInfo("[{}]设备未出货,退款失败",reportePacket.getDevNum());
							LogUtils.logInfo("[{}]设备未出货,退款成功",comsumeLog.getDevNum());
						}
						//根据response中的结果继续业务逻辑处理
					}else if(comsumeLog.getPayType()==3 || comsumeLog.getPayType()==4){
						//中信退款
						SortedMap<String,String> map = new TreeMap<String,String>();
						map.put("service", "unified.trade.refund");
						map.put("sign_type","RSA_1_256");
						map.put("mch_id",PayConfig.zx_mch_id);
						map.put("out_trade_no",orderId);
						map.put("out_refund_no",String.valueOf(snow.nextId()));
						String totalFee = MoneyUtil.changeY2F(comsumeLog.getPayAmount());
						map.put("total_fee",totalFee);
						map.put("refund_fee",totalFee);
						map.put("op_user_id",PayConfig.zx_mch_id);
						map.put("nonce_str",String.valueOf(new Date().getTime()));
						Map<String, String> params = SignUtil.paraFilter(map);
						StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
						SignUtil.buildPayParams(buf, params, false);
						String preStr = buf.toString();
						map.put("sign", SignUtil.getSign(map.get("sign_type")+"", preStr));
						CloseableHttpResponse response = null;
						CloseableHttpClient client = null;
						String res = null;
						try {
							HttpPost httpPost = new HttpPost("https://payapi.citicbank.com/pay/gateway");
							StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map),"utf-8");
							httpPost.setEntity(entityParams);
							httpPost.setHeader("Content-Type", "text/xml;utf-8");
							client = HttpClients.createDefault();
							response = client.execute(httpPost);
							if(response != null && response.getEntity() != null){
								Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
								String reSign = resultMap.get("sign");
								res = XmlUtils.toXml(resultMap);
								LogUtils.logInfo("请求结果：" + res);
								System.out.println("请求结果：" + res);
								if (resultMap.containsKey("sign") && !SignUtil.verifySign(reSign, map.get("sign_type"), resultMap)) {
									res = "验证签名不通过";
								}
							}else{
								res = "操作失败!";
							}
						} catch (Exception e) {
							LogUtils.logError("操作失败，原因：",e);
							res = "操作失败";
						} finally {
							if(response != null){
								try {
									response.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if(client != null){
								try {
									client.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						Map<String,String> result = new HashMap<String,String>();
						if(res.startsWith("<")){
							result.put("status", "200");
							result.put("msg", "操作成功，请在日志文件中查看");
							comsumeLog.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
							//comsumeLog.setTradeType(StateConfig.TRADETYPE_REFUND);
							//设置支付方式
							comsumeLogService.update(comsumeLog);

							//设置内部表支付状态
							InsideComsumeQuery insideComsumeQuery=new InsideComsumeQuery();
							insideComsumeQuery.setOrderId(orderId);
							List<InsideComsume> insideComsumeList = insideComsumeService.findBySelective(insideComsumeQuery).getList();
							for(InsideComsume insideComsume:insideComsumeList){
								insideComsume.setPayStatus(StateConfig.PAYSTATUS_UNUSUAL);
								insideComsumeService.update(insideComsume);
							}


							//outTradeNo = refounResult.get("out_trade_no");//
							LogUtils.logInfo("[{}]订单三分钟未出货，自动退款成功",orderId);
							jsonModel.setSuccess(true);

						}else{
							result.put("status", "500");
							result.put("msg", res);
							LogUtils.logInfo("[{}]订单三分钟未出货，自动退款失败",orderId);
							jsonModel.setSuccess(false);
						}


					}
					service.shutdown();
					return;
				}

				// task to run goes here
				if(comsumeLog.getPayStatus()==3){
					System.out.println("未完成订单");
				}else if(comsumeLog.getPayStatus()==1 ||comsumeLog.getPayStatus()==2){
					service.shutdown();
					return;
				}
				times++;
			}
		};
		service.scheduleAtFixedRate(runnable, 1, 3, TimeUnit.SECONDS);
		jsonModel.setSuccess(true);
		return jsonModel;
	}


	@RequestMapping(value="/payInfo",method= RequestMethod.POST)
	@ResponseBody
	public JsonModel payInfo(String devNum,Integer productId) {
		JsonModel jsonModel = new JsonModel();
		Map<String,Object> map=new HashMap<>();
		Byte mode;
		Dev dev=devService.findByNum(devNum);
		if(productId!=null){
			Product product = productService.findById(productId);
			map.put("img",product.getImg());
		}


		Agent agent = agentService.findById(dev.getAgentId());
		if(agent.getPayMode()==null){
			PayMode payMode = payModeService.findBySelective().get(0);
			mode=payMode.getMode();
		}else {
			mode= agent.getPayMode();
		}
		map.put("mode",mode);
		map.put("phone",dev.getPhone());



		jsonModel.setMsg("搜索成功");
		jsonModel.setSuccess(true);

		jsonModel.setData(map);
		return jsonModel;
	}



}