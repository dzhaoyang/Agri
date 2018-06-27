<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:identity pageTitle="开关信息">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="开关信息编辑">
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
						<div style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;提示： * 号标记信息必须填写</div>
					</div>
					<div style="width: 6%;" id="savebtnDiv">
						<a id="saveBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 1px 12px;">保存</a>
					</div>
					<div  id="savebtnDiv">
					</div>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="basicTab">
					<form action="/" id="baseForm">
						<input type="hidden" id="id" name="id" value="${data.id}" />
						<table style="width: 100%;">
							<tbody>
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">名称：</td>
									<td width="32%"><input class="form-control" id="name" name="name" type="text" value="${data.name}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="10%" align="right" style="font-weight: bold;">编码(标签)：</td>
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
									<td width="10%" align="right" style="font-weight: bold;">开关类型：</td>
									<td width="32%">
										<c:if test="${data.id==null}">
										<select id="type" name="type" class="form-control">
											<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
										<c:forEach var="switchType" items="${switchTypes}">
		                             		<option value="${switchType.id}">${switchType.name}</option>
		                             	</c:forEach>
											<%-- <option value="1" <c:if test='${data.type==1}'>selected="selected"</c:if>>喷灌头</option>
											<option value="2" <c:if test='${data.type==2}'>selected="selected"</c:if>>气帘</option> --%>
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
									<td width="8%" align="right" style="font-weight: bold;">读写器id：</td>
									<td width="32%"><input class="form-control" id="rWId" name="rWId" type="text" value="${data.rWId}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="8%" align="right" style="font-weight: bold;">继电器中顺序：</td>
									<td width="32%">
										<select id="position" name="position" class="form-control">
											<option value="">&nbsp;&nbsp;&nbsp;&nbsp;</option>
											<option value="1" <c:if test='${data.position==1}'>selected="selected"</c:if>>1</option>
											<option value="2" <c:if test='${data.position==2}'>selected="selected"</c:if>>2</option>
											<option value="3" <c:if test='${data.position==3}'>selected="selected"</c:if>>3</option>
											<option value="4" <c:if test='${data.position==4}'>selected="selected"</c:if>>4</option>
											<option value="5" <c:if test='${data.position==5}'>selected="selected"</c:if>>5</option>
											<option value="6" <c:if test='${data.position==6}'>selected="selected"</c:if>>6</option>
											<option value="7" <c:if test='${data.position==7}'>selected="selected"</c:if>>7</option>
											<option value="8" <c:if test='${data.position==8}'>selected="selected"</c:if>>8</option>
											<option value="9" <c:if test='${data.position==9}'>selected="selected"</c:if>>9</option>
											<option value="10" <c:if test='${data.position==10}'>selected="selected"</c:if>>10</option>
											<option value="11" <c:if test='${data.position==11}'>selected="selected"</c:if>>11</option>
											<option value="12" <c:if test='${data.position==12}'>selected="selected"</c:if>>12</option>
										</select>
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
									<td width="8%" align="right" style="font-weight: bold;">备注：</td>
									<td width="32%"><input class="form-control" id="location" name="location" type="text" value="${data.location}"></td>
									<td width="5%" align="center"></td>
								</tr>
								
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">创建时间：</td>
									<td width="32%"><input class="form-control" id="createTime" name="createTime" type="text" value="${data.createTime}" readonly="readonly"></td>
									<td width="5%" align="center"></td>
									<td width="8%" align="right" style="font-weight: bold;"></td>
									<td width="32%"></td>
									<td width="5%" align="center"></td>
								</tr>
								
							</tbody>
						</table>
					</form>
				</div>
			</div>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/switch.js?sjs=2"></script>
<script type="text/javascript">
<!--
$(document).ready(function () {
    dashboard._switch.edit.init();
});
//-->
</script>