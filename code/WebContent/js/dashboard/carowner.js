$.namespace('dashboard.carowner');
dashboard.carowner.list = (function () {
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
            usernameRef = $('#username');
            nameRef = $('#name');
            phoneNumberRef = $('#phoneNumber');
            emailRef = $('#email');
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
            queryFormRef[0].reset();
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
                url: '/api/v1/dashboard/identity/carowners',
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
                        moreRef.html('<div class="well text-center"><em>目前无车主信息.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var subscribeStatus = data.subscribeStatus;
                        subscribeStatus = subscribeStatus ? subscribeStatus : '';
                        if(subscribeStatus){
                        	subscribeStatus = subscribeStatus == 'subscribe' ? '关注' : '取消关注';
                        }
                        
                        var tr = $('<tr><td>'
                            + (++i)
                            + '</td><td>'
                            + '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                            + (data.displayName ? data.displayName : (data.name ? data.name : '无'))
                            + '</a>'
                            + '</td><td>'
                            + (data.name ? data.name : '')
                            + '</td><td>'
                            + (data.phoneNumber ? data.phoneNumber : '')
                            + '</td><td>'
                            + (data.plateNumber ? data.plateNumber: '')
                            + '</td><td>'
                            + (data.createAt ? data.createAt: '')
                            + '</td><td>'
                            + subscribeStatus
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
        }
    };
})();
dashboard.carowner.ppflist = (function () {
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
                type: 'POST',
                url: '/api/v2/user/carowner/my/privateparking/alllist',
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
                        moreRef.html('<div class="well text-center"><em>目前无招租车位信息.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        
                        var tr = $('<tr><td>'
                                + (++i)
                                + '</td><td>'
                                /*+ '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                                + (data.displayName ? data.displayName : (data.name ? data.name : '无'))
                                + '</a>'*/
                                + (data.areaName?data.areaname:'')
                                + '</td><td>'
                                + (data.communityName ? data.communityName : '')
                                + '</td><td>'
                                + (data.ownerName ? data.ownerName : '')
                                + '</td><td>'
                                + (data.ownerPhone ? data.ownerPhone : '')
                                + '</td><td>'
                                + (data.leaseType ? data.leaseType: '')
                                + '</td><td>'
                                + (data.rent ? data.rent: '')
                                + '</td><td>'
                                + (data.stallType ? data.stallType: '')
                                + '</td><td>'
                                + (data.desc ? data.desc: '')
                                + '</td><td>'
                                + (data.cerficateType ? data.cerficateType: '')
                                + '</td><td>'
                                + (data.publishTime ? data.publishTime: '')
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
        }
    };
})();
//车主（微信）选择页面
var selectCarOwnerId = null;
var selectCarOwnerName = null;
dashboard.carowner.select = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var queryFormRef = null;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            queryFormRef = $('#queryCarOwnerForm');
            tbodyRef = $('#dataCarOwnerContainer tbody');
            tableRef = $('#dataCarOwnerContainer');
            moreRef = $('#showMoreCarOwnerBar');
            /*usernameRef = $('#username');
            nameRef = $('#name');
            phoneNumberRef = $('#phoneNumber');*/
            $('#refreshCarOwnerBtn').bind('click', function () {
                _this.refresh();
            });

            /*$('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });*/

            $('#searchCarOwnerBtn').bind('click', function () {
                _this.refresh();
            });

            $('#resetCarOwnerBtn').bind('click', function () {
                _this.resetQuery();
            });

            /*$("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });*/
            
            $('#okCarOwnerBtn').bind('click', function () {
                _this.returnSelect();
            });

            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
       /* toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },*/
        resetQuery: function () {
            i = 0;
            start = 0;
            queryFormRef[0].reset();
        },
        /*showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'carowners/' + itemId
            });
        },*/
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
                block: '#showMoreCarOwnerBar',
                type: 'GET',
                url: '/api/v1/dashboard/identity/carowners',
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
                        moreRef.html('<div class="well text-center"><em>目前无车主信息.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        /*var subscribeStatus = data.subscribeStatus;
                        subscribeStatus = subscribeStatus ? subscribeStatus : '';
                        if(subscribeStatus){
                        	subscribeStatus = subscribeStatus == 'subscribe' ? '关注' : '取消关注';
                        }*/
                        
                        var tr = $('<tr><td>'
                            + (++i)
                            + '</td><td>'
                            + '<input name="carOwnerRadio" type="radio" value="'+data.id+'" carOwnerName="'+data.name+'" />'
                            + '</td><td>'
                            //+ '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                            + (data.displayName ? data.displayName : (data.name ? data.name : '无'))
                            //+ '</a>'
                            + '</td><td>'
                            + (data.name ? data.name : '')
                            + '</td><td>'
                            + (data.phoneNumber ? data.phoneNumber : '')
                            + '</td><td>'
                            + (data.plateNumber ? data.plateNumber: '')
                            /*+ '</td><td>'
                            + subscribeStatus*/
                            + '</td></tr>');
                        
                        /*tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            //_this.showDetail($(detailBtn.target).attr('dataId'));
                        });*/

                        tbodyRef.append(tr);
                    }

                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreCarOwnerBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreCarOwnerBtn', 'click', function () {
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
        returnSelect:function(){
        	//$(":radio[checked]"):获取被选择的单选框
        	//$("input[name='carOwnerRadio']:radio[checked]"):获取name为carOwnerRadio的被选择的单选框
        	var id = $("input[name='carOwnerRadio']:radio[checked]").val();
        	var name = $("input[name='carOwnerRadio']:radio[checked]").attr('carOwnerName');
        	if(id==null||id==''){
        		return;
        	}
        	$(selectCarOwnerId).val(id);
        	$(selectCarOwnerName).val(name);
        	
        	$('#cancelCarOwnerBtn').trigger("click");//触发id为cancelBtn的按钮click事件
        },
        selectCarOwner:function(selectDiv,carOwnerId_,carOwnerName_) {
        	selectCarOwnerId = carOwnerId_;
        	selectCarOwnerName = carOwnerName_;

        	//每次打开对话框之前移除数据，使得可以顺利重新加载
        	$(selectDiv).removeData("bs.modal");
        	
        	$(selectDiv).modal({
            	remote: '/dashboard/identity/select'
            });
        }
    };
})();