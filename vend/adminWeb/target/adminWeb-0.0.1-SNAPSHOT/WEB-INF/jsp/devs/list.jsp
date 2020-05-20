<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend" %>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/select2/dist/css/select2.min.css">
    <title>设备列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>设备管理</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
          <li><a href="#">设备管理</a></li>
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
                    <%--<form id="searchForm" action="" onsubmit="return false;">
                      <div class="col-xs-2">
                        <input type="text" name="name" class="form-control" placeholder="名称">
                      </div>
                      <div class="col-xs-2">
                        <input type="text" name="num" class="form-control" placeholder="设备编号">
                      </div>
                       <div class="col-xs-2">
                        <input type="text" name="shortNum" class="form-control" placeholder="设备自编号">
                      </div>
                      <div class="col-xs-2">
                         <button type="submit" class="btn bg-olive btn-flat">查询</button>
                      </div>
                    </form>--%>
                      <form id="searchForm" action="" onsubmit="return false;" class="form-inline">
                        <div class="form-group">
                          <select class="form-control" name="key" id="query_key">
                            <option value="name">名称</option>
                            <option value="userName">所属商户</option>
                            <option value="num">设备编号</option>
                            <option value="shortNum">设备自编号</option>
                          </select>
                        </div>


                        <div class="form-group">
                          <input type="text" id="query_value" name="value" class="form-control" placeholder="关键字">
                        </div>

                        <div class="form-group">
                          <select class="form-control" name="state">
                          </select>
                        </div>

                        <div class="form-group">
                          <button type="submit" class="btn bg-olive btn-flat">查询</button>
                        </div>
                      </form>

                  </div>
                </div>
                
                <div id="toolbar">
                  <mz:menuTag menuURL="/devs/add">
                    <a href="${pageContext.request.contextPath}/devs/new" type="button" class="btn bg-maroon btn-flat">添加</a>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="/devs/delete">
                    <button type="button" data-ope="delete" class="btn bg-olive btn-flat">删除</button>
                  </mz:menuTag>
                  
                  <mz:menuTag menuURL="/devs/edit">
                    <button type="button" data-ope="edit" class="btn bg-orange btn-flat">编辑</button>
                  </mz:menuTag>
                </div>
       
                <table id="dev_table"></table>
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
  <script src="${pageContext.request.contextPath}/static/bower_components/select2/dist/js/select2.full.min.js"></script>
  <!-- 引入switch -->
  <script src="${pageContext.request.contextPath}/static/plugins/switch/bootstrap-switch.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/switch/bootstrap-switch.min.css">
  
  <script type="text/javascript">
    $(function (){
      $("#toolbar").hide();
        $("select[name='access_token']").select2();

      var values = cache.get(6);

      if(values){
        var $querySelect = $("select[name='state']");
        var result = "<option value=''>请选择状态</option>";
        $.each(values,function(key,value){
          result += "<option value="+key+">"+value+"</option>";
        });
        $querySelect.html(result);
      }

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
    	
    	$("#toolbar > button").click(function(){
    		var idArray = $table.bootstrapTable('getAllSelections');
    		if(idArray.length==0){
    			tip.warn("至少选择一条设备记录");
    			return;
    		}
    		idArray = $.map(idArray,function(item){
		       return item.id;
		    });
    		var ope = $(this).data("ope");
    		if("delete"==ope){
    			var ids = idArray.join(",");
    			request.ajax("${pageContext.request.contextPath}/devs/"+ids,"delete",{},true,function(data){
    				if(data.success){
    					//TODO 确保参数问题？？？？
    					$table.bootstrapTable('refresh');
    				}else{
    					tip.error(data.msg);
    				}
    			});
    		}else if("edit"==ope){
    			//界面跳转
    			window.location.href="${pageContext.request.contextPath}/devs/"+idArray[0]+"/edit";
    		}
    	});
    	
  	  var queryUrl = '${pageContext.request.contextPath}/devs';
  	  $table = $('#dev_table').bootstrapTable({
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
              field: 'name',
              title: '名称',
              formatter:nameFormatter 
          },{
              field: 'num',
              title: '设备编号'
          },{
              field: 'shortNum',
              title: '设备自编号'
          },{
              field: 'token',
              title: 'Token令牌'
          },{
              field: 'userName',
              title: '所属商户'
          },{
            field: 'csl',
            title: '单价'
          },/*{
              field: 'speed1',
              title: '购买速度'
          },{
              field: 'speed2',
              title: '退货速度'
          },{
              field: 'KD',
              title: '购买脉冲'
          },{
              field: 'KD2',
              title: '退货脉冲'
          },{
              field: 'tf',
              title: '是否可退货',
              formatter:tFFormatter
          },{
              field: 'tftype',
              title: '退货模式',
              formatter:tFTypeFormatter
          },{
              field: 'commodity',
              title: '商品数'
          },*/{
              field: 'state',
              title: '状态',
              formatter:stateFormatter
          },{
              field: 'obtain',
              title: '锁开关',
              formatter:obtainFormatter
          }],
          onPostBody: function () {
            //加载成功触发
              $('[name="Obtain"]').bootstrapSwitch({
  		    	  onText : "开锁",
  		    	  offText : "关锁",
  		    	  onColor : "success",
  		    	  offColor : "info",
  		    	  size : "small",
		    	  handleWidth : 50,
                  onSwitchChange : function(event, state) {
                      var param={};
                      var devId = $(this).data("id");
                      if (state == true) { //激活
                          param.sign = "y";
                      } else {//冻结
                          param.sign = "n";
                      }
                      request.ajax("${pageContext.request.contextPath}/devs/"+devId+"/status","put",param,true,function(data){
                          if(data.success){
                                tip.success(data.msg);
                          }else{
                                tip.error(data.msg);
                          }
                      });
                  }
  		      });
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
  	  
  	  function stateFormatter (value,row,index) {
          return cache.getName(6,value);
      }

      function obtainFormatter(value,row,index) {
          return '<input type="checkbox" data-id="'+row.id+'" name="Obtain" '+(value==1?'checked':'')+' data-on-text="'+cache.getName(8,1)+'" data-off-text="'+cache.getName(8,2)+'"/>';
      }

      function tFTypeFormatter(value,row,index) {
          return cache.getName(3,value);
      }

      function tFFormatter(value,row,index) {
          return cache.getName(4,value);
      }

    });
    
	function nameFormatter (value,row,index) {
		return '<a href="'+request.contextPath+'/devs/'+row.id+'">'+value+'</a>';
	}
  </script>
</body>
</html>