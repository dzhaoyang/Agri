$.namespace('dashboard.parking');
$.namespace('dashboard.parking.registration');

dashboard.parking.base = (function () {
	return {
		apiUrl: dashboard.utils.getApiUrl() + '/parkings',
		pageUrl: dashboard.utils.getPageUrl() + '/parkings'
	};
})();
dashboard.parking.list = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null, filterRef = null;
    var isSalesman = false, isParkManager = false, isManager = false;
    var currentUserId = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            filterRef = $('#filter');
            isSalesman = $('#isSalesman').size() > 0;
            isParkManager = $('#isParkManager').size() > 0;
            isManager = $('#isManager').size() > 0;
            currentUserId = $('#currentUserId');

            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
                _this.refresh();
            });
            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });
            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });
            $('#resetBtn').bind('click', function () {
                _this.resetQuery();
            });
            $("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });
            _this.refresh();
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: dashboard.parking.base.pageUrl + '/' + itemId
            });
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            formRef[0].reset();
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                limit = 10;
                tbodyRef.empty();
                dashboard.utils.hide(tableRef);
                dashboard.utils.show(moreRef);
            }

            moreRef.show();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/' + filterRef.val(),
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit, 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无停车场.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++i);
                        tr.push('</td><td>');
                        tr.push('<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">');
                        tr.push(data.name);
                        tr.push('</a>');
                        tr.push('</td><td>');
                        tr.push(data.address ? data.address : '');
                        tr.push('</td><td>');
                        tr.push(data.phoneNumber ? data.phoneNumber : '');
                        tr.push('</td><td>');
                        tr.push(data.status ? data.status.label : '');
                        tr.push('</td><td>');
                        tr.push(data.createAt ? data.createAt : '');
                        tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        //if (((isSalesman && data.creator && data.creator.id == currentUserId.val()) || isManager) && (data.status.name == "CREATED" || data.status.name == "PENDING")) {
                            tr.push('<a style="width:40px;" href="' + dashboard.parking.base.pageUrl + '/' + data.id + '/edit" class="btn btn-info btn-xs">&gt;&gt;</a>');
                            //tr.push('<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>');
                        //}
                        tr.push('</span>');
                        tr.push('</td></tr>');


                        var $tr = $(tr.join(''));

                        $tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });
                        $tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            _this.showDetail($(detailBtn.target).attr('dataId'));
                        });

                        tbodyRef.append($tr);
                    }


                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        },
        removeById: function (id) {
            dashboard.dialog.confirm('确认',
                '是否删除选中停车场?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = dashboard.parking.base.apiUrl + '/' + id;
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'DELETE',
                            url: deleteUserEndpoint,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                _this.refresh();
                            }
                        });
                    }
                });
        }
    };
})();

