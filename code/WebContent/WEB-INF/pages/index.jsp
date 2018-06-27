<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:index pageTitle="WePack">
<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
 
  <!-- Wrapper for slides -->
  <div class="carousel-inner">
    <div class="item active">
      <!-- <img style="width:100%" src="img/lllll.jpg" alt=""> -->
      <div class="carousel-caption">
      	<%-- <h3>农业检测及控制系统</h3>
      	<ul>
      		<li>帮你寻找最近的停车路线</li>
      		<li>帮你寻找最便宜的停车场</li>
      		<li>帮你寻找最多的停车位</li>
      	</ul>
      	<a href="<c:url value="/login"/>" class="btn btn-success btn-lg">登录</a> --%>
      	<a href="<c:url value="/login"/>" class="btn btn-success btn-lg"><span id="login_span_id">登录</span></a>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
<!--
$('#login_span_id').click();
//-->
</script>
</template:index>
