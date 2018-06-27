$.namespace('dashboard.privatebits');
//私家车位查询列表
dashboard.privatebits.list = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            filterRef = $('#filter');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
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
            $('#parkingName').dblclick(function () {
                _this.selectParking();
            });
            $('#carOwnerName').bind('dblclick',function () {
                _this.selectCarOwner();
            });
            _this.refresh();
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote:  '/dashboard/privatebits/view/' + itemId
            });
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
            formRef[0].reset();
            $("#parkingId").val('');
            $("#carOwnerId").val('');
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
                url: '/api/v1/dashboard/privatebits/privatebits/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无私家车位.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var statusLabel = '';
                        if(data.status == 1){
                        	statusLabel = '启用';
                        	statusLabel += (data.openStatus==1 ? ' | 开放' : ' | 关闭');
                            statusLabel += (data.parkStatus==1 ? ' | 有车' : ' | 无车');
                        }else{
                        	statusLabel = '禁用';
                        }
                        

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++i);
                        tr.push('</td><td>');
                        //tr.push('<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">');
                        tr.push(data.parkNo);
                        //tr.push('</a>');
                        tr.push('</td><td>');
                        tr.push(data.label ? data.label.code : '');
                        tr.push('</td><td>');
                        tr.push(data.parking ? data.parking.name : '');
                        tr.push('</td><td>');
                        tr.push(data.carOwner ? data.carOwner.name : '');
                        tr.push('</td><td>');
                        tr.push(statusLabel);
                        tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a href="edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>');
                        /*if(data.openStatus!=1){
                        	tr.push('<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>');
                        }*/
                        tr.push('</span>');
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));

                        /*$tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });*/
                        $tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            _this.showDetail($(detailBtn.target).attr('dataId'));
                        });

                        tbodyRef.append($tr);
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
                '是否删除选中私家车位?', function (confirmed) {
                    if (confirmed) {
                        var durl = '/api/v1/dashboard/privatebits/delete/' + id;
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'DELETE',
                            url: durl,
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
        },
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parkingName'));
        },
        //弹出车主选择框
        selectCarOwner:function() {
        	dashboard.carowner.select.selectCarOwner($('#selectCarOwner'),$('#carOwnerId'),$('#carOwnerName'));
        },
    };
})();
//私家车位编辑页面
dashboard.privatebits.edit = (function () {
    var _this = null;
    var detailValidator = null;
    var idRef = null;
    var editable = false;
    
    /*var moreEmployeeRef = null, employeeTableRef = null, employeeTbodyRef = null;
    var employeeValidator = null;*/
    
    var moreValidTimeRef = null, validTimeTableRef = null, validTimeTbodyRef = null;
    var validTimeValidator = null;


    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            
            moreValidTimeRef = $('#moreValidTimeBar');
            validTimeTableRef = $('#validTimeContainer');
            validTimeTbodyRef = $('#validTimeContainer tbody');
            
            /*$("#installDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });*/
            
            $('#backBtn').bind('click', function () {
                _this.back();
            });

           /* $('#locateBtn').bind('click', function () {
                _this.locateGeo();
            });*/

            $('#saveBtn').bind('click', function () {
                _this.save();
            });

           /* $('#submitBtn').bind('click', function () {
                _this.submitParking();
            });*/

            /*$('#processBtn').bind('click', function () {
                _this.processParking();
            });*/

           /* $('#enableBtn').bind('click', function () {
                _this.enableParking();
            });*/

            /*$('#disableBtn').bind('click', function () {
                _this.disableParking();
            });*/
            /*$('#label.labelCode').bind('',function () {
                _this.selectLabel();
            });*/
           /* $('#parking.name').bind('dblclick',function () {
            	alert('bind dblclick');
                _this.selectParking();
            });*/
            $('#parking\\.name').dblclick(function () {
            	if(!idRef.val()) {//只有新建时才能设置停车场
            		_this.selectParking();
            	}
            });
            $('#carOwner\\.name').bind('dblclick',function () {
            	if(!idRef.val()) {//只有新建时才能设置业主
            		_this.selectCarOwner();
            	}
            });
            
            $('#deleteBtn').bind('click', function () {
                _this.deletePrivateBit();
            });
            //关闭车位
            $('#disableBtn').bind('click', function () {
                _this.closeStatus();
            });
            //开启车位
            $('#enableBtn').bind('click', function () {
                _this.openStatus();
            });
            //禁用车位
            $('#unuseBtn').bind('click', function () {
                _this.unUse();
            });
            //启用车位
            $('#useBtn').bind('click', function () {
                _this.use();
            });

            /*$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            	if(e.target.hash == '#addressTab' && (!mapInited)) {
                    _this.initMap();
            	}
            });*/

            _this.initializeCreate();
            if(idRef.val()) {
            	/*_this.refreshPhotos();
            	_this.refreshEmployees();*/
            	_this.refreshValidTimes();
            }
            
            /*$('#addValidTimeBtn').bind('click', function() {
                $('#validTimeModal').modal({
                    remote: '/das/label/new/' + idRef.val()
                });
            });*/
            
            /*$('#validTimeModal').on('hidden.bs.modal', function () {
                $(this).removeData('bs.modal');
            });*/
            
            /*$('#saveValidTimeBtn').live('click', function(){
        		_this.saveLabel();
        	});*/
            
            $('#validTimeModal').on('shown.bs.modal', function () {
            	$('#username').focus();
            });
        },
        initializeCreate: function () {
           /* if ($('#farePerUnit').val() == "0") {
                $('#fareDescription').parents('.form-group').hide();
            } else {
                $('#fareDescription').parents('.form-group').show();
            }*/
            //alert('detailValidator1 === '+detailValidator);
            //alert('privatebitForm === '+$("#privatebitForm"));
            detailValidator = $("#priBitForm").validate({
                rules: {
                	parkNo: {required: true},
                	"label.code": {required: true/*,
					           			 remote: {
					     				    url: "/dashboard/privatebits/validateLable",     //后台处理程序
					     				    type: "post",               //数据发送方式
					     				    dataType: "json",           //接受数据格式   
					     				    data: {                     //要传递的数据
					     				        privateBitId: idRef.val()
					     				    }
					           			 }*/
                					   },
                	"parking.name": {required: true},
                	"carOwner.name": {required: true}/*,
                	"ownerLabel.code": {required: true}*/
                }
            });
        },
        
        back: function() {
        	window.location.href = '/dashboard/privatebits/list';
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            var saveUrl = '/api/v1/dashboard/privatebits/add';
            var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveUrl = '/api/v1/dashboard/privatebits/update';
            }
            
            var confirmMsg = '';
            if(isUpdate){
            	confirmMsg = '是否确认变更?';
            }else{
            	confirmMsg = '保存后车位编码、停车场与业主将不能变更，是否继续保存?';
            }
            
            dashboard.dialog.confirm('确认',confirmMsg, function (confirmed) {
                if (confirmed) {
                	dashboard.utils.ajax({
                        block: '.form-actions',
                        type: isUpdate ? 'PUT' : 'POST',
                        url: saveUrl,
                        dataType: 'json',
                        data: $('#priBitForm').serialize(),
                        success: function (json) {
                            if (!json.succeed) {
                                dashboard.message.warning(json.message);
                                return;
                            }
                            dashboard.message.info('保存成功!');
                            if (!$("#id").val()) {
                                window.location.href = '/dashboard/privatebits/edit/' + json.data.id;
                            } else {
                                window.location.href = window.location.href;
                            }
                        }
                    });
                }
             });
        },
        
        closeStatus: function(){
        	$('#openStatus').val("0");
        	$('#saveBtn').trigger("click");//触发saveBtn按钮的click操作
        },
        
        openStatus: function(){
        	$('#openStatus').val("1");
        	$('#saveBtn').trigger("click");//触发saveBtn按钮的click操作
        },
        
        unUse: function(){//当车位被禁用时，就关闭车位
        	$('#status').val("0");
        	$('#openStatus').val("0");
        	$('#saveBtn').trigger("click");//触发saveBtn按钮的click操作
        },
        
        use: function(){
        	$('#status').val("1");
        	$('#saveBtn').trigger("click");//触发saveBtn按钮的click操作
        },
        
        deletePrivateBit: function () {
            dashboard.dialog.confirm('确认',
                '是否删除此私家车位?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = '/api/v1/dashboard/privatebits/delete/' + idRef.val();
                        dashboard.utils.ajax({
                            block: '.form-actions',
                            type: 'DELETE',
                            url: deleteUserEndpoint,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                dashboard.message.info('已删除!');
                                _this.back();
                            }
                        });
                    }
            });
        },
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parking\\.name'));
        },
        //弹出车主选择框
        selectCarOwner:function() {
        	dashboard.carowner.select.selectCarOwner($('#selectCarOwner'),$('#carOwnerId'),$('#carOwner\\.name'));
        },
        
        refreshValidTimes: function() {
        	dashboard.utils.hide(validTimeTableRef);
        	validTimeTbodyRef.empty();
        	dashboard.utils.show(moreValidTimeRef);

            dashboard.utils.ajax({
                block: moreValidTimeRef,
                type: 'GET',
                url: '/api/v1/dashboard/privatebits/findValidTimes/' + idRef.val(),
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    if (json.data == null || json.data.length == 0) {
                    	moreLabelRef.html('<div class="well text-center"><em>无记录.</em></div>');
                        return;
                    }

                    dashboard.utils.hide(moreValidTimeRef);
                    dashboard.utils.show(validTimeTableRef);
                    var ei = 0;
                    for (var p in json.data) {
                        var data = json.data[p];
                        var tr = $('<tr><td>'
                            + (++ei)
                            + '</td><td>'
                            + (data.startTime ? data.startTime : '')
                            + '</td><td>'
                            + (data.endTime ? data.endTime : '')
                             + '</td><td>'
                            + (data.weekday ? data.weekday : '')
                            + '</td><td>'
                            + (data.openStatus=='1' ? '已开放' : '未开放')
                            //+ '</td><td>'
                            //+ (data.createAt ? data.createAt : '')
                            //+ '</td><td>'
                            //+ '<span class="operation btn-group" id="operation' + data.id + '">'
                            //+ '<a id="editLabelBtn" href="javascript:void(0);" class="btn btn-info btn-xs" dataId="' + data.id + '">编辑</a>'
                            //+ '<a id="deleteLabelBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>'
                            //+ '</span>'
                            + '</td></tr>');

                        var $tr = $(tr);

                        /*$tr.delegate('#deleteLabelBtn', 'click', function (btn) {
                            _this.deleteLabel($(btn.target).attr('dataId'));
                        });
                        $tr.delegate('#editLabelBtn', 'click', function (btn) {
                            _this.editLabel($(btn.target).attr('dataId'));
                        });*/

                        validTimeTbodyRef.append($tr);
                    }
                }
            });        	
        }
        /*editLabel: function(id) {
            $('#labelModal').modal({
                //remote: das.parking.base.pageUrl + '/' + idRef.val() + '/employees/' + id + '/edit'
            	remote: '/das/label/editInParking/'+id
            });
        },
        deleteLabel: function(id) {
            dashboard.dialog.confirm('确认',
                    '是否删除此标签?', function (confirmed) {
                        if (confirmed) {
                            dashboard.utils.ajax({
                                block: '#operation' + id,
                                type: 'DELETE',
                                url: '/das/label/delete/' + id,
                                dataType: 'json',
                                success: function (json) {
                                    if (!json.succeed) {
                                        dashboard.message.warning(json.message);
                                        return;
                                    }
                                    dashboard.message.info('已删除!');
                                    _this.refreshLabels();
                                	//_this.refreshReaders();
                                }
                            });
                        }
                });
        },
        saveLabel: function() {
        	labelValidator = $("#labelForm").validate({
                rules: {
                    "labelCode": {required: true}
                }
            });
        	
        	if(!labelValidator.form()) {
        		return;
        	}
        	
        	var saveUrl = '/das/label/save';
        	var saveMethod = 'POST';
        	if ($('#label_id').val()) {
        		saveUrl = '/das/label/update';
        		saveMethod = 'PUT';
        	}
        	
            dashboard.utils.ajax({
                block: '#labelModal .modal-footer',
                type: saveMethod,
                url: saveUrl,
                dataType: 'json',
                data: $('#labelForm').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    $('#labelModal').modal('hide');
                    _this.refreshLabels();
                	//_this.refreshReaders();
                    dashboard.message.info('保存成功!');
                }
            });
        },*/
    };
})();

