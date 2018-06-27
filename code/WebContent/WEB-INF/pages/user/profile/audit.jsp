<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:userProfileLayout>
	<template:panel title="用户 - ${user.name} - 操作记录">
		<jsp:attribute name="body"> 
		<input type='hidden' name='userId' id='userId' value='${user.id}'/>
			<table id="auditDataContainer"
				class="table table-bordered table-striped">
		        <thead>
		        <tr>
		            <th width='30'>#</th>
		            <th width='160'>时间</th>
		            <th width='100'>地址</th>
		            <th>操作资源</th>
		            <th width='50'></th>
		        </tr>
		        </thead>
		        <tbody>
		        </tbody>
		    </table>
		    <div id="auditShowMoreBar" class="text-center">
		    </div>
		</jsp:attribute>
	</template:panel>
</template:userProfileLayout>

<script type="text/javascript" src="/js/dashboard/userDetail.js"></script>
<script type="text/javascript">
dashboard.userDetail.audit.init();
</script>
