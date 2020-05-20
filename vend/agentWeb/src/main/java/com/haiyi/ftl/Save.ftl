<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>${msg}管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>$${r"{"}id==null?'添加${msg}':'编辑${msg}'${r"}"}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">${msg}管理</a></li>
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
            <form:form class="form-horizontal" action="$${r"{"}pageContext.request.contextPath ${r"}"}/${req}/$${r"{"}${smallClassName}.id ${r"}"}" modelAttribute="${smallClassName}">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="$${r"{"}empty ${smallClassName}.id ? 'POST' : 'PUT' ${r"}"}">
              
              <div class="box-body">
              
  	  <#list fieldList as var>
  	  
  	    <#if var_index%2==0>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="${var[0]}" class="col-sm-2 control-label">${var[6]}</label>
                    <div class="col-sm-10">
                      <form:input path="${var[0]}"  cssClass="form-control" placeholder="${var[6]}" <#if "${var[4]}"=="false">data-bv-notempty="true"</#if>/>
                    </div>
                  </div>
        <#else>
                  <div class="form-group">
                    <label for="${var[0]}" class="col-sm-2 control-label">${var[6]}</label>
                    <div class="col-sm-10">
                      <form:input path="${var[0]}"  cssClass="form-control" placeholder="${var[6]}" <#if "${var[4]}"=="false">data-bv-notempty="true"</#if>/>
                    </div>
                  </div>
                </div>
          </#if>
      </#list>
                </div>
                
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">$${r"{"}empty ${smallClassName}.id ? '保 存' : '更 新'${r"}"}</button>
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
        $("form[id='${smallClassName}']").bootstrapValidator({
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
 
 
 