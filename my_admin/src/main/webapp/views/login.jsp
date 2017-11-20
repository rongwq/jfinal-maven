<%@page import="com.rong.common.bean.MyConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
  <%@ include file="/views/common/meta.jsp"%>	
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>管理后台</title>
  <meta name="description" content="admin">
  <meta name="keywords" content="index">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="icon" type="image/png" href="<%=basePath%>/assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="<%=basePath%>/assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.min.css" />
  <link rel="stylesheet" href="<%=basePath%>/assets/css/admin.css">
  <link rel="stylesheet" href="<%=basePath%>/assets/css/app.css">
  <link rel="stylesheet" href="<%=basePath%>/assets/css/validate.css">
</head>

<body data-type="login">
  <div class="am-g myapp-login">
	<div class="myapp-login-logo-block  tpl-login-max">
		<div class="myapp-login-logo-text">
			<div class="myapp-login-logo-text">
				<span> 管理后台</span> <i class="am-icon-skyatlas"></i>
				
			</div>
		</div>

		<div class="login-font">
			<i>version </i>  <span> <%=MyConst.version %></span>
		</div>
		<div class="am-u-sm-10 login-am-center">
			<form class="am-form" name="form_login" id="form_login" action="<%=basePath%>/admin/login" method="post">
				<fieldset>
					<div class="am-form-group">
						<input type="text" name="userName" id="userName" placeholder="用户名">
					</div>
					<div class="am-form-group">
						<input type="password" name="password" id="password" placeholder="密码">
					</div>
					<p><button type="submit" class="am-btn am-btn-default">登录</button></p>
				</fieldset>
			</form>
			<!-- 错误提示参数 -->
			<input type="hidden" id="isError" value="${param.isError }">
			
			<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
			  <div class="am-modal-dialog">
			    <div class="am-modal-hd">登录提示</div>
			    <div class="am-modal-bd">
			      ${param.msg }
			    </div>
			    <div class="am-modal-footer">
			      <span class="am-modal-btn">确定</span>
			    </div>
			  </div>
			</div>
		</div>
	</div>
</div>

  <script src="<%=basePath%>/assets/js/jquery.min.js"></script>
  <script src="<%=basePath%>/assets/js/amazeui.min.js"></script>
  <script src="<%=basePath%>/assets/js/app.js"></script>
  <script src="<%=basePath%>/assets/js/jquery.validate.min.js"></script>
  <script src="<%=basePath%>/assets/js/jquery.validate.messages_zh.js"></script>
</body>
<script type="text/javascript">

	function keyLogin() {
		if (event.keyCode == 13) {//回车键的键值为13
			$("#form_login").submit(); //提交表单   
		}
	}

	$(function() {
		//提示框
		var isError = $('#isError').val();
		if('1'==isError){
			$('#my-alert').modal();
		}
		var validate = $("#form_login").validate({
			submitHandler : function(form) { //表单提交句柄,为一回调函数，带一个参数：form
				form.submit(); //提交表单   
			},
			rules : {
				userName : {
					required : true
				},
				password : {
					required : true,
					minlength : 6
				}
			},
			messages : {
				userName : {
					required : "必填"
				},
				password : {
					required : "必填",
					minlength : $.validator.format("密码最小长度:{0}")
				}
			}

		});

	});
</script>

</html>