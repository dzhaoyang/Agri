jQuery.validator.addMethod('username', function(value, element) {
	return this.optional(element)
			|| /^[a-zA-Z]{1}([a-zA-Z0-9]|[._-]){1,29}$/i.test(value);
}, 'Please enter a valid value.');

var MOBILE_PREFIXS = ['13','15','18'];
var PHONE_PREFIXS = MOBILE_PREFIXS.concat(['028']);                      
jQuery.validator.addMethod("mobile", function(value, element) {
	if(!value || value.length==0){
		return true;
	}
	var result = false;
	$.each(MOBILE_PREFIXS,function(index, ele){
		if(value.indexOf(ele)==0){
			result=true;
		}
	});
	return result;
}, "请输入有效的手机号码.");

jQuery.validator.addMethod("phone", function(value, element) {
	if(!value || value.length==0){
		return true;
	}
	var result = false;
	$.each(PHONE_PREFIXS,function(index, ele){
		if(value.indexOf(ele)==0){
			result=true;
		}
	});
	return result;
}, "请输入有效的电话号码（含手机）.");

jQuery.validator.addMethod('integer', function(value, element, param) {
    return (value == parseInt(value, 10));
}, '请输入正确的数字(不能含有小数点)');

jQuery.validator.addMethod('gtezero', function(value, element, param) {
    return (value >= 0);
}, '请输入正确的数字(不能小于0)');

jQuery.namespace = function() {
    var a=arguments, o=null, i, j, d;
    for (i=0; i<a.length; i=i+1) {
        d=a[i].split('.');
        o=window;
        for (j=0; j<d.length; j=j+1) {
            o[d[j]]=o[d[j]] || {};
            o=o[d[j]];
        }
    }
    return o;
};

$.namespace('dashboard');

dashboard.dialog = (function($) {
	return {
		open: function(title, message, callback) {
			bootbox.dialog({
				title: title,
				message: message,
				buttons: {
					CANCEL: {
						label: '取消',
						className: 'btn'
					},
					OK: {
						label: '确定',
						className: 'btn-primary',
						callback: function() {
							if (callback != undefined) {
								callback();
							}
						}
					}
				}
			});
		},
		alert : function(title, message, callback) {
			bootbox.dialog({
				title: title,
				message: message,
				buttons: {
					OK: {
						label: '确定',
						className: 'btn-primary',
						callback: function() {
							if (callback != undefined) {
								callback();
							}
						}
					}
				}
			});
		},
		confirm : function(title, message, callback) {
			bootbox.dialog({
				title: title,
				message: message,
				buttons: {
					CANCEL: {
						label: '取消',
						className: 'btn'
					},
					OK: {
						label: '确定',
						className: 'btn-primary',
						callback: function() {
							if (callback != undefined) {
								callback(true);
							}
						}
					}
				}
			});
		}
	};
})(jQuery);

dashboard.message = (function($) {
	return {
		info : function(message) {
			dashboard.dialog.alert('通知',message);
		},
		warning : function(message) {
			dashboard.dialog.alert('警告',message);
		},
		error : function(message) {
			dashboard.dialog.alert('出错',message);
		}
	};
})(jQuery);

dashboard.utils = (function($) {
	return {
		currentParams : function() {
			var params = window.location.search.substring(1).split('&');
			var paramObj = {};
			for ( var i = 0; i < params.length; i++) {
				var param = params[i];
				paramObj[param.substring(0, param.indexOf('='))] = param
						.substring(param.indexOf('=') + 1);
			}
			return paramObj;
		},
		active: function() {
			
		},
		ajax: function (options ) {
			var args = $.extend({},options,
				{
					success : function(json) {
						if (options.block) {
							dashboard.utils.unblock(options.block);
						}
			            if (typeof options.success === 'function') {
			            	options.success(json);
			            }
					},
					error:function(){
						if (options.block) {
							dashboard.utils.unblock(options.block);
						}
						dashboard.message.error('System busy, please try later.');
					}
				});
			if (options.block) {
				dashboard.utils.block(options.block);
			}			
			return $.ajax(args);
		},
		load: function () {
			return $.load(arguments);
		},
		get : function() {
			return $.get(arguments);
		},
		post: function() {
			return $.post(arguments);
		},
		
		block: function(element, message) {
			/**
			 * @param element selector to be blocked, 'body' if null
			 * @param message message to show on the block div
			 */
			if (element == undefined) {
				element = 'body';
			}
			if (message == undefined) {
				message = 'Loading';
			}
			var options;
			var text = message;
			if ($(element).width() < 100) {
				text = '';
			}
			var widthClass = ' class="col-md-6 col-md-offset-3"';
			if ($(element).width() < 200) {
				widthClass = ' class="col-md-10 col-md-offset-1"';
			}
			var paddingHeight = (($(element).outerHeight(false) - 20)/2);
			message = '<div class="row" style="padding-top:' + paddingHeight + 'px"><div' + widthClass + '><div class="progress progress-striped active" style="margin-bottom:' + paddingHeight + 'px"><div style="width:100%" class="progress-bar">' + text + '</div></div></div></div>';
			options = {
				message : message,
				css : {
					width : '100%',
					top : 0,
					left: 0,
					height : '100%',
					border : 'none'
				}
			};
			$(element).block(options);
		},
		unblock : function(element) {
			$(element).unblock();
		},
		
		show : function(element) {
			$(element).each(function() {
				$(this).removeClass('hide');
				$(this).removeClass('hidden');
				$(this).removeClass('.sr-only');
				if ($(this).is(':hidden')) {
					$(this).show();
				}
			});
		},
		hide : function(element) {
			$(element).each(function() {
				$(this).addClass('hidden');
				//if ($(this).is(':visible')) {
				//	$(this).hide();
				//}
			});
		},
        getApiUrl : function(){
            return '/api/v1/dashboard';
        },
        getPageUrl : function(){
        	return '/dashboard';
        }
	};
	
})(jQuery);
