<%@tag description="SSO Page template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="pageTitle"%>
<template:splitLayout pageTitle="${pageTitle}">
	<jsp:attribute name="part1"> 
			<jsp:doBody />
	</jsp:attribute>
	<jsp:attribute name="part2"> 
		<template:subnavGenerator data="/sso/manager/sites/list.do,SP Sites Management,/sso/manager/sites,
				/sso/manager/samlattrs/list.do,SAML Attribute Management,/sso/manager/samlattrs"></template:subnavGenerator>
	</jsp:attribute>
</template:splitLayout>