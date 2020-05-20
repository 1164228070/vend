<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>微信安全支付</title>

		<script src="http://pub.idqqimg.com/qqmobile/qqapi.js?_bid=152"></script>
		<style type="text/css">
			.pay_header {
				background-color: #4cb131;
				width: 100%;
				padding: 50px 0;
				text-align: center;
			}

			.pay_header .title {
				color: #ffffff;
				font-size: 26px;
				text-align: center;
				padding-top: 10px;
			}

			.safe{
				width:90%;
				margin:10px auto;
				text-align: left;
				font-size: 17px;
				color: #9d9087;
			}

			.pay_btn {
				background-color: #4cb131;
				width: 90%;
				font-size: 20px;
				border-radius: 5px;
				margin: 0 auto;
				padding: 14px 0;
				text-align: center;
				color: #ffffff;
			}
		</style>

		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>


	</head>
	<body>
	
	    <div class="ticketinfo">
			<div class="pay_header">
				<img src="http://cdn2.pmpsys.cn/wx~xc6/wxt/images/icon_wx.png" width="60px" alt="微信支付">
				<div class="title">微信安全支付</div>
			</div>
		</div>
		
		<div class="safe">
			<div>
				<input type="checkbox" checked="checked" disabled="disabled">已开启支付安全
			</div>
		</div>
		
		<div class="pay_btn">确认支付</div>

	<script type="text/javascript">
		var payInfo="";
		var orderId="";
		function onBridgeReady(){
			WeixinJSBridge.invoke(
					'getBrandWCPayRequest',{
						"appId" : payInfo.start.appId, //公众号名称，由商户传入
						"timeStamp": payInfo.start.timestamp, //戳，自1970 年以来的秒数
						"nonceStr" : payInfo.start.nonceStr, //随机串
						"package" : payInfo.pay.packageStr,
						"signType" : "MD5", //微信签名方式:
						"paySign" : payInfo.pay.paySign  //微信签名,
					},function(res){
						if(res.err_msg == "get_brand_wcpay_request:ok" ) {
							// 此处可以使用此方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回ok，但并不保证它绝对可靠。
							location.href = "${pageContext.request.contextPath}/callback/wx/"+orderId;
						}
					}
			);
		}


		$(function(){
			//发送ajax请求
			request.ajax("http://www.peshion.com/apiWeb/pay/unifiedorder/${orderId}?code=${code}","POST",{},false,function(result){
				if(result.success){
					//payInfo = JSON.parse(result.data);
					payInfo = result.data;
					orderId = "${orderId}";
					//唤起
					if (typeof WeixinJSBridge == "undefined"){
						if( document.addEventListener ){
							document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
						}else if (document.attachEvent){
							document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
							document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
						}
					}else{
						onBridgeReady();
					}
				}else{
				}
			})
		});
	</script>
	</body>
</html>