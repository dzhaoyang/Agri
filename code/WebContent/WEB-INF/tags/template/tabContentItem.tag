<%@tag description="Tab" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="id"%>
<%@attribute name="active" type="java.lang.Boolean"%>
<div id="<%=id %>" class="tab-pane <%=active!=null&&active?"active":""%>">
<jsp:doBody></jsp:doBody>
</div>
