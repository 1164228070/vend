<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/morris/morris.css">

      <style>
    .item4{


    }

    </style>

  </head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

 <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
 
 <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
 
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>控制面板
      </h1>
      <ol class="breadcrumb">
        <li class="active">控制面板</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <!-- Small boxes (Stat box) -->
      <div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner box">
              <h4>商品数</h4>
              <h3>12</h3>
              <span class="item1">未支付10</span>
              <span class="item2">已支付10</span>
              <span class="item3">缺货标示</span>
              <span class="item4">温度:</span>
            </div>
            <div class="icon">
              <i class="ion ion-bag"></i>
            </div>
            <a href="${pageContext.request.contextPath}/comsumeLogs" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3>23</h3>
              <p>商品数</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="${pageContext.request.contextPath}/products" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3>2323</h3>
              <p>注册会员</p>
            </div>
            <div class="icon">
              <i class="ion ion-person-add"></i>
            </div>
            <a href="${pageContext.request.contextPath}/members" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h3>2323</h3>
              <p>异常设备</p>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a href="${pageContext.request.contextPath}/devs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
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