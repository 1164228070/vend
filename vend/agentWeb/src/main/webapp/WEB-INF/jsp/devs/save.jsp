<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>设备管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/select2/dist/css/select2.min.css">
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加设备':'编辑设备'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">设备管理</a></li>
          <li class="active">列表</li>
        </ol>
      </section>
  
      <!-- Main content -->
      <section class="content">
        <div class="row">
        <!-- right column -->
        <div class="col-md-12">
          <!-- Horizontal Form -->
          <div class="box box-info">
            <!-- form start -->
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/devs/${dev.id }" modelAttribute="dev">
            
              <form:hidden path="id"/>
              <input type="hidden"  name="_method" value="${empty dev.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
              
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="num" class="col-sm-2 control-label">设备编号</label>
                    <div class="col-sm-10">
                      <form:input path="num"  readonly="${empty dev.id ? 'false' : 'true' }" cssClass="form-control" placeholder="设备编号" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="shortNum" class="col-sm-2 control-label">设备自编号</label>
                    <div class="col-sm-10">
                      <form:input path="shortNum"  cssClass="form-control" placeholder="设备自编号" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                   <div class="form-group">
                    <label for="token" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <form:input path="name" cssClass="form-control" placeholder="名称" data-bv-notempty="true"/>
                    </div>
                  </div>
                   <div class="form-group">
                    <label for="token" class="col-sm-2 control-label">Token令牌</label>
                    <div class="col-sm-10">
                      <form:input path="token"  cssClass="form-control" placeholder="Token令牌" />
                    </div>
                  </div>
                </div>
  	  
                <%--<div class="col-md-6">
                  <div class="form-group">
                    <label for="speed1" class="col-sm-2 control-label">购买速度</label>
                    <div class="col-sm-10">
                      <form:input path="speed1"  cssClass="form-control" placeholder="购买速度" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="speed2" class="col-sm-2 control-label">退货速度</label>
                    <div class="col-sm-10">
                      <form:input path="speed2"  cssClass="form-control" placeholder="退货速度" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="KD" class="col-sm-2 control-label">购买脉冲</label>
                    <div class="col-sm-10">
                      <form:input path="KD"  cssClass="form-control" placeholder="购买脉冲" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="KD2" class="col-sm-2 control-label">退货脉冲</label>
                    <div class="col-sm-10">
                      <form:input path="KD2"  cssClass="form-control" placeholder="退货脉冲" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>--%>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="CSL" class="col-sm-2 control-label">感应扣款金额</label>
                    <div class="col-sm-10">
                      <form:input path="CSL"  cssClass="form-control" placeholder="单价" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label">客服电话</label>
                    <div class="col-sm-10">
                      <form:input path="phone"  cssClass="form-control" placeholder="客服电话" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>

              <%--<div class="form-group">
                    <label for="TF" class="col-sm-2 control-label">是否可退货</label>
                    &lt;%&ndash;<div class="col-sm-10">
                      <form:input path="TF"  cssClass="form-control" placeholder="是否可退货(1为可退，2为不可退）" data-bv-notempty="true"/>
                    </div>&ndash;%&gt;
                    <div class="col-sm-10">
                      <form:select path="TF" cssClass="form-control" data-placeholder="选择是否可退货">
                        <form:options items="${dictVals}" itemLabel="name" itemValue="value"/>
                      </form:select>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="TFType" class="col-sm-2 control-label">退货模式</label>
                    <div class="col-sm-10">
                      &lt;%&ndash; <form:input path="TFType"  cssClass="form-control" placeholder="退货模式" data-bv-notempty="true"/> &ndash;%&gt;
                   		<form:select path="TFType" cssClass="form-control" data-placeholder="选择退货模式">
                        	<form:options items="${tfType}" itemLabel="name" itemValue="value"/>
                      	</form:select>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="TBPercentage" class="col-sm-2 control-label">退货比例</label>
                    <div class="col-sm-10">
                      <form:input path="TBPercentage"  cssClass="form-control" placeholder="退货比例" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="TFMS" class="col-sm-2 control-label">购买比例</label>
                    <div class="col-sm-10">
                      <form:input path="TFMS"  cssClass="form-control" placeholder="购买比例" data-bv-notempty="true"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="tradeWay" class="col-sm-2 control-label">交易方式</label>
                    <div class="col-sm-10">
                      <form:select path="tradeWay" cssClass="form-control" data-placeholder="选择交易方式" data-bv-notempty="true">
                          <form:options items="${tradeWays}" itemLabel="name" itemValue="value"/>
                      </form:select>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="payType" class="col-sm-2 control-label">支付类型</label>
                    <div class="col-sm-10">
                      <select name="payType" class="form-control" data-placeholder="选择支付类型" multiple="multiple" data-val="${dev.payType}">
                        <c:forEach items="${payTypes}" var="payType">
                          <option value="${payType.value}">${payType.name}</option>  
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                </div>--%>
  	  
              <%--   <div class="col-md-6">
                  <div class="form-group">
                    <label for="Reserve1" class="col-sm-2 control-label">预留1</label>
                    <div class="col-sm-10">
                      <form:input path="Reserve1"  cssClass="form-control" placeholder="预留1" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="Reserve2" class="col-sm-2 control-label">预留2</label>
                    <div class="col-sm-10">
                      <form:input path="Reserve2"  cssClass="form-control" placeholder="预留2" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div> --%>
  	  
                <%-- <div class="col-md-6">
                  <div class="form-group">
                    <label for="Reserve3" class="col-sm-2 control-label">预留3</label>
                    <div class="col-sm-10">
                      <form:input path="Reserve3"  cssClass="form-control" placeholder="预留3" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="Reserve4" class="col-sm-2 control-label">预留4</label>
                    <div class="col-sm-10">
                      <form:input path="Reserve4"  cssClass="form-control" placeholder="预留4" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div> --%>
  	  
                <%--<div class="col-md-6">
                  <div class="form-group">
                    <label for="state" class="col-sm-2 control-label">设备状态(根据心跳包为主，每3分种通信一次）</label>
                    <div class="col-sm-10">
                      <form:input path="state"  cssClass="form-control" placeholder="设备状态(根据心跳包为主，每3分种通信一次）" />
                    </div>
                  </div>
                </div>--%>
  	  
                <%-- <div class="col-md-6">
                  <div class="form-group">
                    <label for="Obtain" class="col-sm-2 control-label">获取参数开关</label>
                    <div class="col-sm-10">
                      <form:input path="Obtain"  cssClass="form-control" placeholder="获取参数开关" />
                    </div>
                  </div>
                </div> --%>
                
              <!-- /.box-body -->
                <div class="col-md-6">
                  <div class="box-footer">
                    <button type="reset" class="btn btn-default btn-flat">重 置</button>
                    <button type="submit" class="btn btn-success btn-flat">${empty dev.id ? '保 存' : '更 新'}</button>
                  </div>
                </div>

              <!-- /.box-footer -->
            </form:form>
          </div>
          <!-- /.box -->
        </div>
        <!--/.col (right) -->
      </div>
        <!-- /.row -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    
    <%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
  </div>
  <script src="${pageContext.request.contextPath}/static/bower_components/select2/dist/js/select2.full.min.js"></script>
  <script type="text/javascript">
    $(function(){
        $("form[id='dev']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }); 
        var $payType =$('select[name="payType"]');
        $payType.val($payType.data("val")?($payType.data("val")+"").split(","):"");
        $payType.select2();
        $('select[name="tradeWay"]').select2();
    });
  </script>
</body>
</html>
 
 
