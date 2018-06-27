<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:identity pageTitle="传感器信息">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="传感器信息编辑">
		<jsp:attribute name="titlebtns">
			<template:panelTitleBtn href="javascript:void(0)" id="backBtn" label="返回" icon="circle-arrow-left"/>
		</jsp:attribute>
		<jsp:attribute name="body">
			<ul id="infoUl" class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">基本信息</a>
				</li>
				<li>
					<div style="width: 94%;float: left;">
						<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;提示：基本信息编辑并保存后，还需要编辑并保存传感器收集的数据信息。 * 号标记信息必须填写</div>
					</div>
					<div style="width: 6%;" id="savebtnDiv">
						<a id="saveBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 1px 12px;">保存</a>
					</div>
				</li>
			</ul>
			<form action="/" id="baseForm">
			<div class="tab-content">
				<div class="tab-pane active" id="basicTab">
					<!-- <form action="/" id="baseForm"> -->
						<input type="hidden" id="id" name="id" value="${data.id}" />
						<table style="width: 100%;">
							<tbody>
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">名称：</td>
									<td width="32%"><input class="form-control" id="name" name="name" type="text" value="${data.name}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="10%" align="right" style="font-weight: bold;">编码：</td>
									<td width="32%"><input class="form-control" id="uuid" name="uuid" type="text" value="${data.uuid}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
								</tr>
								
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">所在位置：</td>
									<td width="32%">
										<select id="greenHouseId" name="greenHouse.Id" class="form-control">
												<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
											<c:forEach var="greenHouse" items="${greenHouses}">
			                             		<option value="${greenHouse.id}" <c:if test='${data.greenHouse.id==greenHouse.id}'>selected="selected"</c:if>>${greenHouse.name}</option>
			                             	</c:forEach>
										</select>
									</td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="10%" align="right" style="font-weight: bold;">传感器类型：</td>
									<td width="32%">
										<c:if test="${data.id==null}">
										<select id="type" name="type" class="form-control">
											<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
											<option value="1" <c:if test='${data.type==1}'>selected="selected"</c:if>>PM2.5传感器</option>
											<option value="2" <c:if test='${data.type==2}'>selected="selected"</c:if>>土壤传感器</option>
											<option value="3" <c:if test='${data.type==3}'>selected="selected"</c:if>>光照传感器</option>
										</select>
										</c:if>
										<c:if test="${data.id!=null}">
											<input type="hidden" id="type" name="type" value="${data.type}" />
											<input class="form-control" type="text" value="${typestr}" readonly="readonly">
										</c:if>
									</td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
								</tr>
								
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">位置(X)：</td>
									<td width="32%"><input class="form-control" id="coordinateX" name="coordinateX" type="text" value="${data.coordinateX}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="10%" align="right" style="font-weight: bold;">位置(Y)：</td>
									<td width="32%"><input class="form-control" id="coordinateY" name="coordinateY" type="text" value="${data.coordinateY}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
								</tr>
								
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">安装时间：</td>
									<td width="32%"><input class="form-control" id="installDate" name="installDate" type="text" value="${data.installDate}" placeholder="单击选择" readonly="readonly"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="8%" align="right" style="font-weight: bold;">创建时间：</td>
									<td width="32%"><input class="form-control" id="createTime" name="createTime" type="text" value="${data.createTime}" readonly="readonly"></td>
									<td width="5%" align="center"></td>
								</tr>
								
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">备注：</td>
									<td width="32%"><input class="form-control" id="location" name="location" type="text" value="${data.location}"></td>
									<td width="5%" align="center"></td>
									<td width="8%" align="right" style="font-weight: bold;"></td>
									<td width="32%"></td>
									<td width="5%" align="center"></td>
								</tr>
								
							</tbody>
						</table>
					<!-- </form> -->
				</div>
			</div>
			
			<c:if test="${data.transducerDataTypes!=null}">
			<c:forEach var="tdt" varStatus="ind" items="${data.transducerDataTypes}">
			<!-- <form action="/" id="detailForm"> -->
			<!-- <br> -->
			<br>
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">${tdt.dataTypeName}</a>
				</li>
				<li style="color: red;font-size: 13px">&nbsp;&nbsp;&nbsp;&nbsp;</li>
			</ul>
			<div style="width: 100%;">
				<input type="hidden" name="transducerDataTypes[${ind.index}].dataType" value="${tdt.dataType}">
				<input type="hidden" name="transducerDataTypes[${ind.index}].dataTypeName" value="${tdt.dataTypeName}">
				<table style="width: 100%;">
					<tbody>
						<tr>
							<td width="8%" align="right" style="font-weight: bold;">阈值上限：</td>
							<td width="32%"><input class="form-control" id="transducerDataTypes[${ind.index}].upperLimit" name="transducerDataTypes[${ind.index}].upperLimit" type="text" value="${tdt.upperLimit}"></td>
							<td width="5%" align="center"><span style="color: red;">*</span></td>
							<td width="10%" align="right" style="font-weight: bold;">阈值下限：</td>
							<td width="32%"><input class="form-control" id="transducerDataTypes[${ind.index}].lowerLimit" name="transducerDataTypes[${ind.index}].lowerLimit" type="text" value="${tdt.lowerLimit}"></td>
							<td width="5%" align="center"><span style="color: red;">*</span></td>
						</tr>
						
						<tr>
							<td width="8%" align="right" style="font-weight: bold;">计量单位：</td>
							<td width="32%"><input class="form-control" id="transducerDataTypes[${ind.index}].measure" name="transducerDataTypes[${ind.index}].measure" type="text" value="${tdt.measure}"></td>
							<td width="5%" align="center"><span style="color: red;">*</span></td>
							<td width="10%" align="right" style="font-weight: bold;">是否自动：</td>
							<td width="32%">
								<select id="transducerDataTypes[${ind.index}].isAuto" name="transducerDataTypes[${ind.index}].isAuto" class="form-control">
									<option value="0" <c:if test='${tdt.isAuto==0}'>selected="selected"</c:if>>否</option>
									<option value="1" <c:if test='${tdt.isAuto==1}'>selected="selected"</c:if>>是</option>
								</select>
							</td>
							<td width="5%" align="center"><span style="color: red;">*</span></td>
						</tr>
						
						<tr>
							<td width="8%" align="right" style="font-weight: bold;">超上限命令：</td>
							<td width="32%"><textarea class="form-control" id="transducerDataTypes[${ind.index}].upperLimitCommand" name="transducerDataTypes[${ind.index}].upperLimitCommand" placeholder="格式：开关编码=1\0,1代表开0代表关,一个开关一行命令">${tdt.upperLimitCommand}</textarea></td>
							<td width="5%" align="center"><span style="color: red;">*</span></td>
							<td width="10%" align="right" style="font-weight: bold;">超下限命令：</td>
							<td width="32%"><textarea class="form-control" id="transducerDataTypes[${ind.index}].lowerLimitCommand" name="transducerDataTypes[${ind.index}].lowerLimitCommand" placeholder="格式：开关编码=1\0,1代表开0代表关,一个开关一行命令">${tdt.lowerLimitCommand}</textarea></td>
							<td width="5%" align="center"><span style="color: red;">*</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- </form> -->
			</c:forEach>
			</c:if>
			</form>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/transducer.js?sjs=13"></script>
<script type="text/javascript">
<!--
$(document).ready(function () {
    dashboard.transducer.edit.init();
});
//-->
</script>