dashboard.parking.edit = (function () {
    var _this = null;
    var detailValidator = null, addressValidator = null, uploadValidator = null;
    var idRef = null;
    var photoListRef = null;
    var morePhotoRef = null;
    var map = null;
    var marker = null;
    var mapInited = false;
    var editable = false;
    
    var moreEmployeeRef = null, employeeTableRef = null, employeeTbodyRef = null;
    var employeeValidator = null;

    var geoUrl = 'http://api.map.baidu.com/geocoder/v2/?output=json';
    var ak = "sSgGpO5hSCu3wz4nRZENCbq6";
    geoUrl = geoUrl + "&ak=" + ak;
    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            photoListRef = $('#photoList');
            morePhotoRef = $('#morePhotoBar');
            editable = $('#saveBtn').length > 0;
            
            moreEmployeeRef = $('#moreEmployeeBar');
            employeeTableRef = $('#employeeContainer');
            employeeTbodyRef = $('#employeeContainer tbody');
            $('#backBtn').bind('click', function () {
                _this.back();
            });

            $('#locateBtn').bind('click', function () {
                _this.locateGeo();
            });
            
            $('#saveAddressBtn').bind('click', function() {
            	_this.saveAddress();
            });
            
            $('#uploadBtn').bind('click', function() {
            	_this.uploadPhoto();
            });

            $('#saveBtn').bind('click', function () {
                _this.save();
            });

            $('#submitBtn').bind('click', function () {
                _this.submitParking();
            });

            $('#processBtn').bind('click', function () {
                _this.processParking();
            });

            $('#enableBtn').bind('click', function () {
                _this.enableParking();
            });

            $('#disableBtn').bind('click', function () {
                _this.disableParking();
            });
            
            $('#deleteBtn').bind('click', function () {
                _this.deleteParking();
            });            

            $('#farePerUnit').bind('keyup', function () {
                if ($(this).val() == "0") {
                    $('#fareDescription').parents('.form-group').hide();
                } else {
                    $('#fareDescription').parents('.form-group').show();
                }
            });
            
            if(!editable){
            	$('input,textarea,select').each(function(){
            		$(this).attr('disabled', 'disabled');
            	});
            }

            $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            	if(e.target.hash == '#addressTab' && (!mapInited)) {
                    _this.initMap();
            	}
            });

            _this.initializeCreate();
            if(idRef.val()) {
            	_this.refreshPhotos();
            	_this.refreshEmployees();
            }
            
            $('#addEmployeeBtn').bind('click', function() {
                $('#employeeModal').modal({
                    remote: dashboard.parking.base.pageUrl + '/' + idRef.val() + '/employees/new'
                });
            });
            
            $('#employeeModal').on('hidden.bs.modal', function () {
                $(this).removeData('bs.modal');
            });
            
            $('#saveEmployeeBtn').live('click', function(){
        		_this.saveEmployee();
        	});
        	
            
            $('#employeeModal').on('shown.bs.modal', function () {
            	$('#username').focus();
            	
            });
        },
        initializeCreate: function () {
            if ($('#farePerUnit').val() == "0") {
                $('#fareDescription').parents('.form-group').hide();
            } else {
                $('#fareDescription').parents('.form-group').show();
            }

            detailValidator = $("#parkingForm").validate({
                rules: {
                    name: {required: true},
                    "parkingLot.total": {required: true, digits:true, min:1},
                    "parkingLot.available": {required: true, digits:true, min:1},
                    /*baseFare: {required: true, number: true, gtezero:true},
                    bookingFare: {required: true, number: true, gtezero:true},
                    farePerUnit: {required: true, number: true, gtezero:true},*/
                    contactName: {required: true}/*,
                    phoneNumber: {
                    	required: true,
                        number: true,
                        minlength: 11,
                        maxlength:11,
                        phone:true},
                    phoneNumber1: {
                    	required: true,
                        number: true,
                        minlength: 11,
                        maxlength:11,
                        phone:true
                    }*/

                },
	            messages: {
	            	name: "请输入值",
	            	"parkingLot.total": {
	                    required: "请输入值",
	                    digits: "必须输入整数",
	                    min: "输入值不能小于0"
	                },
	                "parkingLot.available": {
	                    required: "请输入值",
	                    digits: "必须输入整数",
	                    min: "输入值不能小于0"
	                },
	                contactName: "请输入值"
	            }
            });
            addressValidator = $("#addressForm").validate({
                rules: {
                    address: {required: true},
                    "coordinate.longitude": {required: true, number: true},
                    "coordinate.latitude": {required: true, number: true}
                }
            });
            
            uploadValidator = $("#uploadForm").validate({
                rules: {
                    file: {required: true}
                }
            });
        },
        initMap: function () {
        	mapInited = true;
            map = new BMap.Map("baiduMap",{enableMapClick:false});
            //chengdu
            var defaultPoint = new BMap.Point(104.0723457315, 30.663536680066);
            $('#backToChenduCenterBtn').click(function(){
            	map.centerAndZoom(defaultPoint, 13);
            });
            
            if ($('#latitude').val() != "" && $('#longitude').val() != "") {
            	var parkingPoint = new BMap.Point($('#longitude').val(), $('#latitude').val());
                marker = new BMap.Marker(parkingPoint);
                map.centerAndZoom(parkingPoint, 15);
            } else {
                marker = new BMap.Marker(defaultPoint);
                map.centerAndZoom(defaultPoint, 13);
            }
            map.addControl(new BMap.NavigationControl());
            map.enableScrollWheelZoom();

            map.addOverlay(marker);

            if ($('#address').size() > 0 && $('#address').attr("type").toLowerCase() == "text") {
                map.addEventListener("click", function (e) {
                    map.removeOverlay(marker);
                    marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));
                    map.addOverlay(marker);
                    _this.reverseLocation(e.point.lng, e.point.lat);
                });
            }
        },
        back: function() {
        	var list = $.cookie('parkingList');
        	if(!list) {
        		list = 'all';
        	}
        	window.location.href = dashboard.parking.base.pageUrl + '/' + list;
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            var saveUrl = dashboard.parking.base.apiUrl;
            var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveUrl += '/' + $('#id').val();
            }
            dashboard.utils.ajax({
                block: '.form-actions',
                type: isUpdate ? 'PUT' : 'POST',
                url: saveUrl,
                dataType: 'json',
                data: $('#parkingForm').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                    if (!$("#id").val()) {
                        window.location.href = dashboard.parking.base.pageUrl + '/' + json.data.id + '/edit';
                    } else {
                        window.location.href = window.location.href;
                    }
                }
            });
        },
        saveAddress: function() {
            if (!addressValidator.form()) {
                return;
            }
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'PUT',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/address',
                dataType: 'json',
                data: $('#addressForm').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                }
            });
       },

        submitParking: function () {
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'POST',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/submit',
                dataType: 'json',
                data: $("#parkingForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('发布成功!');
                    window.location.href = window.location.href;
                }
            });
        },

        processParking: function () {
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/process',
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    dashboard.message.info('取消发布成功!');
                    window.location.href = window.location.href;
                }
            });
        },

        enableParking: function () {
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/enable',
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('已生效!');
                    window.location.href = window.location.href;
                }
            });
        },

        disableParking: function () {
            dashboard.utils.ajax({
                block: '.form-actions',
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/disable',
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('已禁用!');
                    window.location.href = window.location.href;
                }
            });
        },

        deleteParking: function () {
            dashboard.dialog.confirm('确认',
                '是否删除此停车场?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = dashboard.parking.base.apiUrl + '/' + idRef.val();
                        dashboard.utils.ajax({
                            block: '.form-actions',
                            type: 'DELETE',
                            url: deleteUserEndpoint,
                            dataType: 'json',
                            data: {},
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                dashboard.message.info('已删除!');
                                _this.back();
                            }
                        });
                    }
            });
        },
        
        relocateMarker: function (lng, lat) {
            map.centerAndZoom(new BMap.Point(lng, lat), 16);
            map.removeOverlay(marker);
            marker = new BMap.Marker(new BMap.Point(lng, lat))
            map.addOverlay(marker);

            $('#latitude').val(lat);
            $('#longitude').val(lng);
        },
        locateGeo: function () {
            _this = this;
            var city = $('#city :selected').text(), address = $('#area :selected').text() + $('#address').val();
            dashboard.utils.ajax({
                block: '#locateBtn',
                type: 'GET',
                url: geoUrl,
                dataType: 'jsonp',
                data: {city: city, address: address},
                success: function (response) {
                    var location = response.result.location;
                    _this.relocateMarker(location.lng, location.lat);
                    return;
                }
            });
        },
        reverseLocation: function (lng, lat) {
            _this = this;
            var city = $('#city :selected').text(), address = $('#area :selected').text() + $('#address').val();
            dashboard.utils.ajax({
                block: '#locateBtn',
                type: 'GET',
                url: geoUrl,
                dataType: 'jsonp',
                data: {location: lat + ',' + lng},
                success: function (response) {
                    var result = response.result;
                    console.debug(result);
                    $("#provice").val(result.addressComponent.province);
                    $("#city").val(result.addressComponent.city);
                    $("#area").val(result.addressComponent.district);
                    $("#address").val(result.addressComponent.street + result.addressComponent.street_number);
                    $('#latitude').val(result.location.lat);
                    $('#longitude').val(result.location.lng);
                    return;
                }
            });
        },
        
        refreshPhotos: function() {
        	if (!idRef.val()) {
        		return;
        	}
        	
        	dashboard.utils.show(morePhotoRef);
        	dashboard.utils.hide(photoListRef);
        	
            dashboard.utils.ajax({
            	block: '#morePhotoBar',
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/photos',
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    
                    if (json.data == null || json.data.length == 0) {
                    	morePhotoRef.html('<div class="well text-center"><em>无照片.</em></div>');
                    	return;
                    }

                    photoListRef.html('');
                    var html = '';
                    html += '<div class="row">';
                    for (var p in json.data) {
                        var data = json.data[p];
                        html += '<div class="col-xs-6 col-md-4">';
                        if (editable) {
                        	html += '<div><a id="delPhoto' + data.id + '" href="javascript:void(0)" class="btn btn-xs btn-danger">删除</a></div>';
                        }
                        html += '<a target="_blank" href="/media/photo/' + data.photoFileName + '" class="thumbnail">';
                        var photoPath = data.thumbnailFileName ? data.thumbnailFileName : data.photoFileName;
                        html += '<img class="img-responsive" src="/media/photo/' + photoPath + '" alt="点击查看大图">';
                        html += '</a>';
                        html += '</div>';
                    }
                    html += '</div>';
                    photoListRef.html(html);
                    
                    if (editable) {
                    	for (var p in json.data) {
                            var dataId = json.data[p].id;
                            $('#delPhoto' + dataId).bind('click', function(){
                            	var id = $(this).attr('id').substring(8);
                            	_this.deletePhoto(id);
                            });
                    	}
                    }
                    
                    dashboard.utils.show(photoListRef);
                    dashboard.utils.hide(morePhotoRef);
                }
            });
        },
        deletePhoto: function(id) {
            dashboard.dialog.confirm('确认',
                '是否删除此照片?', function (confirmed) {
                    if (confirmed) {
                        dashboard.utils.ajax({
                            block: '#photoList',
                            type: 'DELETE',
                            url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/photos/' + id,
                            dataType: 'json',
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                dashboard.message.info('已删除!');
                                _this.refreshPhotos();
                            }
                        });
                    }
            });
        },
        uploadPhoto: function() {
        	if(!uploadValidator.form()){
        		return;
        	}
        	
        	dashboard.utils.block('#uploadForm');
        	$('#uploadForm').ajaxSubmit({  
        		type: "POST",
        		url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/photos',
        		dataType: 'json',
        		success: function(json) {
    				dashboard.utils.unblock('#uploadForm');
        			if (!json.succeed) {
        				dashboard.message.warning(json.message);
        				return;
        			}
        			$('#uploadForm')[0].reset();
        			_this.refreshPhotos();
        		},
        		error:function(message){
        			dashboard.utils.unblock('#uploadForm');
        			dashboard.message.error('System busy, please try later.');
        		}
        	});
        },
        
        refreshEmployees: function() {
        	dashboard.utils.hide(employeeTableRef);
        	employeeTbodyRef.empty();
        	dashboard.utils.show(moreEmployeeRef);

            dashboard.utils.ajax({
                block: moreEmployeeRef,
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/employees',
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    if (json.data == null || json.data.length == 0) {
                    	moreEmployeeRef.html('<div class="well text-center"><em>无员工.</em></div>');
                        return;
                    }

                    dashboard.utils.hide(moreEmployeeRef);
                    dashboard.utils.show(employeeTableRef);
                    var ei = 0;
                    for (var p in json.data) {
                        var data = json.data[p];
                        var tr = $('<tr><td>'
                            + (++ei)
                            + '</td><td>'
                            + data.username
                            + '</td><td>'
                            + (data.name ? data.name : '')
                             + '</td><td>'
                            + (data.phoneNumber ? data.phoneNumber : '')
                            + '</td><td>'
                            + (data.parkingOwner ? '是' : '否')
                            + '</td><td>'
                            + (data.manager ? '管理员' : '员工')
                            + '</td><td>'
                            + (data.enabled ? '已启用' : '<span style="color:red;">已禁用<span>')
                            + '</td><td>'
                            + '<span class="operation btn-group" id="operation' + data.id + '">'
                            + '<a id="editEmployeeBtn" href="javascript:void(0);" class="btn btn-info btn-xs" dataId="' + data.id + '">编辑</a>'
                            + '<a id="deleteEmployeeBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>'
                            + '</span>'
                            + '</td></tr>');

                        var $tr = $(tr);

                        $tr.delegate('#deleteEmployeeBtn', 'click', function (btn) {
                            _this.deleteEmployee($(btn.target).attr('dataId'));
                        });
                        $tr.delegate('#editEmployeeBtn', 'click', function (btn) {
                            _this.editEmployee($(btn.target).attr('dataId'));
                        });

                        employeeTbodyRef.append($tr);
                    }
                }
            });        	
        },
        editEmployee: function(id) {
            $('#employeeModal').modal({
                remote: dashboard.parking.base.pageUrl + '/' + idRef.val() + '/employees/' + id + '/edit'
            });
        },
        deleteEmployee: function(id) {
            dashboard.dialog.confirm('确认',
                    '是否删除此员工?', function (confirmed) {
                        if (confirmed) {
                            dashboard.utils.ajax({
                                block: '#operation' + id,
                                type: 'DELETE',
                                url: dashboard.parking.base.apiUrl + '/' + idRef.val() + '/employees/' + id,
                                dataType: 'json',
                                success: function (json) {
                                    if (!json.succeed) {
                                        dashboard.message.warning(json.message);
                                        return;
                                    }
                                    dashboard.message.info('已删除!');
                                    _this.refreshEmployees();
                                }
                            });
                        }
                });
        },
        saveEmployee: function() {
        	employeeValidator = $("#employeeForm").validate({
                rules: {
                    "username": {
                        required: true,
                        number: true,
                        minlength: 11,
                        maxlength:11,
                        mobile:true
                    },
                    "firstName": {
                        required: true
                    },
                    "name": {
                        required: true
                    },
                    "email": {
                        email: true
                    },
                    "password": {
                        minlength: 6
                    },
                    "phoneNumber":{
                    	required: true,
                        number: true,
                        minlength: 11,
                        maxlength:11,
                        phone:true
                    },
                    "idNumber":{
                    	minlength: 18,
                        maxlength:18,
                    }
                }
            });
        	
        	if(!employeeValidator.form()) {
        		return;
        	}
        	
        	var saveUrl = dashboard.parking.base.apiUrl + '/' + idRef.val() + '/employees';
        	var saveMethod = 'POST';
        	if ($('#employee_id').val()) {
        		saveUrl += '/' + $('#employee_id').val();
        		saveMethod = 'PUT';
        	}
        	
            dashboard.utils.ajax({
                block: '#employeeModal .modal-footer',
                type: saveMethod,
                url: saveUrl,
                dataType: 'json',
                data: $('#employeeForm').serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    $('#employeeModal').modal('hide');
                    _this.refreshEmployees();
                    dashboard.message.info('保存成功!');
                }
            });
        }
    };
})();

