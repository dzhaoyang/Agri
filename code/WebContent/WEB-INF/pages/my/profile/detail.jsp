<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:userLayout>
    <template:panel title="我的资料">
		<jsp:attribute name="titlebtns">
			<template:panelTitleBtn href="profile/${user.id}/edit" icon="edit" label="编辑" title="编辑"/>
		</jsp:attribute>
		<jsp:attribute name="body">
			<table class="table table-bordered  table-striped">
                <tr>
                    <td width="200px;">登录用户名:</td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td>姓名:</td>
                    <td>${user.name}</td>
                </tr>
                <tr>
                    <td>显示名:</td>
                    <td>${user.displayName}</td>
                </tr>
                <tr>
                    <td width="200px;">电子邮箱:</td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td width="200px;">联系电话:</td>
                    <td>${user.phoneNumber}</td>
                </tr>
                <tr>
                    <td>用户角色:</td>
                    <td><c:forEach items="${roles}" var="role">
                        <c:if test="${role.name!='用户' && role.name!='超级管理员'}">
                            ${role.name} ,
                        </c:if>
                    </c:forEach></td>
                </tr>
            </table>
		</jsp:attribute>
    </template:panel>
</template:userLayout>
