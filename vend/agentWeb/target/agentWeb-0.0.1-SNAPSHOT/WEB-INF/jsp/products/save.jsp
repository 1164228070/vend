<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>商品管理</title>
  <%@ include file="/WEB-INF/jsp/common/common.jsp" %>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <%@ include file="/WEB-INF/jsp/common/top.jsp" %>

  <%@ include file="/WEB-INF/jsp/common/left.jsp" %>

  <div class="content-wrapper">
    <section class="content-header">
      <h1>${id==null?'添加商品':'编辑商品'}</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i>系统管理</a></li>
        <li><a href="#">商品管理</a></li>
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


            <!-- 图片库模态框 -->
            <div class="modal fade" id="photoGarlleryModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">图片库</h4>
                  </div>
                  <div class="modal-body">
                    <!-- 图片库表单表单 -->
                    <form class="form-horizontal">
                      <div id="photoShow" class="form-group">
                      </div>
                    </form>

                  </div>
                </div>
              </div>
            </div>



            <!-- form start -->
            <form:form class="form-horizontal" action="${pageContext.request.contextPath }/products/${product.id }" modelAttribute="product">

              <form:hidden path="id"/>
              <input type="hidden" name="_method" value="${empty product.id ? 'POST' : 'PUT' }">

              <div class="box-body">

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                      <form:input path="name"  cssClass="form-control" placeholder="名称" data-bv-notempty="true"/>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="productNum" class="col-sm-2 control-label">商品编号</label>
                    <div class="col-sm-10">
                      <form:input path="productNum"  cssClass="form-control" placeholder="商品编号" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-group">
                    <label for="price" class="col-sm-2 control-label">单价</label>
                    <div class="col-sm-10">
                      <form:input path="price"  cssClass="form-control" placeholder="单价" data-bv-notempty="true"/>
                    </div>
                  </div>



                  <div class="form-group">
                    <label for="cost" class="col-sm-2 control-label">成本</label>
                    <div class="col-sm-10">
                      <form:input path="cost"  cssClass="form-control" placeholder="成本" data-bv-notempty="true"/>
                    </div>
                  </div>


                  <div class="form-group">
                    <label for="storeCount" class="col-sm-2 control-label">库存</label>
                    <div class="col-sm-10">
                      <form:input path="storeCount" cssClass="form-control" placeholder="库存" data-bv-notempty="true"/>
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
                      <div class="col-sm-10">
                        <div class="form-group">
                          <div class="form-inline">
                            <input type="text" class="form-control" id="photoGallery" placeholder="图片库搜索关键字">
                            <button id="photoGalleryBtn" type="button" class="btn btn-info">从图片库中查找</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="productGroup.id" class="col-sm-2 control-label">商品组</label>
                    <div class="col-sm-10">
                      <!-- 选择框 -->
                      <form:select path="productGroup.id" cssClass="form-control" data-placeholder="选择商品组" data-bv-notempty="true">
                        <form:options items="${productGroups}" itemLabel="name" itemValue="id"/>
                      </form:select>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="storeCount" class="col-sm-2 control-label">货道</label>
                    <div class="col-sm-10">
                      <form:input path="orderNum" cssClass="form-control" placeholder="过道" data-bv-notempty="true"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="storeCount" class="col-sm-2 control-label">阀值</label>
                    <div class="col-sm-10">
                      <form:input path="threshold" cssClass="form-control" placeholder="阀值" data-bv-notempty="true"/>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="reset" class="btn btn-default btn-flat">重 置</button>
                <button type="submit" class="btn btn-success btn-flat">${empty product.id ? '保 存' : '更 新'}</button>
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
    var productId = $("input[id='id']").val();
    var initialPreview=new Array(0),initialPreviewConfig=new Array(0);

    var $imgInput = $("input[id='img']");
    var img = $imgInput.val();
    if(productId && img){
      initialPreview[0]="<img src="+request.imageServer+img+" class='file-preview-image' style='width:auto;height:auto;max-width:100%;max-height:100%;'>";
      initialPreviewConfig[0]={caption: img, size: 329892, url: request.contextPath+"/files/delete", key: img};
    }
    $("form[id='product']").bootstrapValidator({
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
      alert("成功");
      $imgInput.val("");
    });

    /* 打开新增模态框 */
    $("#photoGalleryBtn").click(function() {
      var param=$("#photoGallery").val();
      if(param==null || param==""){
        alert("请输入图片库搜索关键词");
      }else {
        request.ajax("${pageContext.request.contextPath}/photoGallery/photoGalleryByName?photoName="+param,"POST","",true,function(data){
          if(data.success){
            $("#photoShow").empty();
            $.each(data.photoGallerys,function (i,item) {
              var a=$("<a></a>");
              a.attr("name","imgName");
              a.attr("photoSrc",item.img);
              var div=$("<div></div>");
              div.addClass("inline");
              var img=$("<img style='margin : 10px 10px 10px 10px;' width='150' height='150'/>").attr("src",request.imageServer+item.img);
              div.append(img);
              a.append(div);
              $("#photoShow").append(a);
            });
            $('#photoGarlleryModal').modal({
              backdrop : "static"
            });
          }else{
            tip.error("图片库加载失败");
          }
        });

      }


    });





    $(document).on('click',"[name='imgName']",function () {
      $imgInput.val($(this).attr("photoSrc"));
      $('#photoGarlleryModal').modal('hide');
    });

  });







</script>
</body>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/fileupload/css/fileinput.css">
<script src="${pageContext.request.contextPath}/static/plugins/fileupload/js/fileinput.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/fileupload/js/zh.js"></script>

</html>