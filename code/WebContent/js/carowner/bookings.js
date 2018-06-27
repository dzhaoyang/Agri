$.namespace('carowner.bookings');
carowner.bookings.add = (function () {
    var _this = null;
    var detailValidator = null;
    return {
        init: function () {
            _this = this;
            var now =  new Date();
            var dateIndex = 1;
            var bookingDayHtml = '';
            while(dateIndex<=5){
            	var newDate = new Date(now.getTime()+dateIndex*24*60*60*1000);
            	var label = newDate.getFullYear()+'年'+(newDate.getMonth()+1)+'月'+newDate.getDate()+'日';
            	if(dateIndex == 1){
            		label = "明天";
            	}else if(dateIndex == 2){
            		label = "后天";
            	}
            	bookingDayHtml +='<option value="'+newDate.getFullYear()+'-'+(newDate.getMonth()+1)+'-'+newDate.getDate()+' 00:00:00">'
            					+label
            					+'</option>';
            	dateIndex++;
            }
            $("#bookingDay").html(bookingDayHtml);

            detailValidator = $('#bookingForm').validate({
                rules: {
                    'bookingLots': {
                        required: true
                    }
                }
            });

            $('#submitBtn').click(function () {
                _this.save();
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }
            var bookingDay = $('#bookingDay').val();
            if (bookingDay && bookingDay.length > 0) {
                bookingDay += " 00:00:00";
            }
            var data = {
                parking: {
                    id: $('#parkingId').val()
                },
                bookingLots: $('#bookingLots').val(),
                phoneNumber1: $('#phoneNumber1').val(),
                phoneNumber2: $('#phoneNumber2').val(),
                bookingDay: bookingDay,
                licensePlateNumber: $('#licensePlateNumber').val()
            };

            $.ajax({
                type: 'POST',
                url: '/api/v1/carowner/bookings',
                dataType: 'json',
                contentType: 'application/json',
                data: $.stringifyJSON(data),
                success: function (json) {
                    if (!json.succeed) {
                        carowner.message.error(json.message);
                        return;
                    }
                    carowner.message.info('预订成功!');
                    window.location = '/app/carowner/bookings?_random='+Math.random();
                },
                error: function () {
                    carowner.message.error('error');
                }
            });

        }
    }
})();

carowner.bookings.list = (function () {
    var _this = null;
    var idRef = null;
    var i = 0, start = 0, limit = 10;
    var listContainerRef = null, moreRef = null;
    var totalheight = null;
    var statusFlag = null;
    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            statusFlag = $('#statusFlag');

            listContainerRef = $('#dataContainer');
            moreRef = $('#showMoreBar');

            totalheight = 0;
            $(window).scroll(function () {
                _this.loadData();
            });


            $('#allQueryBtn').click(function () {
                _this.statusQuery('all', this);
            });


            $('#confirmedQueryBtn').click(function () {
                _this.statusQuery('confirmed', this);
            });

            $('#processingQueryBtn').click(function () {
                _this.statusQuery('booking', this);
            });

            $('#cancelQueryBtn').click(function () {
                _this.statusQuery('cancelled', this);
            });

            $('#closedQueryBtn').click(function () {
                _this.statusQuery('closed', this);
            });

            $('#cancelBtn').click(function () {
                _this.cancelBooking();
            });
            
            $('#bookingBtn').click(function(){
            	window.location.href="/app/carowner/booking/avaliable";
            });

            listContainerRef.delegate('div[name="content"]', 'click', function (event) {
                var id = $(event.currentTarget).attr('dataid');
                $('#handle_' + id).toggle();
            });

            listContainerRef
                .delegate(
                'a[name="parkingInfoBtn"]',
                'click',
                function (event) {
                    window.location = "/app/carowner/parking/"
                        + $(
                            event.currentTarget)
                            .attr(
                            'dataId') + '?_random='+Math.random();
                    targetBtn.stopPropagation();
                });

            listContainerRef.delegate('a[name="deleteBtn"]', 'click', function (event) {
                _this.showDetail($(event.currentTarget).attr(
                    'dataId'));
            });

            _this.refresh();

        },
        statusQuery: function (bookingStatus, btn) {
            $('#topbarBtns').collapse('hide');
            $('#bookingStatus').val(bookingStatus);
            $(btn).append(statusFlag);
            _this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        loadData: function () {
            totalheight = parseFloat($(window).height())
                + parseFloat($(window).scrollTop());
            if ($(document).height() <= totalheight) {
                _this.showMore();
            }
        },
        cancelBooking: function () {
            var bookingId = $('#bookingId');
            carowner.utils.ajax({
                block: '#showMoreBar',
                type: 'POST',
                url: '/api/v1/carowner/bookings/' + bookingId.val() + '/remove?_random='+Math.random(),
                dataType: 'json',
                data: $("#cancelBookingForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        carowner.utils.warning(json.message);
                        return;
                    }
                    carowner.message.info('取消预订成功!');
                    $("#cancelBookingForm")[0].reset();
                    _this.refresh();
                }
            });
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
            carowner.utils
                .ajax({
                    block: '#showMoreBar',
                    type: 'GET',
                    url: '/api/v1/carowner/bookings?_random='+Math.random(),
                    dataType: 'json',
                    data: {
                        start: start,
                        limit: 10,
                        bookingStatus: $('#bookingStatus').val()
                    },
                    success: function (json) {
                        if (!json.succeed) {
                            carowner.utils.warning(json.message);
                            return;
                        }

                        start++;
                        $('#recordSize').html(
                                '(' + json.data.content.totalElements + ')');
                        if (json.data.content == null || json.data.content.length == 0) {
                            moreRef
                                .html('<div class="well text-center"><em>无预定记录.</em></div>');
                            return;
                        }

                        carowner.utils.show(listContainerRef);
                        var totalStar = 5;
                        for (var p in json.data.content) {
                            var data = json.data.content[p];

                            var item = [];

                            item.push(' <a class="list-group-item" href="/app/carowner/parking/' + data.parkingId + '">');
                            item.push(' <div  class="thumbnail" style="float: left;margin-right:10px;width:69px;"> <img src="/img/taxi.png" alt="..." ></div>');
                            item.push(' <p style="float: right;font-size: small;color: #c0c0c0;;" class="list-group-item-text">' + data.requestAt + '</p>');
                            item.push(' <h4 class="list-group-item-heading" style="width:90%;">' + data.name + ' ');
                            item.push(' </h4>');
                            item.push(' <p style="float: right;"><img src="/img/car.png" class="img-gray" style="height: 20px;width: 20px;"/> ' + data.bookingLots + '(' + data.bookingStatus + '）</p>');
                            item.push(' <p style="width: 90%;"><img src="/img/shop.png" class="img-gray" style="height: 20px;width: 20px;"/> 预计' + data.bookingDay + ' </p>');
                            item.push(' <p class="list-group-item-text"><img src="/img/pin.png" class="img-gray" style="height: 20px;width: 20px;"/> ' + data.address + '</p>');
                            item.push(' </a>');
                            var $item = $(item.join(''));
                            listContainerRef.append($item);
                        }

                        if (i < json.data.totalElements) {
                            moreRef.empty();
                            var more = $('<a style="width:100%" id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a>');
                            more.delegate('#showMoreBtn', 'click',
                                function () {
                                    _this.showMore();
                                });
                            moreRef.append(more);
                            moreRef.hide();
                        } else {
                            moreRef.hide();
                        }
                    }
                });
        },
        showDetail: function (id) {
            $('#bookingId').val(id);
            $('#detailBox').modal({});
        }
    }
})();