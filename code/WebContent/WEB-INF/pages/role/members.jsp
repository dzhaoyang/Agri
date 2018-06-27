<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="角色成员">
	<template:panel title="角色成员---- ${role.description} <span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
				<template:panelTitleFormItem id="filterName" placeholder="在此输入要查询的用户名" title=""></template:panelTitleFormItem>
				<template:panelTitleBtn id="searchBtn" href="javascript:void(0);" icon="search" label="查询"></template:panelTitleBtn>
				<template:panelTitleBtn id="refreshBtn" href="javascript:void(0);" icon="refresh" label="刷新"></template:panelTitleBtn>
				<template:panelTitleBtn href="/dashboard/identity/roles" icon="circle-arrow-left" label="返回"></template:panelTitleBtn>
			</template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
			<input type="hidden" id="roleId" name="roleId" value="${role.id}"/>
			<table id="dataContainer" class="table table-bordered  table-striped">
				<thead>
				<tr>
				<th width='30'>#</th>
				<th width='100'>用户名</th>
				<th width='100'>姓名</th>
				<th width='100'>电子邮箱</th>
				<th>联系电话</th>
				<th width='50'>状态</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
	        </table>
	        <div id="showMoreBar" class="text-center">
			</div>
		</jsp:attribute>
	</template:panel>
<script type="text/javascript" src="/js/dashboard/role.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function() {
	dashboard.role.members.init();	
});
//-->
</script>
</template:identity>
