/*
* jquery  tabNavSlideExpand plugin
* require jquery SlideNavTab
*  
*/

// SlideNavTab
// 用于定位tab导航定位子项
// #refresh 更新定位
// #slideTo 定位到某个子项

(function ($) {
	// tab导航定位子项
	function SlideNavTab(el) {
	
		if (!(this instanceof SlideNavTab)) {
			return new SlideNavTab(el);
		}
		
		if (el && !el.length) {
			throw('This ' + el + ' is Not found');
		}
		
		this.slideEl = el;
		//this.children = el.children().eq(0);
		this.children = el.find('.tab-slide-list');
		this.slideTabOpts = this.children.children();
		this.currentIndex = 1;
		
		var parentLeft = el.offset().left;
		
		this.slideTabOpts.each(function (i, ele) {
			$(ele).attr('data-pos-left', ( $(ele).offset().left - parentLeft) ).attr('data-width', $(ele).width());
		})
		
		this._init();
	}
	
	SlideNavTab.prototype._init = function () {
		var _index;
		
		this.viewHalfWidth = Math.ceil( this.slideEl.width() / 2 );

		//if ('localStorage' in window) {
		//	_index = Number(localStorage.getItem('_t_index')) || 1;
			
		//} else {
			_index = (this.slideEl.find('.on').index() + 1) || 1;
		//}
		
		this.currentIndex = _index;
		this.slideTo(this.currentIndex);
		
	}

	SlideNavTab.prototype.slideTo = function (index) {
		var slideTabOpts;
		
		slideTabOpts = this.slideTabOpts.eq(index - 1);
		
		if (!slideTabOpts.length) {
			throw('This ' + index + ' is beyond the scope');
		}

		this.slideTabOpts.removeClass('on');
		slideTabOpts.addClass('on');
		
		//if ('localStorage' in window) {
		//	localStorage.setItem('_t_index', index);
		//}
		
		this.currentIndex = index;
		_offsetLeft = Number(slideTabOpts.data('pos-left'));
		
		if (_offsetLeft > this.viewHalfWidth) {
			this.slideEl.animate({'scrollLeft': (_offsetLeft - this.viewHalfWidth)}, 300)
		}
		
	}
	
	SlideNavTab.prototype.refresh = function () {
		this._init();
	}
	
	$.fn.slideNavTab = function () {
		
		return this.each(function (i, el) {
			$(el).data('slideNavTab', SlideNavTab( $(el) ));
		})
	}

})(jQuery);

(function ($) {
	// tab
	$.fn.tabToggle = function (callback) {
		return this.each(function (i) {
			var $tabWrap      = $(this),
				$tabList      = $tabWrap.find('.tab-slide-list'),
				$tabListChild = $tabList.children(),
				$openBtn      = $tabWrap.find('.arr-down'),
				$tabMask,
				_width        = 0;
				
			if (!$tabListChild.length) return;
			
			$tabListChild.each(function () {
				var $self = $(this);
				
				_width += $self.outerWidth();
			
			})
			
			$tabList.width( _width + 5 );
			$tabWrap.append('<div class="tab-slide-mask"/>');
			$tabMask = $tabWrap.find('.tab-slide-mask');
			
			// 定位子项
			var slideNavTab = $tabWrap.find('.J-slide-nav').slideNavTab().data('slideNavTab');
			//slideNavTab.slideTo(5);
			
			var expandHandle = function () {
				if ($tabWrap.hasClass('expand')) {
					// 展开则隐藏
					$tabWrap.removeClass('expand');
					$('html,body').removeClass('overflow-body');
					
					// 定位到该选择的分类位置上
					slideNavTab.refresh();
					
				} else { 
					// 隐藏则展开
					$tabWrap.addClass('expand');
					$('html,body').addClass('overflow-body');

				}
			
			};
			
			// 展开隐藏
			$openBtn.add($tabMask).on('click', function () {
				expandHandle();
				return false;
			})
			
			// 定位到该选择的分类位置上
			$tabListChild.on('click', function (e) {
				e.preventDefault();
				
				var $self = $(this),
					_index = $self.index(),
					_dataVal = $self.data('val');
				
				if ($self.hasClass('on')) return;
				if (callback && (callback.call($self, _dataVal, e) === false)) return;

				// 隐藏 tab 分类			
				if ($tabWrap.hasClass('expand')) {
					$tabWrap.removeClass('expand');
					$('html,body').removeClass('overflow-body');
				}

				// 定位到该选择的分类位置上
				slideNavTab.slideTo(_index + 1);
				
				
				
				e.stopPropagation();
			})

		})
	
	}
	
	
})(jQuery);


