<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>

<template:guest pageTitle="重置密码">
	<template:panel title="重置密码成功">
		<jsp:attribute name="body"> 
			您的密码已重置成功， <a href="<spring:url value="/login"/>">请点击这里登录</a> 。
		</jsp:attribute>
	</template:panel>
</template:guest>
