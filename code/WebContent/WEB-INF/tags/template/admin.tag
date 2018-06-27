<%@tag description="Manager console template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="pageTitle"%>
<template:splitLayout pageTitle="${pageTitle}">
	<jsp:attribute name="part1"> 
			<jsp:doBody />
	</jsp:attribute>
	<jsp:attribute name="part2"> 
			<template:menus></template:menus>
	</jsp:attribute>
</template:splitLayout>