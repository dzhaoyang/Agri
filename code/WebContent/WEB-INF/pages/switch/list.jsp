<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="开关管理-开关列表">
    <template:panel title="开关列表 <span id='recordSize' class='small-text'></span>" customBody="true">
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn id="showQueryBoxBtn" icon="search" label="查询条件"></template:panelTitleBtn>
                <template:panelTitleBtn href="/switch/new" icon="plus" label="新增"></template:panelTitleBtn>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh" label="刷新"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>
         <jsp:attribute name="titlebody">
            <div class="well" id="queryBox" style="display: none;">
                <form class="form-horizontal">
                    <div class="form-group">
                    	 <label class="col-sm-2 control-label">编码</label>
                    	 <div class="col-sm-3">
                            <input type="text" class="form-control" name="uuid" id="uuid" >
                        </div>
                         <label class="col-sm-2 control-label">名称</label>
                    	 <div class="col-sm-3">
                            <input type="text" class="form-control" name="name" id="name">
                        </div>
                    </div> 
                    <div class="form-group">
                    	<label class="col-sm-2 control-label">所在大棚</label>
                    	<div class="col-sm-3">
                             <select id="greenHouseId" name="greenHouseId" class="form-control">
                             		<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
                             	<c:forEach var="greenHouse" items="${greenHouses}">
                             		<option value="${greenHouse.id}" >${greenHouse.name}</option>
                             	</c:forEach>
					        </select>
                        </div>
                        <label class="col-sm-2 control-label">开关类型</label>
                        <div class="col-sm-3">
                            <select id="switchType" name="switchType" class="form-control">
                             		<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
                             	<c:forEach var="switchType" items="${switchTypes}">
                             		<option value="${switchType.id}" >${switchType.name}</option>
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
            <div id="detailBox" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            </div>
			<table id="dataContainer" class="table table-bordered  table-striped">
                <thead>
                <tr>
                    <th style="text-align: center;width: 5%;">#</th>
                    <th style="text-align: center;width: 20%;">编码</th>
                    <th style="text-align: center;width: 25%;">名称</th>
                    <th style="text-align: center;width: 10%;">类型</th>
                    <th style="text-align: center;width: 15%;">所在大棚</th>
                    <th style="text-align: center;width: 15%;">安装时间</th>
                    <th style="text-align: center;width: 10%;">操作</th>
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
<script type="text/javascript" src="/js/dashboard/switch.js?sjs=9"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard._switch.list.init();
    });
    //-->
</script>