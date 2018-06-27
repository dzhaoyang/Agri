<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="用户管理-系统用户列表">
    <template:panel title="系统用户列表 <span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn id="showQueryBoxBtn" icon="search" label="查询条件"></template:panelTitleBtn>
                <template:panelTitleBtn href="users/new" icon="plus" label="新增"></template:panelTitleBtn>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh" label="刷新"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>
         <jsp:attribute name="titlebody">
            <div class="well" id="queryBox" style="display: none;">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="username">
                        </div>
                        
                        <label class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">电话</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="phoneNumber">
                        </div>
                        
                        <label class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-3">
                            <select id="role" class="form-control">
                                <option value="" selected="selected">全部</option>
                                <c:forEach var="role" items="${roles}">
                                    <c:if test="${role.name!='ROLE_USER' && role.name!='ROLE_ADMIN'}">
                                        <option value="${role.id}">${role.description}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-7">
                            <template:panelFooterBtn type="primary" id="searchBtn" icon="search" label="查询"></template:panelFooterBtn>
                            <template:panelFooterBtn type="warning" id="resetBtn" icon="repeat" label="重置"></template:panelFooterBtn>
                        </div>
                    </div>

                </form>
            </div>
        </jsp:attribute>
		<jsp:attribute name="body">
            <div id="detailBox" class="modal fade" id="myModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel"
                 aria-hidden="true">

            </div>
			<table id="dataContainer" class="table table-bordered  table-striped">
                <thead>
                <tr>
                    <th style="text-align: center;width: 5%;">#</th>
                    <th style="text-align: center;width: 25%;">登录名（手机号）</th>
                    <th style="text-align: center;width: 20%;">姓名</th>
                    <th style="text-align: center;width: 13%;">角色</th>
                    <th style="text-align: center;width: 15%;">创建时间</th>
                    <th style="text-align: center;width: 10%;">状态</th>
                    <th style="text-align: center;width: 12%;">操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div id="showMoreBar" class="text-center">
            </div>
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/user.js?sjs=1"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard.user.list.init();
    });
    //-->
</script>