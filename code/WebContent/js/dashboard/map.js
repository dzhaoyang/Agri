$.namespace('dashboard.parkings.map');
dashboard.parkings.map = (function() {
	var _this = null;
	var map = null;
	return {
		init : function() {
			_this = this;
			map = new BMap.Map("allmap");                        // 创建Map实例
			var defaultPoint = new BMap.Point(104.0723457315, 30.663536680066);
			map.centerAndZoom(defaultPoint, 14);     // 初始化地图,设置中心点坐标和地图级别
			map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
			map.addControl(new BMap.ScaleControl());                    // 添加比例尺控件
			map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
			map.enableScrollWheelZoom();                            //启用滚轮放大缩小
			//map.addControl(new BMap.MapTypeControl());          //添加地图类型控件
			map.setCurrentCity("成都");          // 设置地图显示的城市 此项是必须设置的
			
			$('#refreshBtn').click(function(){
				map.centerAndZoom(defaultPoint, 14);
			});
			
			_this.loadParkings();
		},
		loadParkings : function(){
			map.clearOverlays();
			$.get('/api/v1/dashboard/parkings/all4map',function(result){
				 if (!result.succeed) {
                     dashboard.message.warning(result.message);
                     return;
                 }
	            $(result.data).each(function (index, parking) {
	                _this.createParkingMarker(parking);
	            });
			});
		},
		createParkingMarker : function(parking){
			var pt = new BMap.Point(parking.coordinate.longitude, parking.coordinate.latitude);
		    var available = parking.parkingLot.available;
		    var myIcon = null;
	    	if (available > 15) {
				myIcon = new BMap.Icon("/img/map_marker_green_24.png",
						new BMap.Size(24, 32));
			} else if (available >= 5 && available <= 15) {
				myIcon = new BMap.Icon("/img/map_marker_yellow_24.png",
						new BMap.Size(24, 32));
			} else {
				myIcon = new BMap.Icon("/img/map_marker_red_24.png",
						new BMap.Size(24, 32));
			}
		   
		    var marker = new BMap.Marker(pt, {
		        icon: myIcon
		    }); // 创建标注
		    // marker.setTitle(item.name);
		    map.addOverlay(marker); // 将标注添加到地图中
		    marker.addEventListener('click', function () {
		        this.openInfoWindow(_this.createParkingInfoWindow(parking));
		    });
		    return marker;
		},
		createParkingInfoWindow : function(parking){
			var totalLots = !parking.totalLots || parking.totalLots == 'null' ? '' : parking.totalLots;
		    var available = parking.parkingLot.available;
		    var fare = !parking.farePerUnit?'未知':parking.farePerUnit+'元/小时';
		    
		    var icon = null;
	    	if(available>15){
		    	icon = "/img/map_marker_green_24.png";
		    }else if(available>=5 && available<=15){
		    	icon = "/img/map_marker_yellow_24.png";
		    }else{
		    	icon = "/img/map_marker_red_24.png";
		    }
		    var html = '<div class="color:white"><h4 style="margin-bottom:0.5em;margin-top:0.5em;border-bottom:1px solid white;padding-bottom:5px;">'
		        + '<img src="'+icon+'" style="margin-right:5px;">'
		    	+ parking.name + '</h4>';
		    html += '<p style="margin:0;line-height:1.5;font-size:12px;">车位:  '
		        + available + '个空闲&nbsp&nbsp&nbsp单价: '+fare+'</p>';
		    html = html + '<p style="margin:0;line-height:1.5;font-size:12px;">地址:  '
		    + parking.address + '</p>';
		    html = html
		        + '<p style="margin:0;line-height:1.5;font-size:10px;margin-top:20px">'
//		        +'<a class="mapBtn" href="'
//					intent:// map/direction?origin=latlng:'
//					+ lat
//					+ ','
//					+ lng
//					+ '|name:我的位置&destination=latlng:'
//					+ parking.coordinate.latitude
//					+ ','
//					+ parking.coordinate.longitude
//					+ '|name='
//					+ parking.name
//					+ '&mode=driving&region='
//					+ parking.city
//					+ '&src=sunsea|wepark"
		        //+ 'javascript:goToNavigation({lat:'+parking.coordinate.latitude+',lng:'+parking.coordinate.longitude+',name:\''+parking.name+'\'})"'
//		        + 'javascript:goToNavigation(\'' + parking.id + '\')"'
//		        + '>启动导航</a>' +
//		        '<a href="/app/carowner/bookings/new?parkingId=' + parking.id + '" style="margin-left:10px" class="mapBtn">我要预定</a>' +
		        //'<a href="/api/v1/carowner/favorites/' + parking.id + '/add" style="margin-left:10px" class="mapBtn">收藏</a>' +
		        +'<a target="_blank" href="/dashboard/parkings/' + parking.id + '/profile/index" class="mapBtn" style="">查看详情</a></p>';
		    html += '</div>';
		    return new BMap.InfoWindow(html);			
		}
	}
})();

