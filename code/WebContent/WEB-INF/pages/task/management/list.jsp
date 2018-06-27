<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:task pageTitle="任务列表">
	<template:panel title="任务列表" customBody="true">
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
                    <th>任务名称</th>
                    <th width='100'>类别</th>
                    <th width='180'>处理人</th>
                    <th width='180'>生成时间</th>
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
</template:task>
<script type="text/javascript">
<!--
$(document).ready(function() {
});
//-->
</script>