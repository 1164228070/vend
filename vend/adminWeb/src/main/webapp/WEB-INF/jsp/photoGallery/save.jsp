<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>图片库管理</title>
  <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

  <%@ include file="/WEB-INF/jsp/common/left.jsp" %>

  <div class="content-wrapper">
    <section class="content-header">
      <h1>${id==null?'添加图片':'编辑图片'}</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
        <li><a href="#">图片库管理</a></li>
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
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/photoGallery/${photoGallery.id }" modelAttribute="photoGallery">

              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty photoGallery.id ? 'POST' : 'PUT' }">

              <div class="box-body">

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="photoName" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <form:input path="photoName"  cssClass="form-control" placeholder="名称" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>



                <div class="col-md-6">
                  <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">图片</label>
                    <div class="col-sm-10">
                      <form:input path="img" cssClass="form-control" placeholder="图片" data-bv-notempty="true"/>
                      图片尽量选择宽高相近的，且图片大小不能超过2M，只能以'jpg','png','jpeg'为后缀
                      <input type="file" name="imgFile" style="display: inline;">
                    </div>
                  </div>

                </div>

              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty photoGallery.id ? '保 存' : '更 新'}</button>
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


<script type="text/javascript">
  $(function(){
    var photoGalleryId = $("input[id='id']").val();
    var initialPreview=new Array(0),initialPreviewConfig=new Array(0);

    var $imgInput = $("input[id='img']");
    var img = $imgInput.val();
    if(photoGalleryId && img){
      initialPreview[0]="<img src="+request.imageServer+img+" class='file-preview-image' style='width:auto;height:auto;max-width:100%;max-height:100%;'>";
      initialPreviewConfig[0]={caption: img, size: 329892, url: request.contextPath+"/files/delete", key: img};
    }
    $("form[id='photoGallery']").bootstrapValidator({
      feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
      }
    });
    $("input[type='file']").fileinput({
      'language': 'zh', //设置语言
      'uploadUrl' : request.contextPath + "/files/upload",
      'previewFileType':'any',
      'maxFileCount': 1,
      'allowedPreviewTypes' : [ 'image' ],
      'allowedFileExtensions': ['jpg','png','jpeg'],//接收的文件后缀
      'uploadExtraData':{"paramName":"imgFile"},        //额外参数
      'showUpload':false,
      'dropZoneEnabled': false,
      'showClose' : false,//显示右上角X关闭
      'showRemove' : false,//显示移除按钮,跟随文本框的那个

      'maxFileSize' : 2048, //允许图片数据大小

      'initialPreview': initialPreview,
      'initialPreviewConfig':initialPreviewConfig
    }).on('fileuploaded', function(event, data, previewId, index) {
      //异步上传成功结果处理
      alert("上传成功");
      $imgInput.val(data.response.fileName);
    }).on('fileerror', function(event, data, msg) {
      alert("上传失败");
      //异步上传错误结果处理
    }).on('filesuccessremove',function(event,id){
      alert("删除成功");
      //TODO
    }).on('filedeleted',function(event,id){
      alert("失败");
      $imgInput.val("");
    });
  });
</script>
</body>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/fileupload/css/fileinput.css">
<script src="${pageContext.request.contextPath}/static/plugins/fileupload/js/fileinput.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/fileupload/js/zh.js"></script>

</html>