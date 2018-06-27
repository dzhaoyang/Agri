<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal-dialog modal-lg" style="margin-top: 0px;width: 60%;">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">审计操作日志明细</h4>
        </div>
        <div class="modal-body">
            <form class="form-horizontal" role="form"
                  style="overflow-y:auto;overflow-x: hidden; height:400px;width: 100%;">
                <div class="form-group">
                    <label class="col-sm-2 control-label">请求者:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.requestBy}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">请求地址:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.httpMethod}</p>

                        <p class="form-control-static">${data.requestedUrl}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">请求时间:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static"><fmt:formatDate value="${data.requestAt}"
                                                                       pattern="yyyy年MM月dd日 HH:mm:ss"/></p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户端地址:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.clientAddress}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">耗时:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.duration}(毫秒)</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">参数:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.requestedParameters}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">包名:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.packageName}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">类名:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.className}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">方法名:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.methodName}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">分类:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.category}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">服务名:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.serviceName}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">动作名:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.actionName}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">状态:</label>

                    <div class="col-sm-8">
                        <p class="form-control-static">${data.status == 'succeed' ? '成功':'失败'}</p>
                    </div>
                </div>
                <c:if test="${data.status != 'succeed'}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">错误信息:</label>

                        <div class="col-sm-8">
                            <div style="word-break:break-all;">
                                <p class="form-control-static">${data.errorMessage}</p>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">错误明细:</label>

                        <div class="col-sm-9">
                            <p class="form-control-static" style="word-wrap: break-word;">${data.errorDetail}</p>
                        </div>
                    </div>
                </c:if>
            </form>

        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
    </div>
</div>