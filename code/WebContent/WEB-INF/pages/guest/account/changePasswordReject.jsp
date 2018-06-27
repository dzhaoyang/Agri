<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<template:guest pageTitle="重置密码">
	<template:panel title="警告">
		<jsp:attribute name="body"> 
			<p class="text-warning">
				此重置密码连接无效，<a href='<spring:url value="/guest/account/forgetPassword"/>'>请点击这里重新开始</a> 。
			</p>
		</jsp:attribute>
	</template:panel>
</template:guest>		
