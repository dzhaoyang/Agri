<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<template:task pageTitle="任务设置">
	<template:panel title="任务设置" showfooter="true">
		<jsp:attribute name="body">
			<form id="" class="form-horizontal" method="POST">
				<template:formItem label="发送通知给处理人:" type="checkbox"/>
				<template:formCustomItem label="通知类型:">
					<select class="form-control">
						<option>短信</option>
						<option>邮件</option>
						<option>短信+邮件</option>
					</select>
				</template:formCustomItem>
			
				<template:formItem label="激活任务报警:" type="checkbox"/>
				<template:formCustomItem label="报警类型:">
					<select class="form-control">
						<option>按时间优先</option>
						<option>按类型优先</option>
					</select>
				</template:formCustomItem>
            </form>			
		</jsp:attribute>
		<jsp:attribute name="footer">
			<template:panelfooterbtns>
				<a href="javascript:save();" class="btn btn-primary">保存设置</a>
			</template:panelfooterbtns>
		</jsp:attribute>
	</template:panel>
    <script type="text/javascript">
        <!--
        //-->
    </script>
</template:task>