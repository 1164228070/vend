<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>账单管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加账单':'编辑账单'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">账单管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/accountLogs/${accountLog.id }" modelAttribute="accountLog">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty accountLog.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
              
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="accountLog" class="col-sm-2 control-label">流水号</label>
                    <div class="col-sm-10">
                      <form:input path="accountLog"  cssClass="form-control" placeholder="流水号" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="memberId" class="col-sm-2 control-label">员工Id</label>
                    <div class="col-sm-10">
                      <form:input path="memberId"  cssClass="form-control" placeholder="员工Id" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="memberName" class="col-sm-2 control-label">员工名称</label>
                    <div class="col-sm-10">
                      <form:input path="memberName"  cssClass="form-control" placeholder="员工名称" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="moneyType" class="col-sm-2 control-label">积分</label>
                    <div class="col-sm-10">
                      <form:input path="moneyType"  cssClass="form-control" placeholder="积分" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="money" class="col-sm-2 control-label">彩票</label>
                    <div class="col-sm-10">
                      <form:input path="money"  cssClass="form-control" placeholder="彩票" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="dataSrc" class="col-sm-2 control-label">XXXX</label>
                    <div class="col-sm-10">
                      <form:input path="dataSrc"  cssClass="form-control" placeholder="XXXX" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="createDate" class="col-sm-2 control-label">拨款时间</label>
                    <div class="col-sm-10">
                      <form:input path="createDate"  cssClass="form-control" placeholder="拨款时间" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="tradeType" class="col-sm-2 control-label">交易类型(1.充值 2消费 3 退货 4进 5出 6 退款)</label>
                    <div class="col-sm-10">
                      <form:input path="tradeType"  cssClass="form-control" placeholder="交易类型(1.充值 2消费 3 退货 4进 5出 6 退款)" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="comsumeLog" class="col-sm-2 control-label">交易号</label>
                    <div class="col-sm-10">
                      <form:input path="comsumeLog"  cssClass="form-control" placeholder="交易号" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
                
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty accountLog.id ? '保 存' : '更 新'}</button>
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
        $("form[id='accountLog']").bootstrapValidator({
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
 
 
