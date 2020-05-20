<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend" %>

<!DOCTYPE html>
<html>
  <head>
    <title>提现申请列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>商户提现申请管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
          <li><a href="#">商户提现申请管理</a></li>
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
                      <form id="searchForm" action="" onsubmit="return false;" class="form-inline">
                          <div class="form-group">
                              <select class="form-control" name="key" id="query_key">
                                  <option value="userName">商户名</option>
                                  <option value="agentName">代理商</option>
                                  <option value="accNo">卡号</option>
                                  <option value="accUser">收款人</option>
                                  <option value="area">区域</option>
                              </select>
                          </div>
                          <div class="form-group">
                              <input type="text" id="query_value" name="value" class="form-control" placeholder="关键字">
                          </div>
                          <div class="form-group">
                              <select name="status" id="status"  class="form-control" data-placeholder="请选择审核状态">
                              </select>
                          </div>

                          <button type="submit" class="btn bg-olive btn-flat">查询</button>
                      </form>
                  </div>
                </div>
                
                <div id="toolbar">
                </div>
       
                <table id="userCashApply_table">
                </table>
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
  
  <!-- 引入switch -->
  <script src="${pageContext.request.contextPath}/static/plugins/switch/bootstrap-switch.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/switch/bootstrap-switch.min.css">
  
  <div class="modal fade" id="modal-verify">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">商户审核提现申请</h4>
        </div>
        <!-- form start -->
        <form class="form-horizontal" id="verify-form" action="${pageContext.request.contextPath }/cashConfigs">
		  <div class="modal-body">
		    <div class="row">
		      <!-- right column -->
		      <div class="col-md-12">
		        <!-- Horizontal Form -->
		        <input type="hidden" name="id"/>
		   
		        <div class="form-group">
		          <label for="type" class="col-sm-2 control-label">备注</label>
		          <div class="col-sm-10">
		            <textarea name="remark" class="form-control" rows="3" placeholder="备注..."></textarea>
		          </div>
		        </div>

		      	<div class="form-group">
		      	  <label for="accNo" class="col-sm-2 control-label">审核</label>
		      	  <div class="col-sm-10">
		      	    <div class="radio">
		      	      <span>
		      	        <input id="success" name="sign" type="radio" value="y" />
		      	        <label for="success" style="padding-left: 10px;">完成</label>
		      	      </span>
		      	      <span>
		      	        <input id="failure" name="sign" checked type="radio" value="n" />
		      	        <label for="failure" style="padding-left: 10px;">失败</label>
		      	      </span>
		      	    </div>
		      	  </div>
		      	</div>
		      	
		      </div>
		      <!-- /.box -->
		    </div>
		    
		  </div>
		  <div class="modal-footer">
		    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		    <button type="submit" id="save-config-btn" class="btn btn-primary">提交</button>
		  </div>
        </form>
      </div>
    </div>
  </div>
  
  
  <!-- page script -->
  <script type="text/javascript">
    $(function (){

        request.ajax(request.contextPath+"/userCashApplys/selectStatus","POST",{},true,function(data){
            if(data.success){
                var o = data.data;
                $("#status").empty();
                $("#status").append("<option value=''>请选择审核状态</option>");
                $.each(o, function(i, m) {
                    $("#status").append("<option value="+m.value+">"+ m.name + "</option>");
                });
            }else{
                tip.error(data.msg);
            }
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
    	
        $("form[id='verify-form']").on("submit",function(e){
            e.preventDefault();
    	  	var param=$(this).serialize();
  		    request.ajax("${pageContext.request.contextPath}/userCashApplys/"+$(this).find("input[name='id']").val()+"/status","put",param,true,function(data){
  		        if(data.success){
  		        	$('#modal-verify').modal('hide');
  		            $table.bootstrapTable('refresh');
  		        }else{
  		        	tip.error(data.msg);
  		        }
  		    }); 
        });
        
  	    var queryUrl = '${pageContext.request.contextPath}/userCashApplys';
  	    $table = $('#userCashApply_table').bootstrapTable({
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
                field: 'money',
                title: '提现金额'
            },{
                field: 'income',
                title: '商户收入'
            },{
                field: 'agentMoney',
                title: '代理商收入'
            },{
                field: 'adminMoney',
                title: '运营商收入'
            },{
                field: 'userName',
                title: '商户'
            },{
                field: 'agentName',
                title: '代理商'
            },{
                field: 'typeName',
                title: '方式'
            },{
                field: 'accNo',
                title: '收款号码'
            },{
                field: 'accUser',
                title: '收款人'
            },{
                field: 'area',
                title: '区域'
            },{
                field: 'createDate',
                title: '申请时间',
                formatter:createDateFormatter
            },{
                field: 'handlerDate',
                title: '处理时间',
                formatter:createDateFormatter
            },{
                field: 'remark',
                title: '备注'
            },{
                field: 'status',  
                title: '状态',
                formatter:operationFormatter
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
	  	$table.on("click","button[id='verify']",function(){
      	    var $modal = $('#modal-verify'); 
            $modal.modal({
            	keyboard: false
            });
            $modal.find("input[name='id']").val($(this).data("id"));
	  	});
	  	
        $('input[name="sign"]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });
    });
    
    function createDateFormatter(value,row,index) {
    	if(value){
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
    }
     
    function operationFormatter(value,row,index) {
        return '<button type="button" id="verify" '+(value!=0?'disabled':'')+' class="btn btn-block btn-success btn-sm" data-id="'+row.id+'">'+cache.getName(14,value)+'</button>';
    }
  </script>
</body>
</html>
 
 
