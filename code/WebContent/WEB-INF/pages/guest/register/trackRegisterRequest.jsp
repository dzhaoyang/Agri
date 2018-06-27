<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>

<template:guest>
		<template:panel title="Tenant Register Request" showfooter="true">
			<jsp:attribute name="body">
			<form id="dataForm" action="registerResult.do" class="form-horizontal" method="GET">
				<template:formItem label="RequestId:" name="requestId" maxlength="80"></template:formItem>
			</form>
			</jsp:attribute>
			<jsp:attribute name="footer">
				<template:panelfooterbtns>
					<template:panelFooterBtn href="javascript:track();" type="info" label="Track" icon="search"></template:panelFooterBtn>
				</template:panelfooterbtns>
			</jsp:attribute>
		</template:panel>
	<script type="text/javascript">
	<!--
		var detailValidator;

		$(document).ready(function() {
			detailValidator = $("#dataForm").validate({
				rules : {
					"requestId" : {
						required : true
					}
				}
			});
		});

		function track() {
			if (!detailValidator.form()) {
				return;
			}

			$("#dataForm").submit();
		}
	//-->
	</script>

</template:guest>
