function LoadMore(config){
	this.callback = config.callback;
	this.scroll_in_progress = false;
	this.offset = config.offset || 0;
	this.end = false;    //是否加载完毕
	this.myScroll;
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	this.init();
}
 
/**
 * 加载数据
 * @param refresh
 * @param next_page
 */
LoadMore.prototype.init = function(){ 
	var windowH = $(window).height();
    if ($(".list").height()<windowH) {
 	    $(".list").css({
 		    'min-height':windowH
 	    })
    }
}

/**
 * 初始化参数
 * @param refresh
 * @param next_page
 */
LoadMore.prototype.param = function(param){
	this.param = param || {};
	this.pageSize = param.pageSize || 3; 
}

LoadMore.prototype.load_content = function(refresh, next_page,param){ 
	var it = this;
	if(param){
		it.param = param;	
	}
	setTimeout(function() {
		
		it.callback(!refresh,refresh && !next_page,refresh && next_page,it.param,function(sign,total){
			if(sign){ //加载成功
				if(refresh){
					it.actionCallback();
					it.myScroll.refresh();
				}
				if(total<it.pageSize){
					it.end = true;
				}
			}else{    //加载失败
				//TODO   
			}
		});
		
		if(!refresh){
			if (it.myScroll) {
				it.myScroll.destroy();
				$(it.myScroll.scroller).attr('style', ''); // Required since the styles applied by IScroll might conflict with transitions of parent layers.
				it.myScroll = null;
			}
		    $(".pullUp").show();
		    $(".pullDown").show();
			it.createScroll(it.offset);
		}
	},1);
	
}

/**
 *创建滚动条
 * @param offset
 */
LoadMore.prototype.createScroll =	function(offset) {
	
	var it = this;
	it.pullDownEl = document.querySelector('#wrapper .pullDown');
	
	if (it.pullDownEl) {
		it.pullDownOffset = it.pullDownEl.offsetHeight;
	} else {
		it.pullDownOffset = 0;
	}
	it.pullUpEl = document.querySelector('#wrapper .pullUp');	
	if (it.pullUpEl) {
		it.pullUpOffset = it.pullUpEl.offsetHeight;
	} else {
		it.pullUpOffset = 0;
	}
	
	if (!offset) {
		offset = it.pullUpOffset;
	}
	
	this.myScroll = new IScroll('#wrapper', {
		probeType:1, tap:true, click:false, preventDefaultException:{tagName:/.*/}, mouseWheel:true, scrollbars:true, fadeScrollbars:true, interactiveScrollbars:false, keyBindings:false,
		deceleration:0.0002,
		startY:(parseInt(offset)*(-1))
	});
	
	this.myScroll.on('scrollStart', function () {
		scroll_in_progress = true;
	});
	this.myScroll.on('scroll', function () {
		
		scroll_in_progress = true;
		
			if (this.y >= 5 && it.pullDownEl && !it.pullDownEl.className.match('flip')) {
				it.pullDownEl.className = 'pullDown flip';
				it.pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开刷新';
				this.minScrollY = 0;
			} else if (this.y <= 5 && it.pullDownEl && it.pullDownEl.className.match('flip')) {
				it.pullDownEl.className = 'pullDown';
				it.pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新';
				this.minScrollY = -it.pullDownOffset;
			}
		//if ($('#wrapper div.list div.product').length >= it.pageSize) {
			if(this.y <= (this.maxScrollY - 10) && it.pullUpEl && !it.pullUpEl.className.match('flip')){
				it.pullUpEl.className = 'pullUp flip';
				it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '松开加载';
				it.pullUpEl.querySelector('.pullUpIcon').style.display = "inline-block";
			}else if(this.y >= (this.maxScrollY - 10) && it.pullUpEl && it.pullUpEl.className.match('flip')){
				it.pullUpEl.className = 'pullUp';
				it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载';
				it.pullUpEl.querySelector('.pullUpIcon').style.display = "inline-block";
				//this.minScrollY = -it.pullDownOffset;  //TODO
			}
		//}
	});
	this.myScroll.on('scrollEnd', function () {
			if (it.pullDownEl && it.pullDownEl.className.match('flip')) {
				it.pullDownEl.className = 'pullDown loading';
				it.pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
				it.pullDownAction();
				it.pullUpEl.querySelector('.pullUpIcon').style.display = "inline-block";
				it.end = false;
			}
			
			if(it.end){
				it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载完毕';
				it.pullUpEl.className = 'pullUp';
				it.pullUpEl.querySelector('.pullUpIcon').style.display = "none";
				return;
			}else{
				if(it.pullUpEl.className.match('flip')){
					it.pullUpEl.className = 'pullUp loading';
					it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
					it.pullUpAction();
				}
			}
		//}
	});
}


/**
 * 下拉刷新
 */
LoadMore.prototype.pullDownAction = function() {
	this.load_content('refresh');
	$('#wrapper > #scroller > div').data('page', 1);
}

/**
 * 上拉加载更多
 * @param callback
 */
LoadMore.prototype.pullUpAction = function(callback) {
	var next_page;
	if ($('#wrapper > #scroller > div').data('page')) {
		next_page = parseInt($('#wrapper > #scroller > div').data('page')) + 1;
	} else {
	    next_page = 2;
	}
	this.load_content('refresh', next_page);
	$('#wrapper > #scroller > div').data('page', next_page);
	
	if (callback) {
		callback();
	}
}

LoadMore.prototype.actionCallback = function() {
	var it = this;
	if (it.pullDownEl && it.pullDownEl.className.match('loading')) {
		
		it.pullDownEl.className = 'pullDown';
		it.pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新';   
		
		this.myScroll.scrollTo(0, parseInt(it.pullUpOffset)*(-1), 200);
	} else if (it.pullUpEl && it.pullUpEl.className.match('loading')) {
		it.pullUpEl.className = 'pullUp';
		it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载...';   
	}
}

LoadMore.prototype.error = function() {
	var it = this;
	if (it.pullDownEl && it.pullDownEl.className.match('loading')) {
		
		it.pullDownEl.className = 'pullDown';
		it.pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新';   
		
		this.myScroll.scrollTo(0, parseInt(it.pullUpOffset)*(-1), 200);
	} else if (it.pullUpEl && it.pullUpEl.className.match('loading')) {
		it.pullUpEl.className = 'pullUp';
		it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载...';   
	}
}

LoadMore.prototype.loading = function() {
	var it = this;
	it.end = false;
	it.pullDownEl.className = 'pullDown loading';
	it.pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
	
	it.pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载';
	it.pullUpEl.querySelector('.pullUpIcon').style.display = "inline-block";
}