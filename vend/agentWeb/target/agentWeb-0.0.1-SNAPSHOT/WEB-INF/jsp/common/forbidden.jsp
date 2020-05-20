<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
        <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
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
        403 Error Page
      </h1>
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="error-page">
        <h2 class="headline text-red">403</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-red"></i> 抱歉! 您没有权限访问该资源.</h3>

          <p>
          ${message}
          </p>

          <form class="search-form">
            <div class="input-group">
              <input type="text" name="search" class="form-control" placeholder="Search">

              <div class="input-group-btn">
                <button type="submit" name="submit" class="btn btn-danger btn-flat"><i class="fa fa-search"></i>
                </button>
              </div>
            </div>
            <!-- /.input-group -->
          </form>
        </div>
      </div>
      <!-- /.error-page -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
  
</div>
<!-- ./wrapper -->
</body>

</html>
 
 
 