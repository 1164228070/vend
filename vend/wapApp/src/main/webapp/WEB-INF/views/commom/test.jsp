<%--
  Created by IntelliJ IDEA.
  User: 乐
  Date: 2020/3/31
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>
</head>
<script type="text/javascript">
    var payMode="";

    request.ajax("http://localhost:80/apiWeb/pay/payMode", "POST", "devNum=1000104", true, function (result) {
        if (result.success) {
            payMode=result.data.mode;
        } else {
            alert("获取支付模式失败");
        }
    });
    $(document).on('click','#test',function () {
        alert(payMode);
    });



</script>
<body>
<button id="test">ssfsd</button>
</body>
</html>
