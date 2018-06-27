<%@tag description="Panel Title Form" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@attribute name="action"%>
<form class="form-inline" action="<%=action != null ? action : ""%>">
	<jsp:doBody></jsp:doBody>
</form>
