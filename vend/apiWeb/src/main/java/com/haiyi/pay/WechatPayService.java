package com.haiyi.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.fasterxml.jackson.xml.XmlMapper;
import com.haiyi.service.ComsumeLogService;
import com.haiyi.service.ProductService;
import com.haiyi.service.UserService;
import com.maizi.utils.ConstantUtil;
import com.maizi.utils.JsonUtil;
import com.maizi.utils.LogUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付Service
 * @author Administrator
 *
 */
@Service
public class WechatPayService {

    @Autowired
    ComsumeLogService comsumeLogService;
    @Autowired
    ProductService productService;



    @Autowired
    UserService userService;

    SignMD5 encoder = new SignMD5();

    RestTemplate restTemplate = new RestTemplate();



    /**
     * 微信支付统一下单接口
     * @param tradType      交易类型
     * @param openId        用户的 openId
     * @param body          商品描述
     * @param total         支付金额（单位分）
     * @param out_trade_no  订单唯一订单号
     * @return
     * @throws UnsupportedEncodingException
     */
    public String unifiedOrder(String tradType,String openId,String body, String total, String out_trade_no,String ip) throws UnsupportedEncodingException {
        LogUtils.logInfo("请求微信支付统一下单接口tradType[{}],openId[{}],body[{}],total[{}],out_trade_no[{}],ip[{}]",tradType,openId,body,total,out_trade_no,ip);

        WechatPayModel xml = new WechatPayModel();
        xml.setAppid(PayConfig.appId);
        xml.setMch_id(PayConfig.mch_id);
        xml.setNonce_str(encoder.createNonceStr());
        xml.setBody(body);
        xml.setOut_trade_no(out_trade_no);
        xml.setTotal_fee(total);
        xml.setSpbill_create_ip(ip);
        xml.setNotify_url(PayConfig.notify_url);
        xml.setTrade_type(tradType);
        xml.setOpenid(openId);
        xml.sign(encoder);


        LogUtils.logInfo("请求微信支付统一下单接口{}",xml);
        LogUtils.logInfo("请求微信支付统一下单接口{}", JsonUtil.domainToJson(xml));
        String result =  restTemplate.postForObject(Configure.UNIFIEDORDER_API, xml, String.class);

        LogUtils.logInfo("微信支付统一下单接口响应{}",result);
        LogUtils.logInfo("微信支付统一下单接口响应{}",new String(result.getBytes("ISO8859-1")));

        return result;
    }

    /**
     *
     * @param res
     * @return
     */
    public Map<String,Object> wechatPay(String res) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Map<String, String> start = new HashMap<>();
            StringBuilder startSign = new StringBuilder();