dashboard.parking.registration.list = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    var formRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            formRef = $('#queryForm');
            $('#refreshBtn').bind('click', function () {
                _this.resetQuery();
                _this.refresh();
            });
            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });
            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });
            $('#resetBtn').bind('click', function () {
                _this.resetQuery();
            });
            /*$("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });*/
            
            $('#showExportBoxBtn').bind('click', function () {
                _this.toggleExportBox();
            });
            
            $('#exportBtn').click(function(){
            	_this.doExport();
            });
            $('#requestAt').datepicker();
            _this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
            $("#exportBox").hide();
        },
        toggleExportBox: function () {
            $("#exportBox").toggle("fast");
            $("#queryBox").hide();
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            formRef[0].reset();
        },
        doExport: function(){
        	var requestAt = $('#requestAt').val();
        	if(!requestAt || requestAt.length==0){
        		alert('请您选择申请日期');
        		return;
        	}
        	window.open('/dashboard/parkings/registration/export?requestAt='+$('#requestAt').val());
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                limit = 10;
                tbodyRef.empty();
                dashboard.utils.hide(tableRef);
                dashboard.utils.show(moreRef);
            }

            moreRef.show();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: dashboard.parking.base.apiUrl + '/registration',
                dataType: 'json',
                data: formRef.serialize() + '&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无停车场申请记录.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++i);
                        tr.push('</td><td>');
                        tr.push(data.name);
                        tr.push('</td><td>');
                        tr.push(data.address);
                        tr.push('</td><td>');
                        tr.push(data.managerUsername);
                        tr.push('</td><td>');
                        tr.push(data.contactName);
                        tr.push('</td><td>');
                        tr.push(data.phoneNumber);
                        tr.push('</td><td>');
                        tr.push(data.status.label);
                        tr.push('</td><td>');
                        tr.push('<span class="operation btn-group" id="operation' + data.id + '">');
                        tr.push('<a href="' + dashboard.parking.base.pageUrl + '/registration/' + data.id + '/edit" class="btn btn-info btn-xs">处理</a>');
                        tr.push('</span>');
                        tr.push('</td></tr>');

                        var $tr = $(tr.join(''));
                        tbodyRef.append($tr);
                    }


                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        }
    };
})();

