<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<template:identity pageTitle="大棚信息">
    <script type="text/javascript" src="<c:url value="/js/jstz-1.0.4.min.js"/>"></script>
    <template:panel title="大棚信息编辑">
		<jsp:attribute name="titlebtns">
			<template:panelTitleBtn href="javascript:void(0)" id="backBtn" label="返回" icon="circle-arrow-left"/>
		</jsp:attribute>
		<jsp:attribute name="body">
			<ul id="infoUl" class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">基本信息</a>
				</li>
				<li style="text-align: right;">
					<div  id="savebtnDiv">
						<a id="saveBtn" href="javascript:void(0)" class="btn btn-primary" style="padding: 1px 12px;" onclick="saveit()">保存</a>
					</div>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="basicTab">
					<form action="/" enctype="multipart/form-data" id="uploadForm">
						<input type="hidden" id="id" name="id" value="${data.id}" />
						<input type="file" id="uploadImg" name="uploadImg" style="display: none;"/>
						<input type="hidden" id="mapPhotoFileId" name="mapPhotoFileId" value="${data.mapPhotoFileId}"/>
						<input type="hidden" id="mapOriginalFileName" name="mapOriginalFileName" value="${data.mapOriginalFileName}"/>
						<input type="hidden" id="mapPhotoFileName" name="mapPhotoFileName" value="${data.mapPhotoFileName}"/>
						<input type="hidden" id="mapWidth" name="mapWidth" value="${data.mapWidth}">
						<input type="hidden" id="mapHeight" name="mapHeight" value="${data.mapHeight}">
						<input type="hidden" id="menuId" name="menuId" value="${data.menuId}">
						<table style="width: 100%;">
							<tbody>
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">名称：</td>
									<td width="32%"><input class="form-control" id="name" name="name" type="text" value="${data.name}"></td>
									<td width="5%" align="center"><span style="color: red;">*</span></td>
									<td width="10%" align="right" style="font-weight: bold;">版本：</td>
									<td width="32%"><input class="form-control" id="varsion" name="varsion" type="text" value="${data.varsion}" readonly="readonly"></td>
									<td width="5%" align="center"></td>
								</tr>
								
								<tr>
									<td width="8%" align="right" style="font-weight: bold;">地图修改时间：</td>
									<td width="32%"><input class="form-control" id="mapUpdateTime" name="mapUpdateTime" type="text" value="${data.mapUpdateTime}" readonly="readonly"></td>
									<td width="5%" align="center">&nbsp;</td>
									<td width="10%" align="right" style="font-weight: bold;">大棚创建时间：</td>
									<td width="32%"><input class="form-control" id="createTime" name="createTime" type="text" value="${data.createTime}" readonly="readonly"></td>
									<td width="5%" align="center">&nbsp;</td>
								</tr>
								
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<br>
			<br>
			<ul class="nav nav-tabs" style="border-color: #dddddd;border-bottom: solid 1px #dddddd;margin-bottom: 10px;">
				<li class="active" style="width: 150px;text-align: center;">
					<a href="#" data-toggle="tab" style="padding-top: 3px;height: 25px;border: solid 1px #dddddd;background-color: #dddddd;color:#3182E4;">大棚地图</a>
				</li>
				<li style="color: red;font-size: 13px">&nbsp;&nbsp;&nbsp;&nbsp;提示：上传地图前请先填写大棚名称。地图应小于500k，避免图片过大引起加载缓慢</li>
			</ul>
			<div style="width: 100%;">
				<table id="picture_table">
					<tr>
						
					</tr>
				</table>
				<c:if test="${data.mapPhotoFileId!=null }">
				<div style="width: 100%;float: left;margin-right: 10px;">
					<div style="width: 100%;border:1px solid #DDDDDD;">
						<a href="/media/photo/${data.mapPhotoFileName}">
							<img style="width: 100%;" class="img-responsive" src="/media/photo/${data.mapPhotoFileName}" alt="点击查看大图">
						</a>
					</div>
					<div style="text-align: center;">
						<a href="javascript:void(0)" onclick="replaceImg()" class="btn btn-info btn-xs">替换</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="deleteImg()" class="btn btn-xs btn-danger">删除</a>
					</div>
				</div>
				</c:if>
				<c:if test="${data.mapPhotoFileId==null }">
				<div style="width: 550px;height: 80px;border:1px solid #DDDDDD;text-align: center;line-height: 70px;margin-left: auto;margin-right: auto;" onclick="addImg(this)">
					<a href="javascript:void(0)" style="font-size: 40px;color: #AFABAB;text-decoration:none;width: 150px;height: 225px;">+</a>
				</div>
				</c:if>
			</div>
		</jsp:attribute>
		<jsp:attribute name="footer">
		</jsp:attribute>
    </template:panel>
