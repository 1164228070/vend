<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>检查报告</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/select2/dist/css/select2.min.css">
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>检查报告</h1>
      </section>
  
      <!-- Main content -->
      <section class="content">


        <div class="row">
          <c:forEach items="${products}" var="product" varStatus="status">

            <div class="col-md-3 col-sm-6 col-xs-12">
              <a href="${pageContext.request.contextPath}/productLogs/new?productId=${product.id}" class="info-box">
                <span class="info-box-icon bg-red"><i class="fa fa-google-plus"></i></span>

                <div class="info-box-content">
                  <span class="info-box-text">${product.name}</span>
                  <span class="info-box-number">库存:${product.storeCount}</span>
                  <span class="info-box-number">阀值:${product.threshold}</span>
                </div>
              </a>
            </div>

          </c:forEach>
        </div>
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
  </div>
  <script src="${pageContext.request.contextPath}/static/bower_components/select2/dist/js/select2.full.min.js"></script>
</body>
</html>