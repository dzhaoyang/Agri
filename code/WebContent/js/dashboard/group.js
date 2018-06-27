$.namespace('dashboard.group');
dashboard.group.list = (function () {
    var _this = null;
    var filterNameRef = null;
    var tbodyRef = null, tableRef = null, moreRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');
            filterNameRef = $('#filterName');
            $('#refreshBtn').bind('click', function () {
                filterNameRef.val('');
                _this.refresh();
            });
            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });
            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
        },
        showMore: function (reset) {
            if (reset) {
                dashboard.utils.hide(tableRef);
                tbodyRef.empty();
            }

            moreRef.show();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/identity/groups',
                dataType: 'json',
                data: {
                    name: filterNameRef.val()
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    if (json.data == null || json.data.length == 0) {
                        if (reset) {
                            moreRef.html('<div class="well text-center"><em>还没有定义用户组.</em></div>');
                        }
                        return;
                    }

                    var i = 0;
                    for (var p in json.data) {
                        var desc = json.data[p].description == null ? '' : json.data[p].description;
                        var tr = $('<tr><td>'
                            + (++i)
                            + '</td><td>'
                            + json.data[p].name
                            + '</td><td>'
                            + desc
                            + '</td><td><span id="operation'
                            + json.data[p].id
                            + '" class="operation btn-group"><a href="/dashboard/identity/groups/'
                            + json.data[p].id
                            + '/edit" class="btn btn-info btn-xs">编辑</a><a href="/dashboard/identity/groups/'
                            + json.data[p].id
                            + '/members" class="btn btn-primary btn-xs">成员</a><a href="javascript:void()" id="deleteBtn" dataId="' + json.data[p].id + '" class="btn btn-danger btn-xs">删除</a></span></td></tr>');
                        tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            _this.removeById($(delBtn.target).attr('dataId'));
                        });
                        tbodyRef.append(tr);
                    }
                    if (reset) {
                        dashboard.utils.show(tableRef);
                    }
                    dashboard.utils.hide(moreRef);
                }
            });
        },
        removeById: function (id) {
            dashboard.dialog.confirm('确认',
                '删除选中的用户组?', function (confirmed) {
                    if (confirmed) {
                        dashboard.utils.ajax({
                            block: '#operation' + id,
                            type: 'DELETE',
                            url: '/api/v1/dashboard/identity/groups/' + id,
                            dataType: 'json',
                            data: {
                                id: id
                            },
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

dashboard.group.edit = (function () {
    var _this = null;
    var detailValidator = null;
    var idRef = null;
    var formRef = null;
    var saveEndpoint = '/api/v1/dashboard/identity/groups';
    return {
        init: function () {
            _this = this;
            formRef = $('#groupForm');
            idRef = $('#id');
            detailValidator = $('#groupForm').validate({
                rules: {
                    'name': {
                        required: true
                    }
                }
            });
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }

            var isUpdate = (idRef.val() != null && idRef.val() != '');
            dashboard.utils.ajax({
                block: '#saveBtn',
                type: isUpdate ? 'PUT' : 'POST',
                url: isUpdate ? (saveEndpoint + '/' + $('#id').val() ) : saveEndpoint,
                data: formRef.serialize(),
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }

                    if (idRef.val() == null || idRef.val() == '') {
                        window.location.href = json.data.id + '/edit';
                    }

                    dashboard.message.info('已保存!');
                }
            });
        }

    };
})();


dashboard.group.members = (function () {
    var _this = null;
    var filterNameRef = null, userFilterNameRef = null;
    var i = 0, start = 0, limit = 10;
    var j = 0, userStart = 0, userLimit = 10;
    var tbodyRef = null, tableRef = null, moreRef = null, userTbodyRef = null, userTableRef = null, userMoreRef = null;
    var groupIdRef = null;
    return {
        init: function () {
            _this = this;
            tbodyRef = $('#dataContainer tbody');
            tableRef = $('#dataContainer');
            moreRef = $('#showMoreBar');

            userTbodyRef = $('#userDataContainer tbody');
            userTableRef = $('#userDataContainer');
            userMoreRef = $('#userShowMoreBar');

            filterNameRef = $('#filterName');
            userFilterNameRef = $('#userFilterName');
            groupIdRef = $('#groupId');

            $('#refreshBtn').bind('click', function () {
                filterNameRef.val('');
                _this.refresh();
            });
            $('#searchBtn').bind('click', function () {
                _this.refresh();
            });
            $('#addMemberBtn').bind('click', function () {
                _this.showAddMemberDlg();
            });
            $('#userSearchBtn').bind('click', function () {
                _this.refreshUser();
            });
            $('#userRefreshBtn').bind('click', function () {
                userFilterNameRef.val('');
                _this.refreshUser();
            });
            this.refresh();
        },
        refresh: function () {
            _this.showMore(true);
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

            moreRef.empty();
            dashboard.utils.ajax({
                block: '#showMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/identity/groups/' + groupIdRef.val() + '/members',
                dataType: 'json',
                data: {
                    id: groupIdRef.val(),
                    name: filterNameRef.val(),
                    start: start,
                    limit: limit
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    start++;
                    $('#recordSize').html('(' + json.data.totalElements + ')');
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>该用户组无成员.</em></div>');
                        return;
                    }

                    dashboard.utils.show(tableRef);
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var tr = $('<tr><td>'
                            + (++i)
                            + '</td><td>'
                            + data.username
                            + '</td><td>'
                            + data.name
                            + '</td><td>'
                            + (data.email ? data.email : '')
                            + '</td><td>'
                            + (data.phoneNumber ? data.phoneNumber : '')
                            + '</td><td>'
                            + (data.enabled ? '启用' : '禁用')
                            + '</td></tr>');
                        /**
                            <td>'
                            + '<span id="operation_list_' + i + '" class="operation btn-group">'
                            + '<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" username="' + data.username + '" index="_list_' + i + '">解除</a>'
                            + '</span>'
                            + '</td>
                         */
                     
                        tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            var deleBtnEl = $(delBtn.target);
                            _this.removeMember(deleBtnEl.attr('username'), deleBtnEl.attr('index'));
                        });
                        tbodyRef.append(tr);
                    }
                    if (reset) {
                        dashboard.utils.show(tableRef);
                    }
                    dashboard.utils.hide(moreRef);
                }
            });
        },
        removeMember: function (username, index) {
            dashboard.dialog.confirm('确认',
                '解除用户组和所选用户的关系?', function (confirmed) {
                    if (confirmed) {
                        dashboard.utils.ajax({
                            block: '#operation' + index,
                            type: 'POST',
                            url: '/api/v1/dashboard/identity/groups/' + groupIdRef.val() + '/members/' + username + '/revoke',
                            dataType: 'json',
                            success: function (json) {
                                if (!json.succeed) {
                                    dashboard.message.warning(json.message);
                                    return;
                                }
                                _this.hideAddMemberDlg('#newAdd');
                                _this.refresh();
                            }
                        });
                    }
                });
        },
        addMember: function (username, index) {
            dashboard.utils.ajax({
                block: '#operation' + index,
                type: 'POST',
                url: '/api/v1/dashboard/identity/groups/' + groupIdRef.val() + '/members/' + username + '/grant',
                dataType: 'json',
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.message.warning(json.message);
                        return;
                    }
                    _this.hideAddMemberDlg('#newAdd');
                    _this.refresh();
                }
            });
        },
        refreshUser: function () {
            _this.showMoreUser(true);
        },
        showMoreUser: function (reset) {
            if (reset) {
                j = 0;
                userStart = 0;
                userLimit = 10;
                userTbodyRef.empty();
                //dashboard.utils.hide(userTableRef);
                //dashboard.utils.show(userMoreRef);
            }

            userMoreRef.empty();
            dashboard.utils.ajax({
                block: '#userShowMoreBar',
                type: 'GET',
                url: '/api/v1/dashboard/identity/users',
                dataType: 'json',
                data: {
                    name: userFilterNameRef.val(),
                    start: userStart,
                    limit: userLimit
                },
                success: function (json) {
                    if (!json.succeed) {
                        dashboard.utils.warning(json.message);
                        return;
                    }

                    userStart++;
                    if (json.data == null || json.data.length == 0) {
                        moreRef.html('<div class="well text-center"><em>无用户.</em></div>');
                        return;
                    }

                    var g = 0;
                    for (var p in json.data.content) {
                        var data = json.data.content[p];
                        var groupId = '';
                        for (var d in data.groups) {
                            groupId = groupId + data.groups[d].id + ".";
                        }
                        var operationStr = '';
                        /**
                        if (groupId.indexOf($("#groupId").val()) != -1) {
                            operationStr = '<a id="deleteBtn" href="javascript:void(0);" class="btn btn-danger btn-xs" username="' + data.username + '" index="_dlg_' + (p + 1) + '">解除</a>';
                        } else {
                            operationStr = '<a id="addMemberBtn" href="javascript:void(0);" class="btn btn-info btn-xs" username="' + data.username + '" index="_dlg_' + (p + 1) + '">绑定</a>';
                        }
                        */
                        var tr = $('<tr><td>'
                            + (++g)
                            + '</td><td>'
                            + data.username
                            + '</td><td>'
                            + data.email
                            + '</td><td>'
                            + '<span id="operation_dlg_'
                            + p
                            + '" class="operation btn-group">'
                            + operationStr
                            + '</span>'
                            + '</td></tr>');
                        tr.delegate('#deleteBtn', 'click', function (delBtn) {
                            var deleBtnEl = $(delBtn.target);
                            _this.removeMember(deleBtnEl.attr('username'), deleBtnEl.attr('index'));
                        });
                        tr.delegate('#addMemberBtn', 'click', function (addMemberBtn) {
                            var addMemberBtnEl = $(addMemberBtn.target);
                            _this.addMember(addMemberBtnEl.attr('username'), addMemberBtnEl.attr('index'));
                        });
                        userTbodyRef.append(tr);
                    }

                    if (g < json.data.totalElements) {
                        userMoreRef.empty();
                        var more = $('<div class="well"><a id="showMoreBtn" href="javascript:void(0);" class="btn btn-primary">查看更多...</a></div>');
                        more.delegate('#showMoreBtn', 'click', function () {
                            _this.showMore();
                        });
                        userMoreRef.append(more);
                    }
                    else {
                        userMoreRef.hide();
                    }
                }
            });
        },
        showAddMemberDlg: function () {
            _this.showMoreUser();
            $("#newAdd").modal('show');
            _this.initModal("#newAdd");
        },
        hideAddMemberDlg: function (modalwindow) {
            $(modalwindow).modal('hide');
            $(modalwindow).removeData('modal');
        },
        initModal: function (modalwindow) {
            $(modalwindow).css({
                'margin-top': function () {
                    return -200;
                }
            });
        }
    };
})();