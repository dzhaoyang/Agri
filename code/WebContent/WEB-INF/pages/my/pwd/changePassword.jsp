<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<template:userLayout>
    <template:panel title="修改密码" showfooter="true">
		<jsp:attribute name="body">
			<form id="userPasswordForm" class="form-horizontal" method="POST">
                <template:formItem label="旧密码:" name="oldPassword" type="password" required="true"/>
                <template:formItem label="新密码:" name="newPassword" type="password" required="true"/>
                <!--
                <div class="form-group">
                    <label class="col-sm-3 control-label">New Password</label>

                    <div class="col-sm-4">
                        <div class="col-sm-11">
                            <input class="form-control" name="newPassword" id="newPassword" type="password">
                        </div>
                        <label class="control-label">*</label>
                    </div>
                    <div class="col-sm-4">
                        <span style="color: green" id="result"></span>
                    </div>
                </div>
                 -->
                <template:formItem type="help" value="密码至少六位，不要用别人容易猜出来的名字，例如宠物的名字，生日等。"/>
                <template:formItem type="help" value="建议密码为大小写字符混合数字以及特殊字符。"/>
                <template:formItem label="重复新密码:" name="newPassword1" type="password" required="true"/>
            </form>			
		</jsp:attribute>
		<jsp:attribute name="footer">
			<template:panelfooterbtns>
                <a href="javascript:save();" class="btn btn-primary">修改密码</a>
            </template:panelfooterbtns>
		</jsp:attribute>
    </template:panel>
    <script type="text/javascript">
        <!--
        var detailValidatorChangePsw = null;
        var msg = "${(msg!=null)?msg:''}";

        var profileApiUrl = dashboard.utils.getApiUrl() + '/user/profile';

        $(document).ready(function () {
            detailValidatorChangePsw = $("#userPasswordForm").validate({
                rules: {
                    "oldPassword": {required: true},
                    "newPassword": {required: true, minlength: 6},
                    "newPassword1": {required: true, minlength: 6, equalTo: "#newPassword"}
                }
            });

            if (msg && msg.length != 0) {
                dashboard.message.info(msg);
            }

            $("input:password :first").focus();

            $("#newPassword").keyup(
                    function () {
                        $("#result").html(passwordStrength($('#newPassword').val()))
                    }
            );
        });

        function save() {
            if (!detailValidatorChangePsw.form()) {
                return;
            }

            $.ajax({
                type: "PUT",
                url: profileApiUrl + '/changePassword',
                data: $("#userPasswordForm").serialize(),
                dataType: "json",
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info("密码已修改！");
                }
            });
        }
        //-->
    </script>
</template:userLayout>