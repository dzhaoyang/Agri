<%@tag description="Menubar" pageEncoding="UTF-8"
       isELIgnored="false" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${sessionScope['secureauth.mobile.scheme'] == null}">
    <script type="text/javascript">
        <!--
        $(document).ready(function () {
            var index = -1;
            if (window.location.href.indexOf("sso/portals/") > 0 || window.location.href.indexOf("home/") > 0 || window.location.href.indexOf("2-factor/") > 0) {
                index = 0;
            }
            if(window.location.href.indexOf("user/profile/") > 0 ){
            	$("#_navBar_ li").eq(1).addClass("hidden");
            	$("#_navBar_ li").eq(2).addClass("hidden");
            	$("#_navBar_ li").eq(3).addClass("hidden");
            	$("#_navBar_ li").eq(4).addClass("hidden");
            	$("#_navBar_ li").eq(5).addClass("hidden");
                $("#_navBar_ li").eq(6).addClass("active");
            	$("#_navBar_ li").eq(7).addClass("hidden");
            }else if(window.location.href.indexOf("app/") > 0){
            	$("#_navBar_ li").eq(1).addClass("hidden");
            	$("#_navBar_ li").eq(2).addClass("hidden");
            	$("#_navBar_ li").eq(3).addClass("hidden");
            	$("#_navBar_ li").eq(4).addClass("hidden");
            	$("#_navBar_ li").eq(5).addClass("hidden");
            	$("#_navBar_ li").eq(6).addClass("hidden");
                $("#_navBar_ li").eq(7).addClass("active");
            }else{
            	$("#_navBar_ li").eq(6).addClass("hidden");
            	$("#_navBar_ li").eq(7).addClass("hidden");
            	if (window.location.href.indexOf("sso/manager") > 0) {
                    index = 1;
                }
                if (window.location.href.indexOf("identity/") > 0) {
                    index = 2;
                }
                //if (window.location.href.indexOf("fp/") > 0) {
                //    index = 2;
               // }
                //if (window.location.href.indexOf("scim/") > 0) {
                //    index = 3;
                //}
                if (window.location.href.indexOf("auth/2faas/") > 0) {
                    index = 3;
                }
                if (window.location.href.indexOf("oauth2/") > 0) {
                    index = 4;
                }
                if (window.location.href.indexOf("company/") > 0) {
                    index = 5;
                }
                if (window.location.href.indexOf("tenancy/") > 0) {
                    index = 6;
                }
                $("#_navBar_ li").eq(index).addClass("active");
            }

        });
        //-->
    </script>
</c:if>
<div class="navbar-default navbar-menu">
    <div class="container">
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-maintext" id="_navBar_">
                <li><a href="<c:url value="/sso/portals/list.do"/>">Portal</a></li>
                <li><a href="<c:url value="/sso/manager/sites/list.do"/>">SSO</a></li>
                <li><a href="<c:url value="/identity/user/list.do"/>">Identity</a></li>
                <!-- <li><a href="<c:url value="/fp/workflow/list.do"/>">FP</a></li>
                <li><a href="<c:url value="/views/scim/scim.jsp"/>">SCIM</a></li> -->
                <li><a href="<c:url value="/auth/2faas/settings.do"/>">2FAAS</a></li>
                <li><a href="<c:url value="/oauth2/client/list.do"/>">OAUTH</a></li>
                <li><a href="<c:url value="/company/preference/settings.do"/>">Company</a></li>
                <li><a href="<c:url value="/tenancy/multitenancy/list.do"/>">Tenancy</a></li>
                <li><a href="<c:url value="/user/profile/profile/detail.do"/>">Profile</a></li>
                <li><a href="<c:url value="/app/2faas/profile/detail.do"/>">Dashboard</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>
<div id="_notificationBar_" class="navbar  ajax-notification-bar" style="float: left;position: absolute;margin:0 auto;width: 100%;display: none;">
    <div class="container"></div>
</div>