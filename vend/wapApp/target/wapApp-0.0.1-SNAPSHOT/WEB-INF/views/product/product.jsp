<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>售货机商品列表</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/product/css/style.css" />

    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/request.js"></script>

    <style type="text/css">
        .image-price {
            font-size: 18px;
            padding-left: 4px;
            font-family: Arial;
            color: #d44d44;
            font-weight: 700;
            margin-top: 20px;
            text-align: center;
            transition: all .1s ease-out;
        }
        .image-title{
            color: #333;
            font-size: 20px;
            padding-bottom: 10px;
            text-align:center;
        }
    </style>
</head>

<body>



<div class="section">
    <div class="container">
        <div class="mod-title">
            <h2 style="font-size: 80px">商品列表</h2>
        </div>
        <div class="case-list">
            <ul id="products">
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    var devNum="${devNum}";
    if(devNum=="1000999"){
        request.ajax("http://www.peshion.com/apiWeb/products/AllProducts?devNum=${devNum}","POST",{},true,function (data) {
            if(data.success){
                $.each(data.data,function (index, item) {
                    var li=$("<li style='height: 320px;width: 220px'></li>");
                    var div=$("<div></div>").addClass("case-show-img");
                    var a=$("<a></a>");
                    a.attr("name","Aimg");
                    a.attr("count",item.storeCount);
                    a.attr("status",item.status);
                    if(item.storeCount>0 && item.status==1){
                        a.attr("href","${pageContext.request.contextPath}/products/toPayUI?productId="+item.id+"&price="+item.price+"&devNum=${devNum}&productName="+item.name);
                    }else {
                        a.attr("href","");
                    }
                    var div01=$("<div></div>").addClass("case-type");
                    var p1=$("<p style='font-size: 35px'></p>").addClass("image-title");
                    p1.text(item.name);
                    div01.append(p1);
                    var div02=$("<div></div>");
                    var img;
                    if(item.status==2){
                        img=$("<img width='220' height='220'/>").attr("src","${pageContext.request.contextPath}/static/images/soldOut.jpg");
                    }else {
                        if(item.storeCount<=0){
                            img=$("<img width='220' height='220'/>").attr("src","${pageContext.request.contextPath}/static/images/sellOut.jpg");
                        }else {
                            img=$("<img width='220' height='220'/>").attr("src","http://118.190.52.54:9090/images/"+item.img);
                            //img=$("<img width='300' height='300'/>").attr("src","${pageContext.request.contextPath}/static/images/sellOut.jpg");
                        }
                    }


                    div02.append(img);
                    var p2=$("<p style='font-size: 30px'></p>").addClass("image-price");
                    p2.text("价格："+item.price);
                    a.append(div01).append(div02).append(p2);
                    div.append(a);
                    li.append(div);
                    $("#products").append(li);
                });

            }else{
                //TODO
                alert("失败");
            }
            /*$("[name='Aimg']").click(function () {
                var counts=$(this).attr("count");
                var status=$(this).attr("status");
                if(status==2){
                    alert("商品已下架");
                }else if(counts<=0){
                    alert("商品库存不足");
                }
            });*/





        });
    }



</script>

</body>
</html>