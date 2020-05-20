$.ajaxSetup({
	dataFilter : function (data, type) {
		try{
			var tempData = $.parseJSON(data);
			var responseType = tempData.responseType;
			if(typeof(responseType)!="undefined"){
				//0 代表session超时  1 代表服务器繁忙(通常是服务器运行时异常) 2 代表未认证
				if(responseType==0){  
					location.href = common.contextPath+'/session/new?redirect='+tempData.from || (common.contextPath+'/members/center');
				}else if(responseType=="1"){
					location.href = common.contextPath+'/members/center';
					//loadPage.unpageload();
					//common.tip('系统繁忙,请稍后再试!','操作失败');
				}else if(responseType=="2"){  
					//loadPage.unpageload();
					common.tip('系统繁忙,请稍后再试吧!','操作失败');
					//selfTip('<p>'+tempData.msg+'</p><br><a style="color:#ff6e3b;margin-top:80px;" href="'+common.contextPath+'/members/certify">>>完成认证</a>');
				}           
		        return {};                                        
			}else{
				return data;  
			}
		}catch(e){ 
	        return data;
		}
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		common.tip('系统繁忙,请稍后再试吧!','操作失败');
	}
});