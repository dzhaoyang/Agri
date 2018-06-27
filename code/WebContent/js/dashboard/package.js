$.namespace('dashboard.package');
dashboard.package.base = (function () {
	return {
		apiUrl: dashboard.utils.getApiUrl() + '/system/packages',
		pageUrl: dashboard.utils.getPageUrl() + '/system/packages'
	};
})();

dashboard.package.list = (function () {
    var _this = null;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');

            $('#refreshBtn').bind('click', function () {
                _this.refresh();
            });
            
            $('#uploadByBackstageBtn').bind('click', function () {
                _this.uploadByBackstage();
            });

            this.refresh();
        },
        refresh: function () {
            moreRef.empty();
            dashboard.utils.show(moreRef);
            dashboard.utils.hide(tableRef);
            tbodyRef.empty();
            _this.showMore();
        },
        showMore: function () {
            dashboard.utils.ajax({
                block: moreRef,
                type: 'GET',
                url: dashboard.package.base.apiUrl,
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无应用版本.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    dashboard.utils.hide(moreRef);
                    var i = 0;
                    for (var p in json.data) {
                        var data = json.data[p];
                        var tr = $('<tr title="' + data.description + '"' +
                            '><td>' + (++i) +
                            '</td><td>' + data.name +
                            '</td><td>' + data.packageName +
                            '</td><td>' + data.versionName + ' / ' + data.versionCode + 
                            '</td><td>' + data.modifiedAt + 
                            '</td><td>' + 
                            '<span class="operation btn-group" id="operation' + data.id + '">' + 
                            '<a id="downloadBtn" class="btn btn-info btn-xs" target="_blank" href="/media/android/' + data.apkFileName + '">下载</a>' +
                            '<a id="deleteBtn" class="btn btn-danger btn-xs" href="javascript:void(0);" dataId="' + data.id + '">删除</a>' +
                            '</span>' +
                            '</td></tr>');
                        tr.delegate('#deleteBtn', 'click', function (btn) {
                            _this.remove($(btn.target).attr('dataId'));
                        });
                        tbodyRef.append(tr);
                    }
                }
            });
        },
        remove: function(id) {
            dashboard.dialog.confirm('确认',
                '是否删除选中版本?', function (confirmed) {
                    if (confirmed) {
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'DELETE',
                            url: dashboard.package.base.apiUrl + '/' + id,
                            dataType: 'json',
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                _this.refresh();
                            }
                        });
                    }
                });
        },
        uploadByBackstage: function(id) {
            dashboard.dialog.confirm('确认',
                '是否从后台上传APK?', function (confirmed) {
                    if (confirmed) {
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'POST',
                            url: '/api/v1/dashboard/system/packages/uploadByBackstage',
                            dataType: 'json',
                            success: function (json) {
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

dashboard.package.edit = (function () {
    var _this = null;
    var idRef = null;
    var detailValidator = null;
    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            
            $('#saveBtn').bind('click', function() {
            	_this.save();
            });
            
            detailValidator = $("#detailForm").validate({
                rules: {
                    "name": {
                        required: true
                    },
                    "file": {
                        required: true
                    }
                }
            });
        },
        save: function() {
            if (!detailValidator.form()) {
                return;
            }

            var saveUrl = dashboard.package.base.apiUrl;
            var saveMethod = 'POST';
            if (idRef.val()) {
            	saveUrl += '/' + $('#id').val();
            	saveUrl = 'PUT';
            }
        	dashboard.utils.block('.form-actions');            
        	$('#detailForm').ajaxSubmit({  
        		type: saveMethod,
        		url: saveUrl,
        		dataType: 'json',
        		data: $("#detailForm").serialize(),
        		success: function(json) {
    				dashboard.utils.unblock('.form-actions');
        			if (!json.succeed) {
        				dashboard.message.warning(json.message);
        				return;
        			}
                    dashboard.message.info('保存成功!');
                    if (!$("#id").val()) {
                        window.location.href = dashboard.package.base.pageUrl;
                    }
        		},
        		error:function(message){
        			dashboard.utils.unblock('.form-actions');
        			dashboard.message.error('System busy, please try later.');
        		}
        	});
        }
    };
})();
