<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:guest>
	<template:panel title="New Tenant Register" showfooter="true">
		<jsp:attribute name="body">
			<template:form id="dataForm" action="apply.do" method="POST">
				<template:formItem name="tenantName" maxlength="80" required="true" label="Name:"></template:formItem>
				<template:formItem name="tenantNamespace" maxlength="80" required="true" label="Namespace:"></template:formItem>
				<template:formItem type="help" value="Namespace Identifier must be between 2 and 30 characters started with lower-case letter, and lower-case letters, digits, and hyphens are acceptable characters."></template:formItem>
				<template:formItem  name="tenantAdminEmail" maxlength="80" required="true" label="Email:"></template:formItem>
				<template:formItem type="help" value="Be created as tenant admin account name."></template:formItem>
				<template:formCustomItem label="Services:">
					<div class="list-group">
						<div class="list-group-item">
							<div class="checkbox">
							<label> <input type="checkbox" name="services" value="ServiceSSO"> <strong>SSO</strong> <br/> SAML2 Single Sign On
							</label>
							</div>
						</div>
						<div class="list-group-item">
							<div class="checkbox">
							<label> <input type="checkbox" name="services" value="Service2Factor"> <strong>Two Factor</strong> <br/> Page-based and API-based two factor authentication.
							</label>
							</div>
						</div>
					</div>
						
				</template:formCustomItem>
				<%-- 
				<template:formItem idPrefix="company" name="address" maxlength="80" label="Address:"></template:formItem>
				<template:formItem idPrefix="company" name="website" maxlength="80" label="Website:"></template:formItem>
				<template:formItem idPrefix="company" name="description" maxlength="80" label="Description:" type="textarea"></template:formItem>
				 --%>
			</template:form>
		</jsp:attribute>
		<jsp:attribute name="footer">
			<template:panelfooterbtns>
				<template:panelFooterBtn type="info" href="javascript:save();" label="Create Tenant" icon="ok"></template:panelFooterBtn>
			</template:panelfooterbtns>
		</jsp:attribute>
	</template:panel>
	<script type="text/javascript">
	<!--
		var detailValidator;

		$(document).ready(function() {
			detailValidator = $("#dataForm").validate({
				rules : {
					"name" : {
						required : true
					},
					"namespace" : {
						required : true,
						minlength : 2,
						maxlength : 30,
						domainname : true
					},
					"email" : {
						required : true,
						email : true
					}
				}
			});
		});

		function save() {
			if (!detailValidator.form()) {
				return;
			}
			$("#dataForm").submit();
		}
	//-->
	</script>

</template:guest>
