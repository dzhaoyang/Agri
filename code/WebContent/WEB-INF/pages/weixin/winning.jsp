<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script src="/js/jquery.min.js"></script>
		<script src="/js/jquery.mobile-1.4.5.min.js"></script>
		<script src="/js/jquery.touchwipe.js"></script>
		<!-- <script src="/js/jquery.touchSwipe.min.js"></script> -->
		<link rel="stylesheet" href="/css/jquery.mobile-1.4.5.min.css">
		
		<title></title>
		<style type="text/css">
			.btn {
			    display: inline-block;
			    margin-bottom: 0;
			    text-align: center;
			    vertical-align: middle;
			    cursor: pointer;
			    background-image: none;
			    border: 1px solid transparent;
			    white-space: nowrap;
			    padding: 10px 10px;
			    border-radius: 5px;
			    -webkit-user-select: none;
			    border-color: #cccccc;
			    background-color: rgba(255, 255, 255, 0.34);
			    color: red;
			    font-weight: 700;
			    font-size: large;
			    margin-left: auto;
			    margin-right: auto;
			}
		</style>
	</head>
	<body style="background-color: white;margin: 0px;padding: 0px;">
		<input type="hidden" id="userId" value="${userId}">
		<input type="hidden" id="prizeId" value="${prizeId}">
		<input type="hidden" id="ernieRecordId" value="${ernieRecordId}">
		<input type="hidden" id="totalImgCount" value="${totalImgCount}">
		<input type="hidden" id="isWinning" value="${isWinning}">
		<input type="hidden" id="show_index" value="0">
		<c:forEach var="imgUrl" items="${imgUrls}" varStatus="status">
			<div data-role="page" id="page${status.index}" style="width: 100%;height: 100%;text-align: center;margin: 0px;">
			  <div data-role="main" class="ui-content" style="padding: 0px;">
			   	<img src="${imgUrl}" style="width: 100%;">
			  </div>
			</div>
		</c:forEach>
		<div id="info_div" style="width: 100%;text-align: center;z-index: 3;position: fixed;">
			<div style="background-color: rgba(255, 255, 255, 0.34);margin-left: auto;margin-right: auto;border-radius:5px;">
				<div style="height: 10px"></div>
				<div style="width: 100%;text-align: center;color: red;font-weight: 700;font-size: x-large;">
					<c:if test="${isWinning==1}">恭 喜 中 奖</c:if>
					<c:if test="${isWinning==0}">遗 憾！未 中 奖</c:if>
				</div>
				<div style="height: 10px"></div>
				<c:if test="${isWinning==1}">
				<div style="height: 10px"></div>
				<div style="width: 100%;text-align: center;font-size: large;color: red;">
					${gradeName}<br>${prizeName} 1${measure}
				</div>
				<div style="height: 10px"></div>
				</c:if>
				<c:if test="${isWinning!=1}">
				<div style="height: 40px"></div>
				</c:if>
			</div>
		</div>
		<div id="button_div" style="width: 100%;text-align: center;margin: 0px;padding: 0px;z-index: 3;position: fixed;">
			<c:if test="${isWinning==1}">
			<button id="button" class="btn">我 要 领 奖</button>
			<div style="height: 10px"></div>
			</c:if>
			<button id="again" class="btn">返回重新抽奖</button>
			<a id="left_a" data-transition="slide"></a><a id="right_a" data-transition="slide" data-direction="reverse"></a>
		</div>
	</body>
