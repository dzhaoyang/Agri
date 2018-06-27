<%@tag description="Overall Page template" pageEncoding="UTF-8"
       isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@attribute name="pageTitle" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="body" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>">

    <!-- Custom CSS-->
    <link rel="stylesheet" href="<c:url value='/css/customize.css'/>">
    <!-- Latest compiled and minified JavaScript -->
    <script src="<c:url value='/js/jquery.min.js'/>"></script>
    <script src="<c:url value='/js/bootstrap.min.js'/>"></script>


    <!-- Le styles -->
    <link href="<c:url value="/css/jquery.ui.1.8.16.custom.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/css/docs.css"/>" rel="stylesheet">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script type="text/javascript" src="<c:url value="/js/jquery.json.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.validate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.cookie.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.ui.1.8.16.custom.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/passwordStrengthMeter.js"/>"></script>
    <script type="text/javascript">
        <!--
        $(document).ready(function () {
            var appIndex = -1;
            if (window.location.href.indexOf("profile/") > 0) {
                appIndex = 0;
            }
            if (window.location.href.indexOf("profile/mobile/") > 0) {
                appIndex = 1;
            }
            if (window.location.href.indexOf("profile/phone/") > 0) {
                appIndex = 2;
            }
            if (window.location.href.indexOf("profile/email/") > 0) {
                appIndex = 3;
            }
            if (window.location.href.indexOf("profile/kba/") > 0) {
                appIndex = 4;
            }
            if (window.location.href.indexOf("dashboard/") > 0) {
                appIndex = 5;
            }
            $("#appNav li").eq(appIndex).addClass("active");

        });
        //-->
    </script>
    <title>${pageTitle}</title>
</head>
<body>
<div class="navbar-fixed-top">
    <template:topbar></template:topbar>
    <template:logobar></template:logobar>
    <template:navbar></template:navbar>
</div>
<div class="container" style="margin-top: 120px;">
    <div class="row" style="margin-top: 20px;">
        <div class="col-lg-9">
            <jsp:doBody/>
        </div>
        <div class="col-lg-3">
            <div class="panel panel-default">
                <div class="panel-heading">
                	<h4>
                    <span class="glyphicon  glyphicon-list"></span>
                    </h4>
                </div>
                <ul id="appNav" class="nav nav-pills nav-stacked">
                    <li><a href="/app/2faas/profile/detail.do">My Profile</a></li>
                    <li><a href="/app/2faas/profile/mobile/list.do">Mobile Phone</a></li>
                    <li><a href="/app/2faas/profile/phone/list.do">Phone</a></li>
                    <li><a href="/app/2faas/profile/email/list.do">Email</a></li>
                    <li><a href="/app/2faas/profile/kba/list.do">KBA</a></li>
                    <li><a href="/app/2faas/dashboard/settings.do">2-Factor Settings</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>