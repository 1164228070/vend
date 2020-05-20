<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>支付模式管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/select2/dist/css/select2.min.css">
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
    
    <style type="text/css">
      .checkbox label, .radio label {
          padding-left: 0px;
      }
    </style>
    
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加支付模式':'编辑支付模式'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>支付模式管理</a></li>
          <li><a href="#">支付模式管理</a></li>
          <li class="active">列表</li>
        </ol>
      </section>
  
      <!-- Main content -->
      <section class="content">
        <div class="row">
        <!-- right column -->
        <div class="col-md-12">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <!-- form start -->
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/payModes/${payMode.id }" modelAttribute="payMode">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty payMode.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="mode" class="col-sm-2 control-label">支付模式</label>
                    <div class="col-sm-10">
                      <form:select path="mode" cssClass="form-control" data-placeholder="选择模式">
                        <form:option value="1">直连微信支付宝</form:option>
                        <form:option value="2">中信支付</form:option>
                      </form:select>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty payMode.id ? '保 存' : '更 新'}</button>
              </div>
              <!-- /.box-footer -->
            </form:form>
          </div>
          <!-- /.box -->
        </div>
        <!--/.col (right) -->
      </div>
        <!-- /.row -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    
    <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
    
  </div>
  
  <script src="${pageContext.request.contextPath}/static/bower_components/select2/dist/js/select2.full.min.js"></script>
  <script type="text/javascript">
    $(function(){

    	
    });
  </script>
</body>
</html>
 
 
