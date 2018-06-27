<%@tag description="Well Title Button" pageEncoding="UTF-8" isELIgnored="false"%>
<%@attribute name="href"%>
<%@attribute name="label"%>
<%@attribute name="icon"%>
<%@attribute name="title"%>
<%@attribute name="type"%>
<%
	String _type ="default";
	if(null!=type){
	    _type = type;
	}
%>
<a href="<%=href %>" class="btn btn-<%=_type %> btn-sm" <%=null==title?"":"title='"+title+"'" %>>
<%=null==icon?"":"<span class='glyphicon glyphicon-"+icon+"'></span>"%>
<%=null==label?"":label %>
 </a>
