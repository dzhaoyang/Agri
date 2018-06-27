$.namespace('dashboard.carOwnerUserDetail');
dashboard.carOwnerUserDetail.audit = (function () {
    var _this = null;
    var userId = null;

    var iA = 0, startA = 0, limitA = 10;
    var tbodyRefA = null, tableRefA = null, moreRefA = null;
    
    return {
        init: function () {
            _this = this;
            userId = $('#userId');
            tbodyRefA = $('#auditDataContainer tbody');
            tableRefA = $('#auditDataContainer');
            moreRefA = $('#auditShowMoreBar');
            $("#auditDetailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });
            this.refreshAudits(true);
        },
//        showAuditDetail: function (itemId) {
//            $('#auditDetailBox').modal({
//                remote: '/dashboard/audits/' + itemId
//            });
//        },
        refreshAudits: function () {
            _this.showMoreAudits(true);
        }, 
        showMoreAudits: function (reset) {
            if (reset) {
                iA = 0;
                startA = 0;
                limitA = 10;
                tbodyRefA.empty();
                dashboard.utils.hide(tableRefA);
                dashboard.utils.show(moreRefA);
            }
            moreRefA.empty();
            dashboard.utils.ajax({
                block: '#auditShowMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/audits/carOwneruser/' + userId.val(),
                dataType: 'json',
                data: {
                    start: startA,
                    limit: limitA
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    startA++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRefA.html('<div class="well text-center"><em>无操作日志.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRefA);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $("<tr " +
                            (data.status == "failed" ? "class='text-danger'" : "class='text-success'") +
                            "><td>" + (++iA) +
                            "</td><td>" + data.createAt +
                            "</td><td>" + data.name.label +
//                            "</td><td>" + data.requestAt +
//                            "</td><td>" + data.clientAddress +
//                            "</td><td><span style='word-break:break-all;'>" + (data.httpMethod ? data.httpMethod +
//                            " " : "") + (data.requestedUrl ? data.requestedUrl : "unknown") +
//                            '</span>'+
                            '</td></tr>');
                            //'</span></td><td><a id="detailBtn" class="btn btn-primary btn-xs" href="javascript:void(0);" dataId="' + data.id + '">详情</button></td></tr>');
//                        tr.delegate('#detailBtn', 'click', function (detailBtn) {
//                            _this.showAuditDetail($(detailBtn.target).attr('dataId'));
//                        });
                        tbodyRefA.append(tr);
                    }

                    if (iA < json.data.totalElements) {
                        moreRefA.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMoreAudits();
                        });
                        moreRefA.append(more);
                    }
                    else {
                        moreRefA.hide();
                    }
                }
            });
        }
    };
})();

dashboard.carOwnerUserDetail.apprisal = (function () {
    var _this = null;
    var userId = null;

    var iD = 0, startD = 0, limitD = 10;
    var tbodyRefD = null, tableRefD = null, moreRefD = null;
 
    return {
        init: function () {
            _this = this;
            userId = $('#userId');
            tbodyRefD = $('#appraisalsDataContainer tbody');
            tableRefD = $('#appraisalsDataContainer');
            moreRefD = $('#appraisalsShowMoreBar');
            this.refreshAppraisals(true);
        },
        refreshAppraisals: function () {
            _this.showMoreAppraisals(true);
        },
        showMoreAppraisals: function (reset) {
            if (reset) {
                iD = 0;
                startD = 0;
                limitD = 10;
                tbodyRefD.empty();
                dashboard.utils.hide(tableRefD);
                dashboard.utils.show(moreRefD);
            }
            moreRefD.empty();
            dashboard.utils.ajax({
                block: '#appraisalsShowMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/appraisalHistories/carowner/' + userId.val(),
                dataType: 'json',
                data: {
                    start: startD,
                    limit: limitD
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    startD++;
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRefD.html('<div class="well text-center"><em>没有该用户的评价.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRefD);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++iD);
                        tr.push('</td><td>');
                        tr.push('<a target="_blank" href="' + dashboard.utils.getPageUrl() + '/parkings/' + data.parking.id + '/profile/index">' + data.parking.name + '</a>');
                        tr.push('</td><td>');
                        tr.push(data.star);
                        tr.push('</td><td>');
                        tr.push(data.appraisalLevel.label);
                        tr.push('</td><td>');
                        tr.push(data.appraisalComment);
                        tr.push('</td><td>');
                        tr.push(data.createAt);
                        tr.push('</td></tr>');

                        tbodyRefD.append(tr.join(''));
                    }

                    if (iD < json.data.totalElements) {
                        moreRefD.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMoreAppraisals();
                        });
                        moreRefD.append(more);
                    }
                    else {
                        moreRefD.hide();
                    }
                }
            });
        }
    };
})();

