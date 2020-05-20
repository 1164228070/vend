(function ($, win, doc) {
    var CityPicker = function (data,el,level,options,callback) {
    	this.data = data;                          //json数据
        this.el = $(el);
        this.level = level ? level : 2;            //需要显示多少级
        this.options = options;
        this.callback = callback;                  //js回调函数
        this.elType = this.el.is('input');         //是否input
        this.init();  
    };

    var pickerPrototype = CityPicker.prototype;

    pickerPrototype.init = function () {
        this.initEvent();
        this.preventPopKeyboard();
    };

    pickerPrototype.initEvent = function () {
    	this.el.on("click", function (e) {
    		var pickerBox = $(".picker-box");
    		pickerBox.remove();
    		
    		//TODO 优化
			//if (pickerBox[0]) {
			//	pickerBox.show();
			//} else {
			//	this.create();
			//}
    		this.create();
    	}.bind(this));
    };
    
    pickerPrototype.preventPopKeyboard = function () {
        if (this.elType) {
            this.el.prop("readonly", true);
        }
    };

    //创建
    pickerPrototype.create = function () {
    	//创建第一级目录
        this.createFirstBox();             
        this.createFirstList();            
        this.bindFirstClick();
        
        //创建拼音导航条
        this.createNavBar();
        this.navEvent();
    };

    //创建第一级列表面板
    pickerPrototype.createFirstBox = function () {
    	$("div.picker-box").remove();   
        var proBox = "<div class='picker-box'></div>";
        $("body").append(proBox);
    };

    //创建第一级列表数据
    pickerPrototype.createFirstList = function () {
        var data = this.data;
        var firstBox;
        var dl = "";
        for (var letterKey in data) {
            var val = data[letterKey];
            if (data.hasOwnProperty(letterKey)) {
                var dt = "<dt id='" + letterKey + "'>" + letterKey + "</dt>";
                var dd = "";
                for (var firstKey in val) {
                    if (val.hasOwnProperty(firstKey)) {
                        dd += "<dd data-index=" + letterKey +'-'+firstKey+">" + firstKey + "</dd>";
                    }
                }
                dl += "<dl>" + dt + dd + "</dl>";
            }
        }
        firstBox = "<section class='pro-picker'>" + dl + "</section>";
        $(".picker-box").append(firstBox);
    };
    
    //绑定事件
    pickerPrototype.bindFirstClick = function () {
        var that = this;
        $(".pro-picker").on("click", function (e) {
            var target = e.target;
            if ($(target).is("dd")) {
                var indexKey = $(target).data("index");
                if(that.level==1){
                     var label = $(target).html();
            		 
            		 var jsonKey;
            		 var indexKeys = $(target).data("index").split("-");
        	    	 for(var index in indexKeys){
        	    	 	if(jsonKey){
        	    	 		jsonKey = jsonKey[indexKeys[index]];  
        	    	 	}else{
        	    	 		jsonKey = that.data[indexKeys[index]];
        	    	 	}  
        	    	 } 
        	    	 
        	    	 if(that.callback && $.isFunction(that.callback)){
        	    		 that.callback(that.el,label,jsonKey);
        	    	 }
          	    	 
            		 $(".picker-box").hide();
            		 $(".pro-picker").show();  
                	return false;
                }else{
                	that.createChildList(indexKey,2);
                	$(this).hide();
                }
            }
        });
    };
    
    //创建子集列表
    pickerPrototype.createChildList = function (indexKey,level) {
    	var indexKeys = indexKey.split("-");
    	
    	var childData;
    	for(var index in indexKeys){
    		if(childData){
    			childData = childData[indexKeys[index]];
    		}else{
      			childData = this.data[indexKeys[index]];
    		}
    	}  
    	
    	var ul, li = "";
    	$.each(childData,function(key,value){
    		li += '<li data-index="'+indexKey+'-'+key+'" data-level='+level+'>' + key + '</li>';  
    	})
        ul = "<ul class='city-picker'>" + li + "</ul>";
        $(".picker-box").append(ul);
        
        this.childClick();
    };

    pickerPrototype.childClick = function () {
        var that = this;
        $(".city-picker").on("click", function (e) {
            var target = e.target;
            if ($(target).is("li")) {
            	
            	 var level = $(target).data("level");
            	 if(level<that.level){  
            		 //继续创建
            		 var indexKey = $(target).data("index");
            		 
            		 that.createChildList(indexKey,level+1);   
            	 }else{
            		 var label = $(target).html();
            		 
            		 var jsonKey;
            		 var indexKeys = $(target).data("index").split("-");
        	    	 for(var index in indexKeys){
        	    	 	if(jsonKey){
        	    	 		jsonKey = jsonKey[indexKeys[index]];  
        	    	 	}else{
        	    	 		jsonKey = that.data[indexKeys[index]];
        	    	 	}  
        	    	 } 
        	    	 
        	    	 if(that.callback && $.isFunction(that.callback)){
        	    		 that.callback(that.el,label,jsonKey);
        	    	 }else{      
        	    		 if (that.elType) {  
        	    			 that.el.data('key',jsonKey);
        	    			 that.el.val(label);
        	    		 } else {     
        	    			 that.el.html(label);   
        	    			 that.el.data("key",jsonKey);
        	    		 }   
        	    	 }
        	    	 
            		 $(".picker-box").hide();
            		 $(".pro-picker").show();  
            		 
            		 $(".picker-box").find(".city-picker").remove();
            		 
            	 }   
                $(this).hide();
            }
        });
    };

    pickerPrototype.createNavBar = function () {
        var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var arr = str.split("");
        var a = "";
        arr.forEach(function (item, i) {
            a += '<a href="#' + item + '">' + item + '</a>';
        });

        var div = '<div class="navbar">' + a + '</div>';

        $(".picker-box").append(div);
    };

    pickerPrototype.navEvent = function () {
        var that = this;
        var navBar = $(".navbar");
        var width = navBar.find("a").width();
        var height = navBar.find("a").height();
        navBar.on("touchstart", function (e) {
            $(this).addClass("active");
            that.createLetterPrompt($(e.target).html());
        });

        navBar.on("touchmove", function (e) {
            e.preventDefault();
            var touch = e.originalEvent.touches[0];
            var pos = {"x": touch.pageX, "y": touch.pageY};
            var x = pos.x, y = pos.y;
            $(this).find("a").each(function (i, item) {
                var offset = $(item).offset();
                var left = offset.left, top = offset.top;
                if (x > left && x < (left + width) && y > top && y < (top + height)) {
                    location.href = item.href;
                    that.changeLetter($(item).html());
                }
            });
        });

        navBar.on("touchend", function () {
            $(this).removeClass("active");
            $(".prompt").hide();
        })
    };

    pickerPrototype.createLetterPrompt = function (letter) {
        var prompt = $(".prompt");
        if (prompt[0]) {
            prompt.show();
        } else {
            var span = "<span class='prompt'>" + letter + "</span>";
            $(".picker-box").append(span);
        }
    };

    pickerPrototype.changeLetter = function (letter) {
        var prompt = $(".prompt");  
        prompt.html(letter);
    };
    $.fn.CityPicker = function (data,level,options,callback) {
        return new CityPicker(data,this,level,options,callback);
    }
}(window.jQuery, window, document));