<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<template:task pageTitle="系统设置">
	<template:panel title="系统设置" showfooter="true">
		<jsp:attribute name="body">
			<form id="" class="form-horizontal" method="POST">
				<template:formCustomItem label="系统语言:">
					<select class="form-control">
						<option>简体中文</option>
						<option>English</option>
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