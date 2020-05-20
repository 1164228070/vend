var minCart = {
        $minCart: $(),

        init: function (phoneNum) {
			if ($('#minCartPanel').length) return;
			if (phoneNum !== undefined) {
				this.phoneNum = phoneNum;
			}
            this.setup();
        },
        
        setup: function () {
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