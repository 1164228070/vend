<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
    <title>支付宝支付</title>
    <meta name="description" content="支付宝支付" />
    <meta name="keywords" content="支付宝支付" />
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>


    <script type="text/javascript">
            request.ajax("http://www.peshion.com/apiWeb/pay/alipay/zx_unifiedorder/${orderId}?code=${code}","POST",{},false,function(info){
                if(info.success){
                    payInfo = JSON.parse(info.data.pay_info);
                    AlipayJSBridge.call("tradePay",{
                        "tradeNO": payInfo.tradeNO
                    }, function(result){
                        if(result.resultCode == '9000'){
                                location.href = "${pageContext.request.contextPath}/callback/wx/${orderId}";

                        }else {
                            alert("调取支付宝支付失败");
                        }
                    });

                }else{
                    alert("支付失败");
                }
            })


    </script>
</head>
<body>


</body>
</html>
