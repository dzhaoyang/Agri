<%@tag description="Well" pageEncoding="UTF-8" isELIgnored="false"%>
<%@attribute name="title"%>
<%@attribute name="titleBtns" fragment="true"%>
<div class="well">
	<%
		if(null!=title){
	%>
	<div class="clearfix">
	<h5 class="pull-left"><%=title %></h5>
	
	<%
		if(null!=titleBtns){
	%>
	
	<div class="pull-right btn-group">
		<jsp:invoke fragment="titleBtns"></jsp:invoke>
	</div>
	<%	    
		}
	%>
	
   	</div>
   	<hr style="margin-top:0px;"/>
	<%
		}
	%>
	<jsp:doBody></jsp:doBody>
</div>