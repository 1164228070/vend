/**
 * 请求js工具类
 */
var request = function() {};

/**
 * ajax请求
 * @param URL              请求地址
 * @param method           请求方法 
 * @param data             请求数据
 * @param async            是否异步
 * @param callBack         处理毁掉函数
 */
request.ajax=function(URL,method,data,async,callBack){
	var param = data ? data : {}; 
	if(typeof(param)==="string"){
		param = param+"&_method="+method;     
	}else{
		param._method=method;
	}  
	$.ajax({
        cache: true,
        type: "POST",
        url:URL,
        data:param,
        async: async,
        success: function(data) {
        	callBack(data);
        	//common.ajaxTreeTip(data,'','');
        	//关闭对话框
            //提示操作信息
        	//刷新指定节点
        }
    });
},
request.ajaxGet=function(URL,method,data,async,callBack){
	var param = data ? data : {};
	$.ajax({
        cache: true,
        type: method,
        url:URL,
        data:param,
        async: async,
        success: function(data) {
        	callBack(data);
        	//common.ajaxTreeTip(data,'','');
        	//关闭对话框
            //提示操作信息
        	//刷新指定节点
        }
    });
},

/**
 * Jsonp请求
 * @param URL
 * @param data
 * @param async
 * @param callBack
 */
request.ajaxJsonp=function(URL,data,async,callBack){
	var param = '';
	if(typeof(data)!='undefined' && data!=""){
		param = data;
	}
	$.ajax({
		cache: true,
		url:URL,
		data:param,
		async: async,
		dataType: 'jsonp',        
		success: function(data) {
			callBack(data);
		}
	});
}


/**
 * 提交表单
 * @param URL
 * @param method
 * @param tip
 * @param jsonStr
 */
request.confirm = function(URL,method,tip,jsonStr,callBack){
	
	//TODO 
	
	/*bootbox.confirm("确定"+tip+"?", function(result) {
		if(result) {
			request.submitForm(URL,method,jsonStr);
		}else{
			callBack(false);
		}
    });*/
},


/**
 * 表单请求
 * @param URL
 * @param method
 * @param data
 */
request.submitForm = function(URL,method,jsonStr){
    var $form = $("#actionForm");
    $form.children('#actionMethod').val(method);
    
    if(jsonStr!=''){
    	data = $.parseJSON(jsonStr);
    	var $input;
    	$.each(data,function(key,value){
    		$input = $('<input type="hidden" name="'+key+'" value="'+value+'">');
    		$input.appendTo($form);
    	});
    }
    
    $form.attr("action",URL);
    //模拟表单提交
    $form.submit();
};


/**
 * 表单请求
 * @param URL
 * @param method
 * @param data
 */
request.submit = function(URL,method,data){
	var $form = $("#actionForm");
	$form.children('#actionMethod').val(method);
	$form.attr("action",URL);
	//模拟表单提交
	$form.submit();
};