<%@tag description="Overall Page template" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="pageTitle"%>
<%@attribute name="topbarTitle"%>
<%@attribute name="topbarItems" fragment="true"%>
<%@attribute name="bottombarItems" fragment="true"%>
<%@attribute name="appBody" fragment="true"%>
<%@attribute name="showBackBtn"%>
<%@attribute name="showTitleBar"%>
<%
	String _topbarTitle = topbarTitle;
	if (null == topbarTitle || topbarTitle.isEmpty()) {
		_topbarTitle = pageTitle;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<link rel="shortcut icon" href="/img/favicon.ico">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/css/bootstrap.min.css">

<!-- Latest compiled and minified JavaScript -->
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>

<!-- Custom CSS-->
<link rel="stylesheet" href="/css/customize-mobile.css">
<!-- Le styles -->
<link href="/css/jquery.ui.1.8.16.custom.css" rel="stylesheet" type="text/css" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script type="text/javascript" src="/js/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script type="text/javascript" src="/js/jquery.stringifyjson.js"></script>
<script type="text/javascript" src="/js/bootbox.min.js"></script>
<script type="text/javascript" src="/js/carowner/carowner.js"></script>
<title>${pageTitle}</title>
<style type="text/css">
</style>

<script type="text/javascript">
<!--
	$(document).ready(function() {
		$('.collapse').on('shown.bs.collapse', function() {
			$('#topBarIcon').removeClass("glyphicon-chevron-down");
			$('#topBarIcon').addClass("glyphicon-chevron-up");
		});
		$('.collapse').on('hidden.bs.collapse', function() {
			$('#topBarIcon').removeClass("glyphicon-chevron-up");
			$('#topBarIcon').addClass("glyphicon-chevron-down");
		});
	});
//-->
</script>
</head>
<body>
	<!-- Fixed navbar -->
	<c:if test='${"false" ne showTitleBar}'>
		<div class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">

				<div class="navbar-header">
					<a type="button" class="navbar-toggle glyphicon glyphicon-refresh" style="border: 0px; color: #000000;"
						href="javaScript:window.refresh();"> </a>

					<c:if test='${"false" eq showBackBtn}'>
						<div class="navbar-brand" href="##">
							<span class="glyphicon"></span>
						</div>
					</c:if>

					<c:if test='${"false" ne showBackBtn}'>
						<a class="navbar-brand" href="javaScript:window.history.go('-1');"><span class="glyphicon glyphicon-arrow-left"></span></a>
					</c:if>


					<div style="padding-top: 6px; margin: 0px auto; vertical-align: text-bottom; text-align: center; min-width: 67px;">
						<%
							if (null != topbarItems) {
						%>
						<h4 data-toggle="collapse" data-target=".navbar-collapse"><%=_topbarTitle%>
							<i id="topBarIcon" style="vertical-align: bottom;" class="glyphicon glyphicon-chevron-down"></i>
						</h4>
						<%
							} else {
						%>
						<h4><%=_topbarTitle%></h4>
						<%
							}
						%>
					</div>
				</div>

				<div id="topbarBtns" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<jsp:invoke fragment="topbarItems" />
					</ul>
				</div>
				<!--/.nav-collapse -->

			</div>
		</div>
	</c:if>
	<jsp:invoke fragment="appBody" />

	<%
		if (null != bottombarItems) {
	%>

	<!-- Fixed navbar -->
	<div class="navbar navbar-default navbar-fixed-bottom" role="navigation">
		<jsp:invoke fragment="bottombarItems" />
	</div>
	<%
		}
	%>

	<script type="text/javascript">
		window.refresh = function() {
			window.location.href = window.location.href;
		}
	</script>
</body>
</html>