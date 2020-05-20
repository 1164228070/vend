<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${pageContext.request.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/table/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/static/table/bootstrap-table-zh-CN.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/table/bootstrap-table.css">
</head>
<body>
<table id="user_table">
</table>

 <script type="text/javascript">
    $(function (){
    	var $table;   	
  	  var queryUrl = '${pageContext.request.contextPath}/members/queryComsumeLog/${memberId}';
  	  $table = $('#user_table').bootstrapTable({
  		  url: queryUrl,                      //请求后台的URL（*）
          method: 'GET',                      //请求方式（*）
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
          uniqueId: "openid",                     //每一行的唯一标识，一般为主键列
          
          /* //得到查询的参数
          queryParams : function (params) {
               var temp = {   
                   page: (params.offset / params.limit) + 1,   //页码
                   sort: params.sort,      //排序列名  
                   sortOrder: params.order //排位命令（desc，asc） 
               };
             return temp;
          }, */
          
          columns: [{
              checkbox: true,  
              visible: true                  //是否显示复选框  
          },{
              field: 'memberName',
              title: '会员名'
          },{
              field: 'orderId',
              title: '订单号'
          },{
              field: 'devName',
              title: '设备名'
          },{
              field: 'devNum',
              title: '设备号'
          },{
              field: 'payAmount',
              title: '消费金额'
          },{
              field: 'time',
              title: '洗车时间'
          },{
              field: 'createDate',
              title: '创建日期'
          }],
          onLoadSuccess: function () {
            //加载成功触发
          },
          onLoadError: function () {
            //加载失败触发
          },
          responseHandler: function(res) {
        	  return {
                  "total": res.total, //总页数
                  "rows": res.rows   //数据
               };
              
          }
      });
    });
  </script>

</body>
</html>