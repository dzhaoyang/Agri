<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal-dialog modal-lg" style="margin-top: 0px;width: 60%;">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">用户信息</h4>
        </div>
        <div class="modal-body">
            <table class="table table-bordered  table-striped">
                <tr>
                    <td width="200px;">登录用户名:</td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td>姓名:</td>
                    <td>${user.name}</td>
                </tr>
                <%--<tr>--%>
                <%--<td>头衔:</td>--%>
                <%--<td>${user.title}</td>--%>
                <%--</tr>--%>

                <tr>
                    <td width="200px;">电子邮箱:</td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td width="200px;">联系电话:</td>
                    <td>${user.phoneNumber}</td>
                </tr>
                <tr>
                    <td>是否激活:</td>
                    <td>
                        <c:if test="${user.enabled == 'true'}">是</c:if>
                        <c:if test="${user.enabled == 'false'}">否</c:if>
                    </td>
                </tr>
                <tr>
                    <td>用户创建时间:</td>
                    <td><fmt:formatDate value="${user.createAt}"
                                        pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>最近编辑时间:</td>
                    <td><fmt:formatDate value="${user.modifiedAt}"
                                        pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td>用户角色:</td>
                    <td><c:forEach items="${roles}" var="role">
                        <c:if test="${role.name!='用户' && role.name!='超级管理员'}">
                            ${role.name}
                        </c:if>
                    </c:forEach></td>
                </tr>
            </table>
        </div>
        <div class="modal-footer">
            <%-- <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.open('/dashboard/identity/users/${user.id}/profile/index');">查看详情</button> --%>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
    </div>
</div>
