<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>商品组管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/select2/dist/css/select2.min.css">
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加商品组':'编辑商品组'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">商品组管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/productGroups/${productGroup.id }" modelAttribute="productGroup">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty productGroup.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
              
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">组名</label>
                    <div class="col-sm-10">
                      <form:input path="name"  cssClass="form-control" placeholder="组名" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="num" class="col-sm-2 control-label">自编号</label>
                    <div class="col-sm-10">
                      <form:input path="num"  cssClass="form-control" placeholder="自编号" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
  	  
                  <div class="form-group">
                    <label for="dev.id" class="col-sm-2 control-label">设备</label>
                    <div class="col-sm-10">
                      <form:select path="dev.id" cssClass="form-control" data-placeholder="选择设备">
                          <form:options items="${devs}" itemLabel="num" itemValue="id"/>   
                      </form:select>
                    </div>
                  </div>
                  
           <%--        <div class="form-group">
                    <label for="agent.id" class="col-sm-2 control-label">代理</label>
                    <div class="col-sm-10">
                      <form:select path="agent.id" cssClass="form-control" data-placeholder="选择代理" data-bv-notempty="true">
                          <form:options items="${agents}" itemLabel="name" itemValue="id"/>
                      </form:select>
                    </div>
                  </div> --%>
                </div>
                
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty productGroup.id ? '保 存' : '更 新'}</button>
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
        $('select[id="agent.id"]').select2();
        $("form[id='productGroup']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }); 
    });
  </script>
</body>
</html>
 
 
