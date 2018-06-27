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
			    padding: 6px 12px;
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
		<form action="" id="infoForm">
			<input type="hidden" name="userId" id="userId" value="${userId}">
			<input type="hidden" name="ernieRecordId" id="ernieRecordId" value="${ernieRecordId}">
			<input type="hidden" id="totalImgCount" value="${totalImgCount}">
			<input type="hidden" id="show_index" value="0">
			<c:forEach var="imgUrl" items="${imgUrls}" varStatus="status">
				<div data-role="page" id="page${status.index}" style="width: 100%;height: 100%;text-align: center;margin: 0px;">
				  <div data-role="main" class="ui-content" style="padding: 0px;">
				   	<img src="${imgUrl}" style="width: 100%;">
				  </div>
				</div>
			</c:forEach>
			<div id="info_div" style="width: 100%;text-align: center;z-index: 3;position: fixed;">
				<div style="margin-left: auto;margin-right: auto;">
					<div id="div1" style="width: 100%;text-align: center;color: red;font-weight: 700;font-size: large;">
						领 奖 须 知
					</div>
					<div style="width: 100%;text-align: left;">
						请耐心填写您的个人信息，然后前往位于2楼东侧的服务台领奖！
					</div>
					<p>
					<div style="width: 100%;text-align: left;">
						<input type="number" id="phone" name="phone" placeholder="请输入手机号" style="width: 100%;height: 40px;" value="${phone}">
					</div>
					<p><p>
					<div style="width: 100%;text-align: left;">
						<input type="radio" name="sex" value="1" ${sex==1?' checked="checked"':''}>男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="sex" value="2" ${sex==2?' checked="checked"':''}>女
					</div>
					<p><p>
					<div style="width: 100%;text-align: left;">
						<input type="number" id="age" name="age" placeholder="请输入你的年龄" style="width: 100%;height: 40px;"  value="${age}">
					</div>
				</div>
			</div>
		</form>
		<div id="button_div" style="width: 100%;text-align: center;margin: 0px;padding: 0px;z-index: 3;position: fixed;">
			<button id="button1" class="btn" style="color: red;padding: 10px 10px;z-index: 3;">提  交</button>
			<div style="height: 10px"></div>
			<button id="button2" class="btn" style="color: black;padding: 10px 10px;z-index: 3;">放  弃</button>
			<a id="left_a" data-transition="slide"></a><a id="right_a" data-transition="slide" data-direction="reverse"></a>
		</div>
	</body>
</html>
<script type="text/javascript">
$(document).ready(function () {
	var scrollWidth = $(document).width();
	var scrollHeight = $(document).height();
	
	//alert('scrollWidth = '+scrollWidth+', scrollHeight = '+scrollHeight);
	
	$('#info_div').css('margin-top',scrollHeight*0.1);
	$('#info_div').children().eq(0).width(scrollWidth*0.8);
	
	var $button1 = $('#button1');
	var $button2 = $('#button2');
	
	$('#button_div').css('margin-top',scrollHeight*0.7);
	$('#button1').width(scrollWidth*0.7);
	$('#button2').width(scrollWidth*0.7);
	
	$button1.click(function(){
		$button1.hide();
		$button2.hide();
		$.ajax({
	        type: 'POST',
	        url: '/api/ernieRecord/add/',
	        dataType: 'json',
	        timeout : 3000,
	        data: $('#infoForm').serialize(), 
	        success: function (json) {
	        	$button1.show();
	    		$button2.show();
	            if (!json.succeed) {
	            	alert('网络缓慢，请重新提交！')
	                return;
	            }
	            alert('提交成功！');
	            window.location.href = '/weixin/advert/'+$('#userId').val();
	        }
	    });
	});
	$button2.click(function(){
		$button1.hide();
		$button2.hide();
		window.location.href = '/weixin/advert/'+$('#userId').val();
	});
	
	
	if(parseInt($('#totalImgCount').val())>1){
		/* jquery.mobile-1.4.5.min.js
		$('body').on("swipeleft",function(){//向左滑动
			alert('向左滑动');
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
		});
		
		$('body').on("swiperight",function(){//向右滑动
			alert('向右滑动');
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
		}); */
		
		//jquery.touchwipe.js
		$('body').touchwipe({
			wipeLeft:function(){
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
			},
			wipeRight:function(){
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

</script>
