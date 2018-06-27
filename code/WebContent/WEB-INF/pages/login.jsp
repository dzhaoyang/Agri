<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:guest pageTitle="巴中农业科技园数据监测及控制系统">
    <template:panel_login title="巴中农业科技园数据监测及控制系统" showfooter="true">
		<jsp:attribute name="titlebtns">
		</jsp:attribute>
		<jsp:attribute name="body">
			<form novalidate action="j_spring_security_check" method="post" class="form-horizontal">
                <template:formItem label="用户名:" name="j_username" placeholder="请输入用户名"
                                   value="${param.j_username}"></template:formItem>
                <template:formItem label="密码:" name="j_password" placeholder="请输入密码" value="${param.j_password}"
                                   type="password"></template:formItem>
                <div id="errorDiv" class="alert alert-danger login-message"
                     style="display: <c:if test='${empty param.login_error}'>none</c:if>">
                    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </div>
            </form>
		</jsp:attribute>
		<jsp:attribute name="footer">
			<template:panelfooterbtns>
                <input id="loginBtn" name="signIn" class="btn btn-info" type="button" value="登&nbsp;&nbsp;录" style="padding-left: 35px;padding-right: 35px;">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="<spring:url value="/guest/account/forgetPassword"/>">忘记密码</a>
            </template:panelfooterbtns>
		</jsp:attribute>
    </template:panel_login>
    <script type="text/javascript" src="/js/signin.js?a=5"></script>
</template:guest>
