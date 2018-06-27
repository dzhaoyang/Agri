<%@tag description="Tab" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="id" required="false"%>
<%@attribute name="href"%>
<%@attribute name="target"%>
<%@attribute name="label"%>
<%@attribute name="type"%>
<%@attribute name="icon"%>
<%
String _type ="default";
if(null!=type){
    _type = type;
}

String _target = "";
if(null!=target) {
    _target = "target=\"" + target + "\"";
}

if (null == href) {
    href = "javascript:void(0)";
}
%>
<a id="<%=id %>" href="<%=href %>" <%=_target %> class="btn btn-<%=_type %>">
<%
	if(null!=icon){
	    %>
	    
	    <span class="glyphicon glyphicon-<%=icon%>"></span>
	    <%
	}
%>
<%=label %></a>
