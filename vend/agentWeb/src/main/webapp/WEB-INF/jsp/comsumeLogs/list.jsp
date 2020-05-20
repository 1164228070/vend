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
	              	<form id="searchForm" action="" class="form-inline" onsubmit="return false;">
	              	  <div class="form-group">
	              	  	<select class="form-control" name="key" id="query_key">
	              	  	  <option value="orderId">订单号</option>
	              	  	  <option value="comsumeLog">流水号</option>
	              	  	  <option value="devNum">设备号</option>
	              	  	  <option value="productName">商品名</option>
	              	  	</select>
	              	  </div>
	              	  
	              	  <div class="form-group">
	              	    <input type="text" id="query_value" name="value" class="form-control" placeholder="关键字">
	              	  </div>
	              	  
	              	  <div class="form-group">
                        <label>下单时间</label>

                        <div class="input-group">
                          <div class="input-group-addon">
                            <i class="fa fa-clock-o"></i>
                          </div>
                          <input type="text" class="form-control pull-right" name="createDateRange" id="create_date_range">
                        </div>
                      </div>
                        <div class="form-group">
                            <select class="form-control" name="payStatus">
                            </select>
                        </div>
	              	  <button type="submit" class="btn bg-olive btn-flat">查询</button>
	              	</form>
	              </div>
                </div>
                <table id="comsumeLog_table"></table>
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
  
  <!-- 引入时间范围插件 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/bootstrap-daterangepicker/daterangepicker.css">
  <script src="${pageContext.request.contextPath}/static/bower_components/moment/moment.js"></script>
  <script src="${pageContext.request.contextPath}/static/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
  
  <!-- page script -->
  <script type="text/javascript">
    var locale = {
        "format": 'YYYY-MM-DD',
        "separator": " - ",
        "applyLabel": "确定",
        "cancelLabel": "取消",
        "fromLabel": "起始时间",
        "toLabel": "结束时间'",
        "customRangeLabel": "自定义",
        "weekLabel": "W",
        "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
        "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        "firstDay": 1
    };
    $(function (){

        var values = cache.get(2);

        if(values){
            var $querySelect = $("select[name='payStatus']");
            var result = "<option value=''>请选择状态</option>";
            $.each(values,function(key,value){
                result += "<option value="+key+">"+value+"</option>";
            });
            $querySelect.html(result);
        }


    	$('#create_date_range').daterangepicker({
    		 autoUpdateInput : false,   
    		 locale:locale,    
    		 timePicker: false, 
    		 timePickerIncrement: 30,   
    		 format: 'YYYY-MM-DD'
        },function(start, end, label) {
            var result= start.format('YYYY-MM-DD')+this.locale.separator+ end.format('YYYY-MM-DD');
            this.element.val(result);
        });
    	
    	var $table;
        var conditionParams;
    	$("#searchForm").on("submit",function(e){
    		var param = $(this).serializeObject();
    		if(param.key && param.value){
    			var key = param.key;
    			var value = param.value;
    		    delete param['key'];
    		    delete param['value'];
    			param[key] = value;
    		}
            conditionParams=param;
    		$table.bootstrapTable('refresh',{query:param});
    		return false;
    	});
    	
  	  var queryUrl = '${pageContext.request.contextPath}/comsumeLogs';
  	  $table = $('#comsumeLog_table').bootstrapTable({
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
               var temp = {   
                   pageStart: (params.offset / params.limit) + 1,   //页码
                   sort: params.sort,      //排序列名  
                   sortOrder: params.order //排位命令（desc，asc） 
               };
              $.extend(temp,conditionParams);
             return temp;
          },

          columns: [{
              checkbox: true,
              visible: true                  //是否显示复选框
          },{
              field: 'orderId',
              title: '订单号',
              formatter:orderFormatter
          },{
              field: 'comsumeLog',
              title: '流水号'
          },{
              field: 'productName',
              title: '会员名称'
          },{
              field: 'devNum',
              title: '设备号'
          },{
              field: 'payType',
              title: '支付方式',
              formatter:payTypeFormatter
          },{
              field: 'payAmount',
              title: '消费金额'
          },{
              field: 'createDate',
              title: '交易时间',
              formatter:createDateFormatter
          },{
              field: 'tradeType',
              title: '交易类型',
              formatter:tradeTypeFormatter
          },{
              field: 'payStatus',
              title: '订单状态',
              formatter:payStatusFormatter
          }],
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
    function payTypeFormatter(value,row,index) {
        return cache.getName(5,value);
    }
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
    function tradeTypeFormatter(value,row,index) {
        return cache.getName(11,value);
    }

    function detailFormatter (value,row,index) {
        return '<a href="'+request.contextPath+'/comsumeDetails?orderId='+row.orderId+'">明细</a>';
    }

    function orderFormatter (value,row,index) {
        return '<a href="'+request.contextPath+'/insideComsumes/toInsideComsumes/'+value+'">'+value+'</a>';
    }

  </script>
</body>
</html>
 
 
