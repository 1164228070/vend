<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>错误页面</title>
	<style type="text/css">
		* { margin: 0; padding: 0; }
		body { background-color: #f8f8f8; -webkit-font-smoothing: antialiased; }
		.error { position: absolute; left: 50%; top: 50%; width: 483px; margin: -300px 0 0 -242px; padding-top: 199px; font-size: 18px; color: #666; text-align: center; background: #f8f8f8 url(${pageContext.request.contextPath}/static/images/500.jpg) 0 0 no-repeat; }
		.error .remind { margin: 30px 0; }
		.error .button { display: inline-block; padding: 0 20px; line-height: 40px; font-size: 14px; color: #fff; background-color: #f8912d; text-decoration: none; }
		.error .button:hover { opacity: .9; }
	</style>
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>
</head>
<body>
<div class="error">
	<p class="remind">${MSG}</p>
	<p><a id="refund" class="button" href="#">申请退款</a></p>
</div>

<script type="text/javascript">
	$("#refund").click(function () {
		request.ajax("http://www.peshion.com/apiWeb/weChat/refundPay?orderId=${orderId}","POST",{},true,function (data) {
			if(data.success){
				alert(data.msg);
			}else{
				//TODO
				alert("退款失败");
			}
		});
	});
</script>

</body>
</html>








