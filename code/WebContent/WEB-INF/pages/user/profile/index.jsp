<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:userProfileLayout>
	<template:panel title="用户 - ${user.name}">
		<jsp:attribute name="body">
			<input type='hidden' name='userId' id='userId' value='${user.id}'/>
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
		            <td width="200px;">电子邮箱:</td>
		            <td>${user.email}</td>
		        </tr>
		        <tr>
		            <td width="200px;">联系电话:</td>
		            <td>${user.phoneNumber}</td>
		        </tr>
		        <tr>
		            <td>是否激活:</td>
		            <td>
		                <c:if test="${user.enabled == 'true'}">是</c:if>
		                <c:if test="${user.enabled == 'false'}">否</c:if>
		            </td>
		        </tr>
		        <tr>
		            <td>用户创建时间:</td>
		            <td><fmt:formatDate value="${user.createAt}"
		                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
		        </tr>
		        <tr>
		            <td>最近编辑时间:</td>
		            <td><fmt:formatDate value="${user.modifiedAt}"
		                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
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
</template:userProfileLayout>
 