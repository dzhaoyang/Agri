<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="报表策略">
	<template:panel title="报表策略" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
				<template:panelTitleBtn id="refreshBtn" icon="refresh"/>
			</template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
			<table id="dataContainer" class="table table-bordered  table-striped">
                <thead>
                <tr>
                    <th width='20'>#</th>
                    <th>策略名称</th>
                    <th width='100'>生成频率</th>
                    <th width='100'>关联区域</th>
                    <th width='100'>类别</th>
                    <th width='70'>状态</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div id="showMoreBar" class="hidden">
				<div class="well text-center">&nbsp;</div>
			</div>
		</jsp:attribute>
	</template:panel>
</template:identity>
<script type="text/javascript">
<!--
$(document).ready(function() {
	//dashboard.user.list.init();	
});
//-->
</script>