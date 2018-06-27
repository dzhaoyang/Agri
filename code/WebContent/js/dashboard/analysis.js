$(document).ready(function () {
	var colors = ['#4572a7','#aa4643','#89a54e','#4b7a5e','#368728'];//饼状图颜色
	$("#startTime").datepicker({dateFormat: 'yy-mm-dd'});
	$("#endTime").datepicker({dateFormat: 'yy-mm-dd'});
	
	$('#ageBtn').click(function(){
		var $ageBtnDiv = $(this).parent();
		var $ageTable = $('#ageTable');
		var $ageLoad = $('#ageLoad');
		var $table = $ageLoad.next();
		$ageBtnDiv.hide();
		$table.hide();
		$ageLoad.show();
		$('#ageTable tbody').empty();
		dashboard.utils.ajax({
            block: '',
            type: 'GET',
            url: '/api/analysis/age',
            dataType: 'json',
            data: {},
            success: function (json) {
            	$ageBtnDiv.show();
            	$table.show();
            	$ageLoad.hide();
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                if (json.data == null) {
                    return;
                }
                
                var chartData = [];
                var i=0;
                for (var p=0;p<json.data.length;p++) {
                	var data = json.data[p];
	                var tr = [];
	                tr.push('<tr><td style="padding: 5px;text-align: center">');
	                tr.push(data.age);
	                tr.push('</td><td style="padding: 5px;text-align: center">');
	                tr.push(data.count);
	                tr.push('</td><td style="padding: 5px;text-align: center">');
	                tr.push(data.proportion+'%');
	                tr.push('</td><tr>');
	                if(data.count>0){
	                	chartData.push({name:data.age,value:data.proportion,color:colors[i]});
	                	i++;
	                }
	                var $tr = $(tr.join(''));
	                $('#ageTable tbody').append($tr);
                }
                if(chartData.length>0){
                	new iChart.Pie2D({
    					render : 'ageAnalysis',
    					data: chartData,
    					title : '',
    					legend : {
    						enable : true
    					},
    					showpercent:true,
    					decimalsnum:2,
    					width : $('#ageTable').parent().width(),
    					height : $('#ageTable').parent().height(),
    					radius:'100%',
    					padding:10
    				}).draw();
                }
            }
        });
		/*$ageBtnDiv.show();
		$table.show();
		$ageLoad.hide();*/
	});
	$('#sexBtn').click(function(){
		var $sexBtnDiv = $(this).parent();
		var $sexTable = $('#sexTable');
		var $sexLoad = $('#sexLoad');
		var $table = $sexLoad.next();
		$sexBtnDiv.hide();
		$table.hide();
		$sexLoad.show();
		$('#sexTable tbody').empty();
		dashboard.utils.ajax({
            block: '',
            type: 'GET',
            url: '/api/analysis/sex/',
            dataType: 'json',
            data: {},
            success: function (json) {
            	$sexBtnDiv.show();
        		$table.show();
        		$sexLoad.hide();
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                if (json.data == null) {
                    return;
                }
                
                var chartData = [];
                var i=0;
                for (var p=0;p<json.data.length;p++) {
                	var data = json.data[p];
	                var tr = [];
	                tr.push('<tr><td style="padding: 5px;text-align: center">');
	                tr.push(data.sex);
	                tr.push('</td><td style="padding: 5px;text-align: center">');
	                tr.push(data.count);
	                tr.push('</td><td style="padding: 5px;text-align: center">');
	                tr.push(data.proportion+'%');
	                tr.push('</td><tr>');
	                if(data.count>0){
	                	chartData.push({name:data.sex,value:data.proportion,color:colors[i]});
	                	i++;
	                }
	                var $tr = $(tr.join(''));
	                $('#sexTable tbody').append($tr);
                }
                if(chartData.length>0){
                	new iChart.Pie2D({
    					render : 'sexAnalysis',
    					data: chartData,
    					title : '',
    					legend : {
    						enable : true
    					},
    					showpercent:true,
    					decimalsnum:2,
    					width : $('#sexTable').parent().width(),
    					height : $('#sexTable').parent().height(),
    					radius:'100%',
    					padding:10
    				}).draw();
                }
            }
        });
		/*$sexBtnDiv.show();
		$table.show();
		$sexLoad.hide();*/
	});
	$('#timeBtn').click(function(){
		var $timeBtnDiv = $(this).parent();
		var $timeLoad = $('#timeLoad');
		var $timeAnalysis = $('#timeAnalysis');
		$timeBtnDiv.hide();
		$timeAnalysis.hide();
		$timeLoad.show();
		dashboard.utils.ajax({
            block: '',
            type: 'GET',
            url: '/api/analysis/time',
            dataType: 'json',
            data: {startTime:$("#startTime").val(),
         	   	   endTime:$("#endTime").val()
         	   	  },
            success: function (json) {
            	$timeBtnDiv.show();
        		$timeAnalysis.show();
        		$timeLoad.hide();
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                if (json.data == null && json.data.size==0) {
                    return;
                }
                var flow=[];
                var labels = [];
        		for (var p=0;p<json.data.length;p++) {
                	var e = json.data[p];
                	flow.push(e.count);
                	labels.push(e.time);
                }
        		var data = [{name : '数量',value:flow,color:'#0d8ecf',line_width:2}];
        		if(flow.length>0){
        			var chart = new iChart.LineBasic2D({
            			render : 'timeAnalysis',
            			data: data,
            			align:'center',
            			width : $('#timeAnalysis').width(),
            			height : $('#timeAnalysis').width()/4,
            			shadow:true,
            			//shadow_color : '#20262f',
            			shadow_blur : 4,
            			shadow_offsetx : 0,
            			shadow_offsety : 2,
            			//background_color:'#383e46',
            			padding:10,
            			tip:{
            				enable:true,
            				shadow:true
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
            				width:$('#timeAnalysis').width()*0.8,
            				height:($('#timeAnalysis').width()/4)*0.8,
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
        		}
            }
        });
	});
});