//私家车位对应的停车场计费规则查询列表 
dashboard.privatebits.feerulelist = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var queryFormRef = null;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            queryFormRef = $('#queryForm');
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            parkingnameRef = $('#parkingName');
            /*usernameRef = $('#username');
            nameRef = $('#name');
            phoneNumberRef = $('#phoneNumber');
            emailRef = $('#email');*/
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
            
            $('#parkingName').dblclick(function () {
                _this.selectParking();
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
            queryFormRef[0].reset();
            $("#parkingId").val('');
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'carowners/' + itemId
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
                url: '/api/v1/dashboard/parkings/parkfeerule',
                dataType: 'json',
                data: queryFormRef.serialize() + '&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>目前无计费规则信息.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        
                        var tr = $('<tr><td style="padding: 0px;">'
                                + (++i)
                                + '</td><td style="padding: 0px;">'
                                /*+ '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                                + (data.displayName ? data.displayName : (data.name ? data.name : '无'))
                                + '</a>'*/
                                + (data.parkName?data.parkName:'')
                                + '</td><td>'
                                + (data.bookFee ? data.bookFee : '')
                                + '</td><td>'
                                + (data.dstartPrice ? data.dstartPrice : '')
                                + '</td><td>'
                                + (data.dunitPrice ? data.dunitPrice : '')
                                + '</td><td>'
                                + (data.startHour ? data.startHour: '')
                                + '</td><td>'
                                + (data.dstartTime ? data.dstartTime : '')
                                + '</td><td>'
                                + (data.dendTime ? data.dendTime : '')
                                + '</td><td>'
                                + (data.outRangeFee ? data.outRangeFee: '')
                                + '</td><td>'
                                + (data.dfifteenPrice ? data.dfifteenPrice: '')
                                + '</td><td>'
                               /* + (data.nstartPrice ? data.nstartPrice : '')
                                + '</td><td>'
                                + (data.nunitPrice ? data.nunitPrice : '')
                                + '</td><td>'
                                + (data.nfifteenPrice ? data.nfifteenPrice: '')
                                + '</td><td>'*/
                                + (data.effectTime ? data.effectTime: '')
                                + '</td><td>'
                                +'<span class="operation btn-group" id="operation' + data.id + '">'
                                +'<a style="width:40px;" href="/dashboard/privatebits/parkingfeerule/' +  data.id + '/edit" class="btn btn-info btn-xs">&gt;&gt;</a>'
            					+'</span>'
                                + '</td></tr>');
                        
                        tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            //_this.showDetail($(detailBtn.target).attr('dataId'));
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
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parkingName'));
        }
    };
})();

