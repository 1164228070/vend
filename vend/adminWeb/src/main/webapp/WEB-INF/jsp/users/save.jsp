<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>用户管理</title>
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
        <h1>${id==null?'添加用户':'编辑用户'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">用户管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/users/${user.id }" modelAttribute="user">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty user.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
              
                <div class="col-md-6">

                  <input id="agentName" type="hidden" name="agentName"/>

                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-10">
                      <form:input path="name"  cssClass="form-control" placeholder="姓名" data-bv-notempty="true"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="age" class="col-sm-2 control-label">年龄</label>
                    <div class="col-sm-10">
                      <form:input path="age"  cssClass="form-control" placeholder="年龄" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="loginName" class="col-sm-2 control-label">登录名</label>
                    <div class="col-sm-10">
                      <form:input path="loginName"  cssClass="form-control" placeholder="登录名" data-bv-notempty="true"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                      <form:input path="password"  cssClass="form-control" placeholder="密码" />
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <div class="checkbox">
                        <label>
                          <form:checkbox path="admin"/>是否管理员
                        </label>
                      </div>
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <div class="radio">
                        <label>
                          <form:radiobuttons path="status" items="${statusList}" itemValue="value" itemLabel="name" data-bv-notempty="true" delimiter="&nbsp;"/>
                        </label>
                        </div>
                    </div>
                  </div>
                
                </div>
                <div class="col-md-6">
                
                  <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">邮箱</label>
                    <div class="col-sm-10">
                      <form:input path="email"  cssClass="form-control" placeholder="邮箱" data-bv-notempty="true"/>
                    </div>
                  </div>
     
                  <div class="form-group">
                    <label for="roles" class="col-sm-2 control-label">角色</label>
                    <div class="col-sm-10">
                      <form:select path="roles" cssClass="form-control" multiple="true" data-placeholder="选择角色">
                          <form:options items="${roles}" itemLabel="name" itemValue="id"/>
                      </form:select>
                    </div>
                  </div>

                  <div class="form-group">
                    <label class="col-sm-2 control-label">代理商</label>
                    <div class="col-sm-4">
                      <select id="agents_select" class="form-control" name="agentId">
                        <option value="">请选择代理商</option>
                        <c:forEach items="${agents}" var="agent">
                          <option value="${agent.id}">${agent.name}</option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>


                
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty user.id ? '保 存' : '更 新'}</button>
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
        $('input[name="admin"],input[name="status"]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });

        $("#agents_select").change(function () {
          var agentName= $("#agents_select").find("option:selected").text();
          $("#agentName").val(agentName);
        });
    	
        $('#roles').select2();
        $("form[id='user']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }).on("success.form.bv",function(e){
        	var $form = $(this),roleIds = $form.find("#roles").val(),extraParam="";
        	if(roleIds){
	        	$.each(roleIds,function(index,value){
	        		extraParam += "<input type='hidden' name='roles["+index+"].id' value='"+value+"'>";
	        	});
	        	$form.append($(extraParam));
	        	$form.find("#roles").remove();
	        	$form.find("input[name='_roles']").remove();
        	}
        });
    });
  </script>
</body>
</html>
 
 
