$.namespace('dashboard.greenHouse');
dashboard.greenHouse.list = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
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

            $("#detailBox").on("hidden.bs.modal", function () {
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
            limit = 10;
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'users/' + itemId
            });
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

            moreRef.show();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/greenHouse/list',
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $('<tr><td style="text-align: center;">'
                            + (++i)
                            + '</td><td style="text-align: center;">'
                            //+ '<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">'
                            + data.name
                            //+ '</a>'
                            + '</td><td style="text-align: center;">'
                            + (data.varsion ? data.varsion : '')
                            + '</td><td style="text-align: center;">'
                            + (data.mapUpdateTime ? data.mapUpdateTime : '')
                            + '</td><td style="text-align: center;">'
                            + (data.createTime ? data.createTime : '')
                            + '</td><td style="text-align: center;">'
                            + '<span class="operation btn-group" id="operation' + data.id + '">'
                            + '<a href="/greenHouse/edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>'
                            + '<a id="deleteBtn" href="javascript:void(0);"  style="margin-left: 10px;" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>'
                            + '</span>'
                            + '</td></tr>');
                        tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });

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
        },
        removeById: function (id) {
            dashboard.dialog.confirm('确认',
                '是否删除选中大棚?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = '/api/greenHouse/delete/' + id;
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'DELETE',
                            url: deleteUserEndpoint,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                _this.refresh();
                            }
                        });
                    }
                });
        }
    };
})();


