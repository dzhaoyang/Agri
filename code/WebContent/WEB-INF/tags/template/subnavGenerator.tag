<%@tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="data" required="true"%>
<%
String[] navs = data.split(",");
%>
	<ul class="nav nav-pills nav-stacked">
		<%
			for(int i=0; i < navs.length; i=i+3){
		%>
			<li>
				<a href="<c:url value="<%=navs[i].trim()%>"/>"><span><%=navs[i+1].trim() %></span><span class="glyphicon glyphicon-chevron-right"></span></a>
			</li>
		<%
			}
		%>
	</ul>
<script type="text/javascript">
<!--
$(function(){
	var currentLocation = window.location.href;
	var selectedMenuIndex = -1;
	<%
		for(int i=0;i<navs.length/3;i++){
		String[] matchedUrls = navs[i*3+2].split(";");
			for(String matchedUrl : matchedUrls){
		    
		    
		
	%>
		if(currentLocation.indexOf('<%=matchedUrl.trim()%>')>-1){
			selectedMenuIndex = <%=i%>;				
		}
	<%
			}
		}	
	%>
	
	if(selectedMenuIndex>-1){
		$('.nav-stacked>li:eq('+selectedMenuIndex+')').addClass('active');
	}
});
//-->
</script>

