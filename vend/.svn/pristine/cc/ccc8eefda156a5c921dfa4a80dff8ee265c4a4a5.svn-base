window.weeks = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];

function getLocation(yesCallback,noCallback,unableCallback){
    var options={
        enableHighAccuracy:true, 
        timeout : 8000,   
        maximumAge:10000          
    }   
    if(navigator.geolocation){
        //浏览器支持geolocation
    	navigator.geolocation.getCurrentPosition(yesCallback,noCallback,options);
    }else{
    	//浏览器不支持geolocation
    	unableCallback;
    }
}
  
//创建滚动
function creatScroll(el, opts) {
    return new IScroll(el, $.extend({ mouseWheel: true, click: true}, opts || {}));
}  

/**
 * 模拟 confirm 弹层
 * @param json   
 * @returns
 */
function layerAlert(json) {
    var _index;    
    json.param = json.param ? json.param : {};    
    htmlContent = [
        '<div id="contact-alert-layer" class="alert-layer">',
        '	<div class="alert-con">',
        (json.contents ? json.contents : ''),
        '	</div>',
        '	<div class="alert-btns mod-box">',
        '		<a href="javascript:;" class="box-flex alert-btn alert-btn-cancel">' + (json.cancelTxt ? json.cancelTxt : '取消') + '</a>',
        '		<a href="javascript:;" class="box-flex alert-btn alert-btn-sure">' + (json.yesTxt ? json.yesTxt : '确定') + '</a>',
        '	</div>',
        '</div>',
    ],

    opts = {
        shift: 1,
        area: '90%',
        content: htmlContent.join(''),
        cancel: $.noop,
        yes: $.noop,
        end: $.noop
    };

    $.extend(opts, json || {});
   

    _index = layer.open({
        type: 1,
        title: false,
        closeBtn: opts.closeBtn || 0,
        shift: opts.shift,
        area: opts.area,
        scrollbar: false,
        content: opts.content,
        success: function(layero, index){
            // 取消
            $(layero).find('.alert-btn-cancel').off('click.layer').on('click.layer', function () {
                opts.cancel(layero, index,opts.$el,opts.param);
                layer.close(index);
            })
            // 确定
            $(layero).find('.alert-btn-sure').off('click.layer').on('click.layer', function () {
                var result = opts.yes(layero, index,opts.$el,opts.param);
                if (result === false) return;
                layer.close(index);
            })
        },
		end: opts.end
    });
    return _index;
};


function selfTip(content){
	var htmlContent = '<div style="padding:10px;text-align:center">'+content+'</div>';
	
	var _index;
	//自定页
	_index = layer.open({
	  type: 1,
	  area: '90%',       
	  skin: 'layui-layer-demo', //样式类名
	  closeBtn: 1, //显示关闭按钮
	  shift: 1,
	  shadeClose: true, //开启遮罩关闭
	  content: htmlContent
	});  
	return _index;
};



//页面跳转
function redirectURL(url) {
	if (url === undefined) {
		url = '/404.html';
	} else if (url === '') {
		url = '/';
	}  
	document.location.href = url;
}

/**
 * 倒计时效果
 * @param seconds
 * @param el
 */
function countDown(seconds,el) {
	var t = seconds;
	el.addClass('disabled');
	var dt = function () {
		t--;
		
		if (t === 0 ) {
			el.text('获取验证码');
			el.removeClass('disabled');
			return;
		}
		el.text( t + 's后重新获取');
		setTimeout(arguments.callee, 1000);
	};
	dt();
}
 		

//页面加载时图标
var loadPage = {
	thimid:null,
	pageload:function() {
		var $overlayer = $('.overlayer'), zIndex = 1;
		if($overlayer.length == 0) {
			$('body').append('<div class="overlayer"></div>');
			$overlayer = $('.overlayer');
		};
		
		zIndex += $overlayer.css('z-index');
		
		$overlayer.show();
		$('body').append('<div id="pageload" style="z-index:' + zIndex + '"><i></i><i></i><i></i></div>');
		
		clearTimeout(this.timeid);
		this.thimid = setTimeout(function(){
			$('#pageload').remove();
			$('.overlayer').hide();    
		},1000 * 60);
		
		return this;
	},
	//取消页面加载
	unpageload:function() {
		clearTimeout(this.timeid);
		$('#pageload').remove();
		$('.overlayer').hide();
		this.timeid = null;
		
		return this;
	}
};

//内容加载时图标
var loadContent = {
	thimid:null,
	// $el 为内容加载容器
	conLoad:function($el, cover) {
		var $html = $('<div id="conLoading" class="text-center ptb10"><img src="/images/loading-1.gif" alt="" /></div>');
		
		if (!cover) {
			$el.prepend($html);
		} else {
			$el.html($html);
		}
		
		
		clearTimeout(this.timeid);
		this.thimid = setTimeout(function(){
			$html.remove();
		},1000 * 15);
	},
	//取消内容加载
	unConload:function() {
		clearTimeout(this.timeid);
		//$('html, body').removeClass('overflow-body')
		$('#conLoading').remove();
		this.timeid = null;
	}
};
 

