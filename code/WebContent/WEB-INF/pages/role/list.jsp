<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:identity pageTitle="角色管理">
    <template:panel title="角色列表 <span id='recordSize' class='small-text'></span>" customBody="true">
    	<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
				<template:panelTitleBtn href="roles/new" icon="plus" label="新增"></template:panelTitleBtn>
				<template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh" label="刷新"></template:panelTitleBtn>
			</template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body"> 
			<table id="dataContainer" class="table table-bordered  table-striped">
                <thead>
	               	<tr>
	                    <th width='20'>#</th>
	                    <th width='150'>编码</th>
	                    <th>名称</th>
	                    <th width='50'></th>
	                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div id="showMoreBar" class="text-center">
			</div>
		</jsp:attribute>
	</template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/role.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function () {
	dashboard.role.list.init();	
});
//-->
</script>