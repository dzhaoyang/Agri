$.namespace('dashboard._switch');
dashboard._switch.list = (function () {
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            $('#refreshBtn').bind('click', function () {
                _this.refresh();
            });

            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });

            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });

            $('#resetBtn').bind('click', function () {
                _this.resetQuery();
            });

            $("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });

            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            limit = 10;
            $('#uuid').val('');
            $('#name').val('');
            $('#greenHouseId').val('');
            $('#switchType').val('');
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'switch/edit/' + itemId
            });
        },
        showMore: function (reset) {
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
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/switch/list',
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit,
                    uuid: $('#uuid').val(),
                    name: $('#name').val(),
                    greenHouseId: $('#greenHouseId').val(),
                    switchType: $('#switchType').val()
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $('<tr><td style="text-align: center;">'
                            + (++i)
                            + '</td><td style="text-align: center;">'
                            //+ '<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">'
                            + (data.uuid ? data.uuid : '')
                            //+ '</a>'
                            + '</td><td style="text-align: center;">'
                            + (data.name ? data.name : '')
                            + '</td><td style="text-align: center;">'
                            + data.typestr
                            + '</td><td style="text-align: center;">'
                            + (data.greenHouse?data.greenHouse.name:'')
                            + '</td><td style="text-align: center;">'
                            + (data.installDate ? data.installDate : '')
                            + '</td><td style="text-align: center;">'
                            + '<span class="operation btn-group" id="operation' + data.id + '">'
                            + '<a href="/switch/edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>'
                            + '<a id="deleteBtn" href="javascript:void(0);"  style="margin-left: 10px;" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>'
                            + '</span>'
                            + '</td></tr>');
                        tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });

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
        removeById: function (id) {
            dashboard.dialog.confirm('确认',
                '是否删除选中开关?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = '/api/switch/delete/' + id;
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'DELETE',
                            url: deleteUserEndpoint,
                            dataType: 'json',
                            data: {},
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

//编辑页面
dashboard._switch.edit = (function () {
    var _this = null;
    var baseValidator = null;
    var detailValidator = null;
    var idRef = null;
    var editable = false;

    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            
            $("#installDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
            $('#backBtn').bind('click', function () {
                _this.back();
            });

            $('#saveBtn').bind('click', function () {
                _this.save();
            });

            _this.initializeCreate();
            if(idRef.val()) {
            	
            }
        },
        initializeCreate: function () {
        	$('#savebtnDiv').width($('#infoUl').width()-150);
        	/*$('#backBtn').bind('click', function () {
        		history.go(-1);
            });*/
        	baseValidator = $("#baseForm").validate({
                rules: {
                	name: {required: true},
                	uuid: {required: true},
                	"greenHouse\\.Id": {required: true},
                	coordinateX: {required: true,digits:true,min:0},
                	coordinateY: {required: true,digits:true,min:0},
                	installDate:{required: true},
                	rWId:{required: true},
                	//electricRelayId:{required: true},
                	position:{required: true}
                },
                messages: {
                	name: {required: "请输入值"},
                	uuid: {required: "请输入值"},
                	"greenHouse\\.Id":{required: "请输入值"},
                	coordinateX: {required: "请输入值",digits:"请输入正整数",min:"不能小于0"},
                	coordinateY: {required: "请输入值",digits:"请输入正整数",min:"不能小于0"},
                	installDate: {required: "请输入值"},
                	rWId:{required: "请输入值"},
                	//electricRelayId:{required: "请输入值"},
                	position:{required: "请输入值"}
                }
            });
        },
        
        back: function() {
        	window.location.href = '/switch/list';
        },
        save: function () {
        	var canCommit = true;
        	if(!baseValidator.form()){
        		canCommit = canCommit & false;
        		 //return;
        	}
            /*if (idRef.val() != null && idRef.val() != '' && !detailValidator.form()) {
            	canCommit = canCommit & false;
            }*/
            
            if(!canCommit){
            	return;
            }

            var saveUrl = '/api/switch/save';
            var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveUrl = '/api/switch/save';
            }
            
            
            var serialize = $('#baseForm').serialize();
            /*if(idRef.val() != null && idRef.val() != ''){
            	serialize = serialize +'&'+$('#detailForm').serializeArray();
            }*/
            //alert(serialize);
            dashboard.utils.ajax({
                block: '.form-actions',
                type: isUpdate ? 'POST' : 'POST',
                url: saveUrl,
                dataType: 'json',
                data: serialize,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                    window.location.href = '/switch/edit/' + json.data.id;
                }
            });
        }
    };
})();

