<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>会员充值</title>

    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link href="${pageContext.request.contextPath}/static/recharge/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/recharge/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>
</head>
<body>

<section class="aui-flexView">
    <header class="aui-navBar aui-navBar-fixed b-line">
        <a href="javascript:;" class="aui-navBar-item">
            <i class="icon icon-return"></i>
        </a>
        <div class="aui-center">
            <span class="aui-center-title">充值中心</span>
        </div>
        <a href="javascript:;" class="aui-navBar-item">
            <i class="icon icon-sys"></i>
        </a>
    </header>
    <section class="aui-scrollView">

        <div class="divHeight"></div>
        <div class="aui-flex">
            <div class="aui-flex-box">
                <h2>套餐充值</h2>
            </div>
        </div>
        <div id="mealBody" class="aui-current-box">

        </div>

        <div class="aui-current-box">
        </div>

    </section>

</section>
<script type="text/javascript">




    $(function(){
        var jsessionid = localStorage.getItem("APIWEB_JSESSIONID");
        request.ajax("http://www.peshion.com/apiWeb/meal/AllMeal","POST",{},true,function (data) {
            if(data.success){
                $.each(data.data,function (index, item) {
                    var mealA=$("<a></a>");
                    mealA.addClass("aui-current-item");
                    mealA.attr("id",item.id);
                    mealA.attr("href","javascript:;");
                    mealA.attr("memey",item.memey);
                    mealA.attr("gift",item.gift);
                    var div2=$("<div></div>").addClass("aui-current-item-hd");
                    var h1=$("<h1></h1>").append(item.name);
                    var h2=$("<h2></h2>").append("促销价格："+item.memey);
                    var h3=$("<h3></h3>").append("赠送金额："+item.gift);
                    div2.append(h1).append(h2).append(h3);
                    mealA.append(div2);
                    $("#mealBody").append(mealA);
                });
                var ysdiv=$("<div></div>").addClass("clearfix");
                $("#mealBody").append(ysdiv);
            }else{
                //TODO
            }
        });


        $(document).on('click','.aui-current-item',function () {
            $(this).addClass('this-card').siblings().removeClass('this-card');
            $('#type-amount').html($(this).find('.cardAmount').html());
            var params = "money=" + $(this).attr("memey") + "&gift=" + $(this).attr("gift");
            request.ajax("http://www.peshion.com/apiWeb/orders/unifiedOrder;jsessionid=" + jsessionid, "POST", params, false, function (data) {
                if (data.success) {
                    //SUCCESS
                    var orderId = data.data.rechargelog;
                    window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7262448316d3e202&redirect_uri=http%3A%2F%2Fwww.peshion.com%2FwapApp%2Fpays%2Fcode&response_type=code&scope=snsapi_userinfo&state=" + orderId + "#wechat_redirect";
                    //调用平台
                } else {
                    //TODO
                }
            });
        });




    })

</script>
</body>

</html>
