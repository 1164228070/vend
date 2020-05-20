<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <title>支付界面</title>
    <%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/static/payUI/css/style.css" type="text/css"/>--%>
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>
    <style type="text/css">
        html {
            font-size: 125%; /* 20梅16=125% min-font-size:12px bug*/
        }
        @media only screen and (min-width: 481px) {
            html {
                font-size: 188%!important; /* 30.08梅16=188% */
            }
        }
        @media only screen and (min-width: 561px) {
            html {
                font-size: 218%!important; /* 38.88梅16=218% */
            }
        }
        @media only screen and (min-width: 641px) {
            html {
                font-size: 250%!important; /* 40梅16=250% */
            }
        }
        body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
            margin: 0;padding: 0;border: 0;font-size: 1em;font: inherit;vertical-align: baseline;font-family: "Microsoft YaHei"}
        body {font-family: "Microsoft YaHei";font-size: 0.7rem;color: #333;line-height: 0.7rem;width: 100%;background: #f2f2f2;text-align: center}
        em {font-style: normal}
        li {list-style: none}
        a {text-decoration: none;outline: 0;color: #333;}
        header{background: #7A5BFA;width: 100%;height: 2.5rem;line-height: 2.5rem;text-align: center;font-size: 0.9rem;position: fixed;left: 0;top: 0;z-index: 97;border-bottom:1px solid #efefef; }
        header ._left {display: block;position: absolute;left: 0;top: 0;}
        header ._left img {height: 1rem;margin:0 0 0 0.6rem;}
        header span{ color:#fff}
        .contaniner {width: 100%;height: 300px; margin-top:2.5rem;background: #2D07C6}
        .pay_test{color: #fff;float: left;margin : 100px 100px 70px 70px;}
        .centerimg{width:100%;margin-top: 50px}


        .buyBtn {
            background: #c4344c;
            background-image: -webkit-linear-gradient(top, #c4344c, #283ab9);
            background-image: -moz-linear-gradient(top, #c4344c, #283ab9);
            background-image: -ms-linear-gradient(top, #c4344c, #283ab9);
            background-image: -o-linear-gradient(top, #c4344c, #283ab9);
            background-image: linear-gradient(to bottom, #c4344c, #283ab9);
            -webkit-border-radius: 28;
            -moz-border-radius: 28;
            border-radius: 28px;
            font-family: Arial;
            color: #fff;
            font-size: 60px;
            padding: 20px 100px 20px 100px;
            text-decoration: none;
        }

        .buyBtn:hover {
            background: #f2230c;
            background-image: -webkit-linear-gradient(top, #f2230c, #7d1b99);
            background-image: -moz-linear-gradient(top, #f2230c, #7d1b99);
            background-image: -ms-linear-gradient(top, #f2230c, #7d1b99);
            background-image: -o-linear-gradient(top, #f2230c, #7d1b99);
            background-image: linear-gradient(to bottom, #f2230c, #7d1b99);
            color: #fff;
            text-decoration: none;
        }





    </style>

</head>
<body>
<!--头部  star-->
<header style="color:#fff">
    <a href="javascript:history.go(-1);">
        <div class="_left"><img src="${pageContext.request.contextPath}/static/payUI/images/left.png"></div><span>支付订单</span></a>
</header>
<div class="contaniner">
    <div  class="pay_test">
        <div style="margin: 11px 10px 50px 10px;font-size: 70px">24小时无人自助便利店</div>
        <div style="margin: 11px 10px 10px 10px;font-size: 50px">客服电话:<span id="devPhone"></span></div>
    </div>
</div>
<div class="centerimg">
    <img style="width: 100%" src="${pageContext.request.contextPath}/static/payUI/images/pay_1.jpg">
</div>

<div style="margin:40px auto;">
    <img id="productImg" style="width: 250px;height: 250px" src="">
</div>
<div style="margin-top: 50px">
    <div style="float: left;margin-left: 100px">
        <div style="font-size: 70px">${productName}</div>
    </div>
    <div style="float: right;margin-right: 100px">
        <div style="color: red;font-size: 70px;margin-left: 70px">￥${price}</div>
    </div>
</div>
<div style="margin-top: 200px;text-align: center">
    <a id="luckyBtn">
        <button id="payBtn" type="button" class="buyBtn">确认购买</button>
    </a>
</div>
<div style="height: 300px"></div>
<!--内容 end-->


<script type="text/javascript">
    function showHideCode(){
        $("#showdiv").toggle();
    }


    $(function () {

        var payMode="";
        //www.peshion.com

        request.ajax("http://www.peshion.com/apiWeb/pay/payInfo", "POST", "devNum=${devNum}&productId=${productId}", true, function (result) {
            if (result.success) {
                payMode=result.data.mode;
                $("#devPhone").text(result.data.phone);
                $("#productImg").attr("src","http://118.190.52.54:9090/images/"+result.data.img);
            } else {
                alert("获取支付模式或客服电话失败");
            }
        });



        $(document).on('click','#payBtn',function () {
            /*var type=$('[name="Fruit"]:checked').val();

            alert(params);
            request.ajax("http://www.peshion.com/apiWeb/orders/unifiedOrder", "POST", params, true, function (data) {
                if (data.success) {
                    //SUCCESS
                    var orderId = data.data.orderId;
                   window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7262448316d3e202&redirect_uri=http%3A%2F%2Fwww.peshion.com%2FwapApp%2Fpays%2Fcode&response_type=code&scope=snsapi_userinfo&state=" + orderId + "#wechat_redirect";
                    //调用平台
                } else {
                    //TODO
                    alert("ss");
                }
            });
           return false;*/

            var params = "productId=${productId}&devNum=${devNum}&price=${price}";
            if(payMode=="2" || payMode==2){
                //通过中信连微信或支付宝支付
                request.ajax("http://www.peshion.com/apiWeb/orders/unifiedOrder", "POST", params, true, function (result) {
                    if (result.success) {
                        var orderId = result.data.orderId;
                        //调用H5支付
                        if(is_weixin()){
                            window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7262448316d3e202&redirect_uri=http%3A%2F%2Fwww.peshion.com%2FwapApp%2Fpays%2FzxCode&response_type=code&scope=snsapi_userinfo&state=" + orderId + "#wechat_redirect";
                        }else if(is_zfb()) {
                            //支付宝支付
                            window.location.href="https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2019072365983174&redirect_uri=http%3A%2F%2Fwww.peshion.com%2FwapApp%2Fpays%2FzxCode&scope=auth_user&state=" + orderId;
                        }else {
                            alert("请用微信或支付宝打开");
                        }
                    } else {
                        $.tip.alert(2, result.msg, 2000);
                    }
                });
            }else {
                //直接连微信或支付宝支付
                request.ajax("http://www.peshion.com/apiWeb/orders/unifiedOrder", "POST", params, true, function (result) {
                    if (result.success) {
                        var orderId = result.data.orderId;
                        //调用H5支付
                        if(is_weixin()){
                            window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7262448316d3e202&redirect_uri=http%3A%2F%2Fwww.peshion.com%2FwapApp%2Fpays%2Fcode&response_type=code&scope=snsapi_userinfo&state=" + orderId + "#wechat_redirect";
                        }else if(is_zfb()) {
                            //支付宝支付
                            $("#pay_form").prop("action", "http://www.peshion.com/apiWeb/pay/alipay/unifiedorder/" + orderId);
                            $("#pay_form").submit();
                        }else {
                            alert("请用微信或支付宝打开");
                        }
                    } else {
                        $.tip.alert(2, result.msg, 2000);
                    }
                });
            }




        });
        function is_weixin(){
            var ua = window.navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == 'micromessenger') {
                return true;
            } else {
                return false;
            }
        }
        function is_zfb(){
            //判断是支付宝app的浏览器
            var userAgent = navigator.userAgent.toLowerCase();
            return userAgent.match(/Alipay/i)=="alipay";
        }


    });


</script>

</body>
</html>