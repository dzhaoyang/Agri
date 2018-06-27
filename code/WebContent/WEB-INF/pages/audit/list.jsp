<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="审计操作">
    <template:panel title="审计操作日志<span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn id="showQueryBoxBtn" icon="search" label="查询条件"></template:panelTitleBtn>
                <template:panelTitleBtn id="cleanBtn" href="/dashboard/audits/clean" icon="remove"
                                        label="清理"></template:panelTitleBtn>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0);" icon="refresh"
                                        label="刷新"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>

         <jsp:attribute name="titlebody">
            <div class="well" id="queryBox" style="display: none;">
                <form class="form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户</label>

                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="requestBy">
                        </div>

                        <label class="col-sm-2 control-label">地址</label>

                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="ClientAddress">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">显示</label>

                        <div class="col-sm-3">
                            <select id="filter" class="form-control">
                                <option value="" selected="selected">全部</option>
                                <option value="succeed">成功</option>
                                <option value="failed">失败</option>
                            </select>
                        </div>

                        <label class="col-sm-2 control-label">请求日期</label>

                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="requestDate" placeholder="请求日期">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-7">
                            <template:panelFooterBtn type="primary" id="searchBtn" icon="search"
                                                     label="查询"></template:panelFooterBtn>
                            <template:panelFooterBtn type="warning" id="resetBtn" icon="repeat"
                                                     label="重置"></template:panelFooterBtn>
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
                     <th width='100'>用户</th>
                     <th width='160'>时间</th>
                     <th width='100'>地址</th>
                     <th>操作资源</th>
                     <th width='50'></th>
                 </tr>
                 </thead>
                 <tbody>
                 </tbody>
             </table>
			<div id="showMoreBar" class="text-center">
            </div>
		</jsp:attribute>
    </template:panel>
    <script type="text/javascript" src="/js/dashboard/audit.js"></script>
    <script type="text/javascript">
        <!--
        $(document).ready(function () {
            dashboard.audit.list.init();
        });
        //-->
    </script>
</template:identity>
 