dashboard.parking.registration.edit = (function () {
    var _this = null;
    var idrRef = null, parkingIdRef = null, reasonRef = null, statusRef = null;
    var approveBtn = null, denyBtn = null, viewParkingBtn = null;
    var addressRef = null, contactNameRef = null, phoneNumberRef = null, idNumberRef = null;
    var validator = null;
    return {
        init: function () {
            _this = this;
            idrRef = $('#id');
            parkingIdRef = $('#parkingId');
            reasonRef = $('#reason');
            statusRef = $('#status');
            approveBtn = $('#approve');
            denyBtn = $('#deny');
            viewParkingBtn = $('#viewParking');
            addressRef = $('#address');
            contactNameRef = $('#contactName');
            phoneNumberRef = $('#phoneNumber');
            idNumberRef = $('#idNumber');
            
            validator = $("#registrationForm").validate({
                rules: {
                	address: {
                		required: true
                	},
                	contactName:{
                		required: true
                	},
                    phoneNumber: {
                    	required: true,
                        number: true,
                        maxlength:13
                   }
                }
            });
            
            $('#backBtn').bind('click', function () {
            	window.location.href = dashboard.parking.base.pageUrl + '/registration';
            });
            $('#approve').bind('click', function () {
            	if (!validator.form()) {
                    return;
                }
                _this.approve();
            });
            $('#deny').bind('click', function () {
            	if(!reasonRef.val()){
            		dashboard.message.info('请填写拒绝原因!');
            		return;
            	}
                _this.deny();
            });
            $('#viewParking').bind('click', function () {
            	window.open(dashboard.parking.base.pageUrl + '/' + parkingIdRef.val() + '/profile/index');
            });
            if(statusRef.val() == 'PENDING'){
            	dashboard.utils.show(approveBtn);
            	dashboard.utils.show(denyBtn);
            	dashboard.utils.hide(viewParkingBtn);
            }else{
            	reasonRef.attr('readonly', 'readonly');
            	addressRef.attr('readonly', 'readonly');
            	contactNameRef.attr('readonly', 'readonly');
            	phoneNumberRef.attr('readonly', 'readonly');
            	idNumberRef.attr('readonly', 'readonly');
            	dashboard.utils.hide(approveBtn);
            	dashboard.utils.hide(denyBtn);
            	if(statusRef.val() == 'APPROVED'){
            		dashboard.utils.show(viewParkingBtn);
            	}
            }
        },
        approve: function () {
            dashboard.utils.ajax({
                block: '#footerBar',
                type: 'PUT',
                url: dashboard.parking.base.apiUrl + '/registration/' + idrRef.val() + '/approve',
                dataType: 'json',
                data: $("#registrationForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    parkingIdRef.val(json.data.parking.id);
                    dashboard.message.info('通过成功!');
                    reasonRef.attr('readonly', 'readonly');
                    addressRef.attr('readonly', 'readonly');
                	contactNameRef.attr('readonly', 'readonly');
                	phoneNumberRef.attr('readonly', 'readonly');
                	idNumberRef.attr('readonly', 'readonly');
                    dashboard.utils.hide(approveBtn);
                	dashboard.utils.hide(denyBtn);
                	dashboard.utils.show(viewParkingBtn);
                }
            });
        },
        deny: function () {
            dashboard.utils.ajax({
                block: '#footerBar',
                type: 'PUT',
                url: dashboard.parking.base.apiUrl + '/registration/' + idrRef.val() + '/deny',
                dataType: 'json',
                data: {reason:reasonRef.val()},
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('拒绝成功!');
                    reasonRef.attr('readonly', 'readonly');
                    addressRef.attr('readonly', 'readonly');
                	contactNameRef.attr('readonly', 'readonly');
                	phoneNumberRef.attr('readonly', 'readonly');
                	idNumberRef.attr('readonly', 'readonly');
                    dashboard.utils.hide(approveBtn);
                	dashboard.utils.hide(denyBtn);
                	dashboard.utils.hide(viewParkingBtn);
                }
            });
        }
    };
})();
//停车场选择页面
var selectParkingId = null;var selectParkingName = null;
dashboard.parking.select = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    
    var formRef = null; 
    
    /*var nameRef = null; var addressRef = null;*/
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataParkingContainer tbody');
            tableRef = $('#dataParkingContainer');
            moreRef = $('#showMoreParkingBar');
            formRef = $('#queryParkingForm');
            
            /*nameRef = $('#name');
            addressRef = $('#address');*/

            $('#refreshParkingBtn').bind('click', function () {
            	_this.refresh();
            });
            
            /*$('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });*/
            /*$('#searchBtn').bind('click', function () {
                _this.refresh();
            });*/
            $('#resetParkingBtn').bind('click', function () {
                _this.resetQuery();
            });
            $('#okParkingBtn').bind('click', function () {
                _this.returnSelect();
            });
            _this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        /*toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },*/
        resetQuery: function () {
            i = 0;
            start = 0;
            formRef[0].reset();
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                limit = 10;
                tbodyRef.empty();
                dashboard.utils.hide(tableRef);
                dashboard.utils.show(moreRef);
            }
            /*nameRef.val($('#queryParm').val());
            addressRef.val($('#queryParm').val());*/
            dashboard.utils.ajax({
                block: '#showMoreParkingBar',
                type: 'GET',
                url: dashboard.parking.base.apiUrl+"/select",
                dataType: 'json',
                data: $('#queryParkingForm').serialize() + '&start=' + start + '&limit=' + limit,/*{
                	start: start,
                    limit: limit,
                    name: nameRef.val(),
                    address: addressRef.val()
                },*/ 
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.content == null || json.data.content.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无停车场.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];

                        var tr = [];
                        tr.push('<tr><td>');
                        tr.push(++i);
                        tr.push('</td><td>');
                        tr.push('<input name="parking" type="radio" value="'+data.id+'" parkName="'+data.name+'" />');
                        tr.push('</td><td>');
                        tr.push(data.name);
                        tr.push('</td><td>');
                        tr.push(data.address ? data.address : '');
                        tr.push('</td></tr>');

                        var $tr = $(tr.join(''));

                        tbodyRef.append($tr);
                    }
                    

                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreParkingBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreParkingBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
            /*$('#queryParm').val('');*/
        },
        returnSelect:function(){
        	//$(":radio[checked]"):获取被选择的单选框
        	//$("input[name='carOwnerRadio']:radio[checked]"):获取name为carOwnerRadio的被选择的单选框
        	var parkId = $("input[name='parking']:radio[checked]").val();
        	var parkName = $("input[name='parking']:radio[checked]").attr('parkName');
        	if(parkId==null||parkId==''){
        		return;
        	}
        	/*$('#parkingId').val(parkingId);
        	$('#parkName').val(parkName);*/
        	$(selectParkingId).val(parkId);
        	$(selectParkingName).val(parkName);
        	
        	$('#cancelParkingBtn').trigger("click");//触发id为cancelBtn的按钮click事件
        },
        selectParking:function(selectDiv,parkingId_,parkingName_) {
        	selectParkingId = parkingId_;
        	selectParkingName = parkingName_;
        	
        	//每次打开对话框之前移除数据，使得可以顺利重新加载
        	$(selectDiv).removeData("bs.modal");
        	
        	$(selectDiv).modal({
            	remote: '/dashboard/parkings/select'
            });
        }
    };
})();

