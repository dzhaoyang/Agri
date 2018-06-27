$.namespace('dashboard.parkingProfile');

dashboard.parkingProfile.base = (function () {
	return {
		apiUrl: dashboard.utils.getApiUrl() + '/parkings',
		pageUrl: dashboard.utils.getPageUrl() + '/parkings'
	};
})();

dashboard.parkingProfile.map = (function () {
	return {
		init: function() {
			if($('#baiduMap').size() == 0) {
				return;
			}
			
            var map = new BMap.Map("baiduMap");
            //chengdu
            var defaultPoint = new BMap.Point(104.0723457315, 30.663536680066);
            
            var marker = null;
            if ($('#latitude').val() != "" && $('#longitude').val() != "") {
            	var parkingPoint = new BMap.Point($('#longitude').val(), $('#latitude').val());
                marker = new BMap.Marker(parkingPoint);
                map.centerAndZoom(parkingPoint, 15);
            } else {
                marker = new BMap.Marker(defaultPoint);
                map.centerAndZoom(defaultPoint, 13);
            }
            map.addControl(new BMap.NavigationControl());
            map.enableScrollWheelZoom();

            map.addOverlay(marker);
		}
	};
})();

dashboard.parkingProfile.photos = (function () {
	var _this = null;
	var idRef = null;
	var photoListRef = null, morePhotoRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#profileId');
            photoListRef = $('#photoList');
            morePhotoRef = $('#morePhotoBar');
            _this.refreshPhotos();
            
		},
	    refreshPhotos: function() {
	    	dashboard.utils.show(morePhotoRef);
	    	dashboard.utils.hide(photoListRef);
	    	
	        dashboard.utils.ajax({
	        	block: '#morePhotoBar',
	            type: 'GET',
	            url: dashboard.parkingProfile.base.apiUrl + '/' + idRef.val() + '/photos',
	            dataType: 'json',
	            success: function (json) {
	                if (!json.succeed) {
	                    dashboard.message.warning(json.message);
	                    return;
	                }
	                
	                if (json.data == null || json.data.length == 0) {
	                	morePhotoRef.html('<div class="well text-center"><em>无照片.</em></div>');
	                	return;
	                }
	
	                photoListRef.html('');
	                var html = '';
	                html += '<div class="row">';
	                for (var p in json.data) {
	                    var data = json.data[p];
	                    html += '<div class="col-xs-6 col-md-4">';
	                    html += '<a target="_blank" href="/media/photo/' + data.photoFileName + '" class="thumbnail">';
	                    var photoPath = data.thumbnailFileName ? data.thumbnailFileName : data.photoFileName;
	                    html += '<img class="img-responsive" src="/media/photo/' + photoPath + '" alt="点击查看大图">';
	                    html += '</a>';
	                    html += '</div>';
	                }
	                html += '</div>';
	                photoListRef.html(html);
	                
	                dashboard.utils.show(photoListRef);
	                dashboard.utils.hide(morePhotoRef);
	            }
	        });
	    }
	};
})();

dashboard.parkingProfile.appraisals = (function () {
	var _this = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var idRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#profileId');
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
				url : dashboard.utils.getApiUrl() + '/appraisalHistories/parking/' + idRef.val(),
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
						moreRef.html('<div class="well text-center"><em>无评价.</em></div>');
						return;
					}
					
					dashboard.utils.show(tableRef);
					for ( var p in json.data.content) {
						var data = json.data.content[p];
						var tr = $("<tr "+
								"><td>"+(++i)+
								"</td><td>"+data.appraisalLevel.label+
								"</td><td>"+data.star+
								"</td><td>"+data.createAt+
								"</td><td>"+data.creatorName+
								'<td><a id="detailBtn" class="btn btn-primary btn-xs" target="_blank" href="' + dashboard.utils.getPageUrl() + '/appraisal/histories/' + data.id + '">详情</button>'+
								'</td></tr>');
						tr.delegate('#detailBtn', 'click', function(delBtn) {
							//_this.removeById($(delBtn.target).attr('dataId'));
						});
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

dashboard.parkingProfile.employees = (function () {
	var _this = null;
	var i = 0;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var idRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#profileId');
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
				tbodyRef.empty();
				dashboard.utils.hide(tableRef);
				dashboard.utils.show(moreRef);
			}

			moreRef.empty();
			dashboard.utils.ajax({
				block : moreRef,
				type : 'GET',
				url : dashboard.utils.getApiUrl() + '/parkings/' + idRef.val() + '/employees',
				dataType : 'json',
				success : function(json) {
					if (!json.succeed) {
						dashboard.utils.warning(json.message);
						return;
					}

					$('#recordSize').html('('+json.data.totalElements+')');
					if (json.data == null || json.data.totalElements == 0) {
						moreRef.html('<div class="well text-center"><em>无员工.</em></div>');
						return;
					}
					
					dashboard.utils.hide(moreRef);
					dashboard.utils.show(tableRef);
                    var ei = 0;
                    for (var p in json.data) {
                        var data = json.data[p];
                        var tr = $('<tr><td>'
                            + (++ei)
                            + '</td><td>'
                            + data.username
                            + '</td><td>'
                            + (data.name ? data.name : '')
                            + '</td><td>'
                            + (data.phoneNumber ? data.phoneNumber : '')
                            + '</td><td>'
                            + (data.manager ? '管理员' : '员工')
                            + '</td><td>'
                            + (data.enabled ? '已启用' : '<span style="color:red;">已禁用<span>')
                            + '</td></tr>');
                        tbodyRef.append(tr);
                    }
				}
			});
		}
	};
})();

