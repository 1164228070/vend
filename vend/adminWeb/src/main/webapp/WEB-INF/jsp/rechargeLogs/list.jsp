<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="mz" uri="http://java.sun.com/jsp/jstl/vend" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户列表</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

    <%@ include file="/WEB-INF/jsp/common/left.jsp" %>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>充值记录管理</h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
                <li><a href="#">充值记录管理</a></li>
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
                                                <option value="rechargelog">充值单号</option>
                                                <option value="memberName">会员名</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <input type="text" id="query_value" name="value" class="form-control" placeholder="关键字">
                                        </div>
                                        <div class="form-group">
                                            <select name="payStatus" id="payStatus"  class="form-control" data-placeholder="请选择充值状态">
                                            </select>
                                        </div>
                                        <button type="submit" class="btn bg-olive btn-flat">查询</button>
                                    </form>
                                </div>
                            </div>


                            <table id="rechargeLog_table">
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


        request.ajax(request.contextPath+"/rechargeLogs/selectStatus","POST",{},true,function(data){
            if(data.success){
                var o = data.data;
                $("#payStatus").empty();
                $("#payStatus").append("<option value=''>请选择充值状态</option>");
                $.each(o, function(i, m) {
                    $("#payStatus").append("<option value="+m.value+">"+ m.name + "</option>");
                });
            }else{
                tip.error(data.msg);
            }
        });



        var queryUrl = '${pageContext.request.contextPath}/rechargeLogs';
        $table = $('#rechargeLog_table').bootstrapTable({
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
                field: 'rechargelog',
                title: '充值单号'
            },{
                field: 'memberName',
                title: '会员名'
            },{
                field: 'recharge_momey',
                title: '充值金额'
            },{
                field: 'gift',
                title: '赠送金额'
            },{
                field: 'payType',
                title: '充值方式',
                formatter:payTypeFormatter
            },{
                field: 'payStatus',
                title: '状态',
                formatter:payStatusFormatter
            },{
                field: 'createDate',
                title: '充值时间',
                formatter:createDateFormatter
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

</script>
</body>
</html>
 
 
