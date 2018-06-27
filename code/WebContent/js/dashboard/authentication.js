$.namespace('dashboard.authentication');

dashboard.authentication.base = (function () {
	return {
		apiUrl: dashboard.utils.getApiUrl() + '/authentications',
		pageUrl: dashboard.utils.getPageUrl() + '/authentications'
	};
})();

dashboard.authentication.list = (function () {
    var _this = null;
    var usernameRef = null, loginAtRef = null, authTypeRef = null, ipAddressRef = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            usernameRef = $('#username');
            loginAtRef = $('#loginAt');
            authTypeRef = $('#authType');
            ipAddressRef = $("#ipAddress");

            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });

            $("#loginAt").datepicker({
                dateFormat: 'yy-mm-dd'
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
            authTypeRef.val('');
            loginAtRef.val('');
            ipAddressRef.val('');
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
                url: dashboard.authentication.base.apiUrl,
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit,
                    username: usernameRef.val(),
                    authType: authTypeRef.val(),
                    loginAt: loginAtRef.val(),
                    ipAddress: ipAddressRef.val()
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>无登录日志.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $('<tr><td>' + (++i) +
                                '</td><td><a target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/users/' + data.user.id + '/profile/authHistory">' + data.user.username + '</a>' +
                                '</td><td>' + data.loginAt +
                                '</td><td>' + data.authType +
                                '</td><td>' + data.ipAddress +
                                '</td><td>' + data.browser +
                                '</td><td>' + data.osFamily + '</td></tr>');
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
        }
    };
})();

dashboard.authentication.clean = (function () {
	var _this = null;
    var detailValidator = null;
	var beginDateRef = null, endDateRef = null;
	return {
		init: function(){
			_this = this;
			beginDateRef = $('#beginDate');
			endDateRef = $('#endDate');            
			
			beginDateRef.datepicker({
                dateFormat: 'yy-mm-dd'
            });

			endDateRef.datepicker({
                dateFormat: 'yy-mm-dd'
            });
			
            detailValidator = $("#detailForm").validate({
                rules: {
                	beginDate: {required: true},
                	endDate: {required: true}
                }
            });
			
			$('#cleanBtn').bind('click', function(){
				_this.clean();
			});
		},
		clean: function() {
            if (!detailValidator.form()) {
                return;
            }

            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'POST',
                url: dashboard.authentication.base.apiUrl + '/clean',
                dataType: 'json',
                data: $("#detailForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('清理成功!');
                }
            });
		}
	};
})();