dashboard.parkingProfile.bookings = (function () {
	var _this = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var idRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#profileId');
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
				url : dashboard.utils.getApiUrl() + '/bookings/parking/' + idRef.val(),
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
						moreRef.html('<div class="well text-center"><em>无预定.</em></div>');
						return;
					}
					
					dashboard.utils.show(tableRef);
					for ( var p in json.data.content) {
						var data = json.data.content[p];
						var tr = $('<tr '+
								'><td>'+(++i)+
								'</td><td>'+
								'<a target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/users/' + data.carOwner.id + '/profile/index">'+
								data.carOwner.name+
								'</a>'+
								'</td><td>'+data.phoneNumber1+
								'</td><td>'+(data.licensePlateNumber ? data.licensePlateNumber : '')+
								'</td><td>'+data.bookingLots+
								'</td><td>'+data.bookingDay.substring(0, 10)+
								'</td><td>'+_this.statusLabel(data.status)+
								'<td><a id="detailBtn" class="btn btn-primary btn-xs" href="javascript:void(0)" dataId="' + data.id + '">详情</button>'+
								'</td></tr>');
						tr.delegate('#detailBtn', 'click', function(btn) {
							_this.showDetail($(btn.target).attr('dataId'));
						});
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
		},
		statusName: function(status) {
			return {
				'booking': '待确认',
				'confirmed': '已确认',
				'cancelled': '已取消',
				'closed': '关闭'
			}[status];
		},
		statusLabel: function(status) {
			var labelClass = {
				'booking': 'label-info',
				'confirmed': 'label-success',
				'cancelled': 'label-warning',
				'closed': 'label-default'
			}[status];
			return '<span class="label ' + labelClass + '">' + _this.statusName(status) + '</span>';
		},
		showDetail: function(id) {
            $('#detailBox').modal({
                remote: dashboard.parkingProfile.base.pageUrl + '/' + idRef.val() + '/profile/bookings/' + id
            });
		}
	};
})();

dashboard.parkingProfile.favorites = (function () {
	var _this = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var idRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#profileId');
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
				url : dashboard.parkingProfile.base.apiUrl + '/' + idRef.val() + '/favorites',
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
						moreRef.html('<div class="well text-center"><em>无用户收藏.</em></div>');
						return;
					}
					
					dashboard.utils.show(tableRef);
					for ( var p in json.data.content) {
						var data = json.data.content[p];
						var tr = $('<tr '+
							'><td>'+(++i)+
							'</td><td>'+
							'<a target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/users/' + data.creatorId + '/profile/index">'+
							data.creatorName+
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

dashboard.parkingProfile.statuses = (function () {
	var _this = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	var idRef = null;
	return {
		init: function() {
			_this = this;
			idRef = $('#profileId');
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
				url : dashboard.parkingProfile.base.apiUrl + '/' + idRef.val() + '/statuses',
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
						moreRef.html('<div class="well text-center"><em>无状态变更记录.</em></div>');
						return;
					}
					
					dashboard.utils.show(tableRef);
					for ( var p in json.data.content) {
						var data = json.data.content[p];
						var tr = $('<tr '+
							'><td>'+(++i)+
							'</td><td>'+data.fromStatus.label+
							'</td><td>'+data.toStatus.label+
							'</td><td>'+data.changedUser.name+
							'</td><td>'+data.changedAt+
							'</td><td>'+(data.memo ? data.memo : '')+
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

