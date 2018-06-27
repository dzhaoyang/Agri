$.namespace('carowner.appraisal');
carowner.appraisal.list = (function () {
    var _this = null;
    var idRef = null;
    var i = 0, start = 0, limit = 10;
    var listContainerRef = null, moreRef = null;
    var totalheight = null;
    return {
        init: function () {
            _this = this;
            idRef = $('#id');

            listContainerRef = $('#dataContainer');
            moreRef = $('#showMoreBar');

            $('#saveBtn').click(function () {
                _this.doAppraisal();
            });

            totalheight = 0;
            $(window).scroll(function () {
                _this.loadData();
            });
            this.refresh();

        },
        refresh: function () {
            _this.showMore(true);
        },
        loadData: function () {
            totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
            if ($(document).height() <= totalheight) {
                _this.showMore();
            }
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                limit = 10;
                listContainerRef.empty();
                carowner.utils.hide(listContainerRef);
                carowner.utils.show(moreRef);
            }

            moreRef.show();
            carowner.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/v1/carowner/appraisals?_random='+Math.random(),
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit
                },
                success: function (json) {
                    if (!json.succeed) {
                        carowner.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无评价记录.</em></div>');
                        return;
                    }

                    carowner.utils.show(listContainerRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var item = [];
                        var appraisalLink = data.done ? "/app/carowner/appraisals/" + data.parking.id + '/my?_random='+Math.random() : "/app/carowner/appraisals/appraisal/" + data.id+'?_random='+Math.random();
                        item.push(' <a  class="list-group-item" href="' + appraisalLink + '">');
                        if (!data.done) {
                            item.push(' <p class="list-group-item-heading" style="width:90%;"><span class="glyphicon glyphicon-edit" style="color: red;"></span> ' + data.title + ' ');
                        } else {
                            item.push(' <p class="list-group-item-heading" style="width:90%;"><span class="glyphicon glyphicon-check" style="color: green;"></span> ' + data.title + ' ');
                        }
                        item.push(' </p> ');
                        item.push(' <div  style="position: absolute; right: 15px;top: 25px;"> <span dataId="' + data.parking.id + '" class="glyphicon glyphicon-chevron-right"></span></div>');
                        item.push(' </a>');
                        listContainerRef.append(item.join(''));
                    }


                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                        moreRef.hide();
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        }
    };
})();

carowner.appraisal.histories = (function () {
    var _this = null;
    var idRef = null;
    var i = 0, start = 0, limit = 10;
    var listContainerRef = null, moreRef = null;
    var totalheight = null;
    return {
        init: function () {
            _this = this;
            idRef = $('#parkingId');

            listContainerRef = $('#dataContainer');
            moreRef = $('#showMoreBar');

            totalheight = 0;
            $(window).scroll(function () {
                _this.loadData();
            });
            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        loadData: function () {
            totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
            if ($(document).height() <= totalheight) {
                _this.showMore();
            }
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                limit = 10;
                listContainerRef.empty();
                carowner.utils.hide(listContainerRef);
                carowner.utils.show(moreRef);
            }

            moreRef.show();
            carowner.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/v1/carowner/appraisals/parking/' + idRef.val() + $('#filter').val()+'?_random='+Math.random(),
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit
                },
                success: function (json) {
                    if (!json.succeed) {
                        carowner.utils.warning(json.message);
                        return;
                    }

                    start++;
                    if (json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无评价记录.</em></div>');
                        return;
                    }

                    carowner.utils.show(listContainerRef);
                    var totalStar = 5;
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var item = [];
                        item.push(' <div  class="list-group-item">');
                        item.push(' <h4 class="list-group-item-heading" style="width:90%;">' + data.appraisalLevel.label);
                        item.push(' </h4> ');
                        for (var starIndex = 0; starIndex < data.star; starIndex++) {
                            item.push(' <span class="glyphicon glyphicon-star" style="color: darkorange;"></span>');
                        }
                        for (var starIndex = 0; starIndex < totalStar - data.star; starIndex++) {
                            item.push(' <span class="glyphicon glyphicon-star-empty" style="color: darkorange;"></span>');
                        }
                        item.push(' <p class="list-group-item-text" style="color: #c0c0c0;font-size: smaller;position: absolute;right: 5px;top:10px;">' + data.createAt + '</p>');
                        item.push(' <p class="list-group-item-text">' + (data.carOwnerUser ? data.carOwnerUser.name : "匿名") + ':</p>');
                        item.push(' <p class="list-group-item-text well">' + data.appraisalComment + '</p>');
                        if(data.reply && data.reply.length>0){
                        	 item.push(' <p class="list-group-item-text">停车场回复:</p>');
                             item.push(' <p class="list-group-item-text well">' + data.reply + '</p>');
                        }
                        item.push(' </div>');
                        var $item = $(item.join(''));
                        listContainerRef.append($item);
                    }


                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                        moreRef.hide();
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        }
    };
})();


carowner.appraisal.add = (function () {
    var _this = null;
    return {
        init: function () {
            _this = this;
            $('[name="starBtn"]').each(function () {
                $(this).click(function () {
                    var currentStar = $(this).attr('index');
                    $('#star').val(currentStar);
                    $('[name="starLabel"]').each(function () {
                        $(this).removeClass('glyphicon-star');
                        if ($(this).parent().attr('index') <= currentStar) {
                            $(this).removeClass('glyphicon-star-empty');
                            $(this).addClass('glyphicon-star');
                        } else {
                            $(this).removeClass('glyphicon-star');
                            $(this).addClass('glyphicon-star-empty');
                        }
                    });
                });
            });


            $('#submitBtn').click(function () {
                _this.doAppraisal();
            });
        },
        doAppraisal: function () {
            var messageId = $('#messageId');
            console.info("apprasisalLevel:" + $('[name="apprasisalLevel"]:checked').val());
            console.info("memo:" + $('#memo').val());
            if ($('[name="apprasisalLevel"]:checked').size() == 0) {
                carowner.message.info('请选择评价');
                return;
            }
            if ($('[name="apprasisalLevel"]:checked').val() == "BAD" && $('#memo').val() == "") {
                carowner.message.info('差评请填写评语');
                return;
            }
            carowner.utils.ajax({
                block: '#showMoreBar',
                type: 'POST',
                url: '/api/v1/carowner/appraisals/' + messageId.val() + '/appraisal?_random='+Math.random(),
                dataType: 'json',
                data: $("#appraisalForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        carowner.message.warning(json.message);
                        return;
                    }
                    carowner.message.info('评价已提交!');
                    window.location = '/app/carowner/messages?_random='+Math.random();
                }
            });
        }
    };
})();