dashboard.carOwnerUserDetail.booking = (function () {
    var _this = null;
    var userId = null;

    var iE = 0, startE = 0, limitE = 10;
    var tbodyRefE = null, tableRefE = null, moreRefE = null;

    return {
        init: function () {
            _this = this;

            userId = $('#userId');
 
            tbodyRefE = $('#bookingsDataContainer tbody');
            tableRefE = $('#bookingsDataContainer');
            moreRefE = $('#bookingsShowMoreBar');
            this.refreshMyBookings(true);
        },
        refreshMyBookings: function () {
            _this.showMoreMyBookings(true);
        },
        showMoreMyBookings: function (reset) {
            if (reset) {
                iE = 0;
                startE = 0;
                limitE = 10;
                tbodyRefE.empty();
                dashboard.utils.hide(tableRefE);
                dashboard.utils.show(moreRefE);
            }
            moreRefE.empty();
            dashboard.utils.ajax({
                block: '#bookingsShowMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/bookings/carowner/' + userId.val(),
                dataType: 'json',
                data: {
                    start: startE,
                    limit: limitE
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    startE++;
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRefE.html('<div class="well text-center"><em>没有该用户创建的预订.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRefE);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++iE);
                        tr.push('</td><td>');
                        tr.push(data.parking.name);
                        tr.push('</td><td>');
                        tr.push(data.requestAt);
                        tr.push('</td><td>');
                        tr.push(data.bookingDay);
                        tr.push('</td><td>');
                        tr.push(data.bookingLots);
                        tr.push('</td><td>');
                        tr.push(data.requestBy);
                        tr.push('</td></tr>');

                        tbodyRefE.append(tr.join(''));
                    }

                    if (iE < json.data.totalElements) {
                        moreRefE.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMoreMyBookings();
                        });
                        moreRefE.append(more);
                    }
                    else {
                        moreRefE.hide();
                    }
                }
            });
        }
    };
})();

dashboard.carOwnerUserDetail.favorite = (function () {
	var _this = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var idRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#userId');
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
			_this.refresh();
		},
		refresh : function() {
			_this.showMore(true);
		},
		showMore : function(reset) {
			if (reset) {
				i = 0;
				start = 0;
				tbodyRef.empty();
				dashboard.utils.hide(tableRef);
				dashboard.utils.show(moreRef);
			}

			moreRef.empty();
			dashboard.utils.ajax({
				block : '#showMoreBar',
				type : 'GET',
				url : dashboard.utils.getApiUrl() + '/favorites/user/' + idRef.val(),
				dataType : 'json',
				data : {
					start:start,
					limit:limit
				},
				success : function(json) {
					if (!json.succeed) {
						dashboard.utils.warning(json.message);
						return;
					}

					start++;
					$('#recordSize').html('('+json.data.totalElements+')');
					if (json.data == null || json.data.totalElements == 0) {
						moreRef.html('<div class="well text-center"><em>无收藏.</em></div>');
						return;
					}
					
					dashboard.utils.show(tableRef);
					for ( var p in json.data.content) {
						var data = json.data.content[p];
						var tr = $('<tr '+
							'><td>'+(++i)+
							'</td><td>'+
							'<a target="_blank" href="' + dashboard.utils.getPageUrl() + '/parkings/' + data.parking.id + '/profile/index">'+
							data.parking.name+
							'</a>'+
							'</td><td>'+data.createAt+
							'</td></tr>');
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
