<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:identity pageTitle="发送信息">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="发送信息">
		<jsp:attribute name="titlebtns">
			<%-- <template:panelTitleBtn href="javascript:void(0)" id="backBtn" label="返回" icon="circle-arrow-left"/> --%>
		</jsp:attribute>
		<jsp:attribute name="body">
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">龄群短信</a>
				</li>
			</ul>
			<div style="width: 100%;">
				<form action="/" id="ageForm">
				<table style="margin-left: 5%;width: 95%">
					<tr style="height: 40px;">
						<td>
							指定年龄：<input type="text" id="age1" name="age1" placeholder="输入整数"/>&nbsp;到&nbsp;<input type="text" id="age2" name="age2" placeholder="输入整数"/>
							&nbsp;<a id="selectBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 2px 5px;height: 27px;">选择</a>
						</td>
						<td>人数：<input type="text" id="ageNumber" name="ageNumber" readonly="readonly"/></td>
						<td style="width: 20%">&nbsp;</td>
					</tr>
					<tr style="height: 40px;">
						<td colspan="2">
							短信内容：<textarea rows="5"style="width: 85.5%" id="opinion1" name="opinion1" placeholder="限128字以内"></textarea>
						</td>
						<td>
							<div class="col-sm-offset-3 form-actions" style="margin-left: 0%;">
								<a id="sendBtn1" href="javascript:void(0)" class="btn btn-primary" style="padding: 38px 12px;height: 98px;width: 98px;">发送</a>
							</div>
						</td>
					</tr>
				</table>
				</form>
			</div>
			<br>
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">全体短信</a>
				</li>
			</ul>
			<div style="width: 100%;">
				<form action="/" id="allForm">
				<table style="margin-left: 5%;width: 95%">
					<tr style="height: 40px;">
						<td>
							&nbsp;&nbsp;&nbsp;总人数：<input type="text" id="totalNumber" name="totalNumber" readonly="readonly" value="${totalNumber}"/>
						</td>
						<td>&nbsp;</td>
						<td style="width: 20%">&nbsp;</td>
					</tr>
					<tr style="height: 40px;">
						<td colspan="2">
							短信内容：<textarea rows="5"style="width: 85.5%" id="opinion2" name="opinion2" placeholder="限128字以内"></textarea>
						</td>
						<td>
							<div class="col-sm-offset-3 form-actions" style="margin-left: 0%;">
								<a id="sendBtn2" href="javascript:void(0)" class="btn btn-primary" style="padding: 38px 12px;height: 98px;width: 98px;">发送</a>
							</div>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript">
$(document).ready(function () {
	$('#selectBtn').bind('click', function () {
		dashboard.utils.ajax({
            block: '',
            type: 'POST',
            url: '/api/customer',
            dataType: 'json',
            data: $('#ageForm').serialize(),
            success: function (json) {
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                $('#ageNumber').val(json.data);
            }
        });
    });
	$('#sendBtn1').bind('click', function () {
		dashboard.utils.ajax({
            block: '',
            type: 'POST',
            url: '/send',
            dataType: 'json',
            data: $('#ageForm').serialize(),
            success: function (json) {
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                dashboard.message.info('发送成功！');
            }
        });
    });
	$('#sendBtn2').bind('click', function () {
		dashboard.utils.ajax({
            block: '',
            type: 'POST',
            url: '/send',
            dataType: 'json',
            data: $('#allForm').serialize(),
            success: function (json) {
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                dashboard.message.info('发送成功！');
            }
        });
    });
});
</script>