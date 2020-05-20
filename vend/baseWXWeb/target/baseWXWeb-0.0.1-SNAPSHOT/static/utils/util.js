var util = function() {};
util.escapeJquery = function(srcString){
    var escapseResult = srcString;
    var jquerySpecialChars = ["~", "`", "@", "#", "%", "&", "=", "'", "/",":",";", "<", ">", ",", "/"];
    for (var i = 0; i < jquerySpecialChars.length; i++) {
     escapseResult = escapseResult.replace(new RegExp(jquerySpecialChars[i],
         "g"), "//" + jquerySpecialChars[i]);
    }
    return escapseResult;
};    

/**
 * 获取字符串字节大小
 * 一个汉字对应三个字节
 */
util.getByteLength = function(strContent){
	if(strContent==null){
		return 0;
	}else{
		strContent = strContent.replace(/[^\x00-\xff]/g,"000");
		return strContent.length;
	}
};
/**
 * 
 * @param formId    表单Id
 * @param obj       json对象
 */
util.formLoad =  function loadData(formId,obj){
    var key,value,tagName,type;
    for(x in obj){
        key = x;
        value = obj[x];        
        $("form[id='"+formId+"'] [name='"+key+"']").each(function(){
            tagName = $(this)[0].tagName; 
            type = $(this).attr('type');
            if(tagName=='INPUT'){
                if(type=='radio'){   
                    $(this).attr('checked',$(this).val()==value);
                }else if(type=='checkbox'){
                	//TODO 
                }else{
                    $(this).val(value);
                }
            }else if(tagName=='SELECT' || tagName=='TEXTAREA'){
                $(this).val(value);          
            }
        });
    }
};

/**
 * 处理座位编号
 * @param seatNum
 * @returns {String}   
 */
util.repairSeatNum = function(seatNum){
	if(seatNum){
		var seatNumEle = seatNum.split("_");
		return seatNumEle[0]+"排"+seatNumEle[1]+"列";
	}
	return "";
};
 

/**
 * 截取图片第一个路径
 */
util.subFirstURI=function(URI){
	if(""==$.trim(URI)){
		return "";
	}
	var index = URI.indexOf(",");
	if(index>0){
		return URI.substr(0,index);
	}else{
		return URI;
	}
};

/**
 * 分割Tag
 * @param tag
 * @returns
 */
util.tags=function(tag){
	var result="";
	if(""==$.trim(tag)){
		return "";
	}
	var indexs = tag.split("，");
	var size = indexs.length;
	
	for(var i=0;i<size;i++){
		result = result+'<div class="mark"><i class="icon-l"></i><i>'+indexs[i]+'</i><i class="icon-r"></i></div>';
	}
	return result;   
};

/**
 * 字符串转时间对象
 * @param param
 * @returns {Date}
 */
util.strToDate = function(param){
    return new Date(param);
};

/**
 * 格式化时间
 * @param inputTime
 * @returns {String}
 */
util.formatDateTime = function (inputTime) {  
    var date = new Date(inputTime);
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
};

 
/**
 * 格式化时间
 * @param param [yyyy-MM-dd xx]
 * @returns
 */
util.formatDate = function(param){
	return param.substr(0,param.indexOf(" "));
};


util.diffNatureDay=function(endDate){
	 var result="";   
	 startDate = new Date();
	 endDate = new Date(endDate);    
	 diffMill = endDate.getTime()-startDate.getTime();    
	 result +=parseInt(diffMill /86400000)+"天";
	 result +=parseInt(diffMill % 86400000 /3600000)+"小时";   
	 result +=parseInt(diffMill % 86400000 % 3600000 /60000)+"分";
	 result +=parseInt(diffMill % 86400000 % 3600000 % 60000 / 1000)+"秒";  
	 return result;   
};

/**
 * 分转元
 * @param cents
 * @returns {Number}
 */
util.centToYuan = function(cents){
	return cents/100;
};

/**
 * 
 * @param param
 * @param length
 * @returns 省略字符串
 */
util.ellipStr = function(param,length){
	if(param.length>length){
		return param.substr(param,length-1)+"...";
	}else{
		return param;
	}
};
  

/**
 * URI编码
 * @param param
 * @returns
 */
util.encodeURI = function(param){
	if(typeof(param)!='undefined'){
		return encodeURI(encodeURI(param));
	}else{
		return '';
	}
};

/**
 * 添加参数
 */
util.addInput = function($input){
	var $form = $("form[id='actionForm']");
	$input.appendTo($form);
};

/**
 * 模拟表单提交
 * @param actionURL
 * @param method
 */
util.formSubmit = function(actionURL,method){
	var $form = $("form[id='actionForm']");
	$form.attr("action",actionURL);
	$form.find("input[id='actionMethod']").val(method);
	$form.submit();
};

/**
 * 自定义手机校验器          
 */
util.validatePhone = function(phone){
	if(typeof(phone)=='undefined' || phone==""){
		return false;
	}
	return /^(13|15|17|18)\d{9}$/i.test(phone); 
};


/**
 * 判断参数是否不为空
 * @param param
 * @returns {Boolean}
 */
util.notEmpty = function(param){
	if(typeof(param)!='undefined' && $.trim(param)!=""){
		return true;
	}
	return false;
}

/**
 * 判断字符串是否以...结尾
 * @param content
 * @param param
 * @returns {Boolean}
 */
util.endWith = function(content,param){
    var d=content.length-param.length;
    return (d>=0&&content.lastIndexOf(param)==d)
 }