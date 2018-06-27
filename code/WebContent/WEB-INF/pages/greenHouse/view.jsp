<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
<!--
.round {
	/* width:16px;
	height:16px; */
	display: inline-block;
	font-size:24px;
	/* line-heigth:24px; */
	text-align:center;
	/* color:rgba(255, 255, 255, 0.34); */
}
-->
</style>
<template:identity pageTitle="${data.name}">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="${data.name}">
		<jsp:attribute name="titlebtns">
			<%-- <template:panelTitleBtn href="javascript:void(0)" id="fullScreenBtn" label="全屏" icon="circle-arrow-left"/> --%>
			<template:panelTitleBtn href="javascript:void(0)" id="newFullScreenBtn" label="全屏" icon="circle-arrow-left"/>
			<template:panelTitleBtn href="javascript:void(0)" id="outFullScreenBtn" label="退出全屏" icon="circle-arrow-left" type="display: none;"/>
		</jsp:attribute>
		<jsp:attribute name="body">
			<div style="display: none;">
				<input type="hidden" id="mapWidth" value="${data.mapWidth}" />
				<input type="hidden" id="mapHeight" value="${data.mapHeight}" />
				<input type="hidden" id="id" value="${data.id}" />
			</div>
			<div id="img_div" style="width: 100%;height: 100%;position: relative;">
				<img id="img_img" style="width: 100%;z-index: 1;" class="img-responsive" src="/media/photo/${data.mapPhotoFileName}">
				<div id="topRightDiv" style="position:absolute;top: 10px;right: 10px;/* left: 660px; */width: 80px;height: 130px;background-color: rgba(255, 255, 255, 1);">
					<div style="width: 100%;margin-bottom: 5px;">图例说明：</div>
					<table style="width: 100%; margin: 0px;padding: 0px;">
						<tr>
							<td style="width: 20%;"><div style="float: left;color: #00B050;" class="round">●</div></td>
							<td style="text-align: left;color: #00B050;">正常</td>
						</tr>
						<tr>
							<td><div style="float: left;color: #00B0F0;" class="round">●</div></td>
							<td style="text-align: left;color: #00B0F0;">偏低</td>
						</tr>
						<tr>
							<td><div style="float: left;color: #FF0000;" class="round">●</div></td>
							<td style="text-align: left;color: #FF0000;">偏高</td>
						</tr>
					</table>
				</div>
				
				<div id="bottomLeftDiv" style="position:absolute;top: 10px;left: 10px;width: 180px;height: 200px;background-color: rgba(255, 255, 255, 0.7);">
					<div style="width: 100%;margin-bottom: 5px;margin-top: 5px;text-align: center;">${data.name}</div>
					<table style="width: 100%; margin: 0px;padding: 0px;">
						<c:forEach items="${averages}" var="average">
						<tr>
							<td style="width: 45%;text-align: right;font-size: 14px;padding-top: 5px;">${average.dataTypeName}：</td>
							<td style="text-align: left;font-size: 14px;" id="transducer${average.dataType}">${average.value==null?'':average.value}${average.measure==null?'':average.measure}</td>
						</tr>
						</c:forEach>
					</table>
				</div>
				<c:forEach items="${elements}" var="element">
				<div class="elementIcon" id="div_${element.id}_${element.dataType}" data-id="${element.id}" data-datatype="${element.dataType}" data-type="${element.type}" data-coordinatex="${element.coordinateX}" data-coordinatey="${element.coordinateY}"
					style="position:absolute;top: 0px;left: 0px;display: none;">
					<c:if test="${element.type=='transducer'}">
					<img id="img_${element.id}_${element.dataType}" src="/img/agri/${element.icon}" style="width: 116px;height: 41.4px"/>
					</c:if>
					<c:if test="${element.type=='switch'}">
						<c:if test="${element.dataType<=2}">
						<img id="img_${element.id}_${element.dataType}" src="/img/agri/${element.icon}" style="width: 64.2px;height: 33.8px"/>
						</c:if>
						<c:if test="${element.dataType>2}">
						<img id="img_${element.id}_${element.dataType}" src="/img/agri/${element.icon}" style="width: 94.4px;height: 33.8px"/>
						</c:if>
					</c:if>
					<div id="title_${element.id}_${element.dataType}" style="margin-top: -31px;margin-left: 41px;z-index: 999;font-size: 13px;color: white;">${element.value==null?'':element.value}</div>
				</div>
				</c:forEach>
			</div>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<div id="elementBox" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
<div id="switchBox" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
<script type="text/javascript" src="/js/dashboard/greenHouse.js?sjs=1"></script>
<script type="text/javascript" src="/js/dashboard/switch.js?sjs=4"></script>
<script type="text/javascript" src="/js/ichart.latest.min.js"></script>
<script type="text/javascript">
<!--
//$('#outFullScreenBtn').hide();
document.getElementById("newFullScreenBtn").onclick=function(){
    var elem = document.getElementById("main_div");
    requestFullScreen(elem);// 某个页面元素
    //requestFullScreen(document.documentElement);// 整个网页
    //$('#newFullScreenBtn').hide();
    //$('#outFullScreenBtn').show();
    setTimeout(dashboard.greenHouse.view.initializeCreate,300);
};

function requestFullScreen(element) {
    // 判断各种浏览器，找到正确的方法
    var requestMethod = element.requestFullScreen || //W3C
    element.webkitRequestFullScreen ||    //Chrome等
    element.mozRequestFullScreen || //FireFox
    element.msRequestFullScreen; //IE11
    if (requestMethod) {
        requestMethod.call(element);
    }
    else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
        }
    }
}

//退出全屏 判断浏览器种类
document.getElementById("outFullScreenBtn").onclick = function() {
    // 判断各种浏览器，找到正确的方法
    var exitMethod = document.exitFullscreen || //W3C
    document.mozCancelFullScreen ||    //Chrome等
    document.webkitExitFullscreen || //FireFox
    document.webkitExitFullscreen; //IE11
    if (exitMethod) {
        exitMethod.call(document);
    }
    else if (typeof window.ActiveXObject !== "undefined") {//for Internet Explorer
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
        }
    }
    //$('#newFullScreenBtn').show();
    //$('#outFullScreenBtn').hide();
    setTimeout(dashboard.greenHouse.view.initializeCreate,300);
    //dashboard.greenHouse.view.initializeCreate();
}
/* $(document).keyup(function(event){
	if(event.keyCode==27||event.keyCode==96){
		$('#newFullScreenBtn').show();
	    $('#outFullScreenBtn').hide();
	}
}); */
$(document).ready(function () {
	dashboard.greenHouse.view.init();
});
//-->
</script>