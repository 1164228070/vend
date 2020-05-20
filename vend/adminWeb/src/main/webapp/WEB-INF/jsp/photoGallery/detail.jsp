<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  <title>图片详情</title>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

  <%@ include file="/WEB-INF/jsp/common/left.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>图片详情</h1>
      <ol class="breadcrumb">
        <li class="active">图片详情</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-4">

          <!-- Profile Image -->
          <div class="box box-primary">
            <div class="box-body box-profile">
              <img class="img-responsive" style="margin: 0 auto;" src="http://118.190.52.54:9090/images/${photoGallery.img}" alt="User profile picture">
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
       
        <!-- /.col -->
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


