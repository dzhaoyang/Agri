var map;
var touchmove = false;
function initMap() {
	window.refresh = function(){
		map.clearOverlays();
    	loadMyAddressMarker(map);
		loadParkings();
	};
	
    map = new BMap.Map("allmap"); // 创建Map实例
    var point = new BMap.Point(lng, lat);
    map.centerAndZoom(point, 16); // 初始化地图,设置中心点坐标和地图级别。
    // map.enableScrollWheelZoom(); //启用滚轮放大缩小
    //map.addControl(new BMap.ZoomControl());
    loadMyAddressMarker(map);

//    function showInfo(e) {
//        console.log(e.point.lng + ", " + e.point.lat);
//    }

    //map.addEventListener("click", showInfo);
    map.addEventListener("touchmove", function(type,target){
    	touchmove = true;
    });
    
    map.addEventListener("touchend", function(type,target){
    	if(touchmove){
    		touchmove = false;
        	var center = map.getCenter();
        	lng = center.lng;
        	lat = center.lat;
        	map.clearOverlays();
        	loadMyAddressMarker(map);
        	loadParkings();
    	}
    });
    
//    $('#address').autocomplete({
//    	paramName:"address",
//    	serviceUrl: '/app/carowner/place/suggesion',
//    	transformResult: function(response) {
//    		if(!response||response.length==0){
//		    	 return {
//		             suggestions: []
//		         };
//    		}
//    		var data = $.map(JSON.parse(response).results, function(dataItem) {
//    			var value = dataItem.name;
//    			if(dataItem.address){
//    				value += ' -- '+dataItem.address;
//    			}
//                return { value:value , data: dataItem };
//            });
//            return {
//                suggestions: data
//            };
//        },
//        onSelect: function (suggestion) {
//            var address = suggestion.data;
//            var location  = address.location;
//            window.location = '/app/carowner/parking/avaliable?lat='+location.lat+'&lng='+location.lng+'&locationLabel='+address.name+'&r='+Math.random();
//        }
//    });
//    
    $('#exitSearchModelBtn').click(function(){
    	$('div.search-container').hide();
    });
    
    $('#enterSearchModelBtn').click(function(){
    	$('div.search-container').show();
    	$('#addressTxt').focus();
    });
    
    $('#searchBtn').click(function(){
    	doAddressSearch();
    });
}

function doAddressSearch(){
	var address = $("#addressTxt").val();
	if(!address || addressTxt.length==0){
		return;
	}
	$.get("/app/carowner/place/suggesion?address="+address,function(result){
		if(result.results && result.results.length && result.results.length>0){
			var html = '';
			$.each(result.results,function(index, place){
				var name = place.name;
				if(name.length>15){
					name = name.substring(0,15)+'...';
				}
				
				var address = place.address;
				if(address.length>27){
					address = address.substring(0,27)+'...';
				}
				if(searchType == 1){
					html += '<a href="/app/carowner/booking/avaliable?lat='+place.location.lat+'&lng='+place.location.lng+'" class="list-group-item">';					
				}else{
					html += '<a href="/app/carowner/parking/avaliable?lat='+place.location.lat+'&lng='+place.location.lng+'" class="list-group-item">';
				}
				html+='<span class="glyphicon glyphicon-search"></span>';
				html += '<div><h4 class="list-group-item-heading">'+name+'</h4>';
				html += '<p class="list-group-item-text">'+address+'</p></div>';
				html += '</a>';
			});
			$('.addresses-group').html(html);
		}
	});
}

function loadMyAddressMarker(map){
	var point = new BMap.Point(userLng, userLat); // 创建点坐标
    // 创建标注
    var myIcon = new BMap.Icon("/img/iamhere3.png", new BMap.Size(56, 42));
    var myAddressMark = new BMap.Marker(point, {
        icon: myIcon
    });
    map.addOverlay(myAddressMark);
    myAddressMark.addEventListener('click', function () {
        this.openInfoWindow(createFakeInfo());
    });
}

function loadParkings() {
    var data = {
        longitude: lng,
        latitude: lat,
        distance: 1,
        searchType: searchType,
        start: 0,
        limit: 5,
        sortType: 0
    };
    $.ajax({
        type: 'POST',
        url: '/api/v1/carowner/parking/search',
        dataType: 'json',
        contentType: 'application/json',
        data: $.stringifyJSON(data),
        success: function (json) {
            if (!json.succeed) {
                alert(json.message);
                return;
            }
            //$(json.data.content).each(function(index, parking) {
            //	createParkingMarker(parking);
            //});
            $(json.data).each(function (index, item) {
                createParkingMarker(item.content);
            });
        },
        error: function () {
            alert('error');
        }
    });
}

