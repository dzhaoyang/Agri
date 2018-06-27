$.namespace('dashboard.parkBookingDetail');
dashboard.parkBookingDetail.bookingList = (function () {
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

            //this.refresh();
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
            $("#parkingId").val("");
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'booking/' + itemId
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
                url: '/api/v1/dashboard/bookings/parking/listall',
                dataType: 'json',
                data: $('#queryForm').serialize()+'&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>没有预订记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var statusLabel = "";
                        if(data.status=='booking'){
                        	statusLabel = "预订中";
                        }else if(data.status=='confirmed'){
                        	statusLabel = "已确认";
                        }else if(data.status=='timeout'){
                        	statusLabel = "已超时";
                        }else if(data.status=='cancelled'){
                        	statusLabel = "已取消";
                        }else if(data.status=='completed'){
                        	statusLabel = "已完成";
                        }
                        var payLabel = "";
                        if(data.payStatus=='unpayment'){
                        	payLabel = "未付款";
                        }else if(data.payStatus=='clientpayment'){
                        	payLabel = "app付款";
                        }else if(data.payStatus=='payment'){
                        	payLabel = "已付款";
                        }
                        if(data.refundStatus=='refunding'){
                        	payLabel += "(待退款)";
                        }else if(data.refundStatus=='refunded'){
                        	payLabel += "(已退款)";
                        }
                        var tr = $('<tr><td>'
                                + (++i)
                                + '</td><td>'
                                /*+ '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                                + (data.displayName ? data.displayName : (data.name ? data.name : '无'))
                                + '</a>'*/
                                + (data.parking?data.parking.name:'')
                                + '</td><td>'
                                + (data.bookingDay ? data.bookingDay : '')
                                + '</td><td>'
                                + (data.carOwner ? data.carOwner.name : '')
                                + '</td><td>'
                                + (data.licensePlateNumber ? data.licensePlateNumber: '')
                                + '</td><td>'
                                + (data.phoneNumber1 ? data.phoneNumber1: '')
                                + '</td><td>'
                                + (data.requestAt ? data.requestAt : '')
                                + '</td><td>'
                                + statusLabel
                                + '</td><td>'
                                + payLabel
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
//退款查询
dashboard.parkBookingDetail.refundList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var queryFormRef = null;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var isManageRef = null;
    return {
        init: function () {
            _this = this;
            queryFormRef = $('#queryForm');
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            parkingnameRef = $('#parkingName');
            isManageRef = $('#isManage');
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

            //this.refresh();
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
            $("#parkingId").val("");
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
                url: '/api/v1/dashboard/bookings/refund/listPassed',
                dataType: 'json',
                data: $('#queryForm').serialize()+'&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>没有预订记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var statusLabel = "";
                        if(data.status=='booking'){
                        	statusLabel = "预订中";
                        }else if(data.status=='confirmed'){
                        	statusLabel = "已确认";
                        }else if(data.status=='timeout'){
                        	statusLabel = "已超时";
                        }else if(data.status=='cancelled'){
                        	statusLabel = "已取消";
                        }else if(data.status=='completed'){
                        	statusLabel = "已完成";
                        }
                        var payLabel = "";
                        if(data.payStatus=='unpayment'){
                        	payLabel = "未付款";
                        }else if(data.payStatus=='clientpayment'){
                        	payLabel = "app付款";
                        }else if(data.payStatus=='payment'){
                        	payLabel = "已付款";
                        }
                        if(data.refundStatus=='refunding'){
                        	payLabel += "(待退款)";
                        }else if(data.refundStatus=='refunded'){
                        	payLabel += "(已退款)";
                        }
                        var tr = $('<tr><td style="padding:1px;">'
                                + (++i)
                                + '</td><td style="padding:1px;">'
                                /*+ '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                                + (data.displayName ? data.displayName : (data.name ? data.name : '无'))
                                + '</a>'*/
                                + (data.parking?data.parking.name:'')
                                + '</td><td style="padding:1px;">'
                                + (data.carOwner ? data.carOwner.name : '')
                                + '</td><td style="padding:1px;">'
                                + (data.licensePlateNumber)
                                + '</td><td style="padding:1px;">'
                                + (data.phoneNumber1 ? data.phoneNumber1: '')
                                + '</td><td style="padding:1px;">'
                                + (data.bookingRefund ? data.bookingRefund.createTime : '')
                                + '</td><td style="padding:1px;">'
                                + statusLabel
                                + '</td><td style="padding:1px;">'
                                + payLabel
                                +(isManageRef.val()=='1'?'</td><td style="padding:1px;">'+data.bookingPay.account+'</td><td style="padding:1px;">'+((data.payStatus=='payment'&&data.refundStatus == 'refunding')?'<a id="refund" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">退款</a>':''):'')
                                + '</td></tr>');                        
                        tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            //_this.showDetail($(detailBtn.target).attr('dataId'));
                        });
                        
                        tr.delegate('#refund', 'click', function (delBtn) {
                            _this.markRefund($(delBtn.target).attr('dataId'));
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
        markRefund: function (id) {
            dashboard.dialog.confirm('确认',
                '是否已进行退款?', function (confirmed) {
                    if (confirmed) {
                        var durl = '/api/v1/dashboard/bookings/refund/markRefund/' + id;
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'POST',
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
        }
    };
})();
//日台账查询
dashboard.parkBookingDetail.daybilldetailList = (function () {
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
            if(isFresh){
            	_this.refresh();
            }
            //
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
                url: '/api/v1/dashboard/bookings/daybilldetails/',
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
                        /*var percent = data.ownerPercent+"+"+data.parkPercent+"+"+data.parkEasyPercent;
                        var percentBill = data.ownerbill+"+"+data.parkbill+"+"+data.parkEasybill;*/

                        var tr = [];
                        tr.push('<tr><td style="padding: 0px;text-align: center">');
                        tr.push(++i);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.billDate ? data.billDate : '');
                        tr.push('</td><td style="padding: 0px;text-align: left">');
                        tr.push(data.parkingName);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.carNo);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.requestAt);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.money);
                        /*tr.push('</td><td style="padding: 0px;text-align: right">');
                        tr.push(data.duration ? data.duration : '');
                        tr.push('</td><td style="padding: 0px;text-align: right">');
                        tr.push(data.bill ? data.bill : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(percentBill);*/
                        /*tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a href="edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>');
                        if(data.openStatus!=1){
                        	tr.push('<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>');
                        }
                        tr.push('</span>');*/
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
        }
    };
})();

