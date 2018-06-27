<%@tag description="Overall Page template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="pageTitle"%>
<template:splitLayout pageTitle="巴中农业科技园数据监测及控制系统 - ${pageTitle}">
	<jsp:attribute name="part1">
		<jsp:doBody />
	</jsp:attribute>
</template:splitLayout>