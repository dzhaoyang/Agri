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
<div id="main_div" class="panel panel-<%=_type%>" style="padding:0px">
	<%
	    if (null != title || null != titlebtns) {
	%>
	<div class="panel-heading clearfix">
		<h4 class="pull-left">${title}</h4>
		<div class="pull-right btn-group"><jsp:invoke
				fragment="titlebtns"></jsp:invoke></div>
	</div>
	<%
	    }
	%>
	
	<div class="panel-body" style="padding:5px">
        <jsp:invoke fragment="titlebody"></jsp:invoke>
		<jsp:invoke fragment="body"></jsp:invoke>
	</div>

	<c:if test="${showfooter}">
		<div class="panel-footer">
			<jsp:invoke fragment="footer"></jsp:invoke>
		</div>
	</c:if>
</div>