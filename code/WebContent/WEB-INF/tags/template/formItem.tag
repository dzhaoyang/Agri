<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="idPrefix"%>
<%@ attribute name="name"%>
<%@ attribute name="label"%>
<%@ attribute name="value"%>
<%@ attribute name="maxlength"%>
<%@ attribute name="required" type="java.lang.Boolean"%>
<%@ attribute name="type"%>
<%@ attribute name="readonly"%>
<%@ attribute name="placeholder"%>
<%
String _value = (value==null || value.equals("null"))?"":value;
String _idPrefix = (idPrefix==null || idPrefix.equals("null"))?"":idPrefix;
String parentClass = "form-group";
%>
<div class="<%=parentClass%>">
	<%
		if("help".equals(type)){
	%>
<span id="<%=idPrefix==null?"":_idPrefix %><%=name %>" class="col-sm-offset-3 help-block" style="padding-left:15px;"><%=_value%></span>
	<%		    
		}else{
		    String eleId = _idPrefix + name;
	%>
	<label class="col-sm-2 control-label" for="<%=eleId%>"><%=label%></label>
	<div class="col-sm-8">

		<%
		    if ("file".equals(type)) {
		%>
			<input id="<%=eleId%>" class="form-control" name="<%=name%>" type="file">
		<%
		    }else if("textarea".equals(type)){
		%>
			<textarea class="form-control" id="<%=eleId%>" name="<%=name%>" rows="3" 
			<%if(null!=readonly&&"readonly".equals(readonly)){
			  %>
			  readonly="<%=readonly %>"
			  <%  
			}%>
			
			><%=_value%></textarea>
		<%    		   
		    }else if("password".equals(type)){
		 %>
		 	<input id="<%=eleId%>" class="form-control" name="<%=name%>" type="password" value="<%=_value%>"
		 		<%if (null != placeholder) {%> 
				placeholder="<%=placeholder%>"
				<%}%> 
		 	>
		 <%       
		    }else if("checkbox".equals(type)){
		 %>
			<div class="checkbox"><label><input type="checkbox" value="true" name="<%=name%>" id="<%=eleId%>" <%="true".equalsIgnoreCase(value)?"checked=checked":"" %>/>是</label></div>
		 <%       
		    }else if("checkbox_01".equals(type)){
		 %>
			<div class="checkbox"><label><input type="checkbox" value="1" name="<%=name%>" id="<%=eleId%>" <%="1".equalsIgnoreCase(value)?"checked=checked":"" %>/>是</label></div>
		 
		 <%       
		    }
		    else {
		%>
		<input class="form-control" id="<%=eleId%>" name="<%=name%>"
			<%if (null != maxlength) {%> 
				maxlength="<%=maxlength%>"
			<%}%> 
			
			type="text"
			
			<%if(null!=readonly&&"readonly".equals(readonly)){
			  %>
			  readonly="<%=readonly %>"
			  <%  
			} %>
			
			<%if (null != placeholder) {%> 
				placeholder="<%=placeholder%>"
			<%}%> 
			
			 value="<%=_value%>">
		
		<%
		    }
		%>


	</div>
	<%
	    if (null != required && required) {
	%>
	<div class="col-sm-1">
		<label class="control-label">*</label>
	</div>
	<%
	    }
		}
	%>

</div>
