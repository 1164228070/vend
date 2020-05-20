<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend" %>

<!DOCTYPE html>
<html>
  <head>
    <title>代理角色列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>代理角色管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
          <li><a href="#">代理角色管理</a></li>
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
                        <input type="text" name="keyWords" class="form-control" placeholder="关键字">
                      </div>
                      <div class="form-group">
                         <button type="submit" class="btn bg-olive btn-flat">查询</button>
                      </div>
                    </form>
                  </div>
                </div>
                
                <div id="toolbar">
                  <mz:menuTag menuURL="agentRoles/new">
                    <a href="${pageContext.request.contextPath}/agentRoles/new" type="button" class="btn bg-maroon btn-flat">添加</a>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="agentRoles/delete">
                    <button type="button" data-ope="delete" class="btn bg-olive btn-flat">删除</button>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="agentRoles/update">
                    <button type="button" data-ope="edit" class="btn bg-orange btn-flat">编辑</button>
                  </mz:menuTag>
                </div>
       
                <table id="agentRole_table">
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
 
    <!-- 引入ztree -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/ztree/css/zTreeStyle/zTreeStyle.css">
      
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ztree/js/jquery.ztree.excheck.js"></script>
  </div>
  
  <div class="modal fade" id="modal-auth" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="label"></h4>
        </div>
        <div class="modal-body">
          <div class="row">
              <ul id="menuTree" class="ztree"></ul>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
          <button id="save-auth-btn" type="button" class="btn btn-primary">更 新</button>
        </div>
      </div>
    </div>
  </div>
  
  <!-- page script -->
  <script type="text/javascript">

  
    var menuIds = [];
    //节点创建的时候触发
    function zTreeOnNodeCreated(event, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("menuTree");

    	  $.each(menuIds,function(index,value){
    	  	  if(treeNode.id==value){
    	  	  	  zTree.checkNode(treeNode,true,false);

    	  	  }      
    	  });

    };

    //处理数据
    function filter(treeId, parentNode, childNodes) {
    	  childNodes = childNodes.data;
    	  for (var i=0, l=childNodes.length; i<l; i++) {
    	      childNodes[i].isParent = !childNodes[i].leaf;
    	      childNodes[i].children=null;  
    	  }
    	  return childNodes;
    }
    
    function getAsyncURL(treeId,treeNode){  
  	 if(treeNode){
  	     return request.contextPath+"/menus/"+treeNode.id+"/menus";
  	 }else{
  	     return request.contextPath+"/menus/-1/menus";
  	 }
    }
	//授权
	var setting = {
      async: {
      	  enable: true,
      	  url:getAsyncURL,
      	  type : "get",
      	  dataFilter: filter
      },
      check: {
      	  enable: true,
      	  chkboxType: {"Y":"ps","N":"s"} 
      },
      data: {
      	  simpleData: {
      	  	enable: true
      	  }
      },
      callback: {
      	  onNodeCreated: zTreeOnNodeCreated  //当节点创建的时候触发
      }
    };
	   
    $(function (){
    	var $table;
    	
    	$("#searchForm").on("submit",function(e){
    		var param = $(this).serializeObject();  
    		$table.bootstrapTable('refresh',{query:param});
    		return false;
    	});
    	
    	$("#toolbar > button").click(function(){
    		var idArray = $table.bootstrapTable('getAllSelections');
    		if(idArray.length==0){
    			tip.warn("至少选择一条代理角色记录");
    			return;
    		}
    		idArray = $.map(idArray,function(item){
		       return item.id;
		    });
    		var ope = $(this).data("ope");
    		if("delete"==ope){
    			var ids = idArray.join(",");
    			request.ajax("${pageContext.request.contextPath}/agentRoles/"+ids,"delete",{},true,function(data){
    				if(data.success){
    					$table.bootstrapTable('refresh');
    				}else{
    					tip.error(data.msg);
    				}
    			});
    		}else if("edit"==ope){
    			//界面跳转
    			window.location.href="${pageContext.request.contextPath}/agentRoles/"+idArray[0]+"/edit";
    		}
    	});
    	
  	  var queryUrl = '${pageContext.request.contextPath}/agentRoles';
  	  $table = $('#agentRole_table').bootstrapTable({
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
             return temp;
          },
          
          columns: [{
              checkbox: true,  
              visible: true                  //是否显示复选框  
          },{
              field: 'name',
              title: '名称'
          },{
              title: '操作',
              width: 120,
              align: 'center',
              valign: 'middle',
              formatter : actionFormatter
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
  	  
	  //操作栏的格式化
      function actionFormatter(value, row, index) {
          var id = value;
          var result = "";
          result += "<a class='btn btn-xs btn-success btn-flat' title='授权' data-ope='auth' data-id='"+row.id+"' data-text='"+row.name+"'><span class='glyphicon glyphicon-pencil'></span></a>";
          return result;
      }
	  
	      $("#modal-auth button[id='save-auth-btn']").on("click",function(e){
  	       	  var zTree = $.fn.zTree.getZTreeObj("menuTree");
		  	  var menuIdArray = zTree.getCheckedNodes(true); 
		  	  menuIdArray = $.map(menuIdArray, function(item){
		  	  	 return item.id;
		      });
		  	  var roleId = $(this).data("roleId");
		  	  //提交数据
	  	      request.ajax(request.contextPath+"/agentRoles/"+roleId+"/menus","put",{"menuIds":menuIdArray.toString()},true,function(data){
  	  	          if(data.success){
  	  	        	  $('#modal-auth').modal('hide');
  	  	          	  tip.success("授权成功");
  	  	          }else{
  	  	          	tip.error(data.msg);
  	  	          }
  	  	      });
  	      });
	  
	  //监听操作
      $("table[id='agentRole_table']").on("click","td > a",function(e){
    	  var obj = $(e.currentTarget);
    	  
    	  var $modal = $('#modal-auth');
          if(obj.data("ope") === 'auth'){  //授权
        	  $modal.modal({
        		  keyboard: false
        	  });
        	  $modal.find("#label").html("修改"+obj.data("text")+"权限");
        	  $modal.find("button[id='save-auth-btn']").data("roleId",obj.data("id"));
        	  
        	  //加载当前角色的权限
        	  request.ajax(request.contextPath+"/agentRoles/"+obj.data("id")+"/menus","get",{},true,function(data){
	  	              if(data.success){
	  	            	    menuIds = data.data;
	  	              }else{
	  	              	  tip.error(data.msg);
	  	              }
	  	          });
        	  //加载属性
        	  $.fn.zTree.init($("#menuTree"), setting);
          }
      });
    });
  </script>
</body>
</html>
 
 
