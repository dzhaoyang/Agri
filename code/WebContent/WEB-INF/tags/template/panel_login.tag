<%@tag description="Panel" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="title"%>
<%@attribute name="type" %>
<%@attribute name="titlebtns" fragment="true"%>
<%@attribute name="titlebody" fragment="true"%>
<%@attribute name="body" fragment="true"%>
<%@attribute name="showfooter" type="java.lang.Boolean"%>
<%@attribute name="footer" fragment="true"%>
<%@attribute name="customBody"%>
<%
    String _type = "default";
    if (null != type) {
        _type = type;
    }
%>
<div class="panel panel-<%=_type%>" style="padding:0px">
	<%
	    if (null != title || null != titlebtns) {
	%>
	<div class="panel-heading clearfix" style="height: auto;">
		<h3 class="pull-left" style="width: 100%;text-align: center;margin-top: 10px;">${title}</h3>
		<div class="pull-right btn-group"><jsp:invoke
				fragment="titlebtns"></jsp:invoke></div>
	</div>
	<%
	    }
	%>
	
	<div class="panel-body" style="padding: 10px 5px;">
        <jsp:invoke fragment="titlebody"></jsp:invoke>
		<jsp:invoke fragment="body"></jsp:invoke>
	</div>

	<c:if test="${showfooter}">
		<div class="panel-footer" style="padding: 15px 15px;">
			<jsp:invoke fragment="footer"></jsp:invoke>
		</div>
	</c:if>
</div>