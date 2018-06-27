$.namespace('dashboard.transducer');
dashboard.transducer.list = (function () {
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
            $('#uuid').val('');
            $('#name').val('');
            $('#greenHouseId').val('');
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'transducer/edit/' + itemId
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
                url: '/api/transducer/list',
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit,
                    uuid: $('#uuid').val(),
                    name: $('#name').val(),
                    greenHouseId: $('#greenHouseId').val()
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
                        var typestr = '';
                        if(data.type==1){
                        	typestr = 'PM2.5传感器';
                        }else if(data.type==2){
                        	typestr = '土壤传感器';
                        }else if(data.type==3){
                        	typestr = '光照传感器';
                        }
                        var tr = $('<tr><td style="text-align: center;">'
                            + (++i)
                            + '</td><td style="text-align: center;">'
                            //+ '<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">'
                            + (data.uuid ? data.uuid : '')
                            //+ '</a>'
                            + '</td><td style="text-align: center;">'
                            + (data.name ? data.name : '')
                            + '</td><td style="text-align: center;">'
                            + typestr
                            + '</td><td style="text-align: center;">'
                            + (data.isAuto==1 ? '是' : '否')
                            + '</td><td style="text-align: center;">'
                            + (data.greenHouse?data.greenHouse.name:'')
                            + '</td><td style="text-align: center;">'
                            + (data.installDate ? data.installDate : '')
                            + '</td><td style="text-align: center;">'
                            + '<span class="operation btn-group" id="operation' + data.id + '">'
                            + '<a href="/transducer/edit/' + data.id + '" class="btn btn-info btn-xs">编辑</a>'
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
                '是否删除选中传感器?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = '/api/transducer/delete/' + id;
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

//编辑页面
dashboard.transducer.edit = (function () {
    var _this = null;
    var baseValidator = null;
    var detailValidator = null;
    var idRef = null;
    var editable = false;

    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            
            $("#installDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
            $('#backBtn').bind('click', function () {
                _this.back();
            });

            $('#saveBtn').bind('click', function () {
                _this.save();
            });

            _this.initializeCreate();
            if(idRef.val()) {
            	
            }
        },
        initializeCreate: function () {
        	$('#savebtnDiv').width($('#infoUl').width()-150);
        	/*$('#backBtn').bind('click', function () {
        		history.go(-1);
            });*/
        	baseValidator = $("#baseForm").validate({
                rules: {
                	name: {required: true},
                	uuid: {required: true},
                	"greenHouse\\.Id": {required: true},
                	coordinateX: {required: true,digits:true,min:0},
                	coordinateY: {required: true,digits:true,min:0},
                	installDate:{required: true},
                	
                	'transducerDataTypes[0].upperLimit': {required: true,number:true},
                	'transducerDataTypes[0].lowerLimit': {required: true,number:true},
                	'transducerDataTypes[0].upperLimitCommand': {required: true},
                	'transducerDataTypes[0].lowerLimitCommand': {required: true},
                	'transducerDataTypes[0].measure': {required: true},
                	'transducerDataTypes[0].isAuto': {required: true},
                	
                	'transducerDataTypes[1].upperLimit': {required: true,number:true},
                	'transducerDataTypes[1].lowerLimit': {required: true,number:true},
                	'transducerDataTypes[1].upperLimitCommand': {required: true},
                	'transducerDataTypes[1].lowerLimitCommand': {required: true},
                	'transducerDataTypes[1].measure': {required: true},
                	'transducerDataTypes[1].isAuto': {required: true},
                	
                	'transducerDataTypes[2].upperLimit': {required: true,number:true},
                	'transducerDataTypes[2].lowerLimit': {required: true,number:true},
                	'transducerDataTypes[2].upperLimitCommand': {required: true},
                	'transducerDataTypes[2].lowerLimitCommand': {required: true},
                	'transducerDataTypes[2].measure': {required: true},
                	'transducerDataTypes[2].isAuto': {required: true},
                	
                	'transducerDataTypes[3].upperLimit': {required: true,number:true},
                	'transducerDataTypes[3].lowerLimit': {required: true,number:true},
                	'transducerDataTypes[3].upperLimitCommand': {required: true},
                	'transducerDataTypes[3].lowerLimitCommand': {required: true},
                	'transducerDataTypes[3].measure': {required: true},
                	'transducerDataTypes[3].isAuto': {required: true}
                },
                messages: {
                	name: {required: "请输入值"},
                	uuid: {required: "请输入值"},
                	"greenHouse\\.Id":{required: "请输入值"},
                	isAuto: {required: "请输入值"},
                	coordinateX: {required: "请输入值",digits:"请输入正整数",min:"不能小于0"},
                	coordinateY: {required: "请输入值",digits:"请输入正整数",min:"不能小于0"},
                	installDate: {required: "请输入值"},
                	
                	'transducerDataTypes[0].upperLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[0].lowerLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[0].upperLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[0].lowerLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[0].measure': {required: "请输入值"},
                	'transducerDataTypes[0].isAuto': {required: "请输入值"},
                	
                	'transducerDataTypes[1].upperLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[1].lowerLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[1].upperLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[1].lowerLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[1].measure': {required: "请输入值"},
                	'transducerDataTypes[1].isAuto': {required: "请输入值"},
                	
                	'transducerDataTypes[2].upperLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[2].lowerLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[2].upperLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[2].lowerLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[2].measure': {required: "请输入值"},
                	'transducerDataTypes[2].isAuto': {required: "请输入值"},
                	
                	'transducerDataTypes[3].upperLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[3].lowerLimit': {required: "请输入值",number:"请输入数字"},
                	'transducerDataTypes[3].upperLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[3].lowerLimitCommand': {required: "请输入值"},
                	'transducerDataTypes[3].measure': {required: "请输入值"},
                	'transducerDataTypes[3].isAuto': {required: "请输入值"}
                }
            });
        	/*detailValidator = $("#detailForm").validate({
                rules: {
                	upperLimit: {required: true,number:true},
                	lowerLimit: {required: true,number:true},
                	upperLimitCommand: {required: true},
                	lowerLimitCommand: {required: true},
                	measure: {required: true}
                },
                messages: {
                	upperLimit: {required: "请输入值",number:"请输入数字"},
                	lowerLimit: {required: "请输入值",number:"请输入数字"},
                	upperLimitCommand: {required: "请输入值"},
                	lowerLimitCommand: {required: "请输入值"},
                	measure: {required: "请输入值"}
                }
            });*/
        },
        
        back: function() {
        	if(idRef.val()!=''){
        		if(!baseValidator.form()){
        			dashboard.dialog.alert('提示','请编辑并保存传感器收集的数据信息！');
            		return;
            	}
        	}
        	window.location.href = '/transducer/list';
        },
        save: function () {
        	var canCommit = true;
        	if(!baseValidator.form()){
        		canCommit = canCommit & false;
        		 //return;
        	}
            /*if (idRef.val() != null && idRef.val() != '' && !detailValidator.form()) {
            	canCommit = canCommit & false;
            }*/
            
            if(!canCommit){
            	return;
            }

            var saveUrl = '/api/transducer/save';
            var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveUrl = '/api/transducer/update';
            }
            
            var confirmMsg = '';
            if(isUpdate){
            	confirmMsg = '是否确认变更?';
            }else{
            	confirmMsg = '保存后传感器类型将不能变更，是否继续保存?';
            }
            var serialize = $('#baseForm').serialize();
            /*if(idRef.val() != null && idRef.val() != ''){
            	serialize = serialize +'&'+$('#detailForm').serializeArray();
            }*/
            //alert(serialize);
            dashboard.dialog.confirm('确认',confirmMsg, function (confirmed) {
                if (confirmed) {
                	dashboard.utils.ajax({
                        block: '.form-actions',
                        type: isUpdate ? 'POST' : 'POST',
                        url: saveUrl,
                        dataType: 'json',
                        data: serialize,
                        success: function (json) {
                            if (!json.succeed) {
                                dashboard.message.warning(json.message);
                                return;
                            }
                            dashboard.message.info('保存成功!');
                            window.location.href = '/transducer/edit/' + json.data.id;
                        }
                    });
                }
             });
        }
    };
})();

dashboard.transducer.analysis = (function () {
    var _this = null;
    return {
        init: function () {
            _this = this;
            $('#refreshBtn').bind('click', function () {
                _this.refresh();
            });
            
            $("#startTime").datepicker({
                dateFormat: 'yy-mm-dd'
            });
            
            $("#endTime").datepicker({
                dateFormat: 'yy-mm-dd'
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


            this.showAnalysis();
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            $('#startTime').val('');
            $('#endTime').val('');
            $('#greenHouseId').val('');
        },
        showMore: function (reset) {
        	/*var measure = $('#measure').val();
        	var startTime = $('#startTime').val();
        	var endTime = $('#endTime').val();
        	var greenHouseId = $('#greenHouseId').val();
        	self.location.href="/transducer/analysis/"+$('#dataType').val()+"?startTime="+startTime+"&endTime="+endTime+"&greenHouseId="+greenHouseId;*/
        	$('#analysisForm').submit();
        },
        showAnalysis: function () {
        	var avgFlow=[];
        	var maxFlow=[];
        	var minFlow=[];
        	var labels = [];
        	var measure = $('#measure').val();
        	$('.analysisData').each(function(){
        		var day = $(this).attr('data-day');
        		var avgValue = $(this).val();
        		var maxValue = $(this).attr('data-maxvalue');
        		var minValue = $(this).attr('data-minvalue');
        		avgFlow.push(avgValue);
        		maxFlow.push(maxValue);
        		minFlow.push(minValue);
        		labels.push(day);
        	});
        	if(labels.length==0){
        		$('#analysisDiv').height(100).html("无记录！").css({"text-align":"center","font-size":"20px"});
        		return;
        	}
        	
        	setTimeout(function(){
        		console.log('analysisDiv_width == '+$('#analysisDiv').width());
            	console.log('analysisDiv_width/3 == '+($('#analysisDiv').width()/3));
            	var data = [{value:avgFlow, name:'平均值', color:'#00FF00', line_width:2},
            	            {value:maxFlow, name:'最大值', color:'#FF0000', line_width:2},
            	            {value:minFlow, name:'最小值', color:'#00B0F0', line_width:2}];
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
            					return name+":"+value+measure;  
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
            			 		value:labels.length-1
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
        }
    };
})();