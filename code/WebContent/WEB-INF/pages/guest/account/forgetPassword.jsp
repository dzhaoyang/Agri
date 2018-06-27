<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:guest pageTitle="重置密码">
		<template:panel title="重置密码">
			<jsp:attribute name="body">
				<form id="dataForm" method="POST" action='<spring:url value="/guest/account/passwordResetRequest.do"/>'>
					<div class="form-group">
					    <label for="">请输入用户名:</label>
					    <input type="text" class="form-control" id="username" name="username" maxlength="80" placeholder="用户名">
				    </div>
				</form>
				<div id="errorMsg" class="alert alert-danger">${message}</div>
				
				<hr>
				<template:panelFooterBtn href="javascript:save();" label="发送重置密码邮件" type="info" icon="envelope"/>
			</jsp:attribute>
		</template:panel>
	<script type="text/javascript">
	<!--
		var detailValidator;

		$(document).ready(function() {
			detailValidator = $("#dataForm").validate({
				rules : {
					"username" : {
						required : true
					}
				}
			});
			if(!$("#errorMsg").text()) {
				$("#errorMsg").hide();
			}
		});

		function showErrorMsg(msg) {
			$("#errorMsg").show();
			$("#errorMsg").text(msg);
		}

		function save() {
			if (!detailValidator.form()) {
				return;
			}

			$("#dataForm").submit();
		}
	//-->
	</script>
</template:guest>
