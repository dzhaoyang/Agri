<%@tag description="Menubar" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<input type="hidden" id="id" name="id" value="${label.id}">
<input type="hidden" id="parkingId" name="parking.id" value="${label.parking.id}">
<input type="hidden" id="type" name="type" value="${label.type}">

<template:formItem label="标签编码：" name="code" value="${label.code}" required="true"></template:formItem>
<template:formItem label="自定义编号：" name="userDefine" value="${label.userDefine}"/>

<c:if test="${isInParking==1}">
	<template:formItem label="停车场：" name="parkName" readonly="readonly" value="${label.parking.name}"></template:formItem>
</c:if>
<c:if test="${isInParking==0}">
	<div class="form-group">
	<label class="col-sm-3 control-label" for="parking.name">停车场：</label>
	<div class="col-sm-7">
		 <input class="form-control" id="parkName" name="parking.name" type="text" readonly="readonly" 
		 placeholder="停车场,双击设置" value="${label.parking.name}">
	</div>
	<div class="col-sm-1">
		<label class="control-label">*</label>
	</div>
</div>
</c:if>

<template:formItem label="发射频率：" name="rate" placeholder="秒(整数)" value="${label.rate}" ></template:formItem>
<template:formItem type="checkbox_01" label="是否启用：" name="isValid" value="${label.isValid}" ></template:formItem>

<c:if test="${not empty label.id}">
	<template:formItem label="创建时间：" name="createAt" readonly="readonly" value="${label.createAtStr}"></template:formItem>               
	<template:formItem label="最修改时间：" name="modifiedAt" readonly="readonly" value="${label.modifiedAtStr}"></template:formItem>
</c:if>

