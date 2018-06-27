<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="用户组成员">
    <template:panel title="用户组成员管理 ---- '${group.name}' <span id='recordSize' class='small-text'></span>"
                    customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleFormItem id="filterName" placeholder="在此输入要查询的用户名"
                                             title=""></template:panelTitleFormItem>
                <template:panelTitleBtn id="searchBtn" href="javascript:void(0);" icon="search"
                                        label="查询"></template:panelTitleBtn>
                <template:panelTitleBtn href="/dashboard/identity/groups" icon="arrow-left" title="返回列表"
                                        label="返回"></template:panelTitleBtn>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh"
                                        label="刷新"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>
		<jsp:attribute name="body">
			<input type="hidden" id="groupId" name="groupId" value="${group.id}"/>
			<table id="dataContainer" class="table table-bordered  table-striped">
                <thead>
                <tr>
                    <th width='20'>#</th>
                    <th width='150'>用户名</th>
                    <th width='150'>姓名</th>
                    <th width='250'>电子邮箱</th>
                    <th width='50'>电话</th>
                    <th width='50'>状态</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
			<div id="showMoreBar" class=" text-center">
            </div>
			<!-- Modal -->
		        <div id="newAdd" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="newAddModal"
                     style="clear: both;"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button>
                                <h4 class="modal-title">用户列表 </h4>

                                <div class="input-group input-group-sm panel-heading">
                                    <input class="form-control" id="userFilterName" type="text"
                                           placeholder="在此输入要查询的用户名" title="">
                                       <span class="input-group-btn">
                                       	<button id="userSearchBtn" class="btn btn-default btn-primary" type="button"
                                                title="Search">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                       </span> &nbsp;&nbsp;
                                       <span class="operation btn-group">
										<a id="userRefreshBtn" href="javascript:void(0);"
                                           class='btn btn-default btn-sm'>
                                            <i class="glyphicon glyphicon-refresh"></i>
                                        </a>
									</span>
                                </div>
                            </div>
                            <div class="modal-body">
                                <table class="table table-bordered table-striped" id="userDataContainer">
                                    <thead>
                                    <tr>
                                        <th width='20'>#</th>
                                        <th width='150'>用户名</th>
                                        <th width='250'>电子邮箱</th>
                                        <th width='50'></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                                <div id="userShowMoreBar">
                                    <div class="well text-center">&nbsp;</div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            </div>

                        </div>
                    </div>
                </div>
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/group.js"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard.group.members.init();
    });
    //-->
</script>