//私家车位停车场分成比例
dashboard.parking.proportionlist = (function () {
    var _this = null;
    var i = 0, start = 0, limit = 10;
    var queryFormRef = null;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            queryFormRef = $('#queryForm');
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            parkingnameRef = $('#parkingName');
            /*usernameRef = $('#username');
            nameRef = $('#name');
            phoneNumberRef = $('#phoneNumber');
            emailRef = $('#email');*/
            $('#refreshBtn').bind('click', function () {
                _this.refresh();
            });

            $('#showQueryBoxBtn').bind('click', function () {
                _this.toggleQueryBox();
            });

            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });

            $('#resetBtn').bind('click', function () {
                _this.resetQuery();
            });

            $("#detailBox").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });

            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        toggleQueryBox: function () {
            $("#queryBox").toggle("fast");
        },
        resetQuery: function () {
            i = 0;
            start = 0;
            queryFormRef[0].reset();
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'carowners/' + itemId
            });
        },
        showMore: function (reset) {
            if (reset) {
                i = 0;
                start = 0;
                limit = 10;
                tbodyRef.empty();
                dashboard.utils.hide(tableRef);
                dashboard.utils.show(moreRef);
            }

            moreRef.show();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/parkings/all',
                dataType: 'json',
                data: queryFormRef.serialize() + '&start=' + start + '&limit=' + limit,
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.totalElements == 0) {
                        moreRef.html('<div class="well text-center"><em>目前无分成比例信息.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        
                        var tr = $('<tr><td>'
                                + (++i)
                                + '</td><td>'
                               /* + '<a id="detailBtn" target="_blank" href="' + dashboard.utils.getPageUrl() + '/identity/carowners/' + data.id + '/profile/index"  dataId="' + data.id + '">'
                                + (data.parkName ? data.parkName : (data.name ? data.name : '无'))
                                + '</a>'*/
                                + (data.name?data.name:'')
                                + '</td><td>'
                                + (data.ownerPercent ? data.ownerPercent*100+'%' : '')
                                + '</td><td>'
                                + (data.parkPercent ? data.parkPercent*100+'%' : '')
                                + '</td><td>'
                                + (data.parkEasyPercent ? data.parkEasyPercent*100+'%' : '')
                                + '</td><td>'
                                +'<span class="operation btn-group" id="operation' + data.id + '">'
                                +'<a style="width:40px;" href="/dashboard/parkings/proportion/' + data.id + '/edit" class="btn btn-info btn-xs">&gt;&gt;</a>'
            					+'</span>'
                                + '</td></tr>');
                        
                        tr.delegate('#detailBtn', 'click', function (detailBtn) {
                            _this.showDetail($(detailBtn.target).attr('dataId'));
                        });

                        tbodyRef.append(tr);
                    }

                    if (i < json.data.totalElements) {
                        moreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        moreRef.append(more);
                    }
                    else {
                        moreRef.hide();
                    }
                }
            });
        }
    };
})();

