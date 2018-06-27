var signin = (function() {
	var _this = null;
	var usernameRef = null,passwordRef = null,errorDivRef = null;
	return {
		init:function() {
			_this = this;
			usernameRef = $("#j_username");
			passwordRef = $("#j_password");
			errorDivRef = $("#errorDiv");
			
			$('form').bind('submit',function() {
				return _this.validate();
			});
			
			$('#j_username,#j_password').bind('keypress',function(event) {
				if (event.which == 13) {
					event.preventDefault();
					$('#loginBtn').click();
				}
			});

			$("#loginBtn").bind('click',function() {
				if (!_this.validate()) {
					return;
				}
				
				_this.signin();
			});
			
			usernameRef.focus();
			
			_this.setloginContainerDivHeigth();
		},
		validate:function() {
			var username = usernameRef.val();
			if (!username || username.length == 0) {
				_this.showErrorMsg('请输入用户名!');
				usernameRef.focus();
				return false;
			}

			var password = passwordRef.val();
			if (!password || password.length == 0) {
				_this.showErrorMsg('请输入用户密码!');
				passwordRef.focus();
				return false;
			}
			return true;
		},
		showErrorMsg:function (msg) {
			errorDivRef.show();
			errorDivRef.text(msg);
		},
		signin:function() {
			$("form").attr("action","j_spring_security_check");
			$("form").submit();
		},
		setloginContainerDivHeigth:function(){
			/*alert('浏览器当前窗口可视区域高度:'+$(window).height());
			alert('浏览器当前窗口文档的高度:'+$(document).height());
			alert('浏览器当前窗口文档body的高度:'+$(document.body).height());
			alert('浏览器当前窗口文档body的总高度:'+$(document.body).outerHeight(true));*/
			
			/*var height = $(window).height();
			height = height - $('.navbar-fixed-top').height();
			height = height - $('.footer').height();
			
			$('#login_container_div').height(height);*/
		}
	};
})();

$(document).ready(function() {
	signin.init();
});
		