<%@tag description="Split Layout template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="pageTitle"%>
<%@attribute name="part1" fragment="true"%>
<%@attribute name="part2" fragment="true"%>
<%@attribute name="customMenu"%>
<template:layout pageTitle="${pageTitle}">
	<div class="row">
		<div class="col-md-3" style="width: 12%;padding-left: 5px;padding-right: 5px;">
			<c:if test='${"true" eq customMenu}'>
				<jsp:invoke fragment="part2" />
			</c:if>
			
			<c:if test='${"true" ne customMenu}'>
				<template:menus></template:menus>
			</c:if>
		</div>
		<div class="col-md-9" style="padding-left: 0px;width: 88%;padding-right: 5px;">
			<jsp:invoke fragment="part1" />
		</div>
	</div>
</template:layout>