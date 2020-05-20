<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend" %>

<!DOCTYPE html>
<html>
  <head>
    <title>代理商列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>代理商管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
          <li><a href="#">代理商管理</a></li>
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
                        <input type="text" name="name" class="form-control" placeholder="名称">
                      </div>
                      <%--<div class="col-xs-2">
                        &lt;%&ndash; <form:select path="agentId" cssClass="form-control" data-placeholder="选择代理商">
                        	<form:options items="${agents}" itemLabel="name" itemValue="id"/>
                      	</form:select> &ndash;%&gt;
                      	<select name="agentId" id="agentId"  class="form-control" data-placeholder="选择代理商">
                      	</select>
                      </div>--%>
                      <div class="form-group">
                        <%-- <form:select path="agentId" cssClass="form-control" data-placeholder="选择代理商">
                        	<form:options items="${agents}" itemLabel="name" itemValue="id"/>
                      	</form:select> --%>
                      	<select name="status" id="status"  class="form-control" data-placeholder="请选择审核状态">
                      	</select>
                      </div>
                      <div class="form-group">
                         <button type="submit" class="btn bg-olive btn-flat">查询</button>
                      </div>
                    </form>
                  </div>
                </div>
                
                <div id="toolbar">
                  <mz:menuTag menuURL="agents/new">
                    <a href="${pageContext.request.contextPath}/agents/new" type="button" class="btn bg-maroon btn-flat">添加</a>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="agents/delete">
                    <button type="button" data-ope="delete" class="btn bg-olive btn-flat">删除</button>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="agents/update">
                    <button type="button" data-ope="edit" class="btn bg-orange btn-flat">编辑</button>
                  </mz:menuTag>
                  <mz:menuTag menuURL="agents/updateRate">
                    <button type="button" data-ope="updateRate" class="btn bg-orange btn-flat">批量修改费率</button>
                  </mz:menuTag>
                  <mz:menuTag menuURL="agents/updatePayMode">
                    <button type="button" data-ope="updatePayMode" class="btn bg-orange btn-flat">批量修改支付模式</button>
                  </mz:menuTag>
                </div>
       
                <table id="agent_table">
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
  
    <script src="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table-zh-CN.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table.css">
  </div>
  
  <!-- page script -->
  <script type="text/javascript">
    $(function (){
    	var $table;
      var conditionParams;

      $("#searchForm").on("submit",function(e){
        var param = $(this).serializeObject();
        conditionParams=param;
        $table.bootstrapTable('refresh',{query:param});
        return false;
      });
    	
    	$("#toolbar > button").click(function(){
    		var idArray = $table.bootstrapTable('getAllSelections');
    		if(idArray.length==0){
    			tip.warn("至少选择一条代理商记录");
    			return;
    		}
    		idArray = $.map(idArray,function(item){
		       return item.id;
		    });
    		var ope = $(this).data("ope");
    		if("delete"==ope){
    			var ids = idArray.join(",");
    			request.ajax("${pageContext.request.contextPath}/agents/"+ids,"delete",{},true,function(data){
    				if(data.success){
    					//TODO 确保参数问题？？？？
    					$table.bootstrapTable('refresh');
    				}else{
    					tip.error(data.msg);
    				}
    			});
    		}else if("updateRate"==ope){
            var ids = idArray.join(",");
              window.location.href="${pageContext.request.contextPath}/agents/toUpdateRate/"+ids;
          }else if("updatePayMode"==ope){
              var ids = idArray.join(",");
              window.location.href="${pageContext.request.contextPath}/agents/toUpdatePayMode/"+ids;
            }else if("edit"==ope){
    			//界面跳转
    			window.location.href="${pageContext.request.contextPath}/agents/"+idArray[0]+"/edit";
    		}
    	});
    	
  	  var queryUrl = '${pageContext.request.contextPath}/agents';
  	  $table = $('#agent_table').bootstrapTable({
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
              field: 'id',
              title: 'ID'
          }
          ,{
              field: 'name',
              title: '名称'
          },{
              field: 'loginName',
              title: '登录名'
          },{
              field: 'linkName',
              title: '联系人'
          },{
              field: 'linkPhone',
              title: '联系电话'
          },{
              field: 'address',
              title: '联系地址'
          },{
              field: 'leftMoney',
              title: '余额'
          },{
              field: 'rate',
              title: '费率'
          },{
              field: 'score',
              title: '积分'
          },{
              field: 'payMode',
              title: '支付模式',
              formatter:nameFormatter
            },{
              field: 'status',
              title: '审核状态',
              formatter:statusFormatter
          },{
              field: 'agentRoleName',
              title: '角色'
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
  	  function statusFormatter(value,row,index) {
          return cache.getName(9,value);
      }
    });
    
    $(function(){
    	/*$select=$("#agentId");
    	request.ajax(request.contextPath+"/devs/selectAgentId","POST",{},true,function(data){
            if(data.success){
          	    var o = data.data
          	    $select.empty();
          	    $select.append("<option value=''>请选择代理商</option>");
					$.each(o, function(i, m) {
						$select.append("<option value="+m.id+">"+ m.name + "</option>");
					});
            }else{
            	  tip.error(data.msg);
            }
        });*/
    	
    	request.ajax(request.contextPath+"/agents/selectStatus","POST",{},true,function(data){
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
    	
    });

    function nameFormatter (value,row,index) {
      return value==1?"微信支付宝":(value==2?"中信支付":"未知");
    }
    
  </script>
</body>
</html>
 
 
