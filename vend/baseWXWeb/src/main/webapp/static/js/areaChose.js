/*
* 地区选择
*/

var areaChose = {
	init: function (successFn, errorFn) {
		var _this = this;
		
		_this.cityIndex = {
			'1': -1,
			'2': -1,
			'3': -1
		}
		
		_this._index = 0;
		_this._level = 1;
		_this.areaData = [];
		_this.successFn = successFn || $.noop;
		_this.errorFn = errorFn || $.noop;
		_this.$tpl = $('script[data-tpl-data="areaChose"]');
		_this.$targetEl = $('.city_con');
		
		
		_this.getAreaData();
		
	}, 
	
	getAreaData: function () {
		var _this = this,
			errorFn = _this.errorFn;
		// 获取地区数据
		$.ajax({
			url: 'js/WXYS_AREA.json',
			dataType: 'json',
			success: function (result) {
				_this.areaData = result;
				_this.eventAction();
			},
			error: function () {
				errorFn();
			}
		})
	}
	, refresh: function () {
		var _this = this,
			$tpl = _this.$tpl,
			$targetEl = _this.$targetEl;
		
		_this.cityIndex = {
			'1': -1,
			'2': -1,
			'3': -1
		}
		
		_this._index = 0;
		_this._level = 1;
		
		// 渲染省、直辖市、自治区数据
		
		renderTpl($tpl.html(), $targetEl, {citys:_this.areaData, level: _this._level}, true);
		
	}
	, eventAction: function () {
		var _this = this,
			_index = _this._index,
			_level = _this._level,
			_areaData = _this.areaData,
			data = [],
			cityIndex = _this.cityIndex,
			$tpl = _this.$tpl,
			$targetEl = _this.$targetEl;
		
		// 渲染省、直辖市、自治区数据
		renderTpl($tpl.html(), $targetEl, {citys:_areaData, level: _level}, true);
		
		// 选择地址
		$targetEl.on('click', 'li', function (e) {
			e.preventDefault();
			
			var $self = $(this);
			//$self.addClass('on').siblings().removeClass('on');
			
			// 所在级别的索引
			_index = $self.data('city-index');
			// 地区级别
			_level = $self.data('city-level');
			// 地区指针对应更新
			cityIndex[_level] = _index;
			
			if (_level == 1) {
				// 获取对应省级下的市级数据（市）
				data = _areaData[_index].sub;
				
				if (!data) return ;
				
				// 市级数据为 1 时，直接获取该市级下的县级数据（县/区）
				if (data.length && data.length === 1) { 
					// 县级数据
					data = data[0].sub;
					
					// 市级指针对应更新
					_level++;
					cityIndex[_level] = 0;
				}
				
			} else if (_level == 2) {			
				// 获取对应市级下的县级数据（县/区）
				data = data[_index]['sub'];
				
				if (!data) return ;
				
				// 县级的数据为 1 时，直接获取该县的数据
				if (data.length && data.length === 1) {
					data = data[0];
				}
				
				
			} else if (_level == 3 ) {
				// 获取对应县级的数据
				data = data[_index];

			}
			
			if (_level == 3) {
				return _this.successFn(data);
			};
			
			// 渲染下一级地区
			_level++;
			renderTpl($tpl.html(), $targetEl, {citys:data, level: _level}, true);

			e.stopPropagation();
		})
	
	} 
	
};

//areaChose.init();
