<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="清除登录日志">
    <template:panel title="清除登录日志 -- <small>请选择要清除日志的时间段</small>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn href="/dashboard/authentications" label="返回" icon="circle-arrow-left"/>
            </template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
			<template:form id="detailForm">
                <div class="form-group" style=" margin-left:0px;margin-right:0px">
                    <label class="col-sm-3 control-label">开始日期(包括):</label>

                    <div class="col-sm-8">
                        <input id="beginDate" name="beginDate" class="datepicker" type="text" readonly="readonly">
                    </div>
                </div>
                <div class="form-group" style=" margin-left:0px;margin-right:0px">
                    <label class="col-sm-3 control-label">结束日期(包括):</label>

                    <div class="col-sm-8">
                        <input id="endDate" name="endDate" class="datepicker" type="text" readonly="readonly">
                    </div>
                </div>
	            <template:formAction>
	                <template:panelFooterBtn id="cleanBtn" type="danger" label="开始清理"/>
	            </template:formAction>
			</template:form>
		</jsp:attribute>
    </template:panel>
    <script type="text/javascript" src="/js/dashboard/authentication.js"></script>
    <script type="text/javascript">
        <!--
        $(document).ready(function () {
            dashboard.authentication.clean.init();
        });
        //-->
    </script>
</template:identity>