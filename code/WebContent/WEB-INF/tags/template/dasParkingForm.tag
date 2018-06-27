<%@tag description="Menubar" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<input type="hidden" id="id" name="id" value="${parking.id}">

<template:formItem label="名称：" name="name" placeholder="名称" value="${parking.name}" required="true"></template:formItem>
<template:formItem type="textarea" label="地址：" name="address" value="${parking.address}" required="true"></template:formItem>
<template:formItem label="联系人：" name="contactName" placeholder="联系人" value="${parking.contactName}" required="true"></template:formItem>
<template:formItem label="联系电话：" name="phoneNumber" placeholder="联系电话" value="${parking.phoneNumber}" required="true"></template:formItem>

<template:formItem label="安装日期：" name="installDate" placeholder="单击选择" readonly="readonly" value="${parking.installDateStr}" required="true"></template:formItem>
<%-- <div class="form-group">
	<label class="col-sm-3 control-label" for="installDate">安装日期：</label>
	<div class="col-sm-7">
		 <input class="form-control datepicker" id="installDate" name="installDate" type="text" value="${parking.installDateStr}" readonly="readonly" title="单机选择">
	</div>

	<div class="col-sm-1">
		<label class="control-label">*</label>
	</div>
</div> --%>

<c:if test="${not empty parking.id}">
	<template:formItem label="创建时间：" name="createAt" readonly="readonly" value="${parking.createAtStr}"></template:formItem>               
	<template:formItem label="最修改时间：" name="modifiedAt" readonly="readonly" value="${parking.modifiedAtStr}"></template:formItem>
</c:if>