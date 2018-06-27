$.namespace('dashboard.privatebitapply');
//私家车位查询列表
dashboard.privatebitapply.list = (function () {
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
            $('#applyerName').bind('dblclick',function () {
                _this.selectCarOwner();
            });
            $("#createTimeStart").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            $("#createTimeEnd").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
            $('#closeWriteRefuseMsgButton').bind('click', function () {
                _this.hideWriteRefuseDiv();
            });
            $('#saveBtn').bind('click', function () {
                _this.saveRefuseMsg();
            });
            
            $('#closeShowRefuseMsgButton').bind('click', function () {
                _this.hideShowRefuseDiv();
            });
            $('#closeDiv').bind('click', function () {
                _this.hideShowRefuseDiv();
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
            $("#applyerId").val('');
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
                url: '/api/v1/dashboard/privatebits/listApply/',
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
                        tr.push('<tr><td>');
                        tr.push(++i);
                        tr.push('</td><td>');
                        //tr.push('<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">');
                        tr.push(data.privateBitCode);
                        //tr.push('</a>');
                        tr.push('</td><td>');
                        tr.push(data.parkingName);
                        tr.push('</td><td>');
                        tr.push(data.applyerName);
                        tr.push('</td><td>');
                        tr.push(data.createTime);
                        tr.push('</td><td>');
                        tr.push('<div id="statusLabel_' + data.id + '">'+data.statusLabel+'</div>');
                        tr.push('</td><td align="center">');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        if(data.status==0){
                        	tr.push('<a id="passBtn" href="javascript:void(0);" class="btn btn-info btn-xs" dataId="' + data.id + '">通过</a>');
                            tr.push('<a id="refuseBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">拒绝</a>');
                        }else if(data.status==-1){
                            tr.push('<a id="showRefuseBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" refuseMsg="' + data.remark + '">原因</a>');
                        }
                        tr.push('</span>');
                        tr.push('</td></tr>');

                        var $tr = $(tr.join(''));

                        $tr.delegate('#passBtn', 'click', function (delBtn) {
                            _this.pass($(delBtn.target).attr('dataId'));
                        });
                        $tr.delegate('#refuseBtn', 'click', function (delBtn) {
                            _this.refuse($(delBtn.target).attr('dataId'));
                        });
                        $tr.delegate('#showRefuseBtn', 'click', function (detailBtn) {
                            _this.showRefuseMsg($(detailBtn.target).attr('refuseMsg'));
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
        hideWriteRefuseDiv: function () {
        	$("#refuseMsg").val('');
        	$("#writeRefuseMsg_div").hide();
        },
        hideShowRefuseDiv: function () {
        	$("#showRefuseMsg").val('');
        	$("#showRefuseMsg_div").hide();
        },
        showRefuseMsg:function (refuseMsg) {
        	$("#showRefuseMsg").val(refuseMsg);
        	$("#showRefuseMsg_div").show();
        },
        saveRefuseMsg: function () {//拒接并保存原因
        	/*dashboard.dialog.confirm('确认','是否确要拒绝?', function (confirmed) {
                if (confirmed) {
                    var durl = '/api/v1/dashboard/privatebits/refuseApply/'+$("#applyId").val()+'/'+$("#refuseMsg").val();
                    dashboard.utils.ajax({
                        block: '#operation' + id,
                        type: 'PUT',
                        url: durl,
                        dataType: 'json',
                        data: {},
                        success: function (json) {
                            if (!json.succeed) {
                            	$("#statusLabel_"+id).html('确认');
                            	var reason = '<a id="showRefuseBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" refuseMsg="' + $("#refuseMsg").val() + '">原因</a>';
                            	$("#operation"+id).html(reason);
                                dashboard.message.warning(json.message);
                                $("#refuseMsg").val('');
                            	$("#writeRefuseMsg_div").hide();
                                return;
                            }
                        }
                    });
                }
            });*/
            dashboard.utils.ajax({
                /*block: '#operation' + id,*/
                type: 'PUT',
                url: '/api/v1/dashboard/privatebits/refuseApply',
                dataType: 'json',
                data: $('#applyForm').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        $("#refuseMsg").val('');
                    	$("#writeRefuseMsg_div").hide();
                        return;
                    }
                    var statusLabel = document.getElementById('statusLabel_'+$("#applyId").val());
                	$(statusLabel).html('拒绝');
                	var reason = '<a id="showRefuseBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" refuseMsg="' + $("#refuseMsg").val() + '">原因</a>';
                	var operation = document.getElementById('operation'+$("#applyId").val());
                	$(operation).html(reason);
                	$("#refuseMsg").val('');
                	$("#writeRefuseMsg_div").hide();
                }
            });
        },
        pass: function (id) {//通过
            dashboard.dialog.confirm('确认',
                '是否确认通过申请?', function (confirmed) {
                    if (confirmed) {
                        dashboard.utils.ajax({
                            /*block: '#operation' + id,*/
                            type: 'PUT',
                            url: '/api/v1/dashboard/privatebits/passApply/' + id,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                var statusLabel = document.getElementById('statusLabel_'+id);
                            	$(statusLabel).html('通过');
                            	var operation = document.getElementById('operation'+id);
                            	$(operation).html('');
                            }
                        });
                    }
                });
        },
        refuse: function (id) {//拒绝，显示拒绝原因录入框
        	$("#applyId").val(id);
        	$("#refuseMsg").val('');
        	$("#writeRefuseMsg_div").show();
        },
        //弹出停车场选择框
        selectParking:function() {
        	dashboard.parking.select.selectParking($('#selectParking'),$('#parkingId'),$('#parkingName'));
        },
        //弹出车主选择框
        selectCarOwner:function() {
        	dashboard.carowner.select.selectCarOwner($('#selectCarOwner'),$('#applyerId'),$('#applyerName'));
        }
    };
})();