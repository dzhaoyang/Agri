<%@tag description="Split Layout template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<template:splitLayout pageTitle="农业检测及控制系统 - 停车场信息" customMenu ="true">
	<jsp:attribute name="part2">
		<input type="hidden" id="profileId" value="${profile.id}">
		<ul id="menuBar" class="nav nav-pills nav-stacked">
		    <sec:authorize ifAnyGranted="ROLE_PARKMANAGER,ROLE_MANAGER,ROLE_ADMIN">
				<li class="subtitle"><span class="glyphicon glyphicon-align-justify"></span><strong>停车场信息</strong></li>
				<li data-url="/profile/index"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/index'/>"><span>基本信息</span></a></li>
				<li data-url="/profile/map"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/map'/>" ><span>地图</span></a> </li>
				<li data-url="/profile/photos"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/photos'/>" ><span>照片</span></a> </li>
				<li data-url="/profile/appraisals"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/appraisals'/>" ><span>评价</span></a></li>
				<li data-url="/profile/bookings"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/bookings'/>" ><span>预定</span></a></li>
				<li data-url="/profile/favorites"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/favorites'/>" ><span>收藏</span></a></li>
				<li data-url="/profile/employees"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/employees'/>" ><span>员工</span></a></li>
				<li data-url="/profile/statuses"><a href="<c:url value='/dashboard/parkings/${profile.id}/profile/statuses'/>" ><span>状态变更</span></a></li>
		    </sec:authorize>
		</ul>
	</jsp:attribute>
	<jsp:attribute name="part1">
		<jsp:doBody />
	</jsp:attribute>
</template:splitLayout>

<script type="text/javascript" src="/js/dashboard/menu.js"></script>
