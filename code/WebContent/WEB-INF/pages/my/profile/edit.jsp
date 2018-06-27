<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<template:userLayout>
    <template:panel title="编辑我的信息">
		<jsp:attribute name="titlebtns">
			<template:panelTitleBtn href="/user/profile" title="返回" label="返回"
                                    icon="arrow-left"></template:panelTitleBtn>
		</jsp:attribute>
		<jsp:attribute name="body">
			<template:form id="userForm" action="save.do">
                <input type="hidden" id="userId" name="id" value="${user.id}">
                <template:formItem label="用户名：" name="username" value="${user.username}"
                                   readonly="${user.id!=null?'readonly':''}" required="true"></template:formItem>

                <!-- name -->
                <template:formItem label="姓名：" name="name" value="${user.name}" required="true"></template:formItem>

                <!-- Display Name -->
                <template:formItem label="显示名：" name="displayName" value="${user.displayName}" required="true"></template:formItem>


                <template:formItem label="电子邮箱：" name="email" value="${user.email}" required="true"></template:formItem>
                <template:formItem label="联系电话：" name="phoneNumber" value="${user.phoneNumber}"
                                   required="true"></template:formItem>


                <template:formAction>
                    <template:panelFooterBtn id="saveBtn" type="primary" label="保存"></template:panelFooterBtn>
                </template:formAction>
            </template:form>
		</jsp:attribute>
        <jsp:attribute name="footer">

		</jsp:attribute>
    </template:panel>
    <script type="text/javascript">
        <!--
        var detailValidator = null;

        var profileApiUrl = dashboard.utils.getApiUrl() + '/user/profile';

        var userId = null;

        $(document).ready(function () {
            userId = $('#userId');
            detailValidator = $("#userForm").validate({
                rules: {
                    "username": {
                        required: true
                    },
                    "name": {
                        required: true
                    },
                    "displayName": {
                        required: true
                    },
                    "phoneNumber": {
                    	required: true,
                        number: true,
                        minlength: 11,
                        maxlength:11,
                        phone:true
                    },
                    "email":{
                    	required: true,
                    	email: true
                    }
                }
            });

            $('#saveBtn').bind('click', function () {
                save();
            });

        });

        function save() {
            if (!detailValidator.form()) {
                return;
            }

            $.ajax({
                type: "PUT",
                url: profileApiUrl + '/' + userId.val(),
                data: $("#userForm").serialize(),
                dataType: "json",
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info("信息已保存！");
                }
            });
        }
        //-->
    </script>
</template:userLayout>
