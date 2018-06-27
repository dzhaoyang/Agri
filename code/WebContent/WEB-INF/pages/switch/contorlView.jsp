<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<div class="modal-dialog modal-lg" style="margin-top: 100px;width: 40%;">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">${typestr}-${data.name}</h4>
        </div>
        <div class="modal-body" style="padding-bottom: 0px;">
        	<input type="hidden" id="switchId" value="${data.id}" />
        	<input type="hidden" id="type" value="${data.type}" />
            <table style="width: 100%;">
            	<tr>
            		<td style="height: 40px;width: 50%;text-align: right;font-size: 26px;">
						当前状态：
					</td>
					<td style="height: 40px;width: 50%;text-align: left;">
						<div id="switch_stauts_div" class="col-sm-offset-3 form-actions"  style="margin-left: 0px;text-align: left;font-size: 26px; ${data.stauts==1?'color: #357ebd;':(data.stauts==0?'color: #d43f3a;':'')}">
							${stauts}
						</div>
					</td>
            	</tr>
				<tr>
					<td colspan="2">
						<div style="width: 100%;text-align: center;margin-top: 20px;">
							<a id="closeSwitchBtn" href="javascript:void(0)" class="btn btn-primary btn-danger" style="letter-spacing:5px; font-size: 18px;padding: 0px 40px;${data.stauts==1?'':'display: none;'}">
								${operateName}
							</a>
							<a id="openSwitchBtn" href="javascript:void(0)" class="btn btn-primary" style="letter-spacing:5px; font-size: 18px;padding: 0px 40px;${data.stauts==0?'':'display: none;'}">
								${operateName}
							</a>
						</div>
					</td>
				</tr>
			</table>
			<div style="width: 100%;overflow-y:auto; max-height:360px;">
				<table id="operationList" style="width: 100%;margin-top: 30px;" class="table table-bordered  table-striped">
					<thead>
						<tr>
		            		<td style="width: 10%;text-align: center;padding: 0px;">#</td>
							<td style="width: 30%;text-align: center;padding: 0px;">操作人</td>
							<td style="width: 20%;text-align: center;padding: 0px;">操作内容</td>
							<td style="width: 40%;text-align: center;padding: 0px;">操作时间</td>
		            	</tr>
					</thead>
					
	            	<tbody>
	            	<%-- <c:if test="${operateRecords!=null}">
					<c:forEach items="${operateRecords}" var="record" varStatus="index_">
					<tr>
						<td style="width: 10%;text-align: center;padding: 0px;">${index_.index+1}</td>
						<td style="width: 30%;text-align: center;padding: 0px;">${record.operatorName}</td>
						<td style="width: 20%;text-align: center;padding: 0px;">${record.stauts}</td>
						<td style="width: 40%;text-align: center;padding: 0px;">${record.operateTime}</td>
					</tr>
					</c:forEach>
					</c:if> --%>
	            	</tbody>
				</table>
				<div id="showMoreBar" class="text-center"></div>
			</div>
			
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    <!--
    $(document).ready(function () {
    	dashboard.greenHouse.view.refreshOperationList(true);
    });
    //-->
</script>