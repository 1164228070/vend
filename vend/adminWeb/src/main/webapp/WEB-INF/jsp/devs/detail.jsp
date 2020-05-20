<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
    <title>设备详情</title>
    
    <style type="text/css">
		.profile_dev {
		    position: relative;
		} 
		.profile_dev > a{
		    position: absolute;
		    top:0px;right:0px;
		} 
		.profile { 
		    font-size: 21px;
		    margin-top: 5px;
		}
    </style>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
   
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>设备详情</h1>
      <ol class="breadcrumb">
        <li class="active">设备详情</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-4">

          <!-- Profile Image -->
          <div class="box box-primary">
            <div class="box-body box-profile">
            
            
              <div class="profile_dev">
                <h3 class="profile text-center" id="dev_info" data-pay="${dev.payType}">${dev.name}</h3>
                <p class="text-muted text-center">${dev.userName}</p>
                <a id="stateId" href="#"><i id="stateClass" class=""></i></a>
                <%--<c:choose>
                  <c:when test="${dev.state==1}">
                    <a href="#"><i class="fa fa-circle text-success margin-r-5"></i>在线</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#"><i class="fa fa-circle text-danger margin-r-5"></i>离线</a>
                  </c:otherwise>
                </c:choose>--%>
              </div>
            
              <ul class="list-group list-group-unbordered">
                <li class="list-group-item">
                  <b>编号</b> <a class="pull-right">${dev.num}</a>
                </li>
                <li class="list-group-item">
                  <b>自编号</b> <a class="pull-right">${dev.shortNum}</a>
                </li>
                <li class="list-group-item">
                  <b>token</b> <a class="pull-right">${dev.token}</a>
                </li>
                <li class="list-group-item">
                  <b>单价</b> <a class="pull-right">${dev.CSL}</a>
                </li>
                <li class="list-group-item">
                  <b>客服电话</b> <a class="pull-right">${dev.phone}</a>
                </li>
              </ul>
              <%--<strong><i class="fa fa-paypal margin-r-5 fa-2x" style="color: #f39c12;"></i>支付方式</strong>
              <p id="pay_type"></p>--%>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
        <%--<div class="col-md-8">
        
          <!-- About Me Box -->
          <div class="box box-primary">
            <div class="box-body">
              <ul class="list-group list-group-unbordered">
                <li class="list-group-item">
                  <b>购买速度</b> <a class="pull-right">${dev.speed1}</a>
                </li>
                <li class="list-group-item">
                  <b>购买速度</b> <a class="pull-right">${dev.speed2}</a>
                </li>
                <li class="list-group-item">
                  <b>购买脉冲</b> <a class="pull-right">${dev.KD}</a>
                </li>
                <li class="list-group-item">
                  <b>购买速度</b> <a class="pull-right">${dev.KD2}</a>
                </li>
                <li class="list-group-item">
                  <b>感应扣款金额</b> <a class="pull-right">${dev.CSL}</a>
                </li>
                <li class="list-group-item">
                  <b>是否可退货</b> <a class="pull-right">${dev.TF}</a>
                </li>
                <li class="list-group-item">
                  <b>退货模式</b> <a class="pull-right">${dev.TFType}</a>
                </li>
                <li class="list-group-item">
                  <b>退货比例</b> <a class="pull-right">${dev.TBPercentage}</a>
                </li>
                <li class="list-group-item">
                  <b>购买比例</b> <a class="pull-right">${dev.TFMS}</a>
                </li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>--%>
        <!-- /.col -->
      </div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>
    
    <!-- /.content-wrapper -->
   
    <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
  </div>
  
  <script src="${pageContext.request.contextPath}/static/plugins/qrcode/jquery.qrcode.min.js"></script>
     <script type="text/javascript">
      $(function(){
        request.ajax("http://www.peshion.com/apiWeb"+"/devs/getStatus/${dev.num}","POST",{},true,function(data){
          if(data.success){
            $("#stateId").append(data.msg);
            $("#stateClass").addClass("fa fa-circle text-success margin-r-5");
          }else{
            $("#stateId").append(data.msg);
            $("#stateClass").addClass("fa fa-circle text-danger margin-r-5");
          }
        });
    	  payTypeFormatter();
      });
      
      function payTypeFormatter() {
          var values =$("#dev_info").data("pay").split(',');
          var $payType = $("#pay_type");
          for(var i=0;i<values.length;i++){
        	  $payType.append("<span class='label label-success'>"+cache.getName(5,values[i])+"</span>\n");
          }
      }
  </script>
</body>
</html>