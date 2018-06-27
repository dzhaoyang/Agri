$.namespace('dashboard.system.suggestion');
dashboard.system.suggestion.list = (function() {
	var _this = null;
    var i=0, start = 0, limit = 10;
	var tbodyRef = null, tableRef = null, moreRef = null;
	return {
		init : function() {
			_this = this;
			tbodyRef = $('#dataContainer tbody');
			tableRef = $('#dataContainer');
			moreRef = $('#showMoreBar');
			$('#refreshBtn').bind('click', function() {
				_this.refresh(true);
			});
			this.refresh(true);
		},
		refresh : function() {
			_this.showMore(true);
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
				url : '/api/v1/dashboard/system/suggestions',
				dataType : 'json',
				data : {
					start:start,limit:limit
				},
				success : function(json) {
					if (!json.succeed) {
						dashboard.utils.warning(json.message);
						return;
					}

					start++;
					$('#recordSize').html('('+json.data.totalElements+')');
					if (json.data == null || json.data.totalElements == 0) {
						moreRef.html('<div class="well text-center"><em>无用户意见反馈.</em></div>');
						return;
					}
					
					dashboard.utils.show(tableRef);
					for ( var p in json.data.content) {
						var data = json.data.content[p];
						var tr = $("<tr "+
							"><td>"+(++i)+
							"</td><td>"+(data.username)+
							"</td><td>"+data.content+
							"</td><td>"+data.createAt+
							"</td><td>"+(data.userType == 'p'?'停车场':'车主')+
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
 