<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>  
 
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/dist/css/AdminLTE.min.css">
    
    <!-- AdminLTE Skins. Choose a skin from the css/skinsfolder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/dist/css/skins/_all-skins.min.css">
    
       
    <!-- iCheck -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/iCheck/square/blue.css">
  
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- 
      <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
     -->
    
    <!-- js -->   
    
    <!-- jQuery 3 -->
    <script src="${pageContext.request.contextPath}/static/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap 3.3.7 -->
    <script src="${pageContext.request.contextPath}/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- iCheck -->
    <script src="${pageContext.request.contextPath}/static/plugins/iCheck/icheck.min.js"></script>
 
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/static/dist/js/adminlte.min.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="${pageContext.request.contextPath}/static/dist/js/demo.js"></script>

 
 <!-- bootstrap校验js -->
 <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/validate/js/bootstrapValidator.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/validate/js/language/zh_CN.js"></script>
 <!-- 校验样式 -->
 <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/validate/css/bootstrapValidator.min.css"/>

<!-- 字典js -->
<script src="${pageContext.request.contextPath}/static/my/cache.js"></script>

<!-- 提示js -->
<script src="${pageContext.request.contextPath}/static/my/tip.js"></script>

<!-- 请求js -->
<script src="${pageContext.request.contextPath}/static/my/request.js"></script>

<script src="${pageContext.request.contextPath}/static/my/extend.js"></script>

 <script type="text/javascript">
     $(function(){
         request.contextPath = "${pageContext.request.contextPath}";
         request.imageServer = "http://118.190.52.54:9090/images/";
     });
 </script>


