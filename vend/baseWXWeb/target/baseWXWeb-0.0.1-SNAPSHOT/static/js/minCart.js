/**
 * 迷你购物车交互
 * require jquery.js、iscroll.js、layer.js store.js
 *
*/

(function ($) {
	
	// 商品添加删除处理
	// relMinCartEl 参数为关联购物车元素
	$.fn.minCart = function(relMinCartEl) {
		// 加减商品
		
		if (!relMinCartEl) return this;
		
		var $minCart = relMinCartEl,
			$minCartTotalNum = $minCart.find('.nums'), // 购物车的数量
			$minCartTotalPrice = $minCart.find('.total-price'), // 购物车的订单总价
			_minCartTotalNum, _minCartTotalPrice,
			_cartGoods;
		
		function bindEvent(i, el) {
			// 处理历史购物车缓存数据
			if (store.get('cartGoods')) {
				_cartGoods = store.get('cartGoods');
				
				var $cartGoodsEl, _num;
				
				$.each(_cartGoods.goodsList, function (i, goods) {
					_num = goods.num;
					
					if (goods.isSendsale) {
						_num = goods.num / 2;
					}

					$cartGoodsEl = $('[data-rel-cart="' + goods.uuid + '"]');
					$cartGoodsEl.find('.minus').removeClass('hide');
					$cartGoodsEl.find('.num').data('num', _num).text(goods.num).removeClass('hide');

				});
				
			}
			
			var $self = $(this),
				//$nativeNum = $self.find('.num'),
				_uuid = $self.data('rel-cart'),
				
				// 根据_uuid 获取对应元素
				$relShopEl = $('[data-rel-cart="' + _uuid + '"]'),
				$minus = $relShopEl.find('.minus'),
				$plus = $relShopEl.find('.plus'),
				$num = $relShopEl.find('.num'),
				// 获取对应元素下的数据
				_shopid = $relShopEl.data('rel-shop-id'),
				_isSendsale = !!($relShopEl.data('sendsale')),
				_inventory = $relShopEl.find('[data-inventory]').data('inventory') || 0,
				
				// 定一些变量
				_tit, _price, _num, cartShopId, _oNewGoods, temp = 0;
				
			_tit = $self.find('[data-rel-tit]').text();
			_price = parseFloat($self.find('[data-rel-price]').text());
			
			
			
			// 更新缓存
			// oCartGoods 已加入到购物车的数据
			// oNewGoods 要加入到购物车的商品数据
			function upDateCacheCartData(oNewGoods) {
				// 缓存
				var isHas = false,
					oCartGoods = store.get('cartGoods'), goodsList;
				
				if (!oCartGoods) {
					oCartGoods = {
						'shopId': '',
						'goodsList': []
					}
				}
				
				// 购物车商品列表
				goodsList = oCartGoods.goodsList;

				// 实行删除
				if (oNewGoods.num <= 0) {
					$.each(goodsList, function (index, goods) {
						if (goods.uuid === oNewGoods.uuid) {
							//goods.num = oNewGoods.num;
							goodsList.splice(index, 1);
							return false;
						}
					});
					
					// 检测购物车商品数量，为 0 则重置购物车的钩子 shopId 为空
					if (!goodsList.length) {
						oCartGoods.shopId = '';
					}
					
				} else {
					// 更新缓存数据
					oCartGoods.shopId = oNewGoods.shopid;
					
					// 存在该商品，则直接更新商品的数量
					$.each(goodsList, function (index, goods) {
						if (goods.uuid === oNewGoods.uuid) {
							goods.num = oNewGoods.num;
							isHas = true;
							return false;
						}
					});
					
					// 不存在，则往购物车添加该商品的完整信息
					if (!isHas) {
						goodsList.unshift(oNewGoods);
					}

				}
				
				// 缓存加入购物车的商品
				store.set('cartGoods', oCartGoods);
				
			}
			
			// 加
			function plusHandle() {
				$num = $('[data-rel-cart="' + _uuid + '"] .num');
				cartShopId = $minCart.data('rel-shop-id');
				//console.log('_shopid', _shopid)
				//console.log('cartId', cartShopId)
				//_num = parseInt($nativeNum.html(), 10) || 0;
				_num = parseInt($num.data('num'), 10) || 0;
				temp = ++_num;
				
				// 判断是不是买一送一
				if (_isSendsale) {
					temp *= 2;
				}
				
				// 判断库存（其实不准）
				if (_inventory < temp) {
					layer.msg('抱歉，库存不足', {icon: 2});
					return false;
				}
				
				// 加处理
				function plus() {
					_minCartTotalNum = getBuyNums();
								
					if (_isSendsale) {
						_minCartTotalNum += 2;						
					} else {
						_minCartTotalNum++;
					}

					// 重置价格
					$minCartTotalPrice.text(priceHandle(_price));

					// 重置购买总数量
					$minCartTotalNum.text(_minCartTotalNum);
					$num.data('num', _num).text(temp);

					// 购买对应商品的数据信息
					_oNewGoods = {
						uuid: _uuid,
						tit: _tit,
						num: temp,
						inventory: _inventory,
						isSendsale: _isSendsale,
						price: _price.toFixed(2),
						shopid: _shopid
						
					};

					// 新增商品
					addToCart(_oNewGoods);

					// 显示 减、数量
					$minus.removeClass('hide');
					$num.removeClass('hide');

					// 保存在缓存中
					upDateCacheCartData(_oNewGoods);

					// 切换购物车的商品提示状态（空、非空）
					// 调用购物车的方法处理
					minCart.setMinCartStatus( (_minCartTotalNum ? 1 : 0) );
					
				}
				
				// 不同店铺 根据 shopId 判断（购物车上的商品必须是同一个店铺的）
				if (cartShopId && cartShopId != _shopid) { 

					layer.open({
						//type:1,
						area: '90%',
						title: '温馨提示',
						content: '您购买了不同店铺的商品，添加会清空购物车',
						btn: ['确定', '取消'],
						yes: function (index, layero) {
							// 清空购物车数据
							minCart.initCartData();
							
							// 添加商品处理
							plus();
							
							// 关闭层
							layer.close(index);
							
						}
					})
					
				}
				// 同一店铺
				else {
					// 添加商品处理
					plus();
				
				}
				
				//return _minCartTotalNum;

			}
			
			// 减
			function minusHandle() {
				//_num = parseInt($nativeNum.html(), 10) || 0;
				_num = parseInt($num.data('num'), 10) || 0;
				temp = --_num;
				
				
				_minCartTotalNum = getBuyNums();
				
				if (_isSendsale) {
					_minCartTotalNum -= 2;
					temp *= 2;
				} else {
					_minCartTotalNum--;
				}
				
				
				_minCartTotalNum = _minCartTotalNum > 0 ? _minCartTotalNum : 0;
				
				if (_num <= 0) {
					$minus.addClass('hide');
					$num.addClass('hide');
					
					delToCart(_uuid);
					
				}
				
				$minCartTotalNum.text(_minCartTotalNum);
				$num.text(temp);
				//$nativeNum.text(_num);
				$minCartTotalPrice.text(priceHandle(_price, 'minus'));
				$num.data('num', _num)
				
				_oNewGoods = {
					uuid: _uuid,
					tit: _tit,
					num: temp,
					isSendsale: _isSendsale
				};
				
				upDateCacheCartData(_oNewGoods);
				
				// 切换购物车的商品提示状态（空、非空）
				// 调用购物车的方法处理
				minCart.setMinCartStatus( (_minCartTotalNum ? 1 : 0) );
				
				//return _minCartTotalNum;
				
			}
			
			// 获取购物车已卖的商品数
			function getBuyNums() {
				return Number($minCartTotalNum.text() || 0);
			}
			
			// 给购物车添加商品
			function addToCart(goodsJsonInfo) {
				
				if (!$minCart.find('[data-rel-cart="' + goodsJsonInfo.uuid + '"]').length) {
					
					var $html = $(minCart.goodsTmp(goodsJsonInfo));
								
					//$html.data('rel-data', goodsJsonInfo);
					$minCart.find('.goodsList').prepend($html);
					$minCart.data('minCartScroll') && $minCart.data('minCartScroll').refresh();
					$minCart.data('rel-shop-id', goodsJsonInfo.shopid);
					
					$html.minCart($minCart);
				}
				
			}
			
			// 从购物车移除商品
			function delToCart(uuid) {

				if (!!$minCart.find('[data-rel-cart="' + uuid + '"]').length  && !_num) {
					$minCart.find('[data-rel-cart="' + uuid + '"]').remove();
					$minCart.data('minCartScroll') && $minCart.data('minCartScroll').refresh();
				}
				
			}
			
			// 总价格处理
			function priceHandle(price, operator) {
				_minCartTotalPrice = parseFloat($minCartTotalPrice.text()) || 0;
				
				if (operator == 'minus') {
					price = -price;
				}
				
				_minCartTotalPrice = ((_minCartTotalPrice * 100 + price * 100) / 100).toFixed(2);
				return 	_minCartTotalPrice;
			}
			
			// 加减处理
			$self.off('click.minCart').on('click.minCart', function (e) {
				//e.preventDefault();
				
				var $target = $(e.target),
					//_minCartTotalNum,
					_targetClick = false;
			
				if ($target.hasClass('plus')) { // 加
					plusHandle();
					_targetClick = true;
				} else if ($target.hasClass('minus')) { // 减
					minusHandle();
					_targetClick = true;
				}
				
				// 切换购物车的商品提示状态（空、非空）
				// 调用购物车的方法处理
				//if (_targetClick) {
				//	minCart.setMinCartStatus( (_minCartTotalNum ? 1 : 0) );
				//}
				
				e.stopPropagation();
				
			})	
		}
		
		//$('[data-rel-cart]').each(bindEvent);
		return this.each(bindEvent);
		
	}
	
})(jQuery);


