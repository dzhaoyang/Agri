$.namespace('carowner.favorites');
carowner.favorites.list = (function () {
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

            totalheight = 0;
            $(window).scroll(function () {
                _this.loadData();
            });
            
            listContainerRef.delegate('div[name="content"]', 'click', function (event) {
            	var id = $(event.currentTarget).attr('dataid');
            	$('#handle_'+id).toggle();
            });

            listContainerRef.delegate('a[name="parkingInfoBtn"]', 'click', function (event) {
                window.location = "/app/carowner/parking/" + $(event.currentTarget).attr('dataId');
            });

            listContainerRef.delegate('a[name="bookingBtn"]', 'click', function (event) {
                window.location = "/app/carowner/bookings/new?parkingId=" + $(event.currentTarget).attr('dataId');
            });

            listContainerRef.delegate('a[name="deleteBtn"]', 'click', function (delBtn) {
                _this.removeById($(delBtn.currentTarget).attr('dataId'));
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
                url: '/api/v1/carowner/favorites',
                dataType: 'json',
                data: {
                    start: start,
                    limit: 10
                },
                success: function (json) {
                    if (!json.succeed) {
                        carowner.utils.warning(json.message);
                        return;
                    }

                    start++;
                    if (json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无收藏记录.</em></div>');
                        return;
                    }

                    carowner.utils.show(listContainerRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var item = [];
                        item.push(' <a class="list-group-item" href="/app/carowner/parking/' + data.parking.id + '">');
                        item.push(' <div  class="thumbnail" style="float: left;margin-right:10px;width:69px;"> <img src="/img/taxi.png" alt="..." ></div>');
                        item.push(' <h4 class="list-group-item-heading" style="width:90%;">' + data.parking.name + ' ');
                        item.push(' </h4>');
                        item.push(' <p style="float: right;"><img src="/img/car.png" class="img-gray" style="height: 20px;width: 20px;"/> ' + (data.parking.parkingLot ? data.parking.parkingLot.available + "个空闲" : "车位已满") + '</p>');
                        item.push(' <p style="width: 90%;"><img src="/img/shop.png" class="img-gray" style="height: 20px;width: 20px;"/> ' + (data.parking.farePerUnit ? data.parking.farePerUnit + "元/小时" : "免费") + ' </p>');
                        item.push(' <p class="list-group-item-text"><img src="/img/pin.png" class="img-gray" style="height: 20px;width: 20px;"/> ' + data.parking.address + '</p>');
                        item.push(' </a>');
                        var $item = $(item.join(''));
                        listContainerRef.append($item);
                    }


                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<a style="width:100%" id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a>');
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
        },
        removeById: function (id) {
            carowner.dialog.confirm('确认',
                '是否取消收藏选您选中的停车场?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = '/api/v1/carowner/favorites/' + id + "/remove";
                        carowner.utils.ajax({
                            block: '#operation' + id,
                            type: 'GET',
                            url: deleteUserEndpoint,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    carowner.message.warning(json.message);
                                    return;
                                }
                                _this.refresh();
                            }
                        });
                    }
                });
        }
    }
})();