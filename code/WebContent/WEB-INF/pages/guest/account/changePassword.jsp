<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:guest pageTitle="重置密码">
	<template:panel title="重置密码" showfooter="true">
		<jsp:attribute name="body">
			<template:form id="userPasswordForm" action="../saveChangePassword"  method="POST">
				<input type="hidden" name="uuid" id="uuid"
					value="${uuid}" type="text">
				<template:formItem name="username" required="true" label="用户名:"/>
				<template:formItem type="help" value="请输入用户名"/>
				<template:formItem name="newPassword1" required="true" type="password" label="新密码:"/>
				<template:formItem type="help" value="至少6个字符，推荐混合使用大小写英文字母、数字、特殊字符"/>
				<template:formItem name="newPassword2" required="true" type="password" label="重复新密码:"/>			
			</template:form>
			<c:if test="${not empty message}">
			<div id="errorMsg" class="alert alert-danger">${message}</div>
			</c:if>
		</jsp:attribute>
		<jsp:attribute name="footer">
			<template:panelfooterbtns>
				<template:panelFooterBtn href="javascript:save();" type="primary" label="重置密码"/>
			</template:panelfooterbtns>
		</jsp:attribute>
	</template:panel>
<script type="text/javascript">
<!--
var detailValidatorChangePsw;

$(document).ready(function() {
	detailValidatorChangePsw = $("#userPasswordForm").validate({
		rules: {
			"username":{required:true},
			"newPassword1":{required:true,minlength:6},
			"newPassword2":{required:true,minlength:6,equalTo:"#newPassword1"},
		}
	});
	
/* 	$("#newPassword1").keyup(
		function(){
			$("#result").html(passwordStrength($('#newPassword1').val()))
		}
	);
 */
 	});

function save(){
	if (!detailValidatorChangePsw.form()) {
		return;
	}
	
	$("#userPasswordForm").submit();
}

//-->
</script>
</template:guest>