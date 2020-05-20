<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend" %>

<!DOCTYPE html>
<html>
  <head>
    <title>菜单列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>菜单管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
          <li><a href="#">菜单管理</a></li>
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
                    <form id="searchForm" action="" onsubmit="return false;">
                      <div class="col-xs-2">
                        <input type="text" name="keyWords" class="form-control" placeholder="关键字">
                      </div>
                      <div class="col-xs-2">
                         <button type="submit" class="btn bg-olive btn-flat">查询</button>
                      </div>
                    </form>
                  </div>
                </div>
                
                <div id="toolbar">
                  <a id="preLevel" data-parents="" data-current="${parentId}" type="button" style="display:none;" class="btn bg-maroon btn-flat">上一级</a>
                  <mz:menuTag menuURL="menus/new">
                    <a id="add" type="button" class="btn bg-maroon btn-flat">添加</a>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="menus/delete">
                    <button type="button" data-ope="delete" class="btn bg-olive btn-flat">删除</button>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="menus/update">
                    <button type="button" data-ope="edit" class="btn bg-orange btn-flat">编辑</button>
                  </mz:menuTag>
                </div>
       
                <table id="menu_table">
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
    	var $table,$preLevel = $("#preLevel");;
    	
    	$("#searchForm").on("submit",function(e){
    		var param = $(this).serializeObject();
    		$table.bootstrapTable('refresh',{query:param});
    		return false;
    	});
    	
    	$("#toolbar > button").click(function(){
    		var idArray = $table.bootstrapTable('getAllSelections');
    		if(idArray.length==0){
    			tip.warn("至少选择一条菜单记录");
    			return;
    		}
    		idArray = $.map(idArray,function(item){
		       return item.id;
		    });
    		var ope = $(this).data("ope");
    		if("delete"==ope){
    			var ids = idArray.join(",");
    			request.ajax("${pageContext.request.contextPath}/menus/"+ids,"delete",{},true,function(data){
    				if(data.success){
    					//TODO 确保参数问题？？？？
    					$table.bootstrapTable('refresh',{query: {"parentId":$preLevel.data("current")}});
    				}else{
    					tip.error(data.msg);
    				}
    			});
    		}else if("edit"==ope){
    			//界面跳转
    			window.location.href="${pageContext.request.contextPath}/menus/"+idArray[0]+"/edit";
    		}
    	});
    	
    	//监听添加事件
    	$("#toolbar > a[id='add']").click(function(){
			window.location.href=request.contextPath+"/menus/new?parentId="+$preLevel.data("current");
    	});
        	
  	    var queryUrl = '${pageContext.request.contextPath}/menus';
  	    $table = $('#menu_table').bootstrapTable({
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
                     page: (params.offset / params.limit) + 1,   //页码
                     sort: params.sort,       //排序列名  
                     sortOrder: params.order //排位命令（desc，asc） 
                 };
               return temp;
            },
            
            columns: [{
                checkbox: true,  
                visible: true                  //是否显示复选框  
            },{
                field: 'name',
                title: '名字',
                formatter : nameFormatter
            },{
                field: 'url',
                title: 'URL'
            },{
                field: 'icon',
                title: '图标'
            },{
                field: 'leaf',
                title: '是否叶子'
            },{
                field: 'accessToken',
                title: '访问token'
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

  	    //监听点击上一级
  	    $preLevel.on("click",function(){
  	    	//显示上一级
  	    	var parentIds = $preLevel.data("parents");
	  	    var parentId = parentIds;
  	    	if(parentIds){
	  	    	var lastIndex = parentIds.lastIndexOf(",");
	  	    	if(lastIndex!=-1){
	  	    		parentId = parentIds.substring(lastIndex);
	  	    		parentIds = parentIds.substring(0,lastIndex);
	  	    	}else{
	  	    		parentIds="";
	  	    	}
	  	        $preLevel.data("parents",parentIds);
	  	    	$table.bootstrapTable("refresh",{query: {"parentId":parentId}});
  	    	}else{
	  	    	$table.bootstrapTable("refresh");
	  	        $preLevel.css("display","none");
  	    	}
  	    	$preLevel.data("current",parentId);
  	    });
  	    
  	    //监听点击名字事件
  	    $("#menu_table").on("click","a[name='parentName']",function(){
  	    	
  	    	var menuId = $(this).data("id")+"";
  	    	//$table.bootstrapTable("refresh",{"url":request.contextPath+"/menus/"+menuId+"/menus/"});
  	    	$table.bootstrapTable("refresh",{query: {"parentId":menuId}});
  	    	//显示上一级
  	    	var parentIds = $preLevel.data("parents"),parentId =$(this).data("parent")+"";
  	    	if(parentIds==""){
  	    		parentIds = parentId;
  	    	}else{
  	    		parentIds =parentIds +","+ parentId;
  	    	}
  	    	$preLevel.data("parents",parentIds).css("display","inline-block");
  	    	$preLevel.data("current",menuId);
  	    });
    });
	//操作栏的格式化
    function nameFormatter(value, row, index) {
		if(!row.leaf){
            return "<a name='parentName' data-id='"+row.id+"' data-parent='"+(row.parentId==null?'':row.parentId)+"' href='javascript:;'>"+value+"</a>";
		}
    }
  </script>
</body>
</html>