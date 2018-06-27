<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<div class="modal-dialog modal-lg" style="margin-top: 0px;width: 60%;">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">${data.name}</h4>
        </div>
        <div id="title_div" class="modal-body" style="padding-bottom: 0px;">
        	<form action="/transducer/analysis/${dataType}" id="monitorTodayBaseForm" target="_bland">
        		<input type="hidden" name="greenHouseId" value="${data.greenHouseId}" />
        		<input type="hidden" name="transducerId" value="${data.transducerId}" />
        	</form>
            <table class="table table-bordered  table-striped">
                <tr>
                    <td style="width: 25%;text-align: right;">当前数值：</td>
                    <td style="width: 25%;">${data.value}</td>
                    <td style="width: 25%;text-align: right;">计量单位：</td>
                    <td style="width: 25%;">${data.measure}</td>
                </tr>
               <tr>
                    <td style="width: 25%;text-align: right;">阈值范围：</td>
                    <td style="width: 25%;">${data.lowerLimit}—${data.upperLimit}</td>
                    <td style="width: 25%;text-align: right;">更新时间：</td>
                    <td style="width: 25%;">${data.lastUpdateTime}</td>
                </tr>
            </table>
        </div>
        <div class="modal-body" style="padding-bottom: 0px;padding-top: 0px;">
        	<div id="analysisDiv" style="width: 100%;">
        	
        	</div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal" id="monitorTodayCloseBtn" >数据分析</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
    </div>
    <div style="display: none;">
    	<input type="hidden" id="dataMeasure" value="${data.measure}"/>
    	<c:if test="${details!=null && fn:length(details)>0}">
    	<c:forEach items="${details}" var="detail">
    	<input class="data_24" type="hidden" data-value="${detail.value}" data-hour="${detail.hour}" />
    	</c:forEach>
    	</c:if>
    </div>
</div>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
    	dashboard.greenHouse.view.refreshAnalysis();
    });
    //-->
</script>