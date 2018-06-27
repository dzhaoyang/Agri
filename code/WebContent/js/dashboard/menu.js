dashboard.menu = (function($) {
	return {
		init : function() {
			var _this = this;
			_this.refresh();
			$("#menuBar li").each(function(i,item) {
				var dataUrl = $(item).attr("data-url");
				if ((dataUrl != null) && (window.location.href.indexOf(dataUrl) > 0)) {
					$(item).addClass("active");
				}
			});
		},
		refresh: function () {
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/menu/getTagMenu',
                dataType: 'json',
                data: {},
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }
                    if (json.data == null) {
                        return;
                    }

                    var menuBar = $('#menuBar');
                    var topMenus = json.data.topMenu;
                    var subMenuMap = json.data.subMenu;
                    
                    var lis = '';
                    for (var p in topMenus) {
                        var topMenu = topMenus[p];
                        if(topMenu.id==null||topMenu.id==''){
                    		continue;
                    	}
                        
                        lis += '<li class="subtitle" style="padding-left: 10px;">';
                        lis += '<span class="glyphicon glyphicon-align-justify"></span><strong>'+topMenu.name+'</strong>';
                        lis += '</li>';
                    	
                        var subMenus = subMenuMap[topMenu.id];
                        for(var k in subMenus){
                        	var subMenu = subMenus[k];
                        	if(subMenu.id==null||subMenu.id==''){
                        		continue;
                        	}
                        	lis += '<li style="margin-left: -10px;" data-url="'+subMenu.url+'">';
                        	lis += '<a href="'+subMenu.url+'"><span>'+subMenu.name+'</span></a>';
                        	lis += '</li>';
                        }
                    }
                    menuBar.html(lis);
                }
            });
        }
	};
})(jQuery);

$(function() {
	dashboard.menu.init();
});