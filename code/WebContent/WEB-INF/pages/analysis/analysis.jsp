<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:identity pageTitle="统计分析">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="统计分析">
		<jsp:attribute name="titlebtns">
			<%-- <template:panelTitleBtn href="javascript:void(0)" id="backBtn" label="返回" icon="circle-arrow-left"/> --%>
		</jsp:attribute>
		<jsp:attribute name="body">
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">年龄段分布</a>
				</li>
				<li>
					<div class="col-sm-offset-3 form-actions">
						<a id="ageBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 2px 12px;font-size: 13px;">开始统计</a>
					</div>
				</li>
			</ul>
			<div style="width: 100%;">
				<div id="ageLoad" style="width: 100%;text-align: center;display: none;">
					<img src="/img/loading.gif">
				</div>
				<table style="width: 100%">
					<tbody>
						<tr>
							<td width="50%">
								<table id="ageTable" class="table table-bordered table-striped" style="margin-bottom: 0px;">
									<thead>
						                <tr>
											<th width='34%' style="text-align: center;">年龄段</th>
											<th width='33%' style="text-align: center;">人数</th>
											<th width='33%' style="text-align: center;">占比</th>
						                </tr>
					                </thead>
					                <tbody></tbody>
								</table>
							</td>
							<td width="50%">
								<div id="ageAnalysis" style="text-align: center;width: 100%"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;width: 100%;margin-top: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">性别分布</a>
				</li>
				<li>
					<div class="col-sm-offset-3 form-actions">
						<a id="sexBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 2px 12px;font-size: 13px;">开始统计</a>
					</div>
				</li>
			</ul>
			<div style="width: 100%;">
				<div id="sexLoad" style="width: 100%;text-align: center;display: none;">
					<img src="/img/loading.gif">
				</div>
				<table style="width: 100%">
					<tbody>
						<tr>
							<td width="50%">
								<table id="sexTable" class="table table-bordered table-striped" style="margin-bottom: 0px;">
									<thead>
						                <tr>
											<th width='34%' style="text-align: center;">性别</th>
											<th width='33%' style="text-align: center;">人数</th>
											<th width='33%' style="text-align: center;">占比</th>
						                </tr>
					                </thead>
					                <tbody>
					                </tbody>
								</table>
							</td>
							<td width="50%">
								<div id="sexAnalysis" style="width: 100%;text-align: center;"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;margin-top: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">时间分布</a>
				</li>
				<li></li>
			</ul>
			<div style="width: 100%;text-align: left;">
				<table style="width: 100%">
					<tbody>
						<tr>
							<td width="35%">
								<input id="startTime" type="text" placeholder="单击选择开始时间" readonly="readonly" style="width: 40%;">
								<input id="endTime" type="text" placeholder="单击选择结束时间" readonly="readonly" style="margin-left: 5px;width: 40%;">
							</td>
							<td>
								<div class="col-sm-offset-3 form-actions" style="margin-left: 5px;width: 100%;text-align: left;">
									<a id="timeBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 2px 12px;font-size: 13px;">开始统计</a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div id="timeLoad" style="width: 100%;text-align: center;display: none;">
					<img src="/img/loading.gif">
				</div>
				<div id="timeAnalysis" style="width: 100%;text-align: left;"></div>
			</div>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/ichart.latest.min.js"></script>
<script type="text/javascript" src="/js/dashboard/analysis.js?a=6"></script>