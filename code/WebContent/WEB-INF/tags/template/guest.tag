<%@tag description="Overall Page template" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@attribute name="pageTitle" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/img/favicon.ico">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>">
    <!-- Custom CSS-->
    <link rel="stylesheet" href="<c:url value='/css/customize.css'/>">
    <!-- Latest compiled and minified JavaScript -->
    <script src="<c:url value='/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/js/bootstrap.min.js'/>"></script>
    <!-- Le styles -->
    <link href="<c:url value="/css/jquery.ui.1.8.16.custom.css"/>" rel="stylesheet" type="text/css"/>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script type="text/javascript" src="<c:url value="/js/jquery.json.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.validate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.cookie.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.ui.1.8.16.custom.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
    <title>巴中农业科技园数据监测及控制系统 - ${pageTitle}</title>
</head>
<body style="background:url(/img/agri/login_background.jpg) no-repeat 0px 0px;">
<%-- <div class="navbar-fixed-top">
    <template:topbar></template:topbar>
</div> --%>
<div id="login_container_div" style="width: 100%;top: 50%;position:absolute;margin-top: -110px;"><!-- class="container" -->
    <div style="max-width: 600px; margin: auto;">
        <jsp:doBody/>
    </div>
</div>
<template:footer_login></template:footer_login>
</body>
</html>