<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>提现申请管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加提现申请':'编辑提现申请'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">提现申请管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/cashApplys" modelAttribute="cashApply">
              <input type="hidden" name="_method" value="POST">
              
              <div class="box-body">
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="money" class="col-sm-2 control-label">金额</label>
                    <div class="col-sm-10">
                      <form:input path="money"  cssClass="form-control" placeholder="金额" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
                
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="typeName" class="col-sm-2 control-label">方式</label>
                    <div class="col-sm-10">
                      <select name="cashConfigId" class="form-control" data-placeholder="选择提现方式">
                        <c:forEach items="${cashConfigs}" var="cashConfig">
                          <option value="${cashConfig.id}">${cashConfig.type}:${cashConfig.accNo}</option>  
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty cashApply.id ? '保 存' : '更 新'}</button>
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
        $("form[id='cashApply']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }); 
        $('select[name="cashConfig"]').select2();
    });
  </script>
</body>
</html>
 
 
