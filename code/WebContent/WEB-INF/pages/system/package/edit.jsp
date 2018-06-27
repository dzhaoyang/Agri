<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:identity pageTitle="应用发布">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="应用版本发布">
		<jsp:attribute name="titlebtns">
			<template:panelTitleBtn href="/dashboard/system/packages" label="返回"
                                    icon="circle-arrow-left"/>
			<p class="text-warning">${message}</p>
		</jsp:attribute>
		<jsp:attribute name="body">
            <template:form id="detailForm" action="save.do" enctype="multipart/form-data">
                <input type="hidden" id="id" name="id" value="${detail.id}">
                <template:formItem label="名称：" name="name" value="${detail.name}" required="true"/>
                
                <%-- 
                <template:formItem label="包名：" name="packageName" value="${detail.packageName}" required="true"/>
                <template:formItem label="版本号：" name="versionName" value="${detail.versionName}" required="true"/>
                <template:formItem label="版本序号：" name="versionCode" value="${detail.versionCode}" required="true"/>
				--%>
				
                <template:formItem label="说明：" name="description" type="textarea" value="${detail.description}"/>
                <template:formItem label="APK文件：" name="file" type="file" required="true"/>

                <template:formAction>
                    <template:panelFooterBtn id="saveBtn" type="primary" label="保存"/>
                </template:formAction>
            </template:form>
		</jsp:attribute>
		<jsp:attribute name="footer">
			
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/package.js?sjs=Math.random()"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard.package.edit.init();
    });
    //-->
</script>