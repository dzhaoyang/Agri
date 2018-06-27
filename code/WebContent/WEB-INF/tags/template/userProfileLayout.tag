<%@tag description="Split Layout template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<template:splitLayout pageTitle="农业检测及控制系统 - 用户信息" customMenu ="true">
	<jsp:attribute name="part2">
		<ul id="menuBar" class="nav nav-pills nav-stacked" style="padding:0px;border-radius: 0px;">
		    <sec:authorize ifAllGranted="ROLE_USER">
				<li style="padding-left: 10px;" class="subtitle"><span class="glyphicon glyphicon-align-justify"></span><strong>用户明细</strong></li>
				<li style="padding-left: 10px;" data-url="/profile/index"><a href="<c:url value='/dashboard/identity/users/${user.id}/profile/index'/>"><span>基本信息</span></a></li>
				<li style="padding-left: 10px;" data-url="/profile/audit"><a href="<c:url value='/dashboard/identity/users/${user.id}/profile/audit'/>" ><span>操作记录</span></a> </li>
				<li style="padding-left: 10px;" data-url="/profile/authHistory"><a href="<c:url value='/dashboard/identity/users/${user.id}/profile/authHistory'/>" ><span>登录记录</span></a></li>
				<%-- <li data-url="/profile/parking"><a href="<c:url value='/dashboard/identity/users/${user.id}/profile/parking'/>" ><span>该用户创建的停车场</span></a></li> --%>
				<%-- 
				<li data-url="/profile/apprisal"><a href="<c:url value='/dashboard/identity/users/${user.id}/profile/apprisal'/>"><span>该用户的评价</span></a></li>
				<li data-url="/profile/booking"><a href="<c:url value='/dashboard/identity/users/${user.id}/profile/booking'/>"><span>该用户的预订</span></a></li>
				--%>
		    </sec:authorize>
		</ul>
	</jsp:attribute>
	<jsp:attribute name="part1">
		<jsp:doBody />
	</jsp:attribute>
</template:splitLayout>

<script type="text/javascript" src="/js/dashboard/menu.js"></script>