//私家车位停车场计费规则编辑
dashboard.privatebits.feeruleedit = (function () {
    var _this = null;
    var detailValidator = null, detailValidatorChangePsw = null;
    var idRef;
    var saveEndpoint = '/api/v1/dashboard/parkings/parkfeerule';
    return {
        init: function () {
            _this = this;
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
            idRef = $('#id');
            _this.initializeCreate();
        },
        initializeCreate: function () {
            detailValidator = $("#parkingFeeRuleForm").validate({
                rules: {
                    "parking.name": {required: true},
                    "bookFee": {required: true,number: true},
                    "outRangeFee": {required: true},
                    "startHour": {required: true},
                    "dstartTime": {required: true},
                    "dendTime": {required: true},
                    "dstartPrice": {required: true,number: true},
                    "dunitPrice": {required: true,number: true},
                    "dfifteenPrice": {required: true,number: true}/*,
                    "nstartTime": {required: true},
                    "nendTime": {required: true},
                    "nstartPrice": {required: true,number: true},
                    "nfifteenPrice": {required: true,number: true}*/
                }
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveEndpoint += '/' + $('#id').val();
            }
            dashboard.utils.ajax({
                block: '#saveBtn',
                type: isUpdate ? 'PUT' : 'POST',
                url: saveEndpoint,
                dataType: 'json',
                data: $("#parkingFeeRuleForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                    if (!$("#id").val()) {
                        window.location.href = json.data.id + '/edit';
                    }
                }
            });
        }
    };
})();
//私家车停车日台账记录查询
dashboard.privatebits.daybilldetailList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
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
            $("#billDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            $('#parkingName').dblclick(function () {
                _this.selectParking();
            });
            $('#carOwnerUserName').bind('dblclick',function () {
                _this.selectCarOwner();
            });
            //_this.refresh();
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
            formRef[0].reset();
            $('#parkingId').val('');
            $('#carOwnerUserId').val('');
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
                url: '/api/v1/dashboard/privatebits/daybilldetails/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无台账记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var percent = data.ownerPercent+"+"+data.parkPercent+"+"+data.parkEasyPercent;
                        var percentBill = data.ownerbill+"+"+data.parkbill+"+"+data.parkEasybill;

                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: left">');
                        tr.push(data.parking ? data.parking.name : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.privateBits ? data.privateBits.parkNo : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.labelCode ? data.labelCode : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.billDate ? data.billDate : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.startTime ? data.startTime : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.endTime ? data.endTime : '');
                        tr.push('</td><td style="padding: 0px;text-align: right">');
                        tr.push(data.duration ? data.duration : '');
                        tr.push('</td><td style="padding: 0px;text-align: right" title="分成费用：'+percentBill+'">');
                        tr.push(data.bill ? data.bill : '');
                        /*tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(percentBill);*/
                        tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a href="/dashboard/privatebits/daybilledit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>');
                        tr.push('</span>');
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));

                        /*$tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });
                        $tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            _this.showDetail($(detailBtn.target).attr('dataId'));
                        });*/

                        tbodyRef.append($tr);
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
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parkingName'));
        },
        //弹出车主选择框
        selectCarOwner:function() {
        	dashboard.carowner.select.selectCarOwner($('#selectCarOwner'),$('#carOwnerUserId'),$('#carOwnerUserName'));
        }
    };
})();

