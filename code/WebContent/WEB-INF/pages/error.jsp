<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ page import="java.io.*"%>

<template:layout>
	<div class="well well-white">
		<div class="page-header">
			<h1>Error Message</h1>
		</div>
		<div id="dataContainer" class="row-fluid">
			<div class='alert'>${exception.message}</div>
		</div>

	</div>

	<c:if test="${!fn:contains(pageContext.request.serverName, idpConfig.baseDomainName)}">
		<div class="well well-white">
			<div class="page-header">
				<h1>Error Detail</h1>
			</div>
			<div id="dataContainer" class="row-fluid">
				<c:forEach items="${exception.stackTrace}" var="trace">
					<c:out value="${trace}" />
					<br />
				</c:forEach>
			</div>
		</div>
	</c:if>
</template:layout>