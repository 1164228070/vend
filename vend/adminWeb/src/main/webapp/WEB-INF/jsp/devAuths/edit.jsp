<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>设备授权管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>编辑设备授权</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">设备授权管理</a></li>
          <li class="active">编辑</li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/devAuths/${devAuth.id}" modelAttribute="devAuth">
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="PUT">

              <div class="box-body">

                <div class="col-md-6">

                  <div class="form-group">
                    <label for="devNum" class="col-sm-2 control-label">设备号</label>
                    <div class="col-sm-10">
                      <form:input path="devNum"  cssClass="form-control" placeholder="设备号" data-bv-notempty="true"/>
                    </div>
                  </div>

               <%--   <div class="form-group">
                    <label for="devIP.ip" class="col-sm-2 control-label">IP</label>
                    <div class="col-sm-10">
                      <!-- 选择框 -->
                      <form:select path="devIP.ip" cssClass="form-control" data-placeholder="选择IP" data-bv-notempty="true">
                        <form:options items="${devIPs}" itemLabel="ip" itemValue="ip"/>
                      </form:select>
                    </div>
                  </div>--%>


                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty devAuth.id ? '保 存' : '更 新'}</button>
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
  
  <script type="text/javascript">
    $(function(){
        $("form[id='devAuth']").bootstrapValidator({
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
 
 
