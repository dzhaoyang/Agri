<%@tag description="Topbar" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-top" style="background:url(/img/agri/top.jpg) no-repeat 0px 0px;">
    <div class="container">
        <div class="navbar-header">
            <a style="margin-top: 3px;color: white;font-family: 'Microsoft YaHei',Arial,Helvetica,sans-serif,'SimSun';font-weight: bold;" class="navbar-brand" href='<spring:url value="/"></spring:url>'>
                <img alt="" src="<c:out value="/img/logo.png" />" style="height:40px;display: none;">
                巴中农业科技园数据监测及控制系统</a>
        </div>
        <div class="collapse navbar-collapse">
            <sec:authorize ifNotGranted="ROLE_USER,ROLE_SALESMAN,ROLE_PARKMANAGER,ROLE_MANAGER">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">登录</a></li>
                </ul>
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_USER,ROLE_SALESMAN,ROLE_PARKMANAGER,ROLE_MANAGER">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                            style="color:#fff;/* color:#222 */">当前用户： <span class="glyphicon glyphicon-user"></span><sec:authentication
                            property="principal.username"/></span><b class="caret" style="border-top-color: #fff;border-bottom-color: #fff;"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:out value="/user/profile" />"> <span
                                    class="glyphicon glyphicon-edit"></span> 个人资料
                            </a></li>
                            <li><a href="<c:out value="/user/profile/changePassword" />"> <span
                                    class="glyphicon glyphicon-lock"></span> 修改密码
                            </a></li>
                            <li><a href="<c:out value="/dashboard" />"> <span
                                    class="glyphicon glyphicon-dashboard"></span> 管理控制台
                            </a></li>
                        </ul>
                    </li>
                    <li><a href="<c:out value="/j_spring_security_logout" />"> <span
                            class="glyphicon glyphicon-off"></span> 退出
                    </a></li>
                    <li><a href="<c:out value="/apk/AgriICSystem.apk" />"> <span
                            class="glyphicon glyphicon-arrow-down"></span> 下载APP
                    </a></li>
                </ul>
                <sec:authorize ifAnyGranted="ROLE_SALESMAN">
                    <input type="hidden" id="isSalesman">
                </sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_PARKMANAGER">
                    <input type="hidden" id="isParkManager">
                </sec:authorize>
                <sec:authorize ifAnyGranted="ROLE_MANAGER">
                    <input type="hidden" id="isManager">
                </sec:authorize>
                <input type="hidden" id="currentUserId" value="<sec:authentication property='principal.id' />">
            </sec:authorize>
        </div>
    </div>
</div>
<div id="_notificationBar_" class="navbar  ajax-notification-bar"
     style="float: left;position: absolute;margin:0 auto;width: 100%;display: none;">
    <div class="container"></div>
</div>