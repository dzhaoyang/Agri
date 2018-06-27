$.namespace('dashboard.appuser');

dashboard.appuser.base = (function () {
	return {
		apiUrl: dashboard.utils.getApiUrl() + '/identity/appusers',
		pageUrl: dashboard.utils.getPageUrl() + '/identity/appusers'
	};
})();

dashboard.appuser.list = (function () {
    var _this = null;
    var usernameRef = null, nameRef = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var queryFormRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            usernameRef = $('#username');
            nameRef = $('#name');
            queryFormRef = $('#queryForm');

            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });

            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });

            $('#resetBtn').bind('click', function () {
                _this.resetQuery();
            });

            $('#refreshBtn').bind('click', function () {
            	_this.resetQuery();
                _this.refresh(true);
            });
            $("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });
            
            _this.refresh(true);
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            usernameRef.val('');
            nameRef.val('');
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                tbodyRef.empty();
                dashboard.utils.hide(tableRef);
                dashboard.utils.show(moreRef);
            }
            moreRef.empty();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: dashboard.appuser.base.apiUrl,
                dataType: 'json',
                data: queryFormRef.serialize() + '&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>无停车场用户.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $('<tr><td>' + (++i) +
                                '</td><td>' + 
                                '<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">' +
                                data.username + 
                                '</a>'  + 
                                '</td><td>' + data.name +
                                '</td><td>' + data.phoneNumber +
                                '</td><td>' + (_this.isAdmin(data.roles) ? '管理员' : '员工') +
                                '</td><td>' + (data.enabled ? '已启用' : '<span style="color:red;">已禁用<span>') + '</td></tr>');
                        tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            _this.showDetail($(detailBtn.target).attr('dataId'));
                        });
                        tbodyRef.append(tr);
                    }

                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'appusers/' + itemId
            });
        },
        isAdmin:function(roles){
        	var len = roles.length;
        	for(var i=0; i<len; i++){
        		if(roles[i].name == 'ROLE_PARKMANAGER'){
        			return true;
        		}
        	}
        	return false;
        }
    };
})();