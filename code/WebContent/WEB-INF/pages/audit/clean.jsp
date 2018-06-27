<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="清除日志">
    <template:panel title="清除操作日志 -- <small>请选择要清除日志的时间段</small>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn href="/dashboard/audits" label="返回"
                                        icon="circle-arrow-left"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
			<form id="form" class="form-horizontal" action="<c:url value="/api/v1/dashboard/audits/clean"/>"
                  method="POST">
                <div class="form-group" style=" margin-left:0px;margin-right:0px">
                    <label class="col-sm-3 control-label">开始日期(包括):</label>

                    <div class="col-sm-8">
                        <input id="beginDate" name="beginDate" class="datepicker" type="text" readonly>
                    </div>
                </div>
                <div class="form-group" style=" margin-left:0px;margin-right:0px">
                    <label class="col-sm-3 control-label">结束日期(包括):</label>

                    <div class="col-sm-8">
                        <input id="endDate" name="endDate" class="datepicker" type="text" readonly>
                    </div>
                </div>
            </form>
			<hr/>
	 		<div class="form-group">
                <div class="col-sm-offset-3 col-sm-10">
                    <a id="cleanUpBtn" class="btn btn-info" href="##">开始清除</a>
                </div>
            </div>
		</jsp:attribute>
    </template:panel>
    <script type="text/javascript">
        <!--
        $(document).ready(function () {
            $("#beginDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });

            $("#endDate").datepicker({
                dateFormat: 'yy-mm-dd'
            });

            $('#cleanUpBtn').click(function () {
                dashboard.utils.ajax({
                    block: '#showMoreBar',
                    type: 'POST',
                    url: $('#form').attr('action'),
                    dataType: 'json',
                    data: $("#form").serialize(),
                    success: function (json) {
                        if (!json.succeed) {
                            dashboard.message.warning(json.message);
                            return;
                        }
                        dashboard.message.info('清理成功!');
                    }
                });
            });
        });
        //-->
    </script>
</template:identity>