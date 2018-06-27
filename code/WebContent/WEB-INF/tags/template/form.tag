<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id"%>
<%@ attribute name="action"%>
<%@ attribute name="type"%>
<%@ attribute name="method"%>
<%@ attribute name="enctype" %>
<%
	String _type = "horizontal";
	if(null!=type){
	    _type = type;   
	}
	
	String _enctype = "";
	if (null != enctype) {
	    _enctype = " enctype=\"" + enctype + "\"";
	}
%>
<form id="<%=id %>" action="<%=action %>" class="form-<%=_type %>" role="form" method="<%=method%>" <%=_enctype%>>
<jsp:doBody></jsp:doBody>
</form>