dashboard.parking.proportionedit = (function () {
    var _this = null;
    var detailValidator = null, detailValidatorChangePsw = null;
    var idRef;
    var saveEndpoint = '/api/v1/dashboard/parkings/setProportion';
    return {
        init: function () {
            _this = this;
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
            idRef = $('#id');
            _this.initializeCreate();
        },
        initializeCreate: function () {
            detailValidator = $("#parkingForm").validate({
                rules: {
                    "ownerPercent": {
                        required: true,number: true,range:[0,1]
                    },
                    "parkPercent": {
                        required: true,number: true,range:[0,1]
                    },
                    "parkEasyPercent": {
                        required: true,number: true,range:[0,1]
                    }
                },
                messages: {
                	"ownerPercent": {
                        required: "请输入值",number: "输入值必须是数字",range:"请输入0~1之间的小数"
                    },
                    "parkPercent": {
                    	required: "请输入值",number: "输入值必须是数字",range:"请输入0~1之间的小数"
                    },
                    "parkEasyPercent": {
                    	required: "请输入值",number: "输入值必须是数字",range:"请输入0~1之间的小数"
                    }
                }
            });
        },
        
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            /*var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveEndpoint += '/' + $('#id').val();
            }*/
            dashboard.utils.ajax({
                block: '#saveBtn',
                type: 'POST',
                url: saveEndpoint,
                dataType: 'json',
                data: $("#parkingForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.info('保存成功!');
                    if (!$("#id").val()) {
                        window.location.href = json.data.id + '/edit';
                    }
                }
            });
        }
    };
})();

