<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:identity pageTitle="新增/编辑  用户">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="新增/编辑 用户">
		<jsp:attribute name="titlebtns">
			<template:panelTitleBtn href="/dashboard/identity/users" label="返回"
                                    icon="circle-arrow-left"></template:panelTitleBtn>
			<p class="text-warning">${message}</p>
		</jsp:attribute>
		<jsp:attribute name="body">
			<template:tab>
                <template:tabTitle>
                    <template:tabTitleItem title="基本信息" active="true" target="tab-1"></template:tabTitleItem>
                    <c:if test="${user.id != null}">
                        <template:tabTitleItem title="修改密码" target="tabs-2"></template:tabTitleItem>
                    </c:if>
                </template:tabTitle>
                <template:tabContent>
                    <template:tabContentItem id="tab-1" active="true">
                        <template:form id="userForm" action="save.do">
                            <input type="hidden" id="id" name="id" value="${user.id}">
                            <template:formItem label="用户名：" name="username" value="${user.username}" readonly="${user.id!=null?'readonly':''}" required="true"></template:formItem>
                            <c:if test="${user.id==null}">
                                <template:formItem type="help" value="用户名只能为字符和数字的组合."></template:formItem>
                                <template:formItem label="输入密码：" type="password" name="password" required="true"></template:formItem>
                                <template:formCustomItem>
                                    <ul class="help-block">
                                        <li>新增用户的时候不设置用户密码，系统会自动发送设置密码的邮件到用户邮箱。</li>
                                        <li>密码至少六位，不要用别人容易猜出来的名字，例如宠物的名字，生日等。</li>
                                        <li>建议密码为大小写字符混合数字以及特殊字符。</li>
                                    </ul>
                                </template:formCustomItem>
                                <template:formItem label="重复输入密码：" type="password" name="password1" required="true"></template:formItem>
                                <hr/>
                            </c:if>
                            <!-- name -->
                            <template:formItem label="姓名：" name="name" value="${user.name}" required="true"></template:formItem>

                            <template:formItem label="电子邮箱：" name="email" value="${user.email}"></template:formItem>
                            <template:formItem label="联系电话：" name="phoneNumber" value="${user.phoneNumber}"></template:formItem>
							<template:formItem type="help" value="请输入以028,13,15,18开头的电话号码."/>
                            <!-- Display Name -->
                            <template:formItem label="显示名：" name="displayName" value="${user.displayName}"></template:formItem>
                            <!-- Title -->
                            <%--<template:formItem label="头衔：" name="title" value="${user.title}"></template:formItem>--%>
                            <!-- status -->
                            <template:formItem type="checkbox" label="是否激活：" name="enabled" value="${user.enabled}"></template:formItem>
                            <!-- role -->
                             <c:if test="${user.roleName!='系统管理员'}">
                             <div class="form-group">
								<label class="col-sm-2 control-label" for="name">角色：</label>
								<div class="col-sm-8">
									领导&nbsp;<input id="roletype" name="roletype" type="radio" value="1" ${user.roleName=='领导'?'checked="checked"':''}>
									&nbsp;&nbsp;&nbsp;
									普通员工&nbsp;<input id="roletype" name="roletype" type="radio" value="0" ${user.roleName=='普通员工'?'checked="checked"':''}>
								</div>
								<div class="col-sm-1"><label class="control-label">*</label></div>
							 </div>
                             </c:if>
                            <%-- <template:formCustomItem label="角色：">
                                <ul class="list-unstyled">
                                    <c:forEach var="role" items="${roles}">
                                        <c:if test="${role.name!='ROLE_USER' && role.name!='ROLE_ADMIN'}">
                                            <li>
                                                <input id="roleIds" name="roleIds" type="radio" <c:forEach
                                                        var="userRole"
                                                        items="${userRoles}">
                                                    <c:if test="${role.id==userRole.id}">checked=checked</c:if>
                                                </c:forEach> value="${role.id}"/>&nbsp;${role.description}
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </template:formCustomItem> --%>

                            <!-- groups -->
                            <%--
                            <template:formCustomItem label="用户组：">
                                <ul class="list-unstyled">
                                    <c:forEach var="group" items="${groups}">
                                        <li>
                                            <input id="groupIds" name="groupIds" type="checkbox" <c:forEach
                                                    var="userGroup" items="${userGroups}">
                                                <c:if test="${group.id==userGroup.id}">checked=checked</c:if>
                                            </c:forEach> value="${group.id}"/>&nbsp;${group.name}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </template:formCustomItem>
                             --%>
                            <template:formAction>
                                <template:panelFooterBtn id="saveBtn" type="primary" label="保存"></template:panelFooterBtn>
                            </template:formAction>
                        </template:form>
                    </template:tabContentItem>
                    <c:if test="${user.id != null}">
                        <template:tabContentItem id="tabs-2">
                            <template:form id="userPasswordForm" method="POST">
                                <input type="hidden" id="id" name="id" value="${user.id}">
                                <template:formItem label="请输入新密码：" type="password" name="newPassword" required="true">
                                </template:formItem>
                                <template:formCustomItem>
                                    <ul class="help-block">
                                        <li>密码至少六位，不要用别人容易猜出来的名字，例如宠物的名字，生日等。</li>
                                        <li>建议密码为大小写字符混合数字以及特殊字符。</li>
                                    </ul>
                                </template:formCustomItem>
                                <template:formItem label="请再输入一次：" type="password" name="newPassword1" required="true"></template:formItem>
                                <%-- <template:formItem name="resetPasswordBtn" type="help" value='点击<a href="javascript:void(0);">这里</a>发送重置密码邮件到用户邮箱。'></template:formItem> --%>
                                <template:formAction>
                                    <template:panelFooterBtn id="savePasswordBtn" type="primary" label="保存"></template:panelFooterBtn>
                                </template:formAction>
                            </template:form>
                        </template:tabContentItem>
                    </c:if>
                </template:tabContent>
            </template:tab>
		</jsp:attribute>
		<jsp:attribute name="footer">
			
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/user.js?sjs=Math.random()"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard.user.edit.init();
        
        /* alert('idRef.val() == '+$('#id').val());
        alert('username == '+$('input[name="username"]').val());
        alert('password == '+$('input[name="password"]').val());
        if($('#id').val()==''){
        	$('input[name="username"]').val('');
        	$('input[name="password"]').val('');
        }
        alert('username == '+$('input[name="username"]').val());
        alert('password == '+$('input[name="password"]').val()); */
    });
    //-->
</script>