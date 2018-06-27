<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:identity pageTitle="联系遐迩">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="联系遐迩">
		<jsp:attribute name="titlebtns">
			<%-- <template:panelTitleBtn href="javascript:void(0)" id="backBtn" label="返回" icon="circle-arrow-left"/> --%>
		</jsp:attribute>
		<jsp:attribute name="body">
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">联系方式</a>
				</li>
			</ul>
			<div style="width: 100%;">
				<table style="margin-left: 5%;width: 95%">
					<tr>
						<td height="45px">公司名称：&nbsp;成都遐迩科技有限公司</td>
						<td rowspan="3" style="width: 60%;text-align: right;"><img src="/img/contact.jpg" style="margin-right: 10%;width: 160px;height: 160px;"></td>
					</tr>
					<tr>
						<td height="45px">官方网站：&nbsp;<a href="javascript:void(0)" onclick="javascript:window.open('http://www.ShareTech.cc')">www.ShareTech.cc</a></td>
					</tr>
					<tr>
						<td height="45px">联系电话：&nbsp;18302844638 / 15928189723</td>
					</tr>
				</table>
			</div>
			<br>
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">建议/意见</a>
				</li>
			</ul>
			<div class="tab-content">
				<form action="/" id="infoForm">
				<div class="tab-pane active">
					<textarea rows="10"style="width: 100%" id="opinion" name="opinion" placeholder="欢迎您畅所欲言！遐迩的成长需要您！"></textarea>
				</div>
				<div class="col-sm-offset-3 form-actions" style="margin-left: 45%;margin-top: 20px;">
					<a id="saveBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 1px 12px;">发给遐迩</a>
				</div>
				</form>
			</div>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript">
$(document).ready(function () {
	$('#saveBtn').bind('click', function () {
		dashboard.utils.block('#infoForm');
		dashboard.utils.ajax({
            block: '',
            type: 'POST',
            url: '/send',
            dataType: 'json',
            data: $('#infoForm').serialize(),
            success: function (json) {
            	dashboard.utils.unblock('#infoForm');
                if (!json.succeed) {
                    dashboard.message.warning(json.message);
                    return;
                }
                dashboard.message.info('已经发送到遐迩科技，谢谢您的宝贵意见！');
            }
        });
		dashboard.utils.unblock('#infoForm');
    });
});
</script>