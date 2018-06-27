<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:identity pageTitle="${dataTypeName}历史数据分析">
    <template:panel title='${dataTypeName}-历史数据分析(${titleName})' customBody="true">	
		<jsp:attribute name="titlebtns">
			<template:panelTitleForm>
                <template:panelTitleBtn id="showQueryBoxBtn" icon="search" label="查询条件"></template:panelTitleBtn>
                <template:panelTitleBtn id="refreshBtn" href="javascript:void(0)" icon="refresh" label="刷新"></template:panelTitleBtn>
            </template:panelTitleForm>
		</jsp:attribute>
         <jsp:attribute name="titlebody">
            <div class="well" id="queryBox" style="display: none;">
                <form class="form-horizontal" id="analysisForm" action="/transducer/analysis/${dataType}">
                	<input type="hidden" id="dataType" name="dataType" value="${dataType}" />
                	<input type="hidden" id="measure" name="measure" value="${measure}" />
                	<input type="hidden" id="transducerId" name="transducerId" value="${transducerId}" />
                    <div class="form-group">
                    	<c:if test="${transducerId==null or transducerId==''}">
                    	<label class="col-sm-2 control-label">开始-结束时间：</label>
                    	<div class="col-sm-3">
                        	<input type="text" class="form-control" name="startTime" id="startTime" style="width: 45%;" placeholder="单击选择开始时间">&nbsp;-
                            <input type="text" class="form-control" name="endTime" id="endTime" style="width: 45%;" placeholder="单击选择结束时间">
                        </div>
                        <label class="col-sm-2 control-label">大棚：</label>
                    	<div class="col-sm-3">
                            <select id="greenHouseId" name="greenHouseId" class="form-control">
								<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
								<c:forEach var="greenHouse" items="${greenHouses}">
                             		<option value="${greenHouse.id}" <c:if test='${greenHouseId==greenHouse.id}'>selected="selected"</c:if>>${greenHouse.name}</option>
                             	</c:forEach>
							</select>
                        </div>
                    	</c:if>
						<c:if test="${transducerId!=null and transducerId!=''}">
						<label class="col-sm-2 control-label">开始时间：</label>
						<div class="col-sm-3">
                        	<input type="text" class="form-control" name="startTime" id="startTime" placeholder="单击选择开始时间">
                            
                        </div>
                        <label class="col-sm-2 control-label">结束时间：</label>
                    	<div class="col-sm-3">
                            <input type="text" class="form-control" name="endTime" id="endTime" placeholder="单击选择结束时间">
                        </div>
						</c:if>
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
			<div style="display: none;">
			<c:forEach items="${details}" var="data">
				<input class="analysisData" type="hidden" value="${data.value}" data-maxvalue="${data.maxValue}" data-minvalue="${data.minValue}" data-day="${data.day}"/>
			</c:forEach>
			</div>
            <div id="analysisDiv" style="width: 100%;"></div>
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript" src="/js/dashboard/transducer.js?sjs=15"></script>
<script type="text/javascript" src="/js/ichart.latest.min.js"></script>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
        dashboard.transducer.analysis.init();
    });
    //-->
</script>