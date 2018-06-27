<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="登录日志">
    <template:panel title="登录日志<span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn id="showQueryBoxBtn" icon="search" label="查询条件"></template:panelTitleBtn>
                <template:panelTitleBtn id="cleanBtn" href="/dashboard/authentications/clean" icon="remove" label="清理"/>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0);" icon="refresh" label="刷新"/>
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

                        <label class="col-sm-2 control-label">IP地址</label>

                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="ipAddress">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">登录方式</label>

                        <div class="col-sm-3">
                            <select id="authType" class="form-control">
                                <option value="" selected="selected">全部</option>
                                <option value="form">form</option>
                                <option value="token">token</option>
                                <option value="password">password</option>
                            </select>
                        </div>

                        <label class="col-sm-2 control-label">登录日期</label>

                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="loginAt" placeholder="登录日期">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-7">
                            <template:panelFooterBtn type="primary" id="searchBtn" icon="search" label="查询"/>
                            <template:panelFooterBtn type="warning" id="resetBtn" icon="repeat" label="重置"/>
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
            <table id="dataContainer" class="table table-bordered table-striped hidden">
                <thead>
                <tr>
					<th width='30'>#</th>
					<th>用户名</th>
					<th width='150'>登录时间</th>
					<th width='80'>登录方式</th>
					<th width='120'>地址</th>
					<th width='100'>浏览器</th>
					<th width='150'>操作系统</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
			<div id="showMoreBar" class="text-center">
            </div>
		</jsp:attribute>
    </template:panel>
    <script type="text/javascript" src="/js/dashboard/authentication.js"></script>
    <script type="text/javascript">
        <!--
        $(document).ready(function () {
            dashboard.authentication.list.init();
        });
        //-->
    </script>
</template:identity>
 