<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
    <title>工作台</title>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

 <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
 
 <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
 
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
                工作台
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>商户中心</a></li>
        <li class="active">工作台</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    
      <div class="row">  
      
        <div class="col-md-12">
          <div class="box">
            <div class="box-body">
              <a class="btn btn-app" href="${pageContext.request.contextPath}/works/checkInfo">
                <i class="fa fa-check-square"></i> 一键检查
              </a>
            </div>
          </div>
      </div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
 
  <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
</div>
</body>
</html>
 
 
 