<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test upload</title>
</head>
<body>
	<h1>test upload</h1>

	<form action="/api/v1/my/profile/avatars" method="post"
		enctype="multipart/form-data">
		<p>
			<label for="bizId">title:</label> <input type="text" name="title" />
		</p>
		<p>
			<label for="bizId">description:</label> <input type="text"
				name="description" />
		</p>
		<p>
			<label for="file">choose file:</label> 
			<input type="file" name="file" id="file" />
		</p>
		<p>
			<input type="submit" value="upload the image" />
		</p>
		<c:if test="${not empty avatar}">
		<p><img alt="avatar" src="<c:url value='/media/photo/${avatar}'/>"></p>
		</c:if>
	</form>

</body>
</html>