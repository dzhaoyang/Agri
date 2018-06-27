<%@tag description="Overall Page template" pageEncoding="UTF-8"
       isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@attribute name="pageTitle" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/img/favicon.ico" >
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="/css/bootstrap.min.css">

    <!-- Custom CSS-->
    <link rel="stylesheet" href="/css/customize.css">
    <!-- Latest compiled and minified JavaScript -->
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <!-- Le styles -->
    <link href="/css/jquery.ui.1.8.16.custom.css" rel="stylesheet" type="text/css"/>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script type="text/javascript" src="/js/jquery.json.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/jquery.ui.1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="/js/bootbox.min.js"></script>
    <script type="text/javascript" src="/js/passwordStrengthMeter.js"></script>
	<script type="text/javascript" src="/js/jquery.form.js"></script>
	<script type="text/javascript" src="/js/jquery.blockUI.min.js"></script>
	<script type="text/javascript" src="/js/dashboard/dashboard.js"></script>
    <title>${pageTitle}</title>
</head>
<body>
<div class="navbar-fixed-top">
    <template:topbar></template:topbar>
</div>
<div class="container" style="min-height: 400px;width: 100%;">
    <jsp:doBody/>
</div>
<template:footer></template:footer>
</body>
</html>