$.namespace('dashboard.userDetail');
dashboard.userDetail.audit = (function () {
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
        showAuditDetail: function (itemId) {
            $('#auditDetailBox').modal({
                remote: '/dashboard/audits/' + itemId
            });
        },
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
                url: '/api/v1/dashboard/audits/user/' + userId.val(),
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
                            "</td><td>" + data.requestAt +
                            "</td><td>" + data.clientAddress +
                            "</td><td><span style='word-break:break-all;'>" + (data.httpMethod ? data.httpMethod +
                            " " : "") + (data.requestedUrl ? data.requestedUrl : "unknown") +
                            '</span></td>' + 
                            //'<td><a id="detailBtn" class="btn btn-primary btn-xs" href="javascript:void(0);" dataId="' + data.id + '">详情</button></td>' + 
                            '</tr>');
                        tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            _this.showAuditDetail($(detailBtn.target).attr('dataId'));
                        });
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

dashboard.userDetail.authHistory = (function () {
    var _this = null;
    var userId = null;
 
    var iB = 0, startB = 0, limitB = 10;
    var tbodyRefB = null, tableRefB = null, moreRefB = null;
 
    return {
        init: function () {
            _this = this;

            userId = $('#userId');

            tbodyRefB = $('#authenticationHisDataContainer tbody');
            tableRefB = $('#authenticationHisDataContainer');
            moreRefB = $('#authenticationHisShowMoreBar');
            this.refreshAuthenticationHistories(true);
 
        },
        refreshAuthenticationHistories: function () {
            _this.showMoreAuthenticationHistories(true);
        },
        showMoreAuthenticationHistories: function (reset) {
            if (reset) {
                iB = 0;
                startB = 0;
                limitB = 10;
                tbodyRefB.empty();
                dashboard.utils.hide(tableRefB);
                dashboard.utils.show(moreRefB);
            }
            moreRefB.empty();
            dashboard.utils.ajax({
                block: '#authenticationHisShowMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/authentications/user/' + userId.val(),
                dataType: 'json',
                data: {
                    start: startB,
                    limit: limitB
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    startB++;
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRefB.html('<div class="well text-center"><em>无登录日志.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRefB);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $("<tr><td>" + (++iB) +
                            "</td><td>" + data.loginAt +
                            "</td><td>" + data.authType +
                            "</td><td>" + data.ipAddress +
                            "</td><td>" + data.browser +
                            "</td><td>" + data.osFamily + "</td></tr>");

                        tbodyRefB.append(tr);
                    }

                    if (iB < json.data.totalElements) {
                        moreRefB.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMoreAuthenticationHistories();
                        });
                        moreRefB.append(more);
                    }
                    else {
                        moreRefB.hide();
                    }
                }
            });
        }
    };
})();


dashboard.userDetail.parking = (function () {
    var _this = null;
    var userId = null;

    var iC = 0, startC = 0, limitC = 10;
    var tbodyRefC = null, tableRefC = null, moreRefC = null;

    return {
        init: function () {
            _this = this;

            userId = $('#userId');
 
            tbodyRefC = $('#parkingsDataContainer tbody');
            tableRefC = $('#parkingsDataContainer');
            moreRefC = $('#parkingsShowMoreBar');
            this.refreshMyParkings(true);
        },
        refreshMyParkings: function () {
            _this.showMoreMyParkings(true);
        },
        showMoreMyParkings: function (reset) {
            if (reset) {
                iC = 0;
                startC = 0;
                limitC = 10;
                tbodyRefC.empty();
                dashboard.utils.hide(tableRefC);
                dashboard.utils.show(moreRefC);
            }
            moreRefC.empty();
            dashboard.utils.ajax({
                block: '#parkingsShowMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/parkings/user/' + userId.val(),
                dataType: 'json',
                data: {
                    start: startC,
                    limit: limitC,
                    sort: 'name-asc'
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    startC++;
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRefC.html('<div class="well text-center"><em>没有我创建的停车场.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRefC);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var parkingStatus = "";
                        switch (data.status) {
                            case 'CREATED':
                                parkingStatus = '录入';
                                break;
                            case 'PENDING':
                                parkingStatus = '等待';
                                break;
                            case 'PROCESSING':
                                parkingStatus = '处理中';
                                break;
                            case 'ENABLED':
                                parkingStatus = '生效';
                                break;
                            case 'DISABLED':
                                parkingStatus = '禁用';
                                break;
                            case 'DELETED':
                                parkingStatus = '已删除';
                                break;
                            default :
                                parkingStatus = '';

                        }

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++iC);
                        tr.push('</td><td>');
                        tr.push(data.name);
                        tr.push('</td><td>');
                        tr.push(data.address ? data.address : '');
                        tr.push('</td><td>');
                        tr.push(data.phoneNumber ? data.phoneNumber : '');
                        tr.push('</td><td>');
                        tr.push(parkingStatus);
                        tr.push('</td><td>');
                        tr.push(data.createAt ? data.createAt : '');
                        tr.push('</td></tr>');

                        tbodyRefC.append(tr.join(''));
                    }

                    if (iC < json.data.totalElements) {
                        moreRefC.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMoreMyParkings();
                        });
                        moreRefC.append(more);
                    }
                    else {
                        moreRefC.hide();
                    }
                }
            });
        }
    };
})();