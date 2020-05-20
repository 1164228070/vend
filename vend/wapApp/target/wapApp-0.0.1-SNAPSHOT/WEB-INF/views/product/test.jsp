<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
        body {font-family: "Microsoft YaHei";font-size: 0.7rem;color: #333;line-height: 0.7rem;width: 100%;background: #f2f2f2;}
        em {font-style: normal}
        li {list-style: none}
        a {text-decoration: none;outline: 0;color: #333;}
        header{background: #7A5BFA;width: 100%;height: 2.5rem;line-height: 2.5rem;text-align: center;font-size: 0.9rem;position: fixed;left: 0;top: 0;z-index: 97;border-bottom:1px solid #efefef; }
        header ._left {display: block;position: absolute;left: 0;top: 0;}
        header ._left img {height: 1rem;margin:0 0 0 0.6rem;}
        header span{ color:#fff}
        .contaniner {width: 100%;height: 300px; margin-top:2.5rem;background: #2D07C6}
        .pay_img{ width:15%;float: left}
        .pay_img img{ width:100%;margin : 35px 35px 35px 35px;}
        .pay_test{color: #fff;float: left;margin : 100px 100px 50px 100px;}
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
            font-size: 30px;
            padding: 12px 30px 12px 30px;
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
    <div class="pay_img">
        <img src="${pageContext.request.contextPath}/static/payUI/images/pay_2.jpg">
    </div>
    <div  class="pay_test">
        <div style="margin: 11px 10px 50px 10px;font-size: 70px">全民嗨购</div>
        <div style="margin: 11px 10px 10px 10px;font-size: 50px">客服电话:15986413735</div>
    </div>
</div>
<div class="centerimg">
    <img style="width: 100%" src="${pageContext.request.contextPath}/static/payUI/images/pay_1.jpg">
</div>

<div>
    <img style="width: 30%;float: left" src="${pageContext.request.contextPath}/static/payUI/images/pay_3.gif">
    <img style="width: 30%;float: right" src="${pageContext.request.contextPath}/static/payUI/images/pay_4.gif">
</div>
<div style="font-size: 50px">
    <div style="margin-left: 150px;float: left">
        <div style="margin-top: 30px">狗不理包子</div>

    </div>
    <div style="float: right;margin-right: 150px">
        <div style="color: red;font-size: 30px;margin-left: 70px">￥15</div>
        <button id="payBtn" type="button" class="buyBtn">原价购买</button>
    </div>
</div>
<div style="margin-top: 500px;text-align: center">
    <a id="luckyBtn">
        <img src="${pageContext.request.contextPath}/static/payUI/images/pay_btn.png">
    </a>
</div>
<script type="text/javascript">


</script>
</body>
</html>
