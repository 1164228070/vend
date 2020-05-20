/**
 * layer通知插件
 * @param $
 * @param win
 */

(function($){
	 
	var tip = (function () {
		var tipModule  = '<div class="alert fixed-top text-center"/>',
			$tip   = $(),
			levelClass = ['alert-success', 'alert-danger', 'alert-info'],
			tipLevelClass  = '',
			timeId = null;
			
		//创建提示框
		function createTip() {
			$tip = $('.alert');
			if (!$tip.length) {
				$tip  = $(tipModule);
				$('body').prepend($tip);
			}
			$tip.hide()
			return $tip
		};
		
		// 显示提示框
		function show(status, msg, duration,callback) {
			duration = duration != null ? duration : 1000;
			tipLevelClass = levelClass[(status - 1)] || 'alert-info';
			
			createTip();
			$tip.addClass(tipLevelClass).html(msg || '').stop().fadeIn();
			
			clearTimeout(timeId);
			timeId = setTimeout(function () {
				hide();
				if(callback){
					callback();
				}
			}, duration)
		}
		
		//隐藏提示框
		function hide() {
			clearTimeout(timeId);
			$tip.removeClass(tipLevelClass).html('').fadeOut();
			timeId==null;
		}
		
		return {
			alert: show,
			close: hide
		}
	})();
	$.tip = tip;
})(window.jQuery);