</html>
<script type="text/javascript">
var interval;
var switchPageIsRun = false;
$(document).ready(function () {
	var scrollWidth = $(document).width();
	var scrollHeight = $(document).height();
	
	//alert('scrollWidth = '+scrollWidth+', scrollHeight = '+scrollHeight);
	
	$('#info_div').css('margin-top',scrollHeight*0.2);
	$('#info_div').children().eq(0).width(scrollWidth*0.7);
	//$('#info_div').children().eq(0).height(scrollHeight*0.2);
	
	$('#button_div').css('margin-top',scrollHeight*0.7);
	
	if($('#isWinning').val()=='1'){
		$('#button').width(scrollWidth*0.6);
		$('#button').click(function(){
			window.location.href = '/weixin/award/'+$('#userId').val()+'/'+$('#ernieRecordId').val()+'/'+$('#prizeId').val();
		});
	}
	
	$('#again').width(scrollWidth*0.6);
	$('#again').click(function(){
		window.location.href = '/weixin/advert/'+$('#userId').val();
	});
	
	
	if(parseInt($('#totalImgCount').val())>1){
		interval = setInterval(function(){
			switchPage('left');
		}, 5000);
		//jquery.touchwipe.js
		$('body').touchwipe({
			wipeLeft:function(){
				/* var show_index = $('#show_index').val();
				var next_show_index = 0;
				if((parseInt(show_index)+1)>=parseInt($('#totalImgCount').val())){
					next_show_index = 0;
				}else{
					next_show_index = parseInt(show_index)+1;
				}
				
				$('#show_index').val(next_show_index);
				
				$a = $('#left_a');
				$a.attr('href','#page'+next_show_index);
				$a.trigger("click"); */
				switchPage('left');
			},
			wipeRight:function(){
				/* var show_index = $('#show_index').val();
				var next_show_index = 0;
				if((parseInt(show_index))<=0){
					next_show_index = parseInt($('#totalImgCount').val())-1;
				}else{
					next_show_index = parseInt(show_index)-1;
				}
				
				$('#show_index').val(next_show_index);
				
				$a = $('#right_a');
				$a.attr('href','#page'+next_show_index);
				$a.trigger("click"); */
				switchPage('right');
			},
			wipeUp:function(){},
			wipeDown:function(){},
			min_move_x:60,
			min_move_y:60,
			preventDefaultEvents:true
		});
		/* jquery.touchSwipe.min.js
		$("body").swipe({
		    swipe:function(event, direction, distance, duration, fingerCount) {
		         alert('direction == '+direction+', distance == '+distance+', duration == '+duration+', fingerCount == '+fingerCount);
		         if(direction=='left'){
		        	 var show_index = $('#show_index').val();
		 			var next_show_index = 0;
		 			if((parseInt(show_index)+1)>=parseInt($('#totalImgCount').val())){
		 				next_show_index = 0;
		 			}else{
		 				next_show_index = parseInt(show_index)+1;
		 			}
		 			
		 			$('#show_index').val(next_show_index);
		 			
		 			$a = $('#left_a');
		 			$a.attr('href','#page'+next_show_index);
		 			$a.trigger("click");
		         }else if(direction=='right'){
		        	 var show_index = $('#show_index').val();
		 			var next_show_index = 0;
		 			if((parseInt(show_index))<=0){
		 				next_show_index = parseInt($('#totalImgCount').val())-1;
		 			}else{
		 				next_show_index = parseInt(show_index)-1;
		 			}
		 			
		 			$('#show_index').val(next_show_index);
		 			
		 			$a = $('#right_a');
		 			$a.attr('href','#page'+next_show_index);
		 			$a.trigger("click");
		         }else{
		        	 return true;
		         }
		    }
		}); */
	}
	
});

function switchPage(direction){
	if(!switchPageIsRun){
		switchPageIsRun = true;
		if(interval!=null){
			clearInterval(interval);
		}
		var show_index = $('#show_index').val();
		var next_show_index = 0;
		if(direction=='left'){
			if((parseInt(show_index)+1)>=parseInt($('#totalImgCount').val())){
				next_show_index = 0;
			}else{
				next_show_index = parseInt(show_index)+1;
			}
		}else{
			if((parseInt(show_index))<=0){
				next_show_index = parseInt($('#totalImgCount').val())-1;
			}else{
				next_show_index = parseInt(show_index)-1;
			}
		}
		$('#show_index').val(next_show_index);
		$a = $('#'+direction+'_a');
		$a.attr('href','#page'+next_show_index);
		$a.trigger("click");
		switchPageIsRun = false;
		interval = setInterval(function(){
			switchPage('left');
		}, 5000);
	}	
}
</script>
