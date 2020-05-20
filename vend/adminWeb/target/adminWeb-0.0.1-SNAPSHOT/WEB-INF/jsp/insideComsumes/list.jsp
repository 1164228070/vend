<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
  <head>
    <title>消费记录列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>消费记录管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
          <li><a href="#">消费记录管理</a></li>
          <li class="active">列表</li>
        </ol>
      </section>
  
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-xs-12">
            <div class="box box-success">
            
              <div class="box-body">
              
                <!-- 添加搜索条件 -->
                <div class="box-body">
                  <div class="row">

	              </div>
                </div>
                <table id="insideComsume_table"></table>
              </div>
              <!-- /.box-body -->
            </div>
            <!-- /.box -->
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
  
  <script src="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table.js"></script>
  <script src="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table-zh-CN.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table.css">



  <!-- page script -->
  <script type="text/javascript">

    $(function (){

    	
  	  var queryUrl = '${pageContext.request.contextPath}/insideComsumes';
  	   $('#insideComsume_table').bootstrapTable({
  		  url: queryUrl,                      //请求后台的URL（*）
          method: 'GET',                      //请求方式（*）
          toolbar: '#toolbar',                //工具按钮用哪个容器
          striped: true,                      //是否显示行间隔色
          cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
          pagination: true,                   //是否显示分页（*）
          sortable: true,                     //是否启用排序
          sortOrder: "asc",                   //排序方式
          
          sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
          
          pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
          pageSize: 10,                       //每页的记录行数（*）
          pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
             
          clickToSelect: true,                //是否启用点击选中行
          uniqueId: "id",                     //每一行的唯一标识，一般为主键列
          
          //得到查询的参数
          queryParams : function (params) {
            var orderId="${orderId}";
            var temp=null;

            if(orderId!=""){
              temp = {
                pageStart: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order, //排位命令（desc，asc）
                orderId:"${orderId}"
              };
            }else {
              temp = {
                pageStart: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
              };
            }
             return temp;
          },

          columns: [{
              checkbox: true,
              visible: true                  //是否显示复选框
          },{
              field: 'orderId',
              title: '订单号'
          },{
              field: 'productName',
              title: '商品名'
          },{
              field: 'devNum',
              title: '设备号'
          },{
              field: 'price',
              title: '单价'
          },{
              field: 'payStatus',
              title: '订单状态',
              formatter:payStatusFormatter
          },{
              field: 'createDate',
              title: '交易时间',
              formatter:createDateFormatter
          }/*,{
              field: '操作',
              title: '操作',
              width : 70,
          }*/],
          onLoadSuccess: function () {
            //加载成功触发
          },
          onLoadError: function () {
            //加载失败触发
          },
          responseHandler: function(res) {
              return {
                  "total": res.total,//总页数
                  "rows": res.rows   //数据
               };
          }
      });
    });

    function payStatusFormatter(value,row,index) {
        return cache.getName(2,value);
    }
    function createDateFormatter(value,row,index) {
    	var date = new Date(value);
    	var y = date.getFullYear();  
    	var m = date.getMonth() + 1;  
    	m = m < 10 ? ('0' + m) : m;  
    	var d = date.getDate();  
    	d = d < 10 ? ('0' + d) : d;  
    	var h = date.getHours();
    	h = h < 10 ? ('0' + h) : h;
    	var minute = date.getMinutes();
    	var second = date.getSeconds();
    	minute = minute < 10 ? ('0' + minute) : minute;  
    	second = second < 10 ? ('0' + second) : second; 
    	return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;  
    }
</script>
</body>
</html>
 
 
