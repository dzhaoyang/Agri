$.namespace('dashboard.user');
dashboard.user.list = (function () {
    var _this = null;
    var usernameRef = null, emailRef = null, nameRef = null, phoneNumberRef = null, roleRef = null, groupRef = null;
    var i = 0, start = 0, limit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            usernameRef = $('#username')
            emailRef = $('#email');
            nameRef = $('#name');
            phoneNumberRef = $('#phoneNumber');
            roleRef = $('#role');
            groupRef = $('#group');
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
            limit = 10;
            usernameRef.val('');
            emailRef.val('');
            nameRef.val('');
            phoneNumberRef.val('');
            roleRef.val('');
            groupRef.val('');
        },
        showDetail: function (itemId) {
            $('#detailBox').modal({
                remote: 'users/' + itemId
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
                url: '/api/v1/dashboard/identity/users',
                dataType: 'json',
                data: {
                    start: start,
                    limit: limit,
                    username: usernameRef.val(),
                    email: emailRef.val(),
                    name: nameRef.val(),
                    phoneNumber: phoneNumberRef.val(),
                    roleId: roleRef.val(),
                    groupId: groupRef.val()
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无用户.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $('<tr><td style="text-align: center;">'
                            + (++i)
                            + '</td><td style="text-align: center;">'
                            + '<a id="detailBtn" href="javascript:void(0);"  dataId="' + data.id + '">'
                            + data.username+(data.phoneNumber==''?'':('('+data.phoneNumber+')'))
                            + '</a>'
                            + '</td><td style="text-align: center;">'
                            + (data.name ? data.name : '')
                            + '</td><td style="text-align: center;">'
                            + (data.roleName ? data.roleName : '')
                            + '</td><td style="text-align: center;">'
                            + (data.createTime ? data.createTime : '')
                            + '</td><td style="text-align: center;">'
                            + (data.enabled ? '已启用' : '<span style="color:red;">已禁用<span>')
                            + '</td><td style="text-align: center;">'
                            + '<span class="operation btn-group" id="operation' + data.id + '">'
                            + '<a href="users/' + data.id + '/edit" class="btn btn-info btn-xs">编辑</a>'
                            + '<a id="deleteBtn" href="javascript:void(0);"  style="margin-left: 10px;" class="btn btn-danger btn-xs" dataId="' + data.id + '">删除</a>'
                            + '</span>'
                            + '</td></tr>');
                        tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });

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
        },
        removeById: function (id) {
            dashboard.dialog.confirm('确认',
                '是否删除选中用户?', function (confirmed) {
                    if (confirmed) {
                        var deleteUserEndpoint = '/api/v1/dashboard/identity/users/' + id;
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

dashboard.user.edit = (function () {
    var _this = null;
    var detailValidator = null, detailValidatorChangePsw = null;
    var idRef;
    var saveEndpoint = '/api/v1/dashboard/identity/users';
    return {
        init: function () {
            _this = this;
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
            idRef = $('#id');
            _this.initializeCreate();
            _this.initializeChangePassword();
            if ($('#id').val()) {
                $('#resetPasswordBtn').bind('click', function () {
                    _this.resetPassword();
                });
                $('#savePasswordBtn').bind('click', function () {
                    _this.changePassword();
                });
                _this.initializeChangePassword();
            }
        },
        initializeCreate: function () {
            detailValidator = $("#userForm").validate({
                rules: {
                    "username": {
                        required: true
                    },
                    /*"firstName": {
                        required: true
                    },*/
                    "name": {
                        required: true
                    },
                    /*"email": {
                        email: true,
                        required: true
                    },*/
                    "phoneNumber": {
                        number: true,
                        minlength:11,
                        maxlength:11,
                        phone:true
                    },
                    "roletype": {
                        required: true
                    },
                    "password": {
                        minlength: 6
                    },
                    "password1": {
                        minlength: 6,
                        equalTo: "#password"
                    }
                }
            });

            $("#password").keyup(function () {
                $("#result").html(passwordStrength($('#password').val()));
            });

            $("#newPassword").keyup(function () {
                $("#result").html(passwordStrength($('#newPassword').val()));
            });
        },
        initializeChangePassword: function () {
            detailValidatorChangePsw = $("#userPasswordForm").validate({
                rules: {
                    "newPassword": {
                        required: true,
                        minlength: 6
                    },
                    "newPassword1": {
                        required: true,
                        minlength: 6,
                        equalTo: "#newPassword"
                    }
                }
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            var isUpdate = (idRef.val() != null && idRef.val() != '');
            if (isUpdate) {
                saveEndpoint += '/' + $('#id').val();
            }
            dashboard.utils.ajax({
                block: '#saveBtn',
                type: isUpdate ? 'PUT' : 'POST',
                url: saveEndpoint,
                dataType: 'json',
                data: $("#userForm").serialize(),
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
        },
        changePassword: function () {
            if (!detailValidatorChangePsw.form()) {
                return;
            }
            var changePasswordEndpoint = '/api/v1/dashboard/identity/users/' + $("#id").val() + '/changePassword';
            dashboard.utils.ajax({
                type: 'POST',
                url: changePasswordEndpoint,
                data: $('#userPasswordForm').serialize(),
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.warning('密码修改成功!');
                }
            });
        },
        resetPassword: function () {
            var resetPasswordEndpoint = '/api/v1/dashboard/identity/users/' + $('#id').val() + '/resetPassword';
            dashboard.utils.ajax({
                type: "POST",
                url: resetPasswordEndpoint,
                data: $("#userForm").serialize(),
                dataType: "json",
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    dashboard.message.warning('重置邮件已发送，请检查自己的邮箱!');
                }
            });
        }
    };
})();