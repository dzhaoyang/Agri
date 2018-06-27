<%@tag description="Menubar" pageEncoding="UTF-8"
       isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<input type="hidden" id="id" name="id" value="${parking.id}">
<input type="hidden" name="status" value="${parking.status}">
<input type="hidden" name="icon" value="${parking.icon}"/>
<input type="hidden" name="parkPercent" value="${parking.parkPercent}"/>
<input type="hidden" name="ownerPercent" value="${parking.ownerPercent}"/>
<input type="hidden" name="parkEasyPercent" value="${parking.parkEasyPercent}"/>
<%-- <input type="hidden" name="createAt" value="${parking.createAt}"/> --%>
<%--
<input type="hidden" name="creator.id" value="${parking.creator.id}">
<input type="hidden" name="creator.name" value="${parking.creator.username}">
 --%>
<template:formItem label="名称：" name="name" value="${parking.name}"
                   required="true"></template:formItem>

<template:formItem type="checkbox" label="支持预订：" name="acceptBooking"
                   value="${parking.acceptBooking}"></template:formItem>
<template:formItem label="总车位数：" name="parkingLot.total" value="${parking.parkingLot.total}" required="true"/>
<c:if test="${not empty parking.id}">
<template:formItem label="空闲车位数：" name="parkingLot.available" value="${parking.parkingLot.available}"/>
<template:formItem label="已预订车位数：" name="parkingLot.booking" value="${parking.parkingLot.booking}" readonly="readonly"/>
</c:if>
<div class="form-group">
    <label class="col-sm-3 control-label">联系人：</label>
    <div class="col-sm-7">
        <input type="text" class="form-control" name="contactName"
               value="${parking.contactName}" placeholder="联系人">
    </div>
    <div class="col-sm-1">
        <label class="control-label">*</label>
    </div>    
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">联系电话：</label>
    <div class="col-sm-7">
        <input type="text" class="form-control" name="phoneNumber"
               value="${parking.phoneNumber}" placeholder="联系电话">
    </div>
    <!-- div class="col-sm-1">
        <label class="control-label">*</label>
    </div -->    
</div>
<template:formItem label="开户银行：" name="parkingAccount.bankName" value="${parking.parkingAccount.bankName}"/>
<template:formItem label="账户名称：" name="parkingAccount.account" value="${parking.parkingAccount.account}"/>
<template:formItem label="银行账号：" name="parkingAccount.accountNumber" value="${parking.parkingAccount.accountNumber}"/>
<%-- <template:formItem label="起步价：" name="baseFare" placeholder="元"
                   value="${parking.baseFare}" required="true"></template:formItem>                   
<template:formItem label="预订金：" name="bookingFare" placeholder="(元/次)"
                   value="${parking.bookingFare}" required="true"></template:formItem>                   
<template:formItem label="单价：" name="farePerUnit" placeholder="(元/小时)"
                   value="${parking.farePerUnit}" required="true"></template:formItem> --%>
<template:formItem type="textarea" label="价格描述：" name="fareDescription"
                   value="${parking.fareDescription}"></template:formItem>
<template:formItem type="textarea" label="停车场介绍：" name="description"
                   value="${parking.description}"></template:formItem>


<div class="form-group">
    <label class="col-sm-3 control-label">服务热线：</label>
    <div class="col-sm-7">
        <input type="text" class="form-control" name="phoneNumber1"
               value="${parking.phoneNumber1}" placeholder="服务热线">
    </div>
    <!--div class="col-sm-1">
        <label class="control-label">*</label>
    </div -->
</div>