//私家车位日台账编辑
dashboard.privatebits.daybilldetailedit = (function () {
    var _this = null;
    var detailValidator = null, detailValidatorChangePsw = null;
    return {
        init: function () {
            _this = this;
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
            $('#backBtn').bind('click', function () {
                _this.back();
            });
            _this.initializeCreate();
        },
        initializeCreate: function () {
            detailValidator = $("#privateBitBillForm").validate({
                rules: {
                    "bill": {required: true,number: true,min: 0.01},
                    "parkbill": {required: true,number: true,min: 0.01},
                    "ownerbill": {required: true,number: true,min: 0.01},
                    "parkEasybill": {required: true,number: true,min: 0.01}
                },

                messages: {
                	"bill": {
                	   required: "必须填值",
                	   number: "必须是数字",
                	   min:"必须大于0"
                   },
                   "parkbill": {
                	   required: "必须填值",
                	   number: "必须是数字",
                	   min:"必须大于0"
                   },
                   "ownerbill": {
                	   required: "必须填值",
                	   number: "必须是数字",
                	   min:"必须大于0"
                   },
                   "parkEasybill": {
                	   required: "必须填值",
                	   number: "必须是数字",
                	   min:"必须大于0"
                   }
                }
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            /*var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveEndpoint += '/' + $('#id').val();
            }*/
            dashboard.utils.ajax({
                block: '#saveBtn',
                type: 'PUT',
                url: '/api/v1/dashboard/privatebits/updateDaybill/',
                dataType: 'json',
                data: $("#privateBitBillForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                    /*if (!$("#id").val()) {
                        window.location.href = json.data.id + '/edit';
                    }*/
                }
            });
        },
        back: function() {
        	window.location.href = '/dashboard/privatebits/daybilldetail/';
        }
    };
})();

//私家车停车月台账记录查询
dashboard.privatebits.monthbilldetailList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            filterRef = $('#filter');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
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
            $('#parkingName').dblclick(function () {
                _this.selectParking();
            });
            $('#carOwnerUserName').bind('dblclick',function () {
                _this.selectCarOwner();
            });
            //_this.refresh();
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
            formRef[0].reset();
            $("#parkingId").val('');
            $("#carOwnerUserId").val('');
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
                url: '/api/v1/dashboard/privatebits/monthbilldetails/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无台账记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var percent = data.ownerPercent+"+"+data.parkPercent+"+"+data.parkEasyPercent+"+";
                        var percentBill = data.ownerbill+"+"+data.parkbill+"+"+data.parkEasybill+"+";

                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: left">');
                        tr.push(data.parking ? data.parking.name : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.privateBits ? data.privateBits.parkNo : '');
                        /*tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.labelCode ? data.labelCode : '');*/
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.carOwnerUser ? data.carOwnerUser.name : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.month ? data.month : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.count ? data.count : '');
                        tr.push('</td><td style="padding: 0px;text-align: right">');
                        tr.push(data.bill ? data.bill : '');
                        /*tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a href="edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>');
                        if(data.openStatus!=1){
                        	tr.push('<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>');
                        }
                        tr.push('</span>');*/
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));

                        tbodyRef.append($tr);
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
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parkingName'));
        },
        //弹出车主选择框
        selectCarOwner:function() {
        	dashboard.carowner.select.selectCarOwner($('#selectCarOwner'),$('#carOwnerUserId'),$('#carOwnerUserName'));
        }
    };
})();

