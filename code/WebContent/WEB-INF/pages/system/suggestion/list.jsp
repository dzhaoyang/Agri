<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="用户意见反馈">
    <template:panel title="意见反馈列表<span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0);" icon="refresh"
                                        label="刷新"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>

		<jsp:attribute name="body">
             <table id="dataContainer" class="table table-bordered table-striped hidden">
                 <thead>
                 <tr>
                     <th width='30'>#</th>
                     <th width='100'>用户</th>
                     <th>意见</th>
                     <th width='160'>时间</th>
                     <th width='80'>来自</th>
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
<script type="text/javascript" src="/js/dashboard/system.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function () {
    dashboard.system.suggestion.list.init();
});
//-->
</script>