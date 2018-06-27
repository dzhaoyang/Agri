<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="新增/编辑 用户组">
	<template:panel title="新增/编辑 用户组" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:btnGroup>
				<template:panelTitleBtn href="${group.id == null ? '.':'..'}" icon="circle-arrow-left" label="返回" title="返回用户组列表"></template:panelTitleBtn>
			</template:btnGroup>
		</jsp:attribute>
		<jsp:attribute name="body">
            <div id="dataContainer" class="panel-body">
	            <form id="groupForm" class="form-horizontal">
	                <input type="hidden" id="id" name="id" value="${group.id}">
	
	                <div class="form-group">
	                    <label class="col-sm-3 control-label">名称：</label>
	
	                    <div class="col-sm-4">
	                    	<div class="col-sm-11">
	                        	<input class="form-control" name="name" id="group_name" value="${group.name}"
	                               	type="text">&nbsp;
	                        </div>
	                        <label class="control-label">*</label>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-3 control-label">描述：</label>
	
	                    <div class="col-sm-4">
	                    	<div class="col-sm-11">
		                        <textarea class="form-control" name="description" id="group_description">${group.description}</textarea>
	                        </div>     
	                    </div> 
	                </div>
	            </form>
	        </div>
	        <div class="panel-footer">
	                <div class="col-sm-offset-4 form-actions">
	                    <a id="saveBtn" class="btn btn-default btn-primary">保存</a>
	                </div>
	        </div>
		</jsp:attribute>
	</template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/group.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function() {
	dashboard.group.edit.init();	
});
//-->
</script>