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
        <h1>${id==null?'添加代理商':'编辑代理商'}</h1>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/agents/${agent.id}" modelAttribute="agent">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty agent.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <form:input path="name"  cssClass="form-control" placeholder="名称" data-bv-notempty="true"/>
                    </div>
                  </div>
                  <!-- 代理角色 -->
                  <div class="form-group">
                    <label for="roles" class="col-sm-2 control-label">角色</label>
                    <div class="col-sm-10">
                      <form:select path="agentRoleId" cssClass="form-control" data-placeholder="选择角色">
                          <form:option value="">请选择父菜单</form:option>
                          <form:options items="${agentRoles}" itemLabel="name" itemValue="id"/>
                      </form:select>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="loginName" class="col-sm-2 control-label">登录名</label>
                    <div class="col-sm-10">
                     <form:input path="loginName"  cssClass="form-control" placeholder="代理商登陆名" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="linkName" class="col-sm-2 control-label">联系人</label>
                    <div class="col-sm-10">
                      <form:input path="linkName"  cssClass="form-control" placeholder="联系人" />
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="linkPhone" class="col-sm-2 control-label">电话</label>
                    <div class="col-sm-10">
                      <form:input path="linkPhone"  cssClass="form-control" placeholder="电话" />
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="address" class="col-sm-2 control-label">地址</label>
                    <div class="col-sm-10">
                      <form:input path="address"  cssClass="form-control" placeholder="地址" />
                    </div>
                  </div>



                </div>


  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="leftMoney" class="col-sm-2 control-label">余额</label>
                    <div class="col-sm-10">
                      <form:input path="leftMoney"  cssClass="form-control" placeholder="余额" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="rate" class="col-sm-2 control-label">费率</label>
                    <div class="col-sm-10">
                      <form:input path="rate"  cssClass="form-control" placeholder="费率" data-bv-notempty="true"/>
                    </div>
                  </div>

                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="score" class="col-sm-2 control-label">积分</label>
                    <div class="col-sm-10">
                      <form:input path="score"  cssClass="form-control" placeholder="积分" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="status" class="col-sm-2 control-label">状态</label>
                    <div class="col-sm-10">
                      <%-- <form:input path="status"  cssClass="form-control" placeholder="状态" data-bv-notempty="true"/> --%>
                      <form:select path="status" cssClass="form-control" data-placeholder="选择状态">
                        <form:options items="${dictVals}" itemLabel="name" itemValue="value"/>
                      </form:select>
                    </div>
                  </div>
                </div>


                  <div class="col-md-6">
                    <div class="form-group">
                      <label for="payMode" class="col-sm-2 control-label">支付模式</label>
                      <div class="col-sm-10">
                        <form:select path="payMode" cssClass="form-control" data-placeholder="选择模式">
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
                <button type="submit" class="btn btn-success btn-flat">${empty agent.id ? '保 存' : '更 新'}</button>
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
        $("form[id='agent']").bootstrapValidator({
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
 
 
