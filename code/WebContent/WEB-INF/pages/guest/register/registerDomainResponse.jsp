<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:guest>
	<template:panel title="Register Result">
		<jsp:attribute name="body">
			<dl class="dl-horizontal">
						<dt>Request Id:</dt>
						<dd>${request.requestId}</dd>
						<dt></dt>
						<dd>
						Register apply request accepted.Please waiting for approve. Click
						<a
							href="/guest/company/registerResult.do?requestId=${request.requestId}">here</a>
						to track the request process.
						</dd>
					</dl>
		</jsp:attribute>
	</template:panel>
</template:guest>
