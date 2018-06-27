<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="应用发布">
    <template:panel title="应用版本列表">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn href="packages/new" icon="plus" label="新增" title="进入新增页面后，再选择本地apk上传"/>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh" label="刷新"/>
                <template:panelTitleBtn id="uploadByBackstageBtn" icon="plus" label="后台上传" title="apk事先上传到服务器临时目录，再通过此功能上传到正式目录中"/>
            </template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
			<table id="dataContainer" class="table table-bordered  table-striped">
                <thead>
                <tr>
                    <th width='20'>#</th>
                    <th width='120'>名称</th>
                    <th>包名</th>
                    <th width='100'>版本</th>
                    <th width='160'>发布时间</th>
                    <th width='100'></th>
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
<script type="text/javascript" src="/js/dashboard/package.js?sjs=Math.random()"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard.package.list.init();
    });
    //-->
</script>