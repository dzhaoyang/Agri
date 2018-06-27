<%@tag description="Tab" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@attribute name="title"%>
<%@attribute name="target"%>
<%@attribute name="active" type="java.lang.Boolean"%>
 <li class="<%=active!=null && active?"active":""%>"><a href="#<%=target %>" data-toggle="tab"><%=title %></a></li>