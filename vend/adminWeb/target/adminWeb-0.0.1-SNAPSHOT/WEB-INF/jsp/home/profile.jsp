<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
   
    <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>个人中心</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Examples</a></li>
        <li class="active">个人中心</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-3">

          <!-- Profile Image -->
          <div class="box box-primary">
            <div class="box-body box-profile">
              <img class="profile-user-img img-responsive img-circle" src="${pageContext.request.contextPath}/static/dist/img/user4-128x128.jpg" alt="User profile picture">

              <h3 class="profile-username text-center">${user.name}</h3>
              <p class="text-muted text-center">${user.email}</p>

              <ul class="list-group list-group-unbordered">
                <li class="list-group-item">
                  <b>Followers</b> <a class="pull-right">1,322</a>
                </li>
                <li class="list-group-item">
                  <b>Following</b> <a class="pull-right">543</a>
                </li>
                <li class="list-group-item">
                  <b>Friends</b> <a class="pull-right">13,287</a>
                </li>
              </ul>

              <a href="#" class="btn btn-primary btn-block"><b>Follow</b></a>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

          <!-- About Me Box -->
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">About Me</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <strong><i class="fa fa-pencil margin-r-5"></i> Skills</strong>
              <p>
                <span class="label label-danger">UI Design</span>
                <span class="label label-success">Coding</span>
                <span class="label label-info">Javascript</span>
                <span class="label label-warning">PHP</span>
                <span class="label label-primary">Node.js</span>
              </p>
              <hr>

              <strong><i class="fa fa-file-text-o margin-r-5"></i> Notes</strong>

              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam fermentum enim neque.</p>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
        <div class="col-md-9">
          <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#settings" data-toggle="tab">设置</a></li>
              <li><a href="#password" data-toggle="tab">重置密码</a></li>
            </ul>
            <div class="tab-content">
              <div class="active tab-pane" id="settings">
                <form class="form-horizontal" id="reset_user_info_form">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" value="${user.name}" placeholder="名字"  data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 control-label">邮箱</label>

                    <div class="col-sm-10">
                      <input type="email" class="form-control" id="inputEmail" value="${user.email}" placeholder="邮箱"  data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputLoginName" class="col-sm-2 control-label">登录名</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputLoginName" value="${user.loginName}" placeholder="登录名" data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputAge" class="col-sm-2 control-label">年龄</label>

                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputAge" value="${user.age}" placeholder="年龄"  data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-danger">更 新</button>
                    </div>
                  </div>
                </form>
              </div>
              <div class="tab-pane" id="password">
                <form class="form-horizontal" id="reset_password_form">
                  <div class="form-group">
                    <label for="oldPassword" class="col-sm-2 control-label">原始密码</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="oldPassword" placeholder="原始密码" data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="newPassword" class="col-sm-2 control-label">新密码</label>

                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="newPassword" placeholder="新密码" data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="secondPassword" class="col-sm-2 control-label">确认密码</label>

                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="secondPassword" placeholder="确认密码" data-bv-notempty="true">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                      <button type="submit" class="btn btn-danger">重 置</button>
                    </div>
                  </div>
                </form>
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
          <!-- /.nav-tabs-custom -->
        </div>
        <!-- /.col -->
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
          $("form[id='reset_user_info_form']").bootstrapValidator({
        	  feedbackIcons: {
        		  valid: 'glyphicon glyphicon-ok',
                  invalid: 'glyphicon glyphicon-remove',
                  validating: 'glyphicon glyphicon-refresh'
              }
          }); 
          $("form[id='reset_password_form']").bootstrapValidator({
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
 
 
 