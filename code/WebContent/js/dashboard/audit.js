$.namespace('dashboard.audit');
dashboard.audit.list = (function () {
    var _this = null;
    var filterRef = null, requestDateRef = null, requestByRef = null, ClientAddressRef = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            filterRef = $('#filter');
            requestDateRef = $('#requestDate');
            requestByRef = $('#requestBy');
            ClientAddressRef = $("#ClientAddress");

            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });

            $("#requestDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });

            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });

            $('#resetBtn').bind('click', function () {
                _this.resetQuery();
            });

            $('#refreshBtn').bind('click', function () {
                filterRef.val('');
                _this.refresh(true);
            });

            $("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });
            this.refresh(true);
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: '/dashboard/audits/' + itemId
            });
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            limit = 10;
            filterRef.val('');
            requestDateRef.val('');
            requestByRef.val('');
            ClientAddressRef.val('');
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

            moreRef.empty();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/audits',
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit,
                    status: filterRef.val(),
                    requestAt: requestDateRef.val(),
                    requestBy: requestByRef.val(),
                    ClientAddress: ClientAddressRef.val()
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>无操作日志.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $("<tr " +
                            (data.status == "failed" ? "class='text-danger'" : "class='text-success'") +
                            "><td>" + (++i) +
                            "</td><td>" + (data.requestBy ? data.requestBy : "unknown") +
                            "</td><td>" + data.requestAt +
                            "</td><td>" + data.clientAddress +
                            "</td><td><span style='word-break:break-all;'>" + (data.httpMethod ? data.httpMethod +
                            " " : "") + (data.requestedUrl ? data.requestedUrl : "unknown") +
                            '</span></td><td><a id="detailBtn" class="btn btn-primary btn-xs" href="javascript:void(0);" dataId="' + data.id + '">详情</button></td></tr>');
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
        }
    };
})();