</template:identity>
<script type="text/javascript">
var method = '';
var objt = null;
var infoValidator = null;

var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
$('input[type="file"]').bind('change', function (obj) {//监控file的change事件
	//uploadImg(this);
	saveAndUpload(this);
});

$(document).ready(function () {
	$('#savebtnDiv').width($('#infoUl').width()-150);
	$('#backBtn').bind('click', function () {
		history.go(-1);
    });
	infoValidator = $("#uploadForm").validate({
        rules: {
        	name: {required: true},
        	count: {required: true,digits:true,min:0},
        	winningRate: {required: true,number:true,min:0},
        	measure:{required: true}
        },
        messages: {
        	name: {required: "请输入值"}
        }
    });
});

function uploadImg(obj){
	if(!fileSizeIsOk(obj)){
		return;
	}
	
	dashboard.utils.block('#uploadForm');
	$('#uploadForm').ajaxSubmit({
		type: "POST",
		url: '/api/greenHouse/uploadPhoto',
		dataType: 'json',
		success: function(json) {
			dashboard.utils.unblock('#uploadForm');
			if (!json.succeed) {
				dashboard.message.warning(json.message);
				return;
			}
			window.location.href = '/greenHouse/edit/';
		},
		error:function(message){
			dashboard.utils.unblock('#uploadForm');
			dashboard.message.error(message);
		}
	});
}

function fileSizeIsOk(target){
	var fileSize = 0;          
    if(isIE && !target.files) {      
		var filePath = target.value;      
      	var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
      	var file = fileSystem.GetFile (filePath);      
      	fileSize = file.Size;     
    }else{     
     	fileSize = target.files[0].size;      
    }
    
    var maxSize = 500;
    var size = fileSize/1024;     
    if(size>maxSize){
    	resizeFile(target);
      	alert("图片不能大于"+maxSize+"K");
      	return false;
    } 
	return true;
}

function resizeFile(obj){
	var outerHTML = obj.outerHTML;
	var $form = $(obj).parent();
	$(obj).remove();
	var $newFile = $(outerHTML);
	$form.prepend($newFile);
	$newFile.bind('change', function (obj) {//监控file的change事件
    	//uploadImg(this);
		saveAndUpload(this);
    });
}

function addImg(obj){
	objt = obj;
	method = 'add';
	$('#oldImgName').val('');//
	$('#uploadImg').trigger('click');
}
function replaceImg(obj,old){
	objt = obj;
	method = 'replace';
	$('#oldImgName').val(old);//
	$('#uploadImg').trigger('click');
}
function deleteImg(obj,imgName){
	dashboard.dialog.confirm('确认','是否删除地图图片?', function (confirmed) {
        if (confirmed) {
        	dashboard.utils.block('#uploadForm');
            var durl = '/api/greenHouse/deletemap/'+$('#id').val();
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'DELETE',
                url: durl,
                dataType: 'json',
                data: {},
                success: function (json) {
                	dashboard.utils.unblock('#uploadForm');
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    window.location.href = '/greenHouse/edit/'+$('#id').val();
                }
            });
            dashboard.utils.unblock('#uploadForm');
        }
    });
}

function saveit(obj){
	if (!infoValidator.form()) {
        return;
    }
	
	dashboard.utils.block('#uploadForm');
	$('#uploadForm').ajaxSubmit({
		block: '.form-actions',
		type: "POST",
		url: '/api/greenHouse/save',
		dataType: 'json',
		data: $('#uploadForm').serialize(),
		success: function(json) {
			dashboard.utils.unblock('#uploadForm');
			if (!json.succeed) {
				dashboard.message.warning(json.message);
				return;
			}
			dashboard.message.info('保存成功!');
			window.location.href = '/greenHouse/edit/'+json.data.id;
		},
		error:function(message){
			dashboard.utils.unblock('#uploadForm');
			dashboard.message.error(message);
		}
	});
	dashboard.utils.unblock('#uploadForm');
}

function saveAndUpload(obj){
	if (!infoValidator.form()) {
        return;
    }
	if($('#uploadImg').val()!=null){
		if(!fileSizeIsOk(obj)){
			return;
		}
	}
	
	dashboard.utils.block('#uploadForm');
	$('#uploadForm').ajaxSubmit({
		block: '.form-actions',
		type: "POST",
		url: '/api/greenHouse/save',
		dataType: 'json',
		data: $('#uploadForm').serialize(),
		success: function(json) {
			dashboard.utils.unblock('#uploadForm');
			if (!json.succeed) {
				dashboard.message.warning(json.message);
				return;
			}
			dashboard.message.info('保存成功!');
			window.location.href = '/greenHouse/edit/'+json.data.id;
		},
		error:function(message){
			dashboard.utils.unblock('#uploadForm');
			dashboard.message.error(message);
		}
	});
	dashboard.utils.unblock('#uploadForm');
}
</script>