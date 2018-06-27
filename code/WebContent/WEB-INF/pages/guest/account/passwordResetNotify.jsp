<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:guest pageTitle="重置密码">
	<div class="panel panel-default">
			<div class="panel-heading">
				<h4>重置密码邮件已发送</h4>
			</div>
			<div id="dataContainer" class="panel-body">
				<p>
					重置密码邮件已发送，请检查你的 <b>${email}</b> 邮箱。
				</p>
			</div>
	</div> 
</template:guest>		
