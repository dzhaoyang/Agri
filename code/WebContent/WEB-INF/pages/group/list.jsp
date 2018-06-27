<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="用户组管理">
	<template:panel title="用户组<span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
				<template:panelTitleFormItem id="filterName" placeholder="在此输入要查询的名字" title=""></template:panelTitleFormItem>
				<template:panelTitleBtn id="searchBtn" href="javascript:void()" icon="search" label="查询"></template:panelTitleBtn>
				<template:panelTitleBtn href="/dashboard/identity/groups/new" icon="plus" label="新增"></template:panelTitleBtn>
				<template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh" label="刷新"></template:panelTitleBtn>
			</template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
             <table id="dataContainer" class="table table-bordered table-striped hidden">
                <thead>
                <tr>
                    <th width='20'>#</th>
                    <th width='150'>名称</th>
                    <th>描述</th>
                    <th width='130'></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
			<div id="showMoreBar" class=" text-center">
			</div>
		</jsp:attribute>
	</template:panel>
<script type="text/javascript" src="/js/dashboard/group.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function() {
	dashboard.group.list.init();	
});
//-->
</script>
</template:identity>
 