<%@tag description="Current Logged User Layout template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<template:splitLayout pageTitle="农业检测及控制系统 - 我的信息" customMenu ="true">
	<jsp:attribute name="part2">
		<ul id="menuBar" class="nav nav-pills nav-stacked" style="padding:0px;border-radius: 0px;">
		    <sec:authorize ifAllGranted="ROLE_USER">
				<li style="padding-left: 10px;" class="<c:if test="${fn:indexOf(pageContext.request.requestURI, '/my/profile')>-1}">active</c:if>"><a href="<c:url value='/user/profile'/>"><span>个人资料</span></a></li>
				<li style="padding-left: 10px;" class="<c:if test="${fn:indexOf(pageContext.request.requestURI, '/my/pwd')>-1}">active</c:if>"><a href="<c:url value='/user/profile/changePassword'/>" ><span>修改密码</span></a> </li>
		    </sec:authorize>
		</ul>
	</jsp:attribute>
	<jsp:attribute name="part1">
		<jsp:doBody />
	</jsp:attribute>
</template:splitLayout>