dashboard.greenHouse.view = (function () {
    var _this = null;
    var baseValidator = null;
    var detailValidator = null;
    var idRef = null;
    var editable = false;
    var scaling = 1;//缩放比例
    var refreshOperationListIsRun = false;
    var i = 0, start = 0, limit = 10;

    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            
            /*$('.elementIcon').bind('click', function () {
                _this.showElement($(this).attr('data-id'),$(this).attr('data-type'),$(this).attr('data-datatype'));
            }).bind('mouseover', function () {
            	$(this).css("cursor","Pointer");
            });*/
            
            $('#img_div').delegate(".elementIcon","click",function(){
            	_this.showElement($(this).attr('data-id'),$(this).attr('data-type'),$(this).attr('data-datatype'));
            });
            
            $('#img_div').delegate(".elementIcon","mouseover",function(){
            	$(this).css("cursor","Pointer");
            });
            
            $('#fullScreenBtn').bind('click', function () {
                _this.fullScreen();
            });
            
            $('#switchBox').delegate("#closeSwitchBtn","click",function(){
            	_this.closeSwitch(this);
            });
            $('#switchBox').delegate("#openSwitchBtn","click",function(){
            	_this.openSwitch(this);
            });
            $('#elementBox').delegate("#monitorTodayCloseBtn","click",function(){
            	$('#monitorTodayBaseForm').submit();
            });
            _this.initializeCreate();
        },
        initializeCreate: function () {
        	$('#img_img').load(function(){
        		// 图片加载完成
        		//var img_img_X = $('#img_img').offset().top;
            	//var img_img_Y = $('#img_img').offset().left;
            	var img_img_h = $('#img_img').height();
            	var img_img_w = $('#img_img').width();
            	scaling = img_img_w/$('#mapWidth').val();
            	//alert('img_img_X='+img_img_X+';img_img_Y='+img_img_Y+';img_img_h='+img_img_h+';img_img_w='+img_img_w+';scaling='+scaling);
            	//var $topRightDiv = $('#topRightDiv');
            	//$topRightDiv.css("left",img_img_w-$topRightDiv.width()-10);
            	
            	var $bottomLeftDiv = $('#bottomLeftDiv');
            	$bottomLeftDiv.css("top",img_img_h-$bottomLeftDiv.height()-10);
        	});
        	
        	$('.elementIcon').each(function(){
        		/*var id = $(this).attr('id');
        		var data_id = $(this).attr('data-id');
        		var data_type = $(this).attr('data-datatype');*/
        		var type = $(this).attr('data-type');
        		var datatype = $(this).attr('data-datatype');
        		var coordinateX = $(this).attr('data-coordinatex');
        		var coordinateY = $(this).attr('data-coordinatey');
        		
        		var $div_ = $(this);
        		
        		var top = parseInt(coordinateY)*scaling;
        		var left = parseInt(coordinateX)*scaling;
        		if(type=='transducer'){
        			left = left - 58;
        			top = top - 41.4 + 5;
        		}else{
        			top = top - 33.8 + 4;
        			if(datatype<=2){
        				left = left - 32.1;
        			}else{
        				left = left - 47.2;
        			}
        		}
        		$div_.css({"top":top+"px","left":left+"px"});
        		$div_.show();
        		
        	});
        	
        	_this.current();
        },
        
        fullScreen: function() {
        	window.open('/greenHouse/fullScreenView/'+$('#id').val());
        	//window.location.href = '/greenHouse/fullScreenView/'+$('#id').val();
        },
        
        showElement: function (itemId,type,dataType) {
        	if(type=='transducer'){
        		$("#elementBox").on("hidden.bs.modal", function() {
            		$(this).removeData("bs.modal");
            		$("#elementBox").html('');
            	});
                $('#elementBox').modal({
                    remote: '/transducer/monitorToday/' + itemId + '/' + dataType
                });
                /*$("#elementBox").on("shown.bs.modal", function() {//此事件在模态框已经显示出来（并且同时在 CSS 过渡效果完成）之后被触 发。
            		_this.refreshAnalysis();
            	});*/
        	}else if(type=='switch'){
        		$("#switchBox").on("hidden.bs.modal", function() {
            		$(this).removeData("bs.modal");
            		$("#switchBox").html('');
            	});
                $('#switchBox').modal({
                    remote: '/switch/contorlView/' + itemId
                });
        	}
        },
        
        refreshAnalysis: function () {
        	var flow=[];
        	var labels = [];
        	var measure = $('#dataMeasure').val();
        	$('.data_24').each(function(){
        		var hour = $(this).attr('data-hour');
        		var value = $(this).attr('data-value');
        		flow.push(value);
        		labels.push(hour);
        	});
        	if(labels.length==0){
        		return;
        	}
        	setTimeout(function(){
        		console.log('analysisDiv_width == '+$('#analysisDiv').width());
            	console.log('analysisDiv_width/3 == '+($('#analysisDiv').width()/3));
            	var data = [{name : '',value:flow,color:'#0d8ecf',line_width:2}];
            	var chart = new iChart.LineBasic2D({
            		render : 'analysisDiv',
            		data: data,
            		align:'center',
            		width : $('#analysisDiv').width(),
            		height : $('#analysisDiv').width()/3,
            		shadow:true,
            		//shadow_color : '#20262f',
            		shadow_blur : 4,
            		shadow_offsetx : 0,
            		shadow_offsety : 2,
            		//background_color:'#383e46',
            		padding:10,
            		tip:{
            			enable:true,
            			shadow:true,
            			listeners:{  
                            //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引  
            				parseText:function(tip,name,value,text,i){  
            					return value+measure;  
            				}  
                        }
            		},
            		crosshair:{
            			enable:true,
            			line_color:'#62bce9'
            		},
            		sub_option : {
            			label:false,
            			hollow_inside:false,
            			point_size:8
            		},
            		coordinate:{
            			width:$('#analysisDiv').width()*0.9,
            			height:($('#analysisDiv').width()/3)*0.84,
            			//grid_color:'#cccccc',
            			axis:{
            				color:'#cccccc',
            				width:[0,0,2,2]
            			},
            			grids:{
            				vertical:{
            					way:'share_alike',
            			 		value:flow.length-1
            				}
            			},
            			scale:[{
            				 position:'left',	
            				 start_scale:0,
            				 scale_size:2,
            				 label : {fontsize:11},
            				 scale_color:'#9f9f9f'
            			},{
            				 position:'bottom',	
            				 label : {fontsize:11},
            				 labels:labels
            				 
            			}]
            		}
            	});
            	//开始画图
            	chart.draw();
        	},500);
        },
        
        closeSwitch: function(obj){
        	$(obj).attr("disabled",true);
        	var type = $('#type').val();
        	var msg = dashboard._switch.SwitchType.getStatusName(parseInt(type),0)
        	dashboard.dialog.confirm('确认', '是否要'+msg+'?', function (confirmed) {
        		if (confirmed) {
                    var url = '/api/switch/close/' + $('#switchId').val();
                    dashboard.utils.ajax({
                        /*block: '#operation' + id,*/
                        type: 'PUT',
                        url: url,
                        dataType: 'json',
                        data: {},
                        success: function (json) {
                        	$(obj).attr("disabled",false);
                            if (!json.succeed) {
                                dashboard.message.warning(json.message);
                                return;
                            }
                            var $closeSwitchBtn = $('#closeSwitchBtn');
                            var $openSwitchBtn = $('#openSwitchBtn');
                            var $switch_stauts_div = $('#switch_stauts_div');
                            
                            var stauts = json.data.stauts;
                            if(stauts==0){//已关闭
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","#d43f3a");
                            	$closeSwitchBtn.hide();
                            	$openSwitchBtn.show();
                            }else if(stauts==1){//已打开
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","#357ebd");
                            	$(obj).html('关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭');
                            	$closeSwitchBtn.show();
                            	$openSwitchBtn.hide();
                            }else if(stauts==2){//打开中
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","");
                            	$closeSwitchBtn.hide();
                            	$openSwitchBtn.hide();
                            }else if(stauts==3){//关闭中
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","");
                            	$closeSwitchBtn.hide();
                            	$openSwitchBtn.hide();
                            }
                            
                            _this.refreshOperationList(true);
                        }
                    });
                }
            });
        	$(obj).attr("disabled",false);
        },
        openSwitch: function(obj){
        	$(obj).attr("disabled",true); 
        	var type = $('#type').val();
        	var msg = dashboard._switch.SwitchType.getStatusName(parseInt(type),1)
        	dashboard.dialog.confirm('确认', '是否要'+msg+'?', function (confirmed) {
        		if (confirmed) {
                    var url = '/api/switch/open/' + $('#switchId').val();
                    dashboard.utils.ajax({
                        /*block: '#operation' + id,*/
                        type: 'PUT',
                        url: url,
                        dataType: 'json',
                        data: {},
                        success: function (json) {
                        	$(obj).attr("disabled",false);
                            if (!json.succeed) {
                                dashboard.message.warning(json.message);
                                return;
                            }
                            var $closeSwitchBtn = $('#closeSwitchBtn');
                            var $openSwitchBtn = $('#openSwitchBtn');
                            var $switch_stauts_div = $('#switch_stauts_div');
                            
                            var stauts = json.data.stauts;
                            if(stauts==0){//已关闭
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","#d43f3a");
                            	$closeSwitchBtn.hide();
                            	$openSwitchBtn.show();
                            }else if(stauts==1){//已打开
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","#357ebd");
                            	$(obj).html('关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭');
                            	$closeSwitchBtn.show();
                            	$openSwitchBtn.hide();
                            }else if(stauts==2){//打开中
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","");
                            	$closeSwitchBtn.hide();
                            	$openSwitchBtn.hide();
                            }else if(stauts==3){//关闭中
                            	$switch_stauts_div.html(dashboard._switch.SwitchType.getStatusName(parseInt(type),stauts));
                            	$switch_stauts_div.css("color","");
                            	$closeSwitchBtn.hide();
                            	$openSwitchBtn.hide();
                            }
                            
                            _this.refreshOperationList(true);
                        }
                    });
                }
            });
        	$(obj).attr("disabled",false);
        },
        refreshOperationList: function(clean){
        	if(refreshOperationListIsRun){
        		return;
        	}
        	refreshOperationListIsRun = true;
        	var $tbody = $('#operationList tbody');
        	var moreRef = $('#showMoreBar');
        	if(clean==true){
        		$tbody.html('');
        		start = 0;
        		i = 0;
        	}
        	dashboard.utils.ajax({
                type: 'GET',
                url: '/api/switch/findOperationList/'+$('#switchId').val(),
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    start++;
                    var content = json.data.content;
                    var le = content.length;
                    var m = 0;
                    for (var k in content) {
                    	if(m>=le){
                    		continue;
                    	}
                    	m++;
                        var data = content[k];
                        var tr = $('<tr><td style="width: 10%;text-align: center;padding: 0px;">'
                            + (++i)
                            + '</td><td style="width: 30%;text-align: center;padding: 0px;">'
                            + data.operatorName
                            + '</td><td style="width: 20%;text-align: center;padding: 0px;">'
                            + data.stauts
                            + '</td><td style="width: 40%;text-align: center;padding: 0px;">'
                            + data.operateTime
                            + '</td></tr>');
                        $tbody.append(tr);
                    }
                    
                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.refreshOperationList(false);
                        });
                        moreRef.append(more);
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        	refreshOperationListIsRun = false;
        },
        current: function(){
        	setInterval(function(){
        		var args = $.extend({},{
                    type: 'GET',
                    url: '/api/greenHouse/current/'+idRef.val(),
                    dataType: 'json',
                    data: {},
                    success: function (json) {
                        if (!json.succeed) {
                            return;
                        }
                        _this.refreshElement(json.data);
                    },
					error: function(){
						return;
					}
                },{});
        		$.ajax(args);
        	},1000*10);
        },
        refreshElement: function(data){
        	var elist = data.elementList;
        	var averageMap = data.average;
        	var measureMap = data.measureMap;
        	
        	$('.elementIcon').each(function(){
        		var type = $(this).attr('data-type');
        		var datatype = $(this).attr('data-datatype');
        		var coordinateX = $(this).attr('data-coordinatex');
        		var coordinateY = $(this).attr('data-coordinatey');
        		var $div_ = $(this);
        		
        		var id = $(this).attr('id');
        		var isExist = false;
        		for (var p in elist) {
        			var elementId = 'div_'+elist[p].id+'_'+elist[p].dataType;
        			if(id == elementId){
        				isExist = true;
        				coordinateX = elist[p].coordinateX;
        				coordinateY = elist[p].coordinateY;
        			}
        		}
        		
        		if(!isExist){//如后台已经删除了就隐藏
        			$div_.hide();
    			}else{
    				var top = parseInt(coordinateY)*scaling;
            		var left = parseInt(coordinateX)*scaling;
            		
            		if(type=='transducer'){
            			left = left - 58;
            			top = top - 41.4 + 5;
            		}else{
            			top = top - 33.8 + 4;
            			if(datatype<=2){
            				left = left - 32.1;
            			}else{
            				left = left - 47.2;
            			}
            		}
            		$div_.css({"top":top+"px","left":left+"px"});
            		$div_.show();
    			}
        	});
        	
        	for (var p in elist) {
        		var imgid = '#img_'+elist[p].id+'_'+elist[p].dataType;
        		var title = '#title_'+elist[p].id+'_'+elist[p].dataType;
        		var $img = $(imgid);
        		var $title = $(title);
        		
        		if($img.length==0){//表示不存在
        			console.log('elist['+p+'].id=='+elist[p].id);
        			if(elist[p].id!=undefined){//排除一些干扰
        				var top = parseInt(elist[p].coordinateY)*scaling;
                		var left = parseInt(elist[p].coordinateX)*scaling;
                		var width = 116;
                		var height = 41.4;
                		if(elist[p].type=='transducer'){
                			left = left - 58;
                			top = top - 41.4 + 5;
                		}else if(elist[p].type=='switch'){
                			top = top - 33.8 + 4;
                			height = 33.8;
                			if(elist[p].dataType<=2){
                				width = 64.2;
                				left = left - 32.1;
                			}else{
                				width = 94.4;
                				left = left - 47.2;
                			}
                		}
            			var html = '<div class="elementIcon" id="div_'+elist[p].id+'_'+elist[p].dataType+'" data-id="'+elist[p].id+'" data-datatype="'+elist[p].dataType+'" data-type="'+elist[p].type+'" data-coordinatex="'+elist[p].coordinateX+'" data-coordinatey="'+elist[p].coordinateY+'" style="position: absolute; top: '+top+'px; left: '+left+'px; cursor: pointer;">';
            			html += '	<img id="img_'+elist[p].id+'_'+elist[p].dataType+'" src="/img/agri/'+elist[p].icon+'" style="width: '+width+'px;height: '+height+'px">';
            			html += '	<div id="title_'+elist[p].id+'_'+elist[p].dataType+'" style="margin-top: -31px;margin-left: 41px;z-index: 999;font-size: 13px;color: white;">'+elist[p].value+'</div>';
            			html += '</div>';
            			
            			$('#img_div').append(html);
        			}
        		}else{
        			$img.attr('src','/img/agri/'+elist[p].icon);
            		$title.html(elist[p].value);
        		}
        	}
        	
        	for(var key in averageMap) {
        		var iddd = '#transducer'+key;
            	$(iddd).html((averageMap[key]==null?'':averageMap[key]+measureMap[key]));
            }
        }
    };
})();