function createFakeInfo() {
    var html = '<h4 style="margin-bottom:0.5em;margin-top:0.5em;border-bottom:1px solid white;padding-bottom:5px;">您在这里</h4>';
    html = html + '<p style="margin:0;line-height:1.5;font-size:13px;">'
        + locationLabel + '</p>';
    return new BMap.InfoWindow(html);
}

function createParkingInfoWindow(parking) {

    var totalLots = !parking.totalLots || parking.totalLots == 'null' ? '' : parking.totalLots;
    var available = parking.parkingLot.available;
    var fare = !parking.farePerUnit?'无':parking.farePerUnit+'元/小时';
    var baseFare = !parking.baseFare?'无':parking.baseFare+'元';
    var name = parking.name;
    if(name.length>5){
    	name = name.substring(0,5)+'..';
    }else{
    	name = name+'  ';
    }
    
    var address = parking.address;
    if(address.length>14){
    	address = address.substring(0,14)+'...';
    }
    
    
    if(!parking.star){
    	$.get('/api/v1/carowner/appraisals/summary/parking/'+parking.id,function(result){
    		if(result.data){
    			parking.star = result.data.star;
    			var starHtml = '';
    			var counter = 1;
    	    	while(counter<=5){
    	    		if(parking.star>=counter){
    	    			starHtml += '<span class="glyphicon glyphicon-star"></span>';
    	    		}else{
    	    			starHtml += '<span class="glyphicon glyphicon-star-empty"></span>';
    	    		}
    	    		counter++;
    	    	}
    			$('#stars').html(starHtml);
    		}
    	});
    }
    
    var icon = null;
    if(searchType == 1){
    	icon = "/img/map_marker_purple.png";
    }else{
    	 if(available>15){
	    	icon = "/img/map_marker_green_24.png";
	    }else if(available>=5 && available<=15){
	    	icon = "/img/map_marker_yellow_24.png";
	    }else{
	    	icon = "/img/map_marker_red_24.png";
	    }
    }
    var html = '<div class="color:white;"><h4 style="margin-bottom:0.5em;margin-top:0.5em;border-bottom:1px solid white;padding-bottom:5px;">'
        + '<img src="'+icon+'" style="margin-right:5px;">'
    	+ name + '<span id="stars" style="font-size:12px;">';
    if(parking.star){
    	var counter = 1;
    	while(counter<=5){
    		if(parking.star>counter){
    			html += '<span class="glyphicon glyphicon-star"></span>';
    		}else{
    			html += '<span class="glyphicon glyphicon-star-empty"></span>';
    		}
    		counter++;
    	}
    }else{
    	var counter = 1;
    	while(counter<=5){
			html += '<span class="glyphicon glyphicon-star-empty"></span>';
			counter++;
    	}
    }
    html += '</span></h4>';
    html += '<p style="margin:0;line-height:1.5;font-size:12px;">车位:  '
        + available + '个空闲</p>';
    html += '<p style="margin:0;line-height:1.5;font-size:12px;">起步价:  '
        + baseFare + '&nbsp&nbsp&nbsp单价: '+fare+'</p>';
    html = html + '<p style="margin:0;line-height:1.5;font-size:12px;">地址:  '
    + address + '</p>';
    html = html
        + '<p style="margin:0;line-height:1.5;font-size:10px;margin-top:20px"><a class="mapBtn" href="'
//			intent:// map/direction?origin=latlng:'
//			+ lat
//			+ ','
//			+ lng
//			+ '|name:我的位置&destination=latlng:'
//			+ parking.coordinate.latitude
//			+ ','
//			+ parking.coordinate.longitude
//			+ '|name='
//			+ parking.name
//			+ '&mode=driving&region='
//			+ parking.city
//			+ '&src=sunsea|wepark"
        //+ 'javascript:goToNavigation({lat:'+parking.coordinate.latitude+',lng:'+parking.coordinate.longitude+',name:\''+parking.name+'\'})"'
        + 'javascript:goToNavigation(\'' + parking.id + '\')"'
        + '>启动导航</a>' +
        //'<a href="/app/carowner/bookings/new?parkingId=' + parking.id + '" style="margin-left:10px" class="mapBtn">我要预定</a>' +//根据三期要求取消“我要预定”按钮 by 2015-01-11
        //'<a href="/api/v1/carowner/favorites/' + parking.id + '/add" style="margin-left:10px" class="mapBtn">收藏</a>' +
        '<a href="/app/carowner/parking/' + parking.id + '" class="mapBtn" style="margin-left:10px;">查看详情</a></p>';
    html += '</div>';
    return new BMap.InfoWindow(html);
}

