<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>字典管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
    
    <style type="text/css">
        #dict_val_table_btody .form-group{
            margin-right: 0px;
            margin-left: 0px;    
            margin-bottom: 0px;
        }
    </style>  
  </head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加字典':'编辑字典'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">字典管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/dicts/${dict.id }" modelAttribute="dict">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty dict.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
              
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <form:input path="name"  cssClass="form-control" placeholder="名称" data-bv-notempty="true"/>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="type" class="col-sm-2 control-label">类型</label>
                    <div class="col-sm-10">
                      <form:input path="type"  cssClass="form-control" placeholder="类型" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="note" class="col-sm-2 control-label">备注</label>
                    <div class="col-sm-10">
                      <form:input path="note"  cssClass="form-control" placeholder="备注" />
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-12">   
                  <div class="col-md-6 ">
                    <div class="form-group">
                      <button type="button" id="add_dict_val" class="btn btn-success btn-flat">添加明细</button>
                    </div>
                  </div>
                </div>
                
                <div class="col-md-8">   
                  <table id="dict_val_table" data-toggle="table">
                    <thead>
                      <tr>
                        <th>名 字</th>
                        <th>值 </th>
                        <th>操 作 </th>
                      </tr>
                    </thead>
                    <tbody id="dict_val_table_btody">
                      <c:choose>
                        <c:when test="${empty dictVals}">
                          <tr>
                            <td><div class="form-group"><input type="text" name="dictValName"  class="form-control" placeholder="名字" data-bv-notempty="true"/></div></td>
                            <td><div class="form-group"><input type="text" name="dictValValue"  class="form-control" placeholder="值" data-bv-notempty="true"/></div></td>
                            <td><button type="button" id="delete_dict_val" class="btn btn-danger btn-flat">删除</button></td>
                          </tr>
                        </c:when>
                        <c:otherwise>
                          <c:forEach items="${dictVals}" var="dictVal">
                            <tr>
                              <td><div class="form-group"><input type="text" name="dictValName" value="${dictVal.name}" class="form-control" placeholder="名字" data-bv-notempty="true"/></div></td>
                              <td><div class="form-group"><input type="text" name="dictValValue" value="${dictVal.value}"  class="form-control" placeholder="值" data-bv-notempty="true"/></div></td>
                              <td><button type="button" id="delete_dict_val" class="btn btn-danger btn-flat">删除</button></td>
                            </tr>
                          </c:forEach>
                        </c:otherwise>
                      </c:choose>
                    </tbody>
                  </table>
                </div>
                
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty dict.id ? '保 存' : '更 新'}</button>
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
    
    <script src="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table.js"></script>
    <script src="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table-zh-CN.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bower_components/table/bootstrap-table.css">
  </div>
  
  <script type="text/javascript">
    $(function(){
        $("form[id='dict']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }).on("success.form.bv",function(e){
        	var $form = $(this);
            var $trs = $("table > tbody[id='dict_val_table_btody'] > tr");
       	    $.each($trs,function(index,value){
       	   	    //$(value).find("input[name='id']").prop("name","dictVals.makeNew["+index+"].id");  
       	   	    $(value).find("input[name='dictValName']").prop("name","dictVals["+index+"].name");
       	   	    $(value).find("input[name='dictValValue']").prop("name","dictVals["+index+"].value");
       	    });
        });
        
        //监听添加明细
        $("#add_dict_val").on("click",function(){
      	    //复制一行
      	    var $tbody = $("table > tbody[id='dict_val_table_btody']");
      	    
      	    var $firtTr = $tbody.children(":eq(0)");
      	    var $colneTr = $firtTr.clone(); 
      	    //清除数据
      	    
      	    var $id = $colneTr.find("input[name='id']");  //伪数组
      	    if($id.length==1){
      	  	  $id.val("");
      	    }
      	     
      	    $colneTr.find("input[name='dictValName']").val("");
      	    $colneTr.find("input[name='dictValValue']").val("");
      	    $tbody.append($colneTr);
        })
        
        //监听删除
       $("table > tbody[id='dict_val_table_btody']").on("click","button[id='delete_dict_val']",function(){
       	  var size = $("table > tbody[id='dict_val_table_btody'] > tr").length;
      	  if(size>1){
      	       $(this).closest("tr").remove();
      	  }
       });
    });
  </script>
  
</body>
</html>
 
 