            Map<String, String> pay = new HashMap<>();
            StringBuilder paySign = new StringBuilder();

            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(res);
            if (Configure.SUCCESS.equals(node.get("return_code").asText())) {

                String prepay_id = node.get("prepay_id").asText();
                String jsapi_ticket = jsapiTicket();
                // 生成 微信支付 config 参数
                start.put("appId", PayConfig.appId);
                start.put("nonceStr", encoder.createNonceStr());
                start.put("timestamp", encoder.createTimeStamp());
                // 生成 config 签名
                startSign.append("jsapi_ticket=").append(jsapi_ticket);
                startSign.append("&noncestr=").append(start.get("nonceStr"));
                startSign.append("&timestamp=").append(start.get("timestamp"));
                //startSign.append("&url=").append(url);
                start.put("signature", encoder.encode(startSign.toString()));

                // config信息验证后会执行ready方法的参数
                pay.put("signType", Configure.MD5);
                pay.put("packageStr", "prepay_id=" + prepay_id);
                // 生成支付签名
                paySign.append("appId=").append(start.get("appId"));
                paySign.append("&nonceStr=").append(start.get("nonceStr"));
                paySign.append("&package=").append(pay.get("packageStr"));
                paySign.append("&signType=").append(pay.get("signType"));
                paySign.append("&timeStamp=").append(start.get("timestamp"));
                paySign.append("&key=").append(Configure.getKey());
                pay.put("paySign", encoder.encode(paySign.toString()));
                result.put("start",start);
                result.put("pay",pay);
            }
            return result;
        } catch (Exception e) {
            LogUtils.logError("xml解析异常",e);
            return null;
        }
    }



    /**
     *  获取支付宝userId
     * @return
     */
    public static String getApiUserId(String code){
        String userId=null;
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2019072365983174", PayConfig.RSA_private_key, "json", "UTF-8", PayConfig.ALIPAY_PUBLIC_KEY, "RSA2");
        AlipaySystemOauthTokenRequest request1 = new AlipaySystemOauthTokenRequest();
        request1.setCode(code);
        request1.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse oauthTokenResponse=null;

        try {
            oauthTokenResponse = alipayClient.execute(request1);
            LogUtils.logInfo("获取用户信息==============AccessToken="+oauthTokenResponse.getAccessToken()+"UserId="+oauthTokenResponse.getUserId());
            userId=oauthTokenResponse.getUserId();

        } catch (AlipayApiException e) {
            LogUtils.logInfo("出现异常============",e.getErrMsg());
            userId=oauthTokenResponse.getUserId();
            LogUtils.logError("userId======="+userId);
            //处理异常
            e.printStackTrace();
        }
        try {
            AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
            userinfoShareResponse.getAvatar();


        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return userId;
    }

    /**
     *  获取微信 JSAPI 支付的临时票据
     * @return
     */
    public String jsapiTicket() {
        if (Configure.checkJsapiTicket()) {
            // 声明 获取临时票据路径
            StringBuilder ticketBuilder = new StringBuilder();
            ticketBuilder.append(Configure.TICKET_API);
            ticketBuilder.append("?access_token=").append(getToken());
            ticketBuilder.append("&type=jsapi");
            // 获取 临时票据
            Map<?, ?> jsapiTicket = restTemplate.getForObject(ticketBuilder.toString(), Map.class);
            Configure.setJsapiTicket((String) jsapiTicket.get("ticket"));
        }
        return Configure.getJsapiTicket();
    }


    /**
     * 微信授权，获取 access_token
     * @return
     */
    public String getToken() {
        if (Configure.checkToken()) {
            // 声明 获取 access_token 路径
            StringBuilder tokenBuilder = new StringBuilder();
            tokenBuilder.append(Configure.TOKEN_API);
            tokenBuilder.append("&appid=").append(Configure.getAppid());
            tokenBuilder.append("&secret=").append(Configure.getAppSecret());
            // 获取 token
            Map<?, ?> token = restTemplate.getForObject(tokenBuilder.toString(), Map.class);
            Configure.setToken((String) token.get("access_token"));
        }
        return Configure.getToken();
    }



     /**
     * 唤起支付宝支付
     * @param orderId  商户订单号
     * @param amount   总金额(单位元)
     * @return
     * @throws AlipayApiException
     */
	public String alipay(String orderId,String amount,String productName) throws AlipayApiException {

	    // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
	    // 调用RSA签名方式
	    AlipayClient client = new DefaultAlipayClient(PayConfig.alipay_gateway, PayConfig.alipayAPPID,
	    		PayConfig.RSA_private_key, "json",
	    		ConstantUtil.CHARSET,PayConfig.ALIPAY_PUBLIC_KEY,
	    		"RSA2");
        LogUtils.logInfo("33333333333333333333333");
	    AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

	    // 封装请求支付信息
	    AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
	    model.setOutTradeNo(orderId);
	    model.setSubject(productName);
	    model.setTotalAmount(amount);//单位为元
	    model.setBody(productName);
	    model.setTimeoutExpress("60m");
	    model.setProductCode("test");
	    alipay_request.setBizModel(model);
	    // 设置异步通知地址
	    alipay_request.setNotifyUrl(PayConfig.alipay_notify_url);
	    // 设置同步地址
	    alipay_request.setReturnUrl(PayConfig.alipay_return_url);

	    LogUtils.logInfo("请求支付宝支付统一下单接口singType[{}],appId[{}],alipay_request[{}]","RSA2",PayConfig.alipayAPPID,alipay_request);
	    // form表单生产
	    String form = client.pageExecute(alipay_request).getBody();
	    // 调用SDK生成表单
        LogUtils.logInfo("支付宝支付统一下单接口响应{}",form);
	    return form;
	}

	/**
     * 微信异步通知
     * @param wechatNotify
     * @return
     *//*
	public String nofificationWXPay(WechatNotify wechatNotify) {
		if(Configure.SUCCESS.equals(wechatNotify.getResult_code())){
			ComsumeLog comsumeLog = comsumeLogService.findByOrderId(wechatNotify.getOut_trade_no());
			if(comsumeLog==null){
				LogUtils.logWarn("微信推送支付成功信息,在本地却找不到该订单[{}]信息",wechatNotify.getOut_trade_no());
				return Configure.NOTIFY_FAIL;
			}else{
				if(StatusConstant.PAY_STATUS_PAIED==comsumeLog.getPayStatus()){
					return Configure.NOTIFY_SUCCESS;
				}else{
					comsumeLog.setComsumeLog(wechatNotify.getTransaction_id());//微信订单ID

					comsumeLog.setPayStatus(StatusConstant.PAY_STATUS_PAIED);
					//设置支付方式
					comsumeLog.setPayType(StatusConstant.PAY_TYPE_WX);
					comsumeLogService.update(comsumeLog);
					//TODO 处理并发问题
					agentService.updateLeftMoney(comsumeLog.getPayAmount(),comsumeLog.getAgentId());


					String devNum = comsumeLog.getDevNum();
					ClientThread clientThread = ManageClientThread.getClientThread(devNum);
					if(clientThread==null){
						//下发通知失败
						LogUtils.logError("推送新单信息失败,客户端[{}]掉线",devNum);
					}else{
						JsonModel jsonModel = new JsonModel();
						try{
							jsonModel.setSuccess(true);
							List<ComsumeDetail> comsumeDetails = comsumeDetailService.findByOrderId(comsumeLog.getOrderId());
							List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
							for(ComsumeDetail comsumeDetail : comsumeDetails){
								Map<String,Object> resultTemp = new HashMap<String,Object>();
								resultTemp.put("orderId",comsumeDetail.getOrderId());
								resultTemp.put("buyCount",comsumeDetail.getBuyCount());
								resultTemp.put("orderNum",comsumeDetail.getOrderNum());
								resultList.add(resultTemp);
							}
							jsonModel.setData(resultList);
							clientThread.printCotnent(JsonUtil.domainToJson(jsonModel));
						}catch (Exception e){
							LogUtils.logError("推送新单信息失败[{}]",e);
						}
					}
					return Configure.NOTIFY_SUCCESS;
				}
			}
		}else{
			return Configure.NOTIFY_FAIL;
		}
	}


	*/

	public boolean checkRSA(Map<String,String> params) throws AlipayApiException{
		return AlipaySignature.rsaCheckV1(params, PayConfig.ALIPAY_PUBLIC_KEY, ConstantUtil.CHARSET, "RSA2");
	}
}