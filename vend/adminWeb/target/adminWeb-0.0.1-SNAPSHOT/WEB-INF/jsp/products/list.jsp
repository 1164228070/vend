<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend"%>

<!DOCTYPE html>
<html>
<head>
  <title>商品列表</title>
  <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

  <%@ include file="/WEB-INF/jsp/common/left.jsp" %>

  <div class="content-wrapper">
    <section class="content-header">
      <h1>商品管理</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 商品中心</a></li>
        <li><a href="#">商品管理</a></li>
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
                  <form id="searchForm" action="" onsubmit="return false; " class="form-inline">
                    <div class="form-group">
                      <select class="form-control" name="key" id="query_key">
                        <option>请选择查询条件</option>
                        <option value="name">商品名</option>
                        <option value="productNum">商品编号</option>
                        <option value="orderNum">货道</option>
                        <option id="userNameOption" value="userName">所属商户</option>
                      </select>
                    </div>
                    <div class="form-group">
                      <input type="text" id="query_value" name="value" class="form-control" placeholder="关键字">
                    </div>

                    <div class="form-group">
                      <select class="form-control" name="productGroupId">
                        <option value="">请选商品组</option>
                        <c:forEach items="${productGroups}" var="productGroup">
                          <option value="${productGroup.id}">${productGroup.name}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <div class="form-group">
                      <select  id="status"  name="mystatus" class="form-control" data-placeholder="请选择状态">
                      </select>
                    </div>
                    <div class="form-group">
                      <select  id="CountStatus"  name="CountStatus" class="form-control" data-placeholder="请选择缺货状态">
                      </select>
                    </div>
                    <div class="form-group">
                      <button type="submit" class="btn bg-olive btn-flat">查询</button>
                    </div>
                  </form>
                </div>
              </div>

              <div id="toolbar">
                <mz:menuTag menuURL="/products/add">
                  <a href="${pageContext.request.contextPath}/products/new" type="button" class="btn bg-maroon btn-flat">添加</a>
                </mz:menuTag>

                <mz:menuTag menuURL="/products/delete">
                  <button type="button" data-ope="delete" class="btn bg-olive btn-flat">删除</button>
                </mz:menuTag>

                <mz:menuTag menuURL="/products/edit">
                  <button type="button" data-ope="edit" class="btn bg-orange btn-flat">编辑</button>
                </mz:menuTag>
              </div>

              <table id="product_table">
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

  <!-- 引入switch -->
  <script src="${pageContext.request.contextPath}/static/plugins/switch/bootstrap-switch.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/switch/bootstrap-switch.min.css">
</div>

<!-- page script -->
<script type="text/javascript">
  $(function (){

    $("#toolbar").hide();




    var values = cache.get(15);

    if(values){
      var $querySelect = $("#status");
      var result = "<option value=''>请选择状态</option>";
      $.each(values,function(key,value){
        result += "<option value="+key+">"+value+"</option>";
      });
      $querySelect.html(result);
    }

    var countValues = cache.get(16);

    if(countValues){
      var $querySelect = $("#CountStatus");
      var result = "<option value=''>请选择缺货状态</option>";
      $.each(countValues,function(key,value){
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
      if(param.mystatus){
        param["status"]=param.mystatus;
        delete param["mystatus"];
      }
      conditionParams=param;
      $table.bootstrapTable('refresh',{query:param});
      return false;
    });

    $("#toolbar > button").click(function(){
      var idArray = $table.bootstrapTable('getAllSelections');
      if(idArray.length==0){
        tip.warn("至少选择一条商品记录");
        return;
      }
      idArray = $.map(idArray,function(item){
        return item.id;
      });
      var ope = $(this).data("ope");
      if("delete"==ope){
        var ids = idArray.join(",");
        request.ajax("${pageContext.request.contextPath}/products/"+ids,"delete",{},true,function(data){
          if(data.success){
            //TODO 确保参数问题？？？？
            $table.bootstrapTable('refresh');
          }else{
            tip.error(data.msg);
          }
        });
      }else if("edit"==ope){
        //界面跳转
        window.location.href="${pageContext.request.contextPath}/products/"+idArray[0]+"/edit";
      }
    });

    var queryUrl = '${pageContext.request.contextPath}/products';
    $table = $('#product_table').bootstrapTable({
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
        field: 'productNum',
        title: '商品编号'
      },{
        field: 'productGroup.name',
        title: '所属商品组'
      },{
        field: 'userName',
        title: '所属商户'
      },{
        field: 'orderNum',
        title: '货道'
      },{
        field: 'cost',
        title: '成本'
      },{
        field: 'price',
        title: '单价'
      },{
        field: 'storeCount',
        title: '库存'
      },{
        field: 'threshold',
        title: '阀值'
      },{
        field: 'countStatus',
        title: '缺货状态',
        formatter:countStatusFormatter
      },{
        field: 'status',
        title: '状态',
        formatter:statusFormatter,
        cellStyle:{
          css:{"padding":"4px;"}
        }
      }],
      onPostBody: function () {

          /*$('[name="status"]').bootstrapSwitch({
            onText : "上架",
            offText : "下架",
            onColor : "success",
            offColor : "info",
            size : "small",
            handleWidth : 40,
            onSwitchChange : function(event, state) {

                var param={};
                var productId = $(this).data("id");
                if (state == true) { //激活
                  param.sign = "y";
                } else {//冻结
                  param.sign = "n";
                }
                request.ajax("${pageContext.request.contextPath}/products/"+productId+"/status","put",param,true,function(data){
                  if(data.success){
                    tip.success(data.msg);
                  }else{
                    tip.error(data.msg);
                  }
                });


            }
          });*/



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
  function nameFormatter (value,row,index) {
    return '<a href="'+request.contextPath+'/products/'+row.id+'">'+value+'</a>';
  }
  function statusFormatter (value,row,index) {
      return value==1?"上架":"下架";

  }

  function countStatusFormatter (value,row,index) {
    return value==1?"充足":"缺货";
  }
</script>
</body>
</html>


