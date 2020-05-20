<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
        <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>
  <body class="hold-transition login-page">
  <div class="login-box">
    <div class="login-logo">
      <b>商户管理</b>系统
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
      <p class="login-box-msg">输入账号密码</p>
  
      <form name="session-creation"  method="post">
        <input id="weChat" type="hidden" name="openId">
        <input type="hidden" name="type" value="user">   
        <div class="form-group has-feedback">
          <input type="text" name="loginName" class="form-control" placeholder="登录名">
          <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
          <input type="password" name="password" class="form-control" placeholder="密码">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="row">
          <div class="col-xs-8">
            <div class="checkbox icheck">
              <label>
                <input type="checkbox"> 记住密码
              </label>
            </div>
          </div>
          <!-- /.col -->
          <div class="col-xs-4">
            <button type="submit" class="btn btn-primary btn-block btn-flat">登 录</button>
          </div>
          <!-- /.col -->
        </div>
      </form>
      
      <div id="changeBtn" class="social-auth-links text-center">
        <a href="${pageContext.request.contextPath}/session/new" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i>代理商入口</a>
      </div>
      <!-- 
      <a href="register.html" class="text-center">Register a new membership</a>
       -->
    </div>
    <!-- /.login-box-body -->  
  </div>
  <!-- /.login-box -->    
	 
  <script type="text/javascript">
      $(function(){
        if(is_weixin()){

        }
        var openId="${openId}";
        if(openId!="" && $("#weChat").val() !=null){
          $("#changeBtn").hide();
        }
        request.ajax(request.contextPath +'/session','POST',"openId=${openId}&type=user",false,function(result){
          if(result.success){
            window.location.href=request.contextPath;
          }else{
            $("#weChat").val(result.data.openId);
            alert(result.msg);
          }
        });






        function is_weixin(){
          var ua = window.navigator.userAgent.toLowerCase();
          if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
          } else {
            return false;
          }
        }


          $('input').iCheck({
              checkboxClass: 'icheckbox_square-blue',
              radioClass: 'iradio_square-blue',
              increaseArea: '20%' /* optional */
          });
      	  $("form[name='session-creation']").bootstrapValidator({
              fields: {
                  loginName: {
                      validators: {
                          notEmpty: {
                              message: '名称不能为空'
                          }
                      }
                  },
                  password: {
                      validators: {
                          notEmpty: {
                              message: '密码不能为空'
                          },
                          regexp: {
                              regexp: /^[a-zA-Z0-9_\.]{6,10}$/,
                              message: '密码由[a-zA-Z0-9_.]组成的6到10位'
                          }
                      }
                  }
              }
          }).on('success.form.bv', function(e) {
              e.preventDefault();
              var $form = $(e.target);
              //提交数据
              var URL = request.contextPath +'/session';
              request.ajax(URL,'POST',$form.serialize(),false,function(result){
              	  if(result.success){
              	  	  window.location.href=request.contextPath;
              	  }else{
              	  	  tip.error(result.msg);
              	  	  $form.data('bootstrapValidator').resetForm();
              	  }        
              });
          });
      });
  </script>
  </body>
</html>
 
 
 