$.namespace('dashboard.ernieRecord');
dashboard.ernieRecord.list = (function () {
    var _this = null;
    var usernameRef = null, emailRef = null, nameRef = null, phoneNumberRef = null, roleRef = null, groupRef = null;
    var i = 0, start = 0, limit = 50;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            
            $("#createTimeStart").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            $("#createTimeEnd").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
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
            
            $('#saveBtn').live('click', function () {
                _this.seveToPass();
            });
            
            $('#awardTime').live('click', function () {
            	$('#awardTime').datetimepicker({todayButton:false});
            });
            
            $("#passBox").on("hidden.bs.modal", function () {
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
            $('#queryForm')[0].reset();
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                tbodyRef.empty();
                dashboard.utils.hide(tableRef);
                dashboard.utils.show(moreRef);
            }

            moreRef.show();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/ernieRecord/list',
                dataType: 'json',
                data: $('#queryForm').serialize() + '&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无纪录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var gradeName = '';
                        if(data.grade==0){
                        	gradeName = '无';
                        }else if(data.grade==1){
                        	gradeName = '一等奖';
                        }else if(data.grade==2){
                        	gradeName = '二等奖';
                        }else if(data.grade==3){
                        	gradeName = '三等奖';
                        }else if(data.grade==4){
                        	gradeName = '四等奖';
                        }else if(data.grade==5){
                        	gradeName = '五等奖';
                        }else{
                        	gradeName = '无';
                        }
                        var sex = '';
                        if(data.sex==1){
                        	sex = '男';
                        }else if(data.sex==2){
                        	sex = '女';
                        }
                        var tr = $('<tr><td style="text-align: center;padding: 1px;">'
                            + (++i)
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + data.customer.nickname
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + sex
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + (data.phone ? data.phone : '')
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + (data.createTime ? data.createTime : '')
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + (data.prizeName?data.prizeName:'') + '('+gradeName+')'
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + (data.isConfirm==1?'是':'否')
                            + '</td><td style="text-align: center;padding: 1px;" id="isAwarded_td_'+data.id + '">'
                            + (data.isAwarded==1?'已领':'未领')
                            + '</td><td style="text-align: center;padding: 1px;" id="awardTime_td_'+data.id + '">'
                            + (data.awardTime ? data.awardTime : '')
                            + '</td><td style="text-align: center;padding: 1px;" id="sendPrizer_td_'+data.id + '">'
                            + (data.sendPrizer ? data.sendPrizer : '')
                            + '</td><td style="text-align: center;padding: 1px;">'
                            + '<span class="operation btn-group" id="operation' + data.id + '" style="'+(data.isConfirm==1?'':'display:none;')+'">'
                            + '<a id="passBtn" href="javascript:void(0);" class="btn btn-info btn-xs" dataId="' + data.id + '">领奖</a>'
                            + '</span>'
                            + '</td></tr>');
                        
                        tr.delegate('#passBtn', 'click', function (delBtn) {
                            _this.pass($(delBtn.target).attr('dataId'));
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
        pass: function (itemId) {
        	 $('#passBox').modal({
                 remote: '/ernieRecord/pass/' + itemId
             });
        },
        seveToPass: function () {
        	var id = $('#id').val();
        	dashboard.utils.ajax({
                block: '.form-actions',
                type: 'POST',
                url: '/api/ernieRecord/pass/',
                dataType: 'json',
                data: $('form').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    $('#isAwarded_td_'+id).html('已领');
                    $('#awardTime_td_'+id).html($('#awardTime').val());
                    $('#sendPrizer_td_'+id).html($('#sendPrizer').val());
                    dashboard.message.info('保存成功!');
                }
            });
        }
    }
})();