$(function () {
	var $minCart = $(),
		$goodsList = $(),
		$totalNums = $(),
		$totalPrice = $(),
		$emptyCart = $(),
		$nonEmptyCart = $();
	
	// 询问是否拨打电话
	function _callKF(phoneNum) {
		layerAlert({
            contents: '<p class="ptb20 text-center">您将联系该店铺，确认拨打吗？</p>'
            , cancel: function () {
				// 嘛都不干
            }
            , yes : function () {
                //console.log('ok');
				location.href = phoneNum;
            }
        })
	}
	
	// 单个商品模板与数据结合
	function _goodsTmp(json) {
		var _html = '';
		
		if (json == undefined) {
			return _html;
		}
		
		_html = '<li class="panel-chunk" data-rel-cart="' + json.uuid + '" data-sendsale="'
				+ (json.isSendsale ? 1 : 0) + '" data-rel-shop-id="' + json.shopid + '">'
				+	'<div class="pull-right">'
				+		'<span class="price emb-red mr5">&yen; <i data-rel-price="true" class="f18">' + json.price + '</i></span> '
				+		'<div class="add-cart" data-inventory="' + json.inventory + '">'
				+			'<span class="minus">-</span> '
				+			'<span class="num">'+ json.num +'</span> '
				+			'<span class="plus">+</span>'
				+		'</div>'
				+	'</div>'
				+	'<h2 data-rel-tit="true" class="nowrap f16">' + json.tit + '</h2>'
				+'</li>';
				
		return _html;
	}

    var minCart = {
        //$minCart: $minCart,
        $minCart: $(),

        init: function (phoneNum) {
			
			if ($('#minCartPanel').length) return;
			
			if (phoneNum !== undefined) {
				this.phoneNum = phoneNum;
			}
			
            this.setup();

        },
        setup: function () {
            
            // refresh
			
			this.creatMinCart();
			this.initMask();
			
			// 商品滚动初始化
            this.minCartScroll = creatSroll('#J-cart-list');
			$minCart.data('minCartScroll', this.minCartScroll);
			
            // 事件绑定
            this.eventActive();

        },
		creatMinCart: function () {
			var _this = this,
				_phoneNum = this.phoneNum,
				_goodsListHtml = '',
				_html = '';
			
			// 从缓存中取数据
			var _cartGoods = store.get('cartGoods');
			//缓存数据的格式：
			//{
			//	'shopId': '',
			//	'goodsList': [{}, {}]
			//}
			
			
			if (_cartGoods) {
				
				$.each(_cartGoods.goodsList, function (index, goods) {
					_goodsListHtml += _this.goodsTmp(goods);
				})
				
			}
			
			
			_html = '<!-- 悬浮购物车 start --><div id="minCartPanel"><div class="minCartInfo"><a class="min-cart" href="javascript:;"><ins class="counter nums">0</ins></a><!-- 空 --><div class="empty-cart">'
			+ (!!_phoneNum ? '<a class="J-contact-sell btn btn-contact" href="tel:' + _phoneNum + '">联系商家</a>' : '')
			+'<p class="txt-line">购物车是空的</p></div><!-- 非空 --><div class="non-empty-cart hidden"><a class="btn btn-ok" href="javascript:;">选好了</a><p class="txt-line f12">共：&yen;<i class="emb-red f18 total-price">0.00</i></p></div></div><!-- 购买商品 start --><div class="minCartGoods panel"><div class="panel-hd"><a class="pull-right clear-all-btn" href="javascript:;"><i class="btn-del-sm"></i>清除全部</a><p class="title">购物车</p></div><div class="minCart-bd"><div id="J-cart-list" class="goodsListWrap"><ul class="goodsList"><!-- 购买列表 -->' + _goodsListHtml + '<!-- 购买列表 end --></ul></div></div></div><!-- 购买商品 end --></div><!-- 悬浮购物车 end -->';
			
			$('body').append(_html);
			
			$minCart = $('#minCartPanel');
			$goodsList = $minCart.find('.goodsList');
			$totalNums = $minCart.find('.nums');
			$totalPrice = $minCart.find('.total-price');
			$emptyCart = $minCart.find('.empty-cart');
			$nonEmptyCart = $minCart.find('.non-empty-cart');
			
			this.$minCart = $minCart;
			
			if (_cartGoods) {
				var _totalPrice = 0, _count = 0, _num;

				$minCart.data('rel-shop-id', _cartGoods.shopId);
				$goodsList.find('[data-rel-cart]').minCart($minCart);
				
				$.each(_cartGoods.goodsList, function (i, goods) {
					_count   += goods.num;
					
					_num = goods.num;
					
					if (goods.isSendsale) {
						_num = _num / 2;
					}
					
					_totalPrice += Number(goods.price) * _num;
				})
				
				$totalNums.text(_count);
				$totalPrice.text(toFixed(_totalPrice));
				
				this.setMinCartStatus((_count ? 1 : 0));
				
			}
			
		},

        eventActive: function () {
            var _this = this;

            // 清空购物车
            $minCart.find('.clear-all-btn').on('click', function () {
				
				_this.emptyCart();
				
                return false;
            });

            // 展开购物车
            $minCart.find('.min-cart').on('click', function () {   
				_this.toggleCart();
                return false;
            })
			
			/*
            // 加、减处理
            $goodsList.on('click', '.minus', function () {
                // ajax 数据提交

				
                return false
            })

            // 加、减处理
            $goodsList.on('click', '.plus', function () {
                // ajax 数据提交

				console.log(11111);
                return false
            })
			*/
			
			// 点击遮罩层关闭购物车
			this.$mask.on('click', function (e) {
				_this.closeCart();
				
				e.stopPropagation();
			})
			
			// 选好了
			$minCart.find('.btn-ok').on('click', function (e) {
				e.preventDefault();
				var _shopid = $minCart.data('rel-shop-id'),
					_goodsArr = [];
					
				$minCart.find('.panel-chunk').each(function (i, el) {
					_goodsArr.push({"goodsId": String($(this).data('rel-cart')), "count": String($(this).find('.num').text())})
					
				})
				
				redirect_url('order-verify.html?shopId='+ _shopid +'&goodsArr=' + JSON.stringify(_goodsArr))
				e.stopPropagation();
				
			})
			
			// 联系商家
			$minCart.find('.J-contact-sell').on('click', function () {
				var phoneNum = $(this).attr('href') || 'javascript:;';
				
				_callKF(phoneNum);
				return false;
			})
			

        },
		
		initMask: function () {
			var $mask = $('.overlayer'),
				zIndex = parseInt($minCart.css('z-index'), 10) || 70;
				
			if (!$mask.length) {
				this.$mask = $mask = $('<div class="overlayer"/>');
				$minCart.before($mask);
			} else {
				this.$mask = $mask;
			}
			
			zIndex--;
			$mask.css('z-index', zIndex);
		},
		
		openCart: function () {
			$minCart.addClass('expanded');
			this.showMask();
		},
		
		closeCart: function () {
			$minCart.removeClass('expanded');
			this.hideMask();
		},
		
		toggleCart: function () {

			if ($minCart.hasClass('expanded')) {
				this.closeCart()
			} else {
				this.openCart()
			}
		},
		
		showMask: function () {
			this.$mask.show();
		},
		
		hideMask: function () {
			this.$mask.hide();
		},
		
		setMinCartStatus: function (status) {
			//不传参默认为1； 0 为空购物车，1反之

			status = status != null ? status : 1;
			
			switch (status) {
				case 0:
					$emptyCart.show();
					$nonEmptyCart.hide();
					this.initCartData();
					break;
				case 1:
					$emptyCart.hide();
					$nonEmptyCart.show();
					break;
				
			} 
			
		},
		
		// 恢复购物车初始数据
		initCartData: function () {
			$minCart.find('.goodsList').empty();
			$totalNums.text('0');
			$totalPrice.text('0');
			$minCart.data('rel-shop-id', null);
			
			// ps： 购物车及商品列表所有商品元素统一设置（比较耗性能）
			$('[data-rel-cart] .minus').addClass('hide');
			$('[data-rel-cart] .num').data('num', 0).html('0').addClass('hide');
			
			// 清空历史购物记录
			this.clearCartCache();
		},
		
		emptyCart: function () {
			this.initCartData();
			this.setMinCartStatus(0);
			
			this.closeCart();
		},
		clearCartCache: function () {
			// 缓存清空
			store.set('cartGoods', {
				'shopId': '',
				'goodsList': []
			})
		},
		goodsTmp: function (json) {
			return _goodsTmp(json);
		}

    };
	
	window.minCart = minCart;
});