function createParkingMarker(parking) {
    var pt = new BMap.Point(parking.coordinate.longitude, parking.coordinate.latitude);
    var available = parking.parkingLot?parking.parkingLot.available:0;
    var myIcon = null;
    if(searchType == 1){
    	myIcon = new BMap.Icon("/img/map_marker_purple.png", new BMap.Size(24, 32));
    }else{
    	 if(available>15){
	    	myIcon = new BMap.Icon("/img/map_marker_green_24.png", new BMap.Size(24, 32));
	    }else if(available>=5 && available<=15){
	    	myIcon = new BMap.Icon("/img/map_marker_yellow_24.png", new BMap.Size(24, 32));
	    }else{
	    	myIcon = new BMap.Icon("/img/map_marker_red_24.png", new BMap.Size(24, 32));
	    }
    }
   
    var marker = new BMap.Marker(pt, {
        icon: myIcon
    }); // 创建标注
    // marker.setTitle(item.name);
    map.addOverlay(marker); // 将标注添加到地图中
    marker.addEventListener('click', function () {
        this.openInfoWindow(createParkingInfoWindow(parking));
    });
    return marker;
}
function goToNavigation(parkingId) {
    //var url = 'http://api.map.baidu.com/direction?origin=latlng:' + lat + ',' + lng + '|name:' + locationLabel + '&destination=latlng:' + destination.lat + ',' + destination.lng + '|name:' + destination.name + '&mode=driving&region=' + city + '&output=html';
    var url = "/app/carowner/parking/navigate?parkingId=" + parkingId + '&nonce=' + _nonce;
    window.location.href = url;
}

