<%@tag description="Panel Title Form Item" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@attribute name="id"%>
<%@attribute name="placeholder"%>
<%@attribute name="title"%>
<div class="form-group">
	<input class="form-control input-sm" id="<%=id%>" type="text"
		placeholder="<%=placeholder%>" title="<%=title%>">
</div>