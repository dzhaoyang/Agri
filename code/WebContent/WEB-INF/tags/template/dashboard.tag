<%@tag description="Overall Page template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="pageTitle"%>
<template:splitLayout pageTitle="Identity - ${pageTitle}">
	<jsp:attribute name="part1">
		<jsp:doBody />
	</jsp:attribute>
	<jsp:attribute name="part2">
		<template:subnavGenerator
			data="/identity/user/list.do,Users,/identity/user,
			/identity/group/list.do,Groups,/identity/group,
			/identity/role/list.do,Roles,/identity/role">
			</template:subnavGenerator>
	</jsp:attribute>
</template:splitLayout>