/**
 * 计算2个时间之差
 * @param startDate    yyyy-MM-dd
 * @param endDate      yyyy-MM-dd
 */
function diffDate(startDate,endDate){
	startDate =  new Date(startDate.replace(/-/g, "/"));
	endDate =  new Date(endDate.replace(/-/g, "/"));
	return parseInt((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
}     


/**
 * 时间格式化
 * @param time
 * @param format
 * @returns {String}
 */
function dateFormat(time, format) {
	try {
		var date = new Date( Number( time ) );
	} catch(e) {}
	
	var fillZero = function (num) {
		return num < 10 ? '0' + num : num;
		
	};
	if (format=== 'yyyy-MM-dd hh:mm:ss') {
		return date.getFullYear() + '-' + fillZero(date.getMonth() + 1) + '-' + fillZero(date.getDate()) + ' ' + fillZero(date.getHours()) + ':' + fillZero(date.getMinutes()) + ':' + fillZero(date.getSeconds());
	}else if(format=== 'MM-dd'){
		return fillZero(date.getMonth() + 1) + '-' + fillZero(date.getDate());
	}else if(format=== 'MM/dd'){
		return fillZero(date.getMonth() + 1) + '/' + fillZero(date.getDate());
	}else if(format=== 'yyyy/MM/dd'){
		return date.getFullYear() + '/' + fillZero(date.getMonth() + 1) + '/' + fillZero(date.getDate());
	}else {
		return date.getFullYear() + '-' + fillZero(date.getMonth() + 1) + '-' + fillZero(date.getDate());
	}
}

/**
 * 补足前缀0
 * @param num
 * @returns
 */
function fillZero(num) {
	return num < 10 ? '0' + num : num;
};


// 根据范围设置图标大小
function setImageSize(url, maxW, maxH, callback) {
	var image = new Image(), imageWidth, imageHeight;

	image.onload = function () {
		if ((image.width > maxW) || (image.height > maxH)) {
			if ((image.width / maxW) > (image.height / maxH)) {
				// 根据视口的宽度
				imageWidth = maxW;
				imageHeight = parseInt(image.height / (image.width / imageWidth) , 10);
			} else {
				// 根据视口的高度
				imageHeight = maxH;
				imageWidth = parseInt(image.width / (image.height / imageHeight), 10);
			}

			callback && callback(url, imageWidth, imageHeight)
		}
	}

	image.src = url;

}

// 预览图片
function loadImageFile(el, callback) {
	var oFReader = new FileReader(),
	rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;

	if (!el.files) {
		alert('不支持拍照本地预览！');
		return false;
	}

	var oFile = el.files[0];

	if (!rFilter.test( oFile.type )) {
		alert("图片格式不正确，请重新拍照或选择！");
		if (el.outerHTML) {
			el.outerHTML = el.outerHTML;
		} else { // FF(包括3.5)
			el.value = '';
		}
		return false;
	}

	oFReader.onload = function (oFREvent) {
		//document.getElementById("uploadPreview").src = oFREvent.target.result;
		callback && callback.call(el, oFREvent);
	};

	oFReader.readAsDataURL(oFile);
}

// 显示删除
function panToggle($el) {
	
	$el.hammer().bind("panleft panright", function(evt) {

		var $target = $(evt.target),
			oGesture = evt.gesture;

		//DIRECTION_LEFT DIRECTION_RIGHT
		if ( oGesture.direction === Hammer.DIRECTION_LEFT && oGesture.distance >= 80 && Math.abs(oGesture.angle) > 170 ) {
			$target.addClass('animat-open');
		} else if (oGesture.direction === Hammer.DIRECTION_RIGHT && oGesture.distance >= 80) {
			$target.removeClass('animat-open');
		}

		return false;
	});
	
}

// 格式化数字 12 -> 12.00
function toFixed(num, count){
	num = parseFloat(num);
	
	if (num == null) {
		num = 0;
		console.log(num + ' 必须是可转化为数字的参数.');
	}
	
	if (count == undefined) {
		count = 2;
	}
	
	return (num).toFixed(count);
}
 

$(function () {
	
	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
		if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
			return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
		} else {
			return this.replace(reallyDo, replaceWith);
		}
	};
	
	// 返回上一页
	$('.J-go-back').on('click', function () {
		history.back();
		return false;
	})
	
	
	// 模拟按钮
	$('.J-btn-switch').on('click', function () {
		var $self = $(this),
			$protectPwd = $self.siblings('.protect-pwd'),
			$publicPwd = $self.siblings('.public-pwd'),
			_checked = $self.data('checked');
		
		if (_checked) {
			$self.data('checked', false);
			$self.removeClass('checked');
			$self.html('隐藏');
			$publicPwd.hide();  
			$protectPwd.show();
		} else {
			$self.data('checked', true);
			$self.addClass('checked');
			$self.html('显示');
			$protectPwd.hide();
			$publicPwd.show();
		}
		
		
	});
	
	// 显示隐藏密码
	$('.protect-pwd, .public-pwd').on('keyup', function () {
		var $self = $(this);
		$relPwd = $( '.' + $self.data('rel') );
		$relPwd.val($.trim( $self.val() ));
	});
});