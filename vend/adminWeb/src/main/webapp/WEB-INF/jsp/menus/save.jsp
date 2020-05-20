<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>菜单管理</title>
    <%@ include file="/WEB-INF/jsp/common/common.jsp" %>

    <style type="text/css">
      .site-doc-icon {
      	font-size: 0;
      	padding: 0px;
      }
      
      .site-doc-icon li {
      	display: inline-block;
      	vertical-align: middle;
      	width: 75.7px;
      	line-height: 25px;
      	padding: 8px 0;
      	margin-right: -1px;
      	margin-bottom: -1px;
      	border: 1px solid #e2e2e2;
      	font-size: 14px;
      	text-align: center;
      	color: #666;
      	transition: all .3s;
      	-webkit-transition: all .3s;
      }
      
      .site-doc-icon li.selected {
      	background-color: #5FB878;
      }
      
      .site-doc-icon li .layui-icon {
      	display: inline-block;
      	font-size: 36px;
      }
      
      .site-doc-icon li .name {
      	color: #c2c2c2;
      }
    </style>

</head>

<body class="hold-transition skin-blue sidebar-mini">
  <div class="wrapper">
  
   <%@ include file="/WEB-INF/jsp/common/top.jsp" %>
   
   <%@ include file="/WEB-INF/jsp/common/left.jsp" %>
   
    <div class="content-wrapper">
      <section class="content-header">
        <h1>${id==null?'添加菜单':'编辑菜单'}</h1>
        <ol class="breadcrumb">
          <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
          <li><a href="#">菜单管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/menus/${menu.id}" modelAttribute="menu">
            
              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty menu.id ? 'POST' : 'PUT' }">
              
              <div class="box-body">
              
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名字</label>
                    <div class="col-sm-10">
                      <form:input path="name"  cssClass="form-control" placeholder="名字" />
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="URL" class="col-sm-2 control-label">URL</label>
                    <div class="col-sm-10">
                      <form:input path="URL"  cssClass="form-control" placeholder="URL" />
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="icon" class="col-sm-2 control-label">图标</label>
                    <div class="col-sm-10">
                    
                      <div class="input-group">
                        <form:input path="icon"  cssClass="form-control" readonly="true" placeholder="图标" />
                        <span class="input-group-addon" style="padding:4px;border:none;">
                          <i id="sample" data-icon='${menu.icon}' class="${menu.icon} fa-2x" style="line-height: 33.99px;"></i>  
                        </span>
                      </div>
                    
                    </div>
                  </div>
                </div>
  	  
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="parentId" class="col-sm-2 control-label">父菜单</label>
                    <div class="col-sm-10">
                      <form:select path="parentId" cssClass="form-control">
                        <form:option value="">请选择父菜单</form:option>
                        <form:options items="${parentMenus}" itemLabel="name" itemValue="id"/>  
                      </form:select>
                    </div>
                  </div>
  	  
                  <div class="form-group">
                    <label for="accessToken" class="col-sm-2 control-label">访问口令</label>
                    <div class="col-sm-10">
                      <form:input path="accessToken" cssClass="form-control" placeholder="访问口令" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty menu.id ? '保 存' : '更 新'}</button>
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
  
  
  
  
  <!-- 选择图标弹出框 -->
  <div class="modal fade" id="modal-icon" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="label"></h4>
        </div>
        <div class="modal-body">  
          <div class="row">
                  <ul class="site-doc-icon" id="icon-area">
      <li data-icon="fa fa-camera-retro">
        <i class="fa fa-camera-retro fa-3x"></i>
        <div class="name">主页</div>  
      </li>
      <li>
        <i class="fa fa-window-restore fa-3x"></i>
        <div class="name">仓库</div>  
      </li>
      <li>
        <i class="fa fa-bath fa-3x"></i>
        <div class="name">洗澡</div>  
      </li>
      <li>
        <i class="fa fa-camera-retro fa-3x"></i>
        <div class="name">主页</div>  
      </li>
      <li>
        <i class="fa fa-window-restore fa-3x"></i>
        <div class="name">仓库</div>  
      </li>
      <li>
        <i class="fa fa-bath fa-3x"></i>
        <div class="name">洗澡</div>  
      </li>
      <li>
        <i class="fa fa-camera-retro fa-3x"></i>
        <div class="name">主页</div>  
      </li>
      <li>
        <i class="fa fa-window-restore fa-3x"></i>
        <div class="name">仓库</div>  
      </li>
      <li>
        <i class="fa fa-bath fa-3x"></i>
        <div class="name">洗澡</div>  
      </li>
      <li>
        <i class="fa fa-camera-retro fa-3x"></i>
        <div class="name">主页</div>  
      </li>
      <li>
        <i class="fa fa-window-restore fa-3x"></i>
        <div class="name">仓库</div>  
      </li>
      <li>
        <i class="fa fa-bath fa-3x"></i>
        <div class="name">洗澡</div>  
      </li>
      <li>
        <i class="fa fa-camera-retro fa-3x"></i>
        <div class="name">主页</div>  
      </li>
      <li>
        <i class="fa fa-window-restore fa-3x"></i>
        <div class="name">仓库</div>  
      </li>
      <li>
        <i class="fa fa-bath fa-3x"></i>
        <div class="name">洗澡</div>  
      </li>
      <li data-icon="fa fa-etsy">
        <i class="fa fa-etsy fa-3x"></i>
        <div class="name">四级</div>  
      </li>
      <li data-icon="fa fa-adjust">
        <i class="fa fa-adjust fa-3x"></i>
        <div class="name">调整</div>  
      </li>
     </ul>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
          <button id="sure-select-btn" type="button" class="btn btn-primary">确 定</button>
        </div>
      </div>
    </div>
  </div>
  
  <script type="text/javascript">
    $(function(){
        $("form[id='menu']").bootstrapValidator({
      	  feedbackIcons: {
      		  valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            }
        }); 
        
      	var $modal = $('#modal-icon');
      	$modal.on('show.bs.modal', function (e) {
        	var $area =$(this).find("ul[id='icon-area']");
        	$area.find("li.selected").removeClass("selected");
    	});
	    
	    $("#modal-icon li").on("click",function(){
	        var $it = $(this);
	        $it.siblings("li.selected").removeClass("selected");
    	    $it.addClass("selected");
	    });
	    $("#modal-icon #sure-select-btn").on("click",function(){
	    	var selectedIcon = $(this).parent().siblings("div.modal-body").find("li.selected").data("icon");
	    	 $("form[id='menu'] #icon").val(selectedIcon);  
	      	 $("#sample").data("icon",selectedIcon);
	      	 $("#sample").prop("class",selectedIcon+" fa-2x");
	      	 //关闭
	      	$modal.modal('hide');
	    });
	    
        $("form[id='menu'] #icon").on("click",function(){
      	    var $it = $(this);  
      	    var icon =$it.val();
      	    if($modal){
      	    	$modal.modal({backdrop:'static',keyboard:false,toggle:true});
      	    }
        	//回显
        	var $selectingIcon = $("#modal-icon li[data-icon='"+icon+"']");
        	if($selectingIcon){
        		$selectingIcon.addClass("selected");
        	}
        })
    });
  </script>
  
</body>
</html>
 
 
