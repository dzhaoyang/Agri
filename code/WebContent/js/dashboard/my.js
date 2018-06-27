$.namespace('dashboard.my.parking');
dashboard.my.parking.list = (function() {
	var _this = null;
	var filterNameRef = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	return {
		init : function() {
			_this = this;
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
			filterNameRef = $('#filterName');
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

			moreRef.show();
			dashboard.utils.ajax({
						block : '#showMoreBar',
						type : 'GET',
						url : '/api/v1/dashboard/my/parkings',
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
							if (json.data == null || json.data.length == 0) {
								moreRef.html('<div class="well text-center"><em>无停车场.</em></div>');
								return;
							}

							dashboard.utils.show(tableRef);
							for ( var p in json.data.content) {
								var data = json.data.content[p];
								var tr = $('<tr><td>'
										+ (++i)
										+ '</td><td>'
										+ '<a href="users/' + data.id + '">'
										+ data.name
										+ '</a>'
										+ '</td><td>'
										+ (data.address ? data.address : '')
										+ '</td><td>'
										+ (data.createAt ? data.createAt : '')
										+ '</td><td>'
										+ '<span class="operation btn-group" id="operation' + data.id + '">'
										+ '<a href="users/' + data.id + '/edit" class="btn btn-info btn-xs">编辑</a>'
										+ '<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>'
										+ '</span>'
										+ '</td></tr>');
								tr.delegate('#deleteBtn', 'click', function(delBtn) {
									_this.removeById($(delBtn.target).attr('dataId'));
								});
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
		},
		removeById : function(id) {
			dashboard.dialog.confirm('确认',
				'是否删除选中停车场?', function(confirmed) {
				if (confirmed) {
					var deleteUserEndpoint = '/api/v1/dashboard/my/parkings/'+id;
					dashboard.utils.ajax({
						block : '#operation' + id,
						type : 'DELETE',
						url : deleteUserEndpoint,
						dataType : 'json',
						data : {},
						success : function(json) {
							if (!json.succeed) {
								dashboard.message.warning(json.message);
								return;
							}
							_this.refresh();
						}
					});
				}
			});
		}
	};
})();

dashboard.my.parking.edit = (function() {
	var _this = null;
	var detailValidator = null;
	var idRef;
	var saveEndpoint = '/api/v1/dashboard/my/parkings';
	return {
		init : function() {
			_this = this;
			$('#saveBtn').bind('click', function() {
				_this.save();
			});
			idRef = $('#id');
			_this.initializeCreate();
		},
		initializeCreate : function() {
			detailValidator = $("#userForm").validate({
				rules : {
					"username" : {
						required : true
					},
					"firstName" : {
						required : true
					},
					"name" : {
						required : true
					},
					"email" : {
						email : true
					},
					"phoneNumber": {
						required : true
					} 
				}
			});
			 
		},
		save : function(){
			if (!detailValidator.form()) {
				return;
			}
			
			var isUpdate= (idRef.val() != null && idRef.val() != '');
			if(isUpdate) {
				saveEndpoint += '/' + $('#id').val();
			}
			dashboard.utils.ajax({
				block : '#saveBtn',
				type : isUpdate ? 'PUT':'POST',
				url : saveEndpoint,
				dataType : 'json',
				data : $("#form").serialize(),
				success : function(json) {
					if (!json.succeed) {
						dashboard.message.warning(json.message);
						return;
					}
					dashboard.message.info('保存成功!');
					if(!$("#id").val()) {
						window.location.href = json.data.id+'/edit';
					}
				}
			});
		}
	};
})();