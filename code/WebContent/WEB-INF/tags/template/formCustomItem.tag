<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="label"%>
<%@ attribute name="required" type="java.lang.Boolean"%>
<div class="form-group">
	<label for="" class="col-sm-3 control-label"><%=label==null?"":label %></label>
	<div class="col-sm-7">
		<jsp:doBody></jsp:doBody>
	</div>
	<%
	    if (null != required && required) {
	%>
	<div class="col-sm-1">
		<label class="control-label">*</label>
	</div>
	<%
	    }
	%>
</div>
