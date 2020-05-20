<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商品结算清单</title>
	<style type="text/css">
		body {
			display: -webkit-box;
			display: -ms-flexbox;
			display: flex;
			-webkit-box-align: center;
			-ms-flex-align: center;
			align-items: center;
			-webkit-box-pack: center;
			-ms-flex-pack: center;
			justify-content: center;
			min-height: 100vh;
			color: #525252;
			font-family: 'Microsoft YaHei','Lantinghei SC','Open Sans',Arial,'Hiragino Sans GB','STHeiti','WenQuanYi Micro Hei','SimSun',sans-serif;
			font-weight: 300;
			line-height: 1.25;
			background: -webkit-linear-gradient(top, #00b09b, #96c93d);
			background: linear-gradient(to bottom, #00b09b, #96c93d);
		}

		a {
			color: #5c7b25;
			font-size: 35px;
			-webkit-text-decoration-skip: ink;
			text-decoration-skip: ink;
		}

		.receipt {
			position: relative;
			-webkit-box-flex: 0;
			-ms-flex: none;
			flex: none;
			padding: 10px 0 20px;
			background: #fff;
			-webkit-clip-path: polygon(100% 0, 100% calc(100% - 6px), 98% 100%, 96% calc(100% - 6px), 94% 100%, 92% calc(100% - 6px), 90% 100%, 88% calc(100% - 6px), 86% 100%, 84% calc(100% - 6px), 82% 100%, 80% calc(100% - 6px), 78% 100%, 76% calc(100% - 6px), 74% 100%, 72% calc(100% - 6px), 70% 100%, 68% calc(100% - 6px), 66% 100%, 64% calc(100% - 6px), 62% 100%, 60% calc(100% - 6px), 58% 100%, 56% calc(100% - 6px), 54% 100%, 52% calc(100% - 6px), 50% 100%, 48% calc(100% - 6px), 46% 100%, 44% calc(100% - 6px), 42% 100%, 40% calc(100% - 6px), 38% 100%, 36% calc(100% - 6px), 34% 100%, 32% calc(100% - 6px), 30% 100%, 28% calc(100% - 6px), 26% 100%, 24% calc(100% - 6px), 22% 100%, 20% calc(100% - 6px), 18% 100%, 16% calc(100% - 6px), 14% 100%, 12% calc(100% - 6px), 10% 100%, 8% calc(100% - 6px), 6% 100%, 4% calc(100% - 6px), 2% 100%, 0 calc(100% - 6px), 0 0);
			clip-path: polygon(100% 0, 100% calc(100% - 6px), 98% 100%, 96% calc(100% - 6px), 94% 100%, 92% calc(100% - 6px), 90% 100%, 88% calc(100% - 6px), 86% 100%, 84% calc(100% - 6px), 82% 100%, 80% calc(100% - 6px), 78% 100%, 76% calc(100% - 6px), 74% 100%, 72% calc(100% - 6px), 70% 100%, 68% calc(100% - 6px), 66% 100%, 64% calc(100% - 6px), 62% 100%, 60% calc(100% - 6px), 58% 100%, 56% calc(100% - 6px), 54% 100%, 52% calc(100% - 6px), 50% 100%, 48% calc(100% - 6px), 46% 100%, 44% calc(100% - 6px), 42% 100%, 40% calc(100% - 6px), 38% 100%, 36% calc(100% - 6px), 34% 100%, 32% calc(100% - 6px), 30% 100%, 28% calc(100% - 6px), 26% 100%, 24% calc(100% - 6px), 22% 100%, 20% calc(100% - 6px), 18% 100%, 16% calc(100% - 6px), 14% 100%, 12% calc(100% - 6px), 10% 100%, 8% calc(100% - 6px), 6% 100%, 4% calc(100% - 6px), 2% 100%, 0 calc(100% - 6px), 0 0);
		}
		.receipt__title {
			margin-bottom: 20px;
			padding: 10px 30px;
			font-family: 'Microsoft YaHei','Lantinghei SC','Open Sans',Arial,'Hiragino Sans GB','STHeiti','WenQuanYi Micro Hei','SimSun',sans-serif;
			font-weight: 500;
			font-size: 70px;
			color: #00b09b;
		}
		.receipt__subtitle {
			padding: 10px 30px;
			font-family: 'Microsoft YaHei','Lantinghei SC','Open Sans',Arial,'Hiragino Sans GB','STHeiti','WenQuanYi Micro Hei','SimSun',sans-serif;
			font-weight: 300;
			font-size: 44px;
		}
		.receipt__lines {
			padding: 30px;
			border-top: 1px dashed #dce2d6;
		}
		.receipt__line {
			display: -webkit-box;
			display: -ms-flexbox;
			display: flex;
			margin: 20px 0;
			-webkit-box-pack: justify;
			-ms-flex-pack: justify;
			justify-content: space-between;
			font-family: 'Microsoft YaHei','Lantinghei SC','Open Sans',Arial,'Hiragino Sans GB','STHeiti','WenQuanYi Micro Hei','SimSun',sans-serif;
			font-size: 35px;
		}
		.receipt__line__item {
			font-weight: 300;
			padding: 10px 10px;
		}
		.receipt__line__price {
			font-weight: 400;
			padding: 10px 10px;
		}
		.receipt__total {
			display: -webkit-box;
			display: -ms-flexbox;
			display: flex;
			margin: 10px 0;
			padding: 20px;
			-webkit-box-pack: justify;
			-ms-flex-pack: justify;
			justify-content: space-between;
			font-family: 'Microsoft YaHei','Lantinghei SC','Open Sans',Arial,'Hiragino Sans GB','STHeiti','WenQuanYi Micro Hei','SimSun',sans-serif;
			font-size: 24px;
			background-color: #eff7e8;
		}
		.receipt__total__item, .receipt__total__price {
			font-weight: 400;
		}
		.receipt__back {
			text-align: center;
		}


	</style>
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>
</head>

<body>
<div class="container">
	<div class="receipt">
		<h2 class="receipt__title">支付成功！</h2>
		<p id="statusMessage"class="receipt__subtitle">以下是您的支付信息：</p>
		<ul class="receipt__lines">
			<li class="receipt__line">
				<span class="receipt__line__item">订单编号</span>
				<span class="receipt__line__price">${orderId}</span>
			</li>
			<li class="receipt__line">
				<span class="receipt__line__item">支付金额</span>
				<span id="price" class="receipt__line__price"></span>
			</li>
			<li class="receipt__line">
				<a id="phoneHref" href=""><span class="receipt__line__item">客服电话</span></a>
				<span id="phone" class="receipt__line__price"></span>
			</li>
		</ul>

		<p class="receipt__back">
			<a id="productHref" href="">返回商品列表</a>
		</p>
	</div>
</div>

<script type="text/javascript">
	$(function () {
		request.ajax("http://www.peshion.com/apiWeb/pay/paySuccess/${orderId}","POST",{},true,function(result){
			if(result.success){
				$("#price").text("¥  "+result.data.price);
				$("#phone").text(result.data.phone);
				$("#phoneHref").attr("href","tel:"+result.data.phone);
				$("#productHref").attr("href","http://www.peshion.com/wapApp/products/toProduct?devNum="+result.data.devNum);
			}else{
				alert("系统繁忙。。");
			}
		});

		request.ajax("http://www.peshion.com/apiWeb/pay/payReturn/${orderId}","POST",{},true,function(result){
			if(result.success){
			}else{
			}
		});


		var t1=window.setInterval(refreshCount, 2000);
		var times=0;
		function payReturn() {
			if(times>60){
				$("#statusMessage").text("系统繁忙，出货失败");
				window.clearInterval(t1);
				alert("出货失败，退款即将到账，请注意查收");
			}
		}
		function refreshCount() {
			request.ajax("http://www.peshion.com/apiWeb/pay/payStatus/${orderId}","POST",{},true,function(result){
				if(result.success){
					if(result.data.payStatus==1){
						$("#statusMessage").text("出货成功，请取货");
					}else if(result.data.payStatus==3 || result.data.payStatus==4|| result.data.payStatus==0 ){
						$("#statusMessage").text("正在出货中，请稍后....");
						times++;
					}else if(result.data.payStatus==2){
						$("#statusMessage").text("未能出货，退款金额即将到账，请注意查收");
						alert("未能出货，退款金额即将到账，请注意查收");
						window.clearInterval(t1);
					}
				}else{
					alert("系统繁忙。。");
				}
			});

			payReturn();

		}
	});
</script>



</body>
</html>