$.namespace('carowner.parking');
carowner.parking.info = (function () {
    var _this = null;
    var parkingId = null;
    var favoriteAble = 'false';
    return {
        init: function () {
            parkingId = $('#parkingId').val();
            favoriteAble = $('#favoriteAble').val();
            _this = this;

            _this.updateFavoriteAbleStatus(favoriteAble == 'true');


            $('.carousel').carousel('pause');
            $('#navigateBtn').click(function () {
                window.location.href = "/app/carowner/parking/navigate?parkingId=" + parkingId + "&nonce=" + _nonce;
            });

            $('#bookingBtn').click(function () {
                window.location.href = "/app/carowner/bookings/new?parkingId=" + parkingId+'&_random='+Math.random();
            });

            $('#favoriteBtn').click(function () {
                ///api/v1/carowner/favorites/
                carowner.utils.ajax({
                    block: '#favoriteBtn',
                    type: 'GET',
                    url: '/api/v1/carowner/favorites/' + parkingId + "/add",
                    dataType: 'json',
                    success: function (json) {
                        carowner.message.info(json.message ? json.message : "收藏成功!");
                        _this.updateFavoriteAbleStatus(false);
                    }
                });
            });
            $('#delFavoriteBtn').click(function () {
                ///api/v1/carowner/favorites/
                carowner.utils.ajax({
                    block: '#favoriteBtn',
                    type: 'GET',
                    url: '/api/v1/carowner/favorites/' + parkingId + "/remove",
                    dataType: 'json',
                    success: function (json) {
                        carowner.message.info(json.message ? json.message : "取消收藏成功!");
                        _this.updateFavoriteAbleStatus(true);
                    }
                });
            });

            $('#viewAppraisalsBtn').click(function () {
                window.location.href = "/app/carowner/appraisals/" + parkingId;
            });
        },
        updateFavoriteAbleStatus: function (able) {
            if (able) {
                $('#favoriteBtn').show();
                $('#delFavoriteBtn').hide();
            } else {
                $('#favoriteBtn').hide();
                $('#delFavoriteBtn').show();
            }
        }
    }
})();
carowner.parking.list = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var listContainerRef = null, moreRef = null;
    var sortFlag = null;
    var sortTypeRef = null;
    return {
        init: function () {
            _this = this;
            listContainerRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            sortFlag = $('#sortFlag');
            sortTypeRef = $('#sortType');

            $('#sortTypeBar').on('shown.bs.collapse', function () {
                $('#sortTypeBtn').removeClass("glyphicon-chevron-down");
                $('#sortTypeBtn').addClass("glyphicon-chevron-up");
            });
            $('#sortTypeBar').on('hidden.bs.collapse', function () {
                $('#sortTypeBtn').removeClass("glyphicon-chevron-up");
                $('#sortTypeBtn').addClass("glyphicon-chevron-down");
            });

            $('#sortByDistance').click(function () {
                _this.sortBy('0', this);
            });

            $('#sortByLots').click(function () {
                _this.sortBy('5', this);
            });

            $('#sortByFarePerUnit').click(function () {
                _this.sortBy('2', this);
            });

//            listContainerRef.delegate('div[name="content"]', 'click', function (event) {
//                var id = $(event.currentTarget).attr('dataid');
//                $('#handle_' + id).toggle();
//            });
//
//            listContainerRef.delegate('a[name="parkingInfoBtn"]', 'click', function (event) {
//                window.location = "/app/carowner/parking/" + $(event.currentTarget).attr('dataId');
//            });
//
//            listContainerRef.delegate('a[name="bookingBtn"]', 'click', function (event) {
//                window.location = "/app/carowner/bookings/new?parkingId=" + $(event.currentTarget).attr('dataId');
//            });
//            $('#address').autocomplete({
//                paramName:"address",
//                serviceUrl: '/app/carowner/place/suggesion',
//                transformResult: function(response) {
//                    if(!response||response.length==0){
//                        return {
//                            suggestions: []
//                        };
//                    }
//                    var data = $.map(JSON.parse(response).results, function(dataItem) {
//                        var value = dataItem.name;
//                        if(dataItem.address){
//                            value += ' -- '+dataItem.address;
//                        }
//                        return { value:value , data: dataItem };
//                    });
//                    return {
//                        suggestions: data
//                    };
//                },
//                onSelect: function (suggestion) {
//                    var address = suggestion.data;
//                    var location  = address.location;
//                    window.location = '/app/carowner/parking/avaliable?lat='+location.lat+'&lng='+location.lng+'&locationLabel='+address.name+'&showType=list'+'&r='+Math.random();
//                }
//            });
            
            $('#exitSearchModelBtn').click(function(){
            	$('div.search-container').hide();
            });
            
            $('#enterSearchModelBtn').click(function(){
            	$('div.search-container').show();
            	$('#addressTxt').focus();
            });
            
            $('#searchBtn').click(function(){
            	doAddressSearch();
            });
            
            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        sortBy: function (sortType, btn) {
            $('#sortTypeBar').collapse('hide');
            $('#sortType').val(sortType);
            $(btn).append(sortFlag);
            _this.refresh();
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

            var params = {
                longitude: lng,
                latitude: lat,
                distance: 1,
                searchType: searchType,
                start: start,
                limit: limit,
                sortType:sortTypeRef.val()
            };

            moreRef.show();
            carowner.utils.ajax({
                block: '#showMoreBar',
                type: 'POST',
                url: '/api/v1/carowner/parking/search',
                dataType: 'json',
                contentType: 'application/json',
                data: $.stringifyJSON(params),
                success: function (json) {
                    if (!json.succeed) {
                        carowner.message.warning(json.message);
                        return;
                    }
                    start++;
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>附近没有合适的停车场.</em></div>');
                        return;
                    }

                    carowner.utils.show(listContainerRef);
                    var totalStar = 5;
                    for (var p in json.data) {
                        var data = json.data[p];
                        var parkingInfo = data.content;
                        var distance = data.distance;
                        var item = [];
                        item.push(' <a class="list-group-item" href="/app/carowner/parking/' + parkingInfo.id + '">');
                        item.push(' <div  class="thumbnail" style="float: left;margin-right:10px;width:69px;"> <img src="/img/taxi.png" alt="..." ></div>');
                        item.push(' <p style="float: right;font-size: small;color: #c0c0c0;;" class="list-group-item-text">距离我' + distance.value.toFixed(2) + '千米</p>');
                        item.push(' <h4 class="list-group-item-heading" style="width:90%;">' + parkingInfo.name + ' ');
                        item.push(' </h4>');
                        item.push(' <p style="float: right;"><img src="/img/car.png" class="img-gray" style="height: 20px;width: 20px;"/> ' + (parkingInfo.parkingLot ? parkingInfo.parkingLot.available + "个空闲" : "车位已满") + '</p>');
                        item.push(' <p style="width: 90%;"><img src="/img/shop.png" class="img-gray"  style="height: 20px;width: 20px;"/> ' + (parkingInfo.farePerUnit ? parkingInfo.farePerUnit + "元/小时" : "免费") + ' </p>');
                        item.push(' <p class="list-group-item-text"><img src="/img/pin.png" class="img-gray"  style="height: 20px;width: 20px;"/> ' + parkingInfo.address + '</p>');
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
        }
    }
})();