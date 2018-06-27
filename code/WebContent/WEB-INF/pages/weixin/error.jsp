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
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script src="/js/jquery.min.js"></script>
		<link rel="stylesheet" href="/css/bootstrap.min.css">
		<title></title>
	</head>
	<body style="background-color: white;margin: 0px;padding: 0px;">
		<div id="top_div" class="rich_media" style="background-image: url(${img});background-repeat:no-repeat;background-attachment:fixed;background-position:center;background-size: cover;">
			<div id="button_div" style="position: fixed;">
				<div id="button" style="position: fixed;color: red;font-size: large;text-align: center;">${msg}</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
$(document).ready(function () {
	var scrollWidth = document.body.scrollWidth;
	var scrollHeight = document.body.scrollHeight;
	$('#top_div').width(scrollWidth);
	$('#top_div').height(scrollHeight);
	var bili = (scrollWidth*0.8)/800;
	
	var $button_div = $('#button_div');
	
	var $button = $('#button');
	$button.css('bottom',scrollHeight/2);
	$button.width(scrollWidth*0.8);
	$button.height(bili*120);
	$button.css('left',scrollWidth*0.1);
});
</script>