//运营费应收账单查询
dashboard.privatebits.parkeasybilldetailList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            filterRef = $('#filter');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
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
            $("#billMonth").datepicker({
                dateFormat: 'yy-mm'
            });
            $('#parkingName').click(function () {
                _this.selectParking();
            });
            //_this.refresh();
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
            formRef[0].reset();
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
                url: '/api/v1/dashboard/privatebits/parkeasyBillDetails/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        /*var percent = data.ownerPercent+"+"+data.parkPercent+"+"+data.parkEasyPercent+"+";
                        var percentBill = data.ownerbill+"+"+data.parkbill+"+"+data.parkEasybill+"+";*/

                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: left">');
                        tr.push(data.parkingName ? data.parkingName : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.month ? data.month : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.bill ? data.bill : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.parkEasybill ? data.parkEasybill : '');
                        /*tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a href="edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>');
                        if(data.openStatus!=1){
                        	tr.push('<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>');
                        }
                        tr.push('</span>');*/
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));

                        tbodyRef.append($tr);
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
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parkingName'));
        },
    };
})();
//停车日志（记录）列表
dashboard.privatebits.parkRecordList = (function () {
    var _this = null;
    var labelCodeRef = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            labelCodeRef = $('#labelCode');

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
            
            $("#startDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
        	$("#endDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
            //_this.refresh(true);
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            /*usernameRef.val('');
            authTypeRef.val('');
            loginAtRef.val('');
            ipAddressRef.val('');*/
        	labelCodeRef.val('');
        	$("#startDate").val('');
        	$("#endDate").val('');
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
                url: '/api/v1/dashboard/privatebits/listParkRecords',
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit,
                    labelCode: labelCodeRef.val(),
                    startDate: $("#startDate").val(),
                    endDate: $("#endDate").val()
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>无停车记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.label);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.start);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.end);
                        tr.push('</td><tr>');
                        
                        var $tr = $(tr.join(''));
                        
                        tbodyRef.append($tr);
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
//停车日志（记录）清理
dashboard.privatebits.parkRecordClean = (function () {
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
                url: '/api/v1/dashboard/privatebits/cleanParkRecords',
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
//日台账监控
dashboard.privatebits.dayMonitorList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            filterRef = $('#filter');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
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
            $("#billDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            _this.refresh();
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
            formRef[0].reset();
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
                url: '/api/v1/dashboard/privatebits/listDayMonitors/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.billDate ? data.billDate : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.sucessed ? '成功':(data.dealing?'正在计费' : '失败'));
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.startDate ? data.startDate : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.endDate ? data.endDate : '');
                        tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a id="reOperateBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.billDate + '">重新计费</a>');
                        tr.push('</span>');
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));
                        
                        $tr.delegate('#reOperateBtn', 'click', function (btn) {
                            _this.reOperate($(btn.target).attr('dataId'));
                        });

                        tbodyRef.append($tr);
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
        reOperate:function(billDate){
            dashboard.dialog.confirm('确认',
                '是否重新计费?', function (confirmed) {
                    if (confirmed) {
                    	var durl = '/api/v1/dashboard/privatebits/reOperateDayBill/' + billDate;
                        dashboard.utils.ajax({
                            block: '#operation' + billDate,
                            type: 'PUT',
                            url: durl,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                dashboard.message.info('已经触发成功，请等待!');
                                _this.refresh();
                            }
                        });
                    }
                });
        
        	
        }
    };
})();
//月台账监控
dashboard.privatebits.monthMonitorList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            filterRef = $('#filter');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
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
            $("#billMonth").datepicker({
                dateFormat: 'yy-mm'
            });
            _this.refresh();
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
            formRef[0].reset();
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
                url: '/api/v1/dashboard/privatebits/listMonthMonitors/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.month ? data.month : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.sucessed ? '成功':(data.dealing?'正在计费' : '失败'));
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.startDate ? data.startDate : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.endDate ? data.endDate : '');
                        tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a id="reOperateBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.month + '">重新计费</a>');
                        tr.push('</span>');
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));
                        
                        $tr.delegate('#reOperateBtn', 'click', function (btn) {
                            _this.reOperate($(btn.target).attr('dataId'));
                        });

                        tbodyRef.append($tr);
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
        reOperate:function(month){
            dashboard.dialog.confirm('确认',
                '是否重新计费?', function (confirmed) {
                    if (confirmed) {
                    	var durl = '/api/v1/dashboard/privatebits/reOperateMonthBill/' + month;
                        dashboard.utils.ajax({
                            block: '#operation' + month,
                            type: 'PUT',
                            url: durl,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                dashboard.message.info('已经触发成功，请等待!');
                                _this.refresh();
                            }
                        });
                    }
                });
        	
        }
    };
})();
//重新生成日台账
dashboard.privatebits.reDoDayBill = (function () {
	var _this = null;
    var detailValidator = null;
	return {
		init: function(){
			_this = this;            
			
			$('#billDate').datepicker({
                dateFormat: 'yy-mm-dd'
            });
			
            detailValidator = $("#detailForm").validate({
                rules: {
                	billDate: {required: true}
                }
            });
			
			$('#okBtn').bind('click', function(){
				_this.operate();
			});
		},
		operate: function() {
            if (!detailValidator.form()) {
                return;
            }

            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'PUT',
                url: '/api/v1/dashboard/privatebits/reOperateDayBill/'+$('#billDate').val(),
                dataType: 'json',
                data: {},
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('已经触发成功，请等待!');
                }
            });
		}
	};
})();
//重新生成月台账
dashboard.privatebits.reDoMonthBill = (function () {
	var _this = null;
    var detailValidator = null;
	return {
		init: function(){
			_this = this;            
			
			$('#month').datepicker({
                dateFormat: 'yy-mm'
            });
			
            detailValidator = $("#detailForm").validate({
                rules: {
                	month: {required: true}
                }
            });
			
			$('#okBtn').bind('click', function(){
				_this.operate();
			});
		},
		operate: function() {
            if (!detailValidator.form()) {
                return;
            }

            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'PUT',
                url: '/api/v1/dashboard/privatebits/reOperateMonthBill/'+$('#month').val(),
                dataType: 'json',
                data: {},
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('已经触发成功，请等待!');
                }
            });
		}
	};
})();