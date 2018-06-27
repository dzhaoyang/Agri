<%@tag description="Panel Title Button" pageEncoding="UTF-8" isELIgnored="false"%>
<%@attribute name="id"%>
<%@attribute name="href"%>
<%@attribute name="label"%>
<%@attribute name="icon"%>
<%@attribute name="title"%>
<%@attribute name="type"%>
<%@attribute name="htmlAttrs"%>
<%
	String _type = "default";
	if(null != type){
	    _type = type;
	}
	
	if (href == null) {
	    href = "javascript:void(0);";
	}
%>
<a id="<%=id %>" href="<%=href %>" class="btn btn-<%=_type %> btn-xs" <%=null==title?"":"title='"+title+"'" %> <%=htmlAttrs==null?"":htmlAttrs %>>
<%=null==icon?"":"<span class='glyphicon glyphicon-"+icon+"'></span>"%>
<%=null==label?"":label %>
 </a>
