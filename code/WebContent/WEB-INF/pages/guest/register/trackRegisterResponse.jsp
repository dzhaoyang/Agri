<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:guest>
	<template:panel title="Requset" customBody="true">
		<jsp:attribute name="body">
			<table class="table table-bordered">
				<tr>
		            <td width="200px;">Request Id:</td>
		            <td>${request.requestId}</td>
		        </tr>
		        <tr>
		            <td>Apply DateTime:</td>
		            <td>${request.applyAt}</td>
		        </tr>
				<tr>
		            <td>Status:</td>
		            <td>
		            <c:if test="${request.status == 'waiting'}"><span class="text-info">Pending</span></c:if> 
		            <c:if test="${request.status == 'approved'}"><span class="text-success">Approved</span></c:if> 
		            <c:if test="${request.status == 'refused'}">'<span class="text-success">Declined</span>'</c:if> 
		            </td>
		        </tr>
		        <tr>
		            <td>Approve/Decline Opinion:</td>
		            <td>${request.opinion}</td>
		        </tr>
		        <tr>
		            <td>Approve/Decline Date:</td>
		            <td>${request.opinionAt}</td>
		        </tr>
		        </table>
		</jsp:attribute>
	</template:panel>
	<template:panel title="Tenant" customBody="true">
		<jsp:attribute name="body">
			<table class="table table-bordered">
			        <tr>
			            <td width="200px;">Name:</td>
			            <td>${request.tenantName}</td>
			        </tr>
					<tr>
			            <td>Namespace:</td>
			            <td>${request.tenantNamespace}</td>
			        </tr>
			        <tr>
			            <td>Email:</td>
			            <td>${request.tenantAdminEmail}</td>
			        </tr>
			        </table>
		</jsp:attribute>
	</template:panel>
</template:guest>