dashboard._switch.SwitchType = (function () {
    return {
    	getName: function (type) {
    		var name = "";
    		if(type==2){
    			name = "卷帘";
    		}else if(type==1){
    			name = "喷灌头";
    		}else if(type==3){
    			name = "大风机";
    		}else if(type==4){
    			name = "环流风扇";
    		}else if(type==5){
    			name = "内遮阳展开";
    		}else if(type==6){
    			name = "内遮阳收拢";
    		}else if(type==7){
    			name = "外遮阳展开";
    		}else if(type==8){
    			name = "外遮阳收拢";
    		}else if(type==9){
    			name = "顶卷展开";
    		}else if(type==10){
    			name = "顶卷收拢";
    		}else if(type==11){
    			name = "卷帘";
    		}
    		return name;
        },
        getStatusName: function(type,status){
        	var statusName = "";
    		if(type==1){
    			if(status==1){
    				statusName = "打开";
    			}else if(status==2){
    				statusName = "打开中";
    			}else if(status==0){
    				statusName = "关闭";
    			}else if(status==3){
    				statusName = "关闭中";
    			}
    		}else if(type==2){
    			if(status==1){
    				statusName = "打开";
    			}else if(status==2){
    				statusName = "打开中";
    			}else if(status==0){
    				statusName = "关闭";
    			}else if(status==3){
    				statusName = "关闭中";
    			}
    		}else if(type==3){
    			if(status==1){
    				statusName = "打开";
    			}else if(status==2){
    				statusName = "打开中";
    			}else if(status==0){
    				statusName = "关闭";
    			}else if(status==3){
    				statusName = "关闭中";
    			}
    		}else if(type==4){
    			if(status==1){
    				statusName = "打开";
    			}else if(status==2){
    				statusName = "打开中";
    			}else if(status==0){
    				statusName = "关闭";
    			}else if(status==3){
    				statusName = "关闭中";
    			}
    		}else if(type==5){
    			if(status==1){
    				statusName = "展开";
    			}else if(status==2){
    				statusName = "展开中";
    			}else if(status==0){
    				statusName = "停止展开";
    			}else if(status==3){
    				statusName = "停止展开中";
    			}
    		}else if(type==6){
    			if(status==1){
    				statusName = "收拢";
    			}else if(status==2){
    				statusName = "收拢中";
    			}else if(status==0){
    				statusName = "停止收拢";
    			}else if(status==3){
    				statusName = "停止收拢中";
    			}
    		}else if(type==7){
    			if(status==1){
    				statusName = "展开";
    			}else if(status==2){
    				statusName = "展开中";
    			}else if(status==0){
    				statusName = "停止展开";
    			}else if(status==3){
    				statusName = "停止展开中";
    			}
    		}else if(type==8){
    			if(status==1){
    				statusName = "收拢";
    			}else if(status==2){
    				statusName = "收拢中";
    			}else if(status==0){
    				statusName = "停止收拢";
    			}else if(status==3){
    				statusName = "停止收拢中";
    			}
    		}else if(type==9){
    			if(status==1){
    				statusName = "展开";
    			}else if(status==2){
    				statusName = "展开中";
    			}else if(status==0){
    				statusName = "停止展开";
    			}else if(status==3){
    				statusName = "停止展开中";
    			}
    		}else if(type==10){
    			if(status==1){
    				statusName = "收拢";
    			}else if(status==2){
    				statusName = "收拢中";
    			}else if(status==0){
    				statusName = "停止收拢";
    			}else if(status==3){
    				statusName = "停止收拢中";
    			}
    		}else if(type==11){
    			if(status==1){
    				statusName = "打开";
    			}else if(status==2){
    				statusName = "打开中";
    			}else if(status==0){
    				statusName = "关闭";
    			}else if(status==3){
    				statusName = "关闭中";
    			}
    		}
    		return statusName;
        }
    };
})();