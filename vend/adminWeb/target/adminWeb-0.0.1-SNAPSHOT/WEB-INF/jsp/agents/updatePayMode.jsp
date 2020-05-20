<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>代理商管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>支付模式管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">代理商管理</a></li>
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
            <form id="rateForm" class="form-horizontal" action="" method="post">
              <div class="box-body">
                <div class="col-md-6">

                  <div class="form-group">
                    <label  class="col-sm-2 control-label">支付模式</label>
                    <div class="col-sm-10">
                      <select style="width:180px;height: 27px" name="payMode" cssClass="form-control" data-placeholder="选择模式">
                        <option value="1">微信支付宝</option>
                        <option value="2">中信支付</option>
                      </select>
                    </div>
                  </div>

                  </div>
                </div>




                

              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button id="updatePayMode" type="submit" class="btn btn-success btn-flat">更新</button>
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
        $("form[id='agent']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        });
        $("#updatePayMode").click(function () {
            var params=$("#rateForm").serialize();
            request.ajax("${pageContext.request.contextPath }/agents/updatePayMode/${ids}","PUT",params,true,function(data){
                if(data.success){
                    window.location.href="${pageContext.request.contextPath}/agents";
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
 
 
