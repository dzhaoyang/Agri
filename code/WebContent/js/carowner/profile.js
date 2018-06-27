$.namespace('carowner.profile');
carowner.profile.edit = (function () {
    var _this = null;
    var updateUrl = "/api/v1/carowner/profile";
    var idRef = null;
    var detailValidator = null;
    return {
        init: function () {
            _this = this;
            idRef = $('#id');
            $('#saveBtn').bind('click', function () {
                _this.save();
            });
//            $('#closeBtn').bind('click', function () {
//                window.opener = null;
//                window.open('', '_self');
//                window.close();
//            });
            _this.initializeCreate();
        },
        initializeCreate: function () {
            detailValidator = $("#carownerProfileForm").validate({
                rules: {
                    phoneNumber: {number: true}
                }
            });
        },
        save: function () {
            if (!detailValidator.form()) {
                return;
            }
            carowner.utils.ajax({
                block: '#saveBtn',
                type: 'PUT',
                url: updateUrl + '/' + idRef.val(),
                dataType: 'json',
                data: $("#carownerProfileForm").serialize(),
                success: function (json) {
                    if (!json.succeed) {
                    	carowner.message.warning(json.message);
                        return;
                    }
                    carowner.message.info('保存成功!');
                }
            });
        }
    }
})();