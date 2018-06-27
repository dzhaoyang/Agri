$.namespace('dashboard.role');
dashboard.role.list = (function() {
	var _this = null;
	var tbodyRef = null, tableRef = null, moreRef = null;
	return {
		init : function() {
			_this = this;
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
			$('#refreshBtn').bind('click', function() {
				_this.refresh();
			});
			this.refresh();
		},
		refresh : function() {
			_this.showMore();
		},
		showMore : function(reset) {
			tbodyRef.empty();
			dashboard.utils.ajax({
						type : 'GET',
						url : '/api/v1/dashboard/identity/roles',
						dataType : 'json',
						data : {},
						success : function(json) {
							if (!json.succeed) {
								dashboard.utils.warning(json.message);
								return;
							}

							if (json.data == null || json.data.length == 0) {
								tableRef.hide();
								moreRef.html('<div class="well text-center"><em>还没有定义角色.</em></div>');
								moreRef.show();
								return;
							}

							tableRef.show();
							moreRef.hide();
							var i = 0;
							for ( var p in json.data) {
								var desc = json.data[p].description == null ? '' : json.data[p].description;
								var tr = $('<tr><td>'
										+ (++i)
										+ '</td><td>'
										+ json.data[p].name
										+ '</td><td>'
										+ desc
										+ '</td><td><span id="operation'
										+ json.data[p].id
										+ '" class="operation btn-group"><a href="/dashboard/identity/roles/'
										+ json.data[p].id
										+ '/members" class="btn btn-primary btn-xs">成员</a></span></td></tr>');
								tbodyRef.append(tr);
							}
						}
					});
		} 
	};
})();

dashboard.role.members = (function() {
	var _this = null;
	var filterNameRef = null;
	var i = 0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var roleIdRef = null;
	return {
		init : function() {
			_this = this;
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
			filterNameRef = $('#filterName');
			roleIdRef = $('#roleId');
			
			$('#refreshBtn').bind('click', function() {
				filterNameRef.val('');
				_this.refresh();
			});
			$('#searchBtn').bind('click', function() {
				_this.refresh();
			}); 
			this.refresh();
		},
		refresh : function() {
			_this.showMore(true);
		},
		showMore : function(reset) {
			if (reset) {
				i = 0;
				start = 0;
				limit = 10;
				tbodyRef.empty();
				dashboard.utils.hide(tableRef);
				dashboard.utils.show(moreRef);
			}

			moreRef.empty();
			dashboard.utils.ajax({
						block : '#showMoreBar',
						type : 'GET',
						url : '/api/v1/dashboard/identity/roles/'+roleIdRef.val()+'/members',
						dataType : 'json',
						data : {
							name : filterNameRef.val(),
							start: start,
					        limit: limit
						},
						success : function(json) {
							if (!json.succeed) {
								dashboard.utils.warning(json.message);
								return;
							}

							start++;
							$('#recordSize').html('('+json.data.totalElements+')');
							if (json.data == null || json.data.totalElements == 0) {
								moreRef.html('<div class="well text-center"><em>该角色无成员.</em></div>');
								return;
							}

							dashboard.utils.show(tableRef);
							for ( var p in json.data.content) {
								var data = json.data.content[p];
								var tr = $('<tr><td>'
										+ (++i)
										+ '</td><td>'
										+ data.username
										+ '</td><td>'
										+ data.name
										+ '</td><td>'
										+ (data.email?data.email:'')
										+ '</td><td>'
										+ (data.phoneNumber?data.phoneNumber:'')
										+ '</td><td>'
										+ (data.enabled?'启用':'禁用')
										+ '</td></tr>');
								tbodyRef.append(tr);
							}
							

							if(i < json.data.totalElements) {
								moreRef.empty();
								var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
								more.delegate('#showMoreBtn','click', function() {
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