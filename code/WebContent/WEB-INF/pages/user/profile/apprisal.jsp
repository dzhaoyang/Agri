<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:userProfileLayout>
	<template:panel title="用户 - ${user.name} - 评价记录">
		<jsp:attribute name="body"> 
		<input type='hidden' name='userId' id='userId' value='${user.id}'/>
			<table id="appraisalsDataContainer" class="table table-bordered table-striped">
			    <thead>
			    <tr>
			        <th width='30'>#</th>
			        <th width='150'>停车场名</th>
			        <th width='100'>评分</th>
			        <th width='100'>评价</th>
			        <th width='100'>内容</th>
			        <th width='100'>日期</th>
			    </tr>
			    </thead>
			    <tbody>
			    </tbody>
			</table>
			<div id="appraisalsShowMoreBar" class="text-center">
			</div>
		</jsp:attribute>
	</template:panel>
</template:userProfileLayout>

<script type="text/javascript" src="/js/dashboard/userDetail.js"></script>
<script type="text/javascript">
dashboard.userDetail.apprisal.init();
</script>
