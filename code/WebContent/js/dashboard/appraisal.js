$.namespace('dashboard.appraisal.summary');
dashboard.appraisal.summary.list = (function() {
	var _this = null;
	var filterNameRef = null;
    var sortRef = null;
    var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	return {
		init : function() {
			_this = this;
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
            sortRef = $('#orderBy');
			filterNameRef = $('#filterName');
			$('#refreshBtn').bind('click', function() {
				filterNameRef.val('');
				_this.refresh(true);
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
			this.refresh(true);
		},
		refresh : function() {
			_this.showMore(true);
		},
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            filterNameRef.val('');
            sortRef.val('name-asc');
        },
		showMore : function(reset) {
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
				block : '#showMoreBar',
				type : 'GET',
				url : '/api/v1/dashboard/appraisals',
				dataType : 'json',
				data : {
					start:start,
					limit:limit,
					parkingName:filterNameRef.val()
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
					$.each(json.data.content,function(index,data){
						var tr = $("<tr "+
								"><td>"+(index+1)+
								"</td><td>"+(data.parking.name)+
								"</td><td>"+data.good+
								"</td><td>"+data.normal+
								"</td><td>"+data.bad+
								"</td><td>"+data.star+
								'</td><td><a id="detailBtn" class="btn btn-primary btn-xs" target="_blank" href="'+dashboard.utils.getPageUrl()+'/parkings/'+data.parking.id+'/profile/appraisals">详情</a></td></tr>');
						tr.delegate('#detailBtn', 'click', function(delBtn) {
							//_this.removeById($(delBtn.target).attr('dataId'));
						});
						tbodyRef.append(tr);
					});
					
					if(json.data.content.length < json.data.totalElements) {
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

$.namespace('dashboard.appraisal.history');
dashboard.appraisal.history.base = (function(){
	return {
		apiUrl: dashboard.utils.getApiUrl() + '/appraisalHistories',
		pageUrl: dashboard.utils.getPageUrl() + '/appraisal/histories'
	};
})();
dashboard.appraisal.history.list = (function() {
	var _this = null;
	var filterNameRef = null, filterRef = null;
	var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	return {
		init : function() {
			_this = this;
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
			filterRef = $('#filter');
			filterNameRef = $('#filterName');
			$('#refreshBtn').bind('click', function() {
				filterRef.val('');
				filterNameRef.val('');
				_this.refresh(true);
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
			this.refresh(true);
		},
		refresh : function() {
			_this.showMore(true);
		},
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            filterRef.val('');
            filterNameRef.val('');
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
				url : dashboard.appraisal.history.base.apiUrl,
				dataType : 'json',
				data : {
					start:start,
					limit:limit,
					appraisalLevel:filterRef.val(),
					parkingName:filterNameRef.val()
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
					$.each(json.data.content,function(index,data){
						var carOwnerName = (data.carOwnerUser&&data.carOwnerUser!=null)?data.carOwnerUser.name:'';
						var tr = $('<tr '+
								'><td>'+(++i)+
								'</td><td>'+(data.parking.name)+
								'</td><td>'+data.appraisalLevel.label+
								'</td><td>'+data.star+
								'</td><td>'+data.createAt+
								//'</td><td><a target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/users/' + data.creator.id + '/profile/index">'+data.creatorName+'</a>'+
								'</td><td>'+carOwnerName+
								'<td><a id="detailBtn" class="btn btn-primary btn-xs" href="' + dashboard.appraisal.history.base.pageUrl + '/' + data.id + '">详情</button></td></tr>');
						tr.delegate('#detailBtn', 'click', function(delBtn) {
							//_this.removeById($(delBtn.target).attr('dataId'));
						});
						tbodyRef.append(tr);
					});
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
dashboard.appraisal.history.detail = (function(){
	var _this = null;
	return {
		init: function() {
			_this = this;
			$('#backBtn').bind('click', function(){
				_this.back();
			});
		},
		back: function() {
			window.location.href = dashboard.appraisal.history.base.pageUrl;
		}
	};
})();

