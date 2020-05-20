<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
    <!-- 引入时间范围插件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/bootstrap-daterangepicker/daterangepicker.css">
    <script src="${pageContext.request.contextPath}/static/bower_components/moment/moment.js"></script>
    <script src="${pageContext.request.contextPath}/static/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

    <%@ include file="/WEB-INF/jsp/common/left.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>控制面板
            </h1>
            <ol class="breadcrumb">
                <li class="active">控制面板</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="col-lg-4 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-aqua">
                        <div class="inner">
                            <h3 id="allLine">0</h3>
                            <p>总设备数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-bag"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/devs" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-4 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-green">
                        <div class="inner">
                            <h3 id="liveLine">0</h3>
                            <p>在线设备数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/devs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-4 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-yellow">
                        <div class="inner">
                            <h3 id="offLine">0</h3>
                            <p>离线设备数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person-add"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/devs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->

                <div class="col-lg-3 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-aqua">
                        <div class="inner">
                            <h3 id="allOrders">0</h3>
                            <p>总订单数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/comsumeLogs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-3 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-green">
                        <div class="inner">
                            <h3 id="finisheds">0</h3>
                            <p>消费完成数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/comsumeLogs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-3 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-yellow">
                        <div class="inner">
                            <h3 id="unfinisheds">0</h3>
                            <p>未进行消费数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/comsumeLogs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>

                <div class="col-lg-3 col-xs-8">
                    <!-- small box -->
                    <div class="small-box bg-red">
                        <div class="inner">
                            <h3 id="unusuals">0</h3>
                            <p>异常订单数</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-pie-graph"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/comsumeLogs" class="small-box-footer">更多信息<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->



                <div class="box-body">
                    <form id="searchForm" action="" onsubmit="return false;" class="form-inline">
                        <div class="form-group">
                            <select class="form-control" name="key" id="query_key">
                                <option>查询全部</option>
                                <option id="userNameOption" value="userName">商户</option>
                                <option value="devNum">设备号</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" id="query_value" name="value" class="form-control" placeholder="关键字">
                        </div>
                        <div class="form-group">
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-clock-o"></i>
                                </div>
                                <input type="text" class="form-control pull-right" name="createDateRange" id="create_date_range">
                            </div>
                        </div>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn bg-olive btn-flat">查询</button>
                        </div>
                    </form>
                </div>


                <section class="col-lg-12 connectedSortable">
                    <!-- DONUT CHART -->
                    <div class="box box-danger">
                        <div class="box-header with-border">
                            <h3 class="box-title">消费统计</h3>
                            <div>
                                <span id="allMoney" style="color: orangered"><span/>
                            </div>



                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <div class="box-body chart-responsive">
                            <div id="category" style="width:1200px;height:500px;"></div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->

                </section>




                <section class="col-lg-12 connectedSortable">
                    <!-- DONUT CHART -->
                    <div class="box box-danger">
                        <div class="box-header with-border">
                            <h3 class="box-title">销量统计</h3>
                            <div>
                                <span id="allOrder" style="color: orangered"><span/>
                            </div>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <div class="box-body chart-responsive">
                            <div id="salesVolume" style="width:1200px;height:500px;"></div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->

                </section>




            </div>
            <!-- /.row -->
            <!-- Main row -->
            <div class="row">

                <!-- /.Rigth col -->

            </div>
            <!-- /.row (main row) -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
    <script src="${pageContext.request.contextPath}/static/plugins/echartsjs/echarts.js"></script>

    <script type="text/javascript">
        $(function(){

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


            var type="${user.type}";
            if(type == "agent"){
            }else if(type == "user"){
                $("#userNameOption").hide();
            }


            var param="";
            var type="${user.type}";
            if(type == "agent"){
                param="agentId=${agentId}";
            }else if(type == "user"){
                param="userId=${userId}";
            }
            request.ajax("http://www.peshion.com/apiWeb"+"/devs/getStatus","POST",param,true,function(data){
                if(data.success){
                    $("#allLine").text(data.data.allLine);
                    $("#liveLine").text(data.data.liveLine);
                    $("#offLine").text(data.data.offLine);
                }
            });
            request.ajax("${pageContext.request.contextPath}/comsumeLogs/getComsumeCounts","POST",param,true,function(data){
                if(data.success){
                    $("#allOrders").text(data.data.allOrders);
                    $("#finisheds").text(data.data.finisheds);
                    $("#unfinisheds").text(data.data.unfinisheds);
                    $("#unusuals").text(data.data.unusuals);
                }
            });



            $("#searchForm").on("submit",function(e){

                var dateValue=$("#create_date_range").val();
                if(dateValue==null || dateValue==""){
                    alert("请选择日期查询统计");
                    return false;
                }

                var param = $(this).serializeObject();
                if(param.key && param.value){
                    var key = param.key;
                    var value = param.value;
                    delete param['key'];
                    delete param['value'];
                    param[key] = value;
                }


                //消费统计线状图
                request.ajax("${pageContext.request.contextPath}/counts/getComsumesCounts","POST",param,true,function(data){
                    if(data.success){

                        $("#allOrder").text("总销量："+data.data.allOrder);
                        $("#allMoney").text("总消费金额："+data.data.allMoney+" 总利润："+data.data.allProfit);

                        //线状图开始



                        var myChart2 = echarts.init(document.getElementById('category'));

                        var option2 = {

                            //backgroundColor: '#FFF0F5',

                            title: {
                                text: '消费统计折线图',
                                x: 'center'
                            },

                            legend: {
                                // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
                                orient: 'horizontal',
                                // x 设置水平安放位置，默认全图居中，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
                                x: 'left',
                                // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
                                y: 'top',
                                data: ['消费金额','利润']
                            },

                            //  图表距边框的距离,可选值：'百分比'¦ {number}（单位px）
                            grid: {
                                top: '16%',   // 等价于 y: '16%'
                                left: '3%',
                                right: '8%',
                                bottom: '3%',
                                containLabel: true
                            },

                            // 提示框
                            tooltip: {
                                trigger: 'axis'
                            },

                            //工具框，可以选择
                            toolbox: {
                                feature: {
                                    saveAsImage: {} //下载工具
                                }
                            },

                            xAxis: {
                                name: '时间轴',
                                type: 'category',
                                axisLine: {
                                    lineStyle: {
                                        // 设置x轴颜色
                                        color: '#912CEE'
                                    }
                                },
                                // 设置X轴数据旋转倾斜
                                axisLabel: {
                                    rotate: 30, // 旋转角度
                                    interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                                },
                                // boundaryGap值为false的时候，折线第一个点在y轴上
                                boundaryGap: false,
                                data: data.data.times
                            },

                            yAxis: {
                                name: '数值',
                                type: 'value',
                                min:0, // 设置y轴刻度的最小值
                                splitNumber:9,  // 设置y轴刻度间隔个数
                                axisLine: {
                                    lineStyle: {
                                        // 设置y轴颜色
                                        color: '#87CEFA'
                                    }
                                },
                            },

                            series: [


                                {
                                    name: '消费金额',
                                    data: data.data.totalMoneys,
                                    type: 'line',
                                    // 设置折线上圆点大小
                                    symbolSize:8,
                                    itemStyle:{
                                        normal:{
                                            // 拐点上显示数值
                                            label : {
                                                show: true
                                            },
                                            borderColor:'red',  // 拐点边框颜色
                                            lineStyle:{
                                                width:5,  // 设置线宽
                                                type:'solid'  //'dotted'虚线 'solid'实线
                                            }
                                        }
                                    }
                                },

                                {
                                    name: '利润',
                                    data:  data.data.totalProfits,
                                    type: 'line',
                                    // 设置折线上圆点大小
                                    symbolSize:10,
                                    // 设置拐点为实心圆
                                    symbol:'circle',
                                    itemStyle: {
                                        normal: {
                                            // 拐点上显示数值
                                            label : {
                                                show: true
                                            },
                                            lineStyle:{
                                                // 使用rgba设置折线透明度为0，可以视觉上隐藏折线
                                                color: '#EEEE11',
                                                type:'solid'  //'dotted'虚线 'solid'实线
                                            }
                                        }
                                    }
                                }
                            ],

                            color: ['#00EE00', '#FF9F7F','#FFD700']
                        };
                        myChart2.setOption(option2);
                        //消费统计结束

                        //销量统计开始


                        var myChart6 = echarts.init(document.getElementById('salesVolume'));
                        var option6 = {
                            tooltip : {
                                trigger: 'axis'
                            },
                            grid: {
                                top: '16%',   // 等价于 y: '16%'
                                left: '3%',
                                right: '8%',
                                bottom: '3%',
                                containLabel: true
                            },
                            //工具框，可以选择
                            toolbox: {
                                feature: {
                                    saveAsImage: {} //下载工具
                                }
                            },
                            xAxis: {
                                name:"时间轴",
                                type: 'category',
                                axisLabel: {
                                    rotate: 30, // 旋转角度
                                    interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                                },
                                data: data.data.times
                            },
                            yAxis: {
                                name:"销量",
                                type: 'value'
                            },
                            series: [{
                                data: data.data.orders,
                                name:"销量",
                                type: 'line',
                                symbol: 'triangle',
                                symbolSize: 20,
                                lineStyle: {
                                    normal: {
                                        color: '#CC3352',
                                        width: 4,
                                        type: 'dashed'
                                    }
                                },
                                itemStyle: {
                                    normal: {
                                        borderWidth: 3,
                                        borderColor: '#3300FF',
                                        color: '#11EE3D'
                                    }
                                }
                            }]
                        };
                        myChart6.setOption(option6);

                        //销量统计结束


                    }
                });


















                return false;
            });





















        });















    </script>


</div>
</body>
</html>