//月台账记录查询
dashboard.parkBookingDetail.monthbilldetailList = (function () {
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
                url: '/api/v1/dashboard/bookings/monthbilldetails/',
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
                        tr.push(data.parkingName);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.billDate);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.count);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.money);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.parkingBill);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.parkeasyBill);
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
        }
    };
})();

//日台账监控
dashboard.parkBookingDetail.dayMonitorList = (function () {
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
                url: '/api/v1/dashboard/bookings/listDayMonitors/',
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
                    	var durl = '/api/v1/dashboard/bookings/reOperateDayBill/' + billDate;
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
dashboard.parkBookingDetail.monthMonitorList = (function () {
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
                url: '/api/v1/dashboard/bookings/listMonthMonitors/',
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
        reOperate:function(month){
            dashboard.dialog.confirm('确认',
                '是否重新计费?', function (confirmed) {
                    if (confirmed) {
                    	var durl = '/api/v1/dashboard/bookings/reOperateMonthBill/' + month;
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
dashboard.parkBookingDetail.reDoDayBill = (function () {
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
                url: '/api/v1/dashboard/bookings/reOperateDayBill/'+$('#billDate').val(),
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
dashboard.parkBookingDetail.reDoMonthBill = (function () {
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
                url: '/api/v1/dashboard/bookings/reOperateMonthBill/'+$('#month').val(),
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

//预约金应支账单列表
dashboard.parkBookingDetail.shouldPayList = (function () {
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
            /*$("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });*/
            $('#closeDiv').bind('click', function () {
                _this.hideBankInfo();
            });
            $('#closeButton').bind('click', function () {
                _this.hideBankInfo();
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
        showBankInfo: function (parkingName,bankName,accountNumber,account) {
        	$("#parkingName_div").html(parkingName);
        	$("#bankName_div").html(bankName);
        	$("#accountNumber_div").html(accountNumber);
        	$("#account_div").html(account);
        	$("#parkingAccount").show();
        },
        hideBankInfo: function () {
        	$("#parkingAccount").hide();
        },
        showDayBill: function (parkingId,billDate) {
            $('#dayBillBox').modal({
                remote: '/dashboard/parkingbooking/showDayBill/'+parkingId+'/'+billDate
            });
        },
        showSettlement: function (id) {
        	//每次打开对话框之前移除数据，使得可以顺利重新加载
        	$('#settlementBox').removeData("bs.modal");
            $('#settlementBox').modal({
                remote: '/dashboard/parkingbooking/settlement/'+id
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
                url: '/api/v1/dashboard/bookings/listShouldPay/',
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
                        tr.push('</td><td style="padding: 0px;text-align: left">');
                        tr.push('<a id="bankBtn" href="javascript:void(0);" title="点击查看付款账号" parkingName="'+data.parkingName+'" bankName="'+data.bankName+'" accountNumber="'+data.accountNumber+'" account="'+data.account+'">'+data.parkingName+'</a>');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.billDate ? data.billDate : '');
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.money);
                        tr.push('</td><td style="padding: 0px;text-align: center" id="factMoneyTd_'+data.id+'">');
                        tr.push(data.factMoney);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push('<a id="dayBillBtn" href="javascript:void(0);" title="点击查看预约金支付记录" parkingId="'+data.parkingId+'" billDate="'+data.billDate+'" class="btn btn-info btn-xs">详细</a>');
                        tr.push('</td><td style="padding: 0px;text-align: center" id="settlementTd_'+data.id+'">');
                        tr.push(data.isSettlemented==1?'已结算':'<a id="settlementBtn" href="javascript:void(0);" dataId="'+data.id+'" class="btn btn-info btn-xs">结算</a>');
                        tr.push('</td><td style="padding: 0px;text-align: center" id="remarkTd_'+data.id+'">');
                        tr.push(data.remark);
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));

                        $tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });
                        $tr.delegate('#bankBtn', 'click', function (bankBtn) {
                            _this.showBankInfo($(bankBtn.target).attr('parkingName'),$(bankBtn.target).attr('bankName'),$(bankBtn.target).attr('accountNumber'),$(bankBtn.target).attr('account'));
                        });
                        $tr.delegate('#dayBillBtn', 'click', function (dayBillBtn) {
                            _this.showDayBill($(dayBillBtn.target).attr('parkingId'),$(dayBillBtn.target).attr('billDate'));
                        });
                        $tr.delegate('#settlementBtn', 'click', function (settlementBtn) {
                            _this.showSettlement($(settlementBtn.target).attr('dataId'));
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
        }
    };
})();

// 付款查询
dashboard.parkBookingDetail.payRecordList = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataPayRecordContainer tbody');
            tableRef = $('#dataPayRecordContainer');
            moreRef = $('#showMorePayRecordBar');
            formRef = $('#queryPayRecordForm');
            _this.showMore(true);
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
                block: '#showMorePayRecordBar',
                type: 'GET',
                url: '/api/v1/dashboard/bookings/daybilldetails/',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#payRecordSize').html('(' + json.data.totalElements + ')');
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
                        tr.push(data.carNo);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.requestAt);
                        tr.push('</td><td style="padding: 0px;text-align: center">');
                        tr.push(data.money);
                        tr.push('</td></tr>');

                        var $tr = $(tr.join(''));

                        tbodyRef.append($tr);
                    }


                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMorePayRecordBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMorePayRecordBtn', 'click', function () {
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

//结算
dashboard.parkBookingDetail.settlement = (function () {
    var _this = null;
    var detailValidator = null;
    var idRef = null;
    var editable = false;

    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
            _this.initializeCreate();

        },
        initializeCreate: function () {
            detailValidator = $("#settlementForm").validate({
                rules: {
                	factMoney: {required: true,number: true,min: 0}
                }
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }
            
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'PUT',
                url: '/api/v1/dashboard/bookings/settlement',
                dataType: 'json',
                data: $('#settlementForm').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                    var settlementTdId = 'settlementTd_'+$("#id").val();
                    var factMoneyTdId = 'factMoneyTd_'+$("#id").val();
                    var remarkTdId = 'remarkTd_'+$("#id").val();
                	$(document.getElementById(settlementTdId)).html('已结算');
                	$(document.getElementById(factMoneyTdId)).html($("#factMoney").val());
                	$(document.getElementById(remarkTdId)).html($("#remark").val());
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
       
        
    };
})();