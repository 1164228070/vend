<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>商户管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>费率管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">商户管理</a></li>
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
            <form id="rateForm" class="form-horizontal" action="" >

              <div class="box-body">
                <div class="col-md-6">
                  <div class="form-group">
                    <label  class="col-sm-2 control-label">费率</label>
                    <div class="col-sm-10">
                      <input id="userRate" name="rate"  cssClass="form-control" placeholder="费率" data-bv-notempty="true"/>
                    </div>
                  </div>
                  </div>
                </div>

                

              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button id="updateRate" type="submit" class="btn btn-success btn-flat">更新</button>
              </div>
              <!-- /.box-footer -->
            </form>
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
        $("form[id='user']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        });
      $("#updateRate").click(function () {
        var params=$("#rateForm").serialize();
        var userRate=parseFloat($("#userRate").val());
        var agentRate=parseFloat(${agentRate});
        if(userRate<agentRate || userRate>1){
          tip.error("商户费率不能低于代理商费率且必须小于1");
          return false;
        }

        request.ajax("${pageContext.request.contextPath }/users/updateRate/${ids}","PUT",params,true,function(data){
          if(data.success){
            window.location.href="${pageContext.request.contextPath}/users";
          }else{
            tip.error(data.msg);
          }
        });
        return false;
      });

    });
  </script>
  
</body>
</html>
 
 
