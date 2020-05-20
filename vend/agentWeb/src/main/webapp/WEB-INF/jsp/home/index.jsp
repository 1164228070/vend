<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/morris/morris.css">
  </head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

 <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
 
 <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
 
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>控制面板
      </h1>
      <ol class="breadcrumb">
        <li class="active">控制面板</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <!-- Small boxes (Stat box) -->
      <div class="row">
          <%--<div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-aqua">
                  <div class="inner">
                      <h3>${orderCount}</h3>
                      <p>今日订单</p>
                  </div>
                  <div class="icon">
                      <i class="ion ion-bag"></i>
                  </div>
                  <a href="${pageContext.request.contextPath}/comsumeLogs" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"></i></a>
              </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-green">
                  <div class="inner">
                      <h3>${productCount}</h3>
                      <p>商品数</p>
                  </div>
                  <div class="icon">
                      <i class="ion ion-stats-bars"></i>
                  </div>
                  <a href="${pageContext.request.contextPath}/products" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
              </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-yellow">
                  <div class="inner">
                      <h3>${memberCount}</h3>
                      <p>注册会员</p>
                  </div>
                  <div class="icon">
                      <i class="ion ion-person-add"></i>
                  </div>
                  <a href="${pageContext.request.contextPath}/members" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
              </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-red">
                  <div class="inner">
                      <h3>${devCount}</h3>
                      <p>异常设备</p>
                  </div>
                  <div class="icon">
                      <i class="ion ion-pie-graph"></i>
                  </div>
                  <a href="${pageContext.request.contextPath}/devs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
              </div>
          </div>
          <!-- ./col -->
      </div>--%>
        <!-- /.row -->
        <!-- Main row -->
        <%--<div class="row">--%>
            <!-- Left col (We are only adding the ID to make the widgets sortable)-->
            <%--<section class="col-lg-8 connectedSortable">

                <!-- solid sales graph -->
                <div class="box box-solid bg-teal-gradient">
                    <div class="box-header">
                        <i class="fa fa-th"></i>

                        <h3 class="box-title">近一年</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn bg-teal btn-sm" data-widget="collapse"><i class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn bg-teal btn-sm" data-widget="remove"><i class="fa fa-times"></i>
                            </button>
                        </div>
                    </div>
                    <div class="box-body border-radius-none">
                        <div class="chart" id="line-chart" style="height: 250px;"></div>
                    </div>
                    <!-- /.box-body -->
                    <div class="box-footer no-border">
                        <div class="row">
                            <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
                                <input type="text" id="finish" data-readonly="true" data-width="60" data-height="60" data-fgColor="#00a65a">
                                <div class="knob-label">今日完成</div>
                            </div>
                            <!-- ./col -->
                            <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
                                <input type="text" id="invalid" data-readonly="true" data-width="60" data-height="60" data-fgColor="#39CCCC">
                                <div class="knob-label">今日无效</div>
                            </div>
                            <!-- ./col -->
                            <div class="col-xs-4 text-center">
                                <input type="text" id="error" data-readonly="true" data-max="300" data-width="60" data-height="60" data-fgColor="#dd4b39">
                                <div class="knob-label">今日异常</div>
                            </div>
                            <!-- ./col -->
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.box-footer -->
                </div>
                <!-- /.box -->

                <div class="nav-tabs-custom">
                    <!-- Tabs within a box -->
                    <ul class="nav nav-tabs pull-right">
                        <li class="active"><a href="#revenue-chart" data-toggle="tab">近一周</a></li>
                        <li class="pull-left header"><i class="fa fa-inbox"></i> 会员注册</li>
                    </ul>
                    <div class="tab-content no-padding">
                        <!-- Morris chart - Sales -->
                        <div class="chart tab-pane active" id="revenue-chart" style="position: relative; height: 300px;"></div>
                    </div>
                </div>
                <!-- /.nav-tabs-custom -->

            </section>
            <!-- /.Left col -->

            <!-- Rigth col -->
            <section class="col-lg-4 connectedSortable">
                <!-- DONUT CHART -->
                <div class="box box-danger">
                    <div class="box-header with-border">
                        <h3 class="box-title">支付方式</h3>

                        <div class="box-tools pull-right">
                            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                            </button>
                            <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                        </div>
                    </div>
                    <div class="box-body chart-responsive">
                        <div class="chart" id="pay-type-chart" style="height: 300px; position: relative;"></div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->

            </section>--%>
        <!-- /.Rigth col -->
        
      </div>
      <!-- /.row (main row) -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
 
  <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>  
  
  <script type="text/javascript">
      $(function(){
    	  
    	/*  //获取最近十二个月的统计数据
    	  request.ajax(request.contextPath+"/statistic/order","post",{},true,function(result){
    		  if(result.success){
    	    	  var line = new Morris.Line({
    	    		  element : 'line-chart',
    	    		  resize : true,
    	    		  parseTime : false,
    	    		  data : [209,236,325,439,507,576,722,879,938,1364,1806,1851,1931,2198,2349,2460,2735],
    	    	      xkey : 'createDate',  
      		          ykeys : ['finishCount','errorCount'],       
      		          labels : ['交易完成', '交易异常'],  
    	    		  lineColors: ['#00a65a', '#ff0000'],
    	    		  hideHover : 'auto'   
    	          });
    	    	  var total=0,finish=0,invalid=0,error=0;
    	    	  $.each([209,236,325,439,507,576,722,879,938,1364,1806,1851,1931,2198,2349,2460,2735],function(key,value){
    	    		  total += value.count;
    	    		  /!*if(value.payStatus=="finish"){
    	    			  finish = value.count;
    	    		  }else if(value.payStatus=="invalid"){
    	    			  invalid = value.count;
    	    		  }else if(value.payStatus=="error"){
    	    			  error = value.count;
    	    		  }*!/
                      finish=value.count;
                      invalid=value.count;
                      error=value.count;
    	    	  });
    	    	  //今日信息统计
    	    	  $('#finish').knob({max: total});
    	    	  $('#finish').val(finish).trigger("change");
    	    	  $('#invalid').knob({max: total});
    	    	  $('#invalid').val(invalid).trigger("change");
    	    	  $('#error').knob({max: total});
    	    	  $('#error').val(error).trigger("change");
    		  }
    	  });*/

/*    	  request.ajax(request.contextPath+"/statistic/members/increment","get",{},true,function(result){
    		  if(result.success){
    	          var area = new Morris.Bar({
    		          element   : 'revenue-chart',
    		          resize    : false,
    		          parseTime : false,
    		          data      : result.weekSummary,
    		          xkey      : 'registeDate',
    		          ykeys     : ['total'],
    		          label    : ['注册数'],
    		          lineColors: ['#a0d0e0'],
    		          hideHover : 'auto'
    		      });
    		  }
          });*/

    	  //支付chart
/*    	  request.ajax(request.contextPath+"/statistic/pay/type/chart","get",{},true,function(result){
    		  if(result.success){
    			  if(result.data){
    				  $.each(result.data,function(key,value){
    					  value.label = cache.getName(5,value.payType);
    					  value.value = value.count;
    				  });
    			  }else{
    				  result.data = [{"label" : "订单数","value":0}];
    			  }

		    	  var donut = new Morris.Donut({
		    	    element  : 'pay-type-chart',
		    	    resize   : true,
		    	    colors   : ['#3c8dbc', '#f56954', '#00a65a', '#f56954', '#00a65a','#ff3454'],
		    	    data     : result.data,
		    	    hideHover: 'auto'
		    	  });
    		  }else{
    			  //TODO

    		  }
    	  });*/
      });
  </script>
  
   <%-- <script src="${pageContext.request.contextPath}/static/plugins/jquery-knob/dist/jquery.knob.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/plugins/raphael/raphael.js"></script>
    <script src="${pageContext.request.contextPath}/static/plugins/morris/morris.js"></script>--%>
</div>
</body>
</html> 