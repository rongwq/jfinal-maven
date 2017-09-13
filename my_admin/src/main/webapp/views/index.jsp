<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/views/common/meta.jsp"%>
<meta charset="utf-8">
<!-- 不缓存 -->
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<META HTTP-EQUIV="expires" CONTENT="0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>管理后台</title>
<meta name="description" content="xk-admin">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="<%=basePath%>/assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed" href="a<%=basePath%>/ssets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/admin.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.page.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/app.css">
<link rel="stylesheet" href="<%=basePath%>/assets/css/amazeui.datetimepicker.css"/>
</head>

<body data-type="index">


	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<a href="javascript:;" class="tpl-logo"> <img
				src="<%=basePath%>/assets/img/logo.png" alt="">
			</a>
		</div>
		<div class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right"></div>
		<button
			class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
			data-am-collapse="{target: '#topbar-collapse'}">
			<span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span>
		</button>
		<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
				<li class="am-hide-sm-only"><a href="javascript:;"
					id="admin-fullscreen" class="tpl-header-list-link"><span
						class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>

				<li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
					<a class="am-dropdown-toggle tpl-header-list-link"
					href="javascript:;"> <span class="tpl-header-list-user-nick">${ADMIN_USER.userName}</span><span
						class="tpl-header-list-user-ico"> <img
							src="<%=basePath%>/assets/img/user.png"></span>
				</a>
					<ul class="am-dropdown-content">
						<li><a href="javascript:onuserInfo()"><span class="am-icon-bell-o"></span> 资料</a></li>
						<li><a href="javascript:onuserInfoEdit()"><span class="am-icon-cog"></span> 设置</a></li>
						<li><a href="javascript:onuserpasswordEdit()"><span class="am-icon-cog"></span> 修改密码</a></li>
						<li><a href="javascript:logout()"><span class="am-icon-power-off"></span>
								退出</a></li>
					</ul>
				</li>
				<li><a href="javascript:logout()" class="tpl-header-list-link"><span
						class="am-icon-sign-out tpl-header-list-ico-out-size"></span></a></li>
			</ul>
		</div>
	</header>

	<div class="tpl-page-container tpl-page-header-fixed">
		<div class="tpl-left-nav tpl-left-nav-hover">
			<!-- 左边菜单栏 start -->
			<div class="tpl-left-nav-title">管理后台</div>
			<div class="tpl-left-nav-list">
				<ul class="tpl-left-nav-menu">
					<li class="tpl-left-nav-item"><a href="<%=basePath %>/views/index.jsp"
						class="nav-link active"> <i class="am-icon-home"></i> <span>首页</span>
					</a></li>
					
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
						class="nav-link tpl-left-nav-link-list"> <i
							class="am-icon-user-md"></i> <span>用户管理</span> <i
							class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
					</a>
						<ul class="tpl-left-nav-sub-menu" style="display: none;">
							<li>
							<shiro:hasPermission name="member-user">
							<a href="javascript:loadRight('<%=basePath %>/user/getUserList','用户列表')"> <i
									class="am-icon-angle-right"></i> <span>用户列表</span>
							</a>
							</shiro:hasPermission>
							</li>
						</ul></li>
					
					<li class="tpl-left-nav-item"><a href="javascript:void(0);"
						class="nav-link tpl-left-nav-link-list"> <i
							class="am-icon-cog"></i> <span>系统管理</span> <i
							class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
					</a>
						<ul class="tpl-left-nav-sub-menu" style="display: none;">
							<li>
							<shiro:hasPermission name="system-user">
							<a href="javascript:loadRight('<%=basePath %>/admin/userList','系统用户管理')"> <i
									class="am-icon-angle-right"></i> <span>系统用户管理</span>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="system-role">
						    <a href="javascript:loadRight('<%=basePath %>/role/list','系统角色管理')"> <i class="am-icon-angle-right"></i>
									<span>系统角色管理</span>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="system-resource">
						    <a href="javascript:loadRight('<%=basePath %>/resource/list','资源管理')"> <i class="am-icon-angle-right"></i>
									<span>资源管理</span>
							</a>
							</shiro:hasPermission> 
							<shiro:hasPermission name="system-config"> 
							<a href="javascript:loadRight('<%=basePath %>/sysConfig/list','系统配置')"> <i class="am-icon-angle-right"></i>
									<span>系统配置</span>
							</a>
							</shiro:hasPermission>
							</li>
						</ul></li>
						
					<li class="tpl-left-nav-item"><a href="javascript:logout()"
						class="nav-link tpl-left-nav-link-list"> <i
							class="am-icon-key"></i> <span>注销</span>

					</a></li>
				</ul>
			</div>
		</div>
		<!-- 左边菜单栏 end -->


		<!-- 右边内容 start -->
		<div class="tpl-content-wrapper">
			<ol class="am-breadcrumb">
				<li><a href="<%=basePath%>/views/index.jsp" class="am-icon-home">首页</a></li>
				<li><a href="javascript:void(0)" id="menu1">-</a></li>
				<li class="am-active"><a href="#" id="menu2">-</a></li>
			</ol>
			<!-- 右边主要更新内容 start -->
			<div id="right">
				<!-- 统计框 start-->
				<div class="row">
					<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
						<div class="dashboard-stat blue">
							<div class="visual">
								<i class="am-icon-comments-o"></i>
							</div>
							<div class="details">
								<div class="number" id="productOrderNum">0</div>
								<div class="desc">昨日商城订单数量</div>
							</div>
							<a class="more" href="#"> 查看更多 <i
								class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
						<div class="dashboard-stat red">
							<div class="visual">
								<i class="am-icon-bar-chart-o"></i>
							</div>
							<div class="details">
								<div class="number" id="orderNum">0</div>
								<div class="desc">昨日洗车订单数量</div>
							</div>
							<a class="more" href="#"> 查看更多 <i
								class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
						<div class="dashboard-stat green">
							<div class="visual">
								<i class="am-icon-apple"></i>
							</div>
							<div class="details">
								<div class="number" id="regUserYesterDay_count" >0</div>
								<div class="desc">昨日注册用户数量</div>
							</div>
							<a class="more" href="#"> 查看更多 <i
								class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
					<div class="am-u-lg-3 am-u-md-6 am-u-sm-12">
						<div class="dashboard-stat purple">
							<div class="visual">
								<i class="am-icon-android"></i>
							</div>
							<div class="details">
								<div class="number" id="activeUserYesterDay_count" >0</div>
								<div class="desc">昨日活跃用户数量</div>
							</div>
							<a class="more" href="#"> 查看更多 <i
								class="m-icon-swapright m-icon-white"></i>
							</a>
						</div>
					</div>
				</div>
				<!-- 统计框 end-->
				<!-- 动态资料 start-->
				<div class="row">
					<div class="am-u-md-6 am-u-sm-12 row-mb">
						<div class="tpl-portlet">
							<div class="tpl-portlet-title">
								<div class="tpl-caption font-red ">
									<i class="am-icon-bar-chart"></i> <span> 洗车员接单排行榜</span>
								</div>

							</div>
							<div class="tpl-scrollable">
								<table class="am-table tpl-table">
									<thead>
										<tr class="tpl-table-uppercase">
											<th>洗车员</th>
											<th>金额</th>
											<th>次数</th>
											<th>好评率</th>
										</tr>
									</thead>
									<tbody class="data-item">
										<tr>
											<td><img src="" alt=""
												class="user-pic"> <a class="user-name" href="###"></a>
											</td>
											<td>￥0</td>
											<td>0</td>
											<td class="font-green bold">0%</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<!-- 动态资料 end-->

					<div class="am-u-md-6 am-u-sm-12 row-mb">
						<div class="tpl-portlet">
							<div class="tpl-portlet-title">
								<div class="tpl-caption font-green ">
									<i class="am-icon-cloud-download"></i> <span> 用户数据统计</span>
								</div>
								<div class="actions">
									<ul class="actions-btn">
										<li class="blue">最近7天</li>
									</ul>
								</div>
							</div>

							<!--此部分数据请在 js文件夹下中的 app.js 中的 “百度图表A” 处修改数据 插件使用的是 百度echarts-->
							<div class="tpl-echarts" id="tpl-echarts-A"></div>
						</div>
					</div>
				</div>
			</div>
		<!-- 右边主要更新内容 end -->
			<footer data-am-widget="footer" class="am-footer am-footer-default"
				data-am-footer="{  }">

				<div class="am-footer-miscs ">

					<p>
						由 <a href="http://www.gwemall.cn/" title="广州涌智" target="_blank"
							class="">广州涌智</a> 提供技术支持
					</p>
					<p>CopyRight©2017 XK Inc.</p>
				</div>
			</footer>
		</div>
		<!-- 右边内容 end -->
	</div>
	<!-- amaze ui 模态窗口 start -->
	<!-- 弹出框alert -->
	<div class="am-modal am-modal-alert" tabindex="-1" id="my_alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd" id="alert_title">提示</div>
			<div class="am-modal-bd" id="alert_msg">ALERT</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>

	<!-- 弹出框alert2 -->
	<div class="am-modal am-modal-alert" tabindex="-1" id="my_alert2">
		<div class="am-modal-dialog">
			<div class="am-modal-hd" id="alert_title">
				提示<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd" id="alert_msg">ALERT</div>
		</div>
	</div>

	<!-- 弹出框confirm -->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my_confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-hd" id="confirm_title">确认提示</div>
			<div class="am-modal-bd" id="confirm_msg">你，确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	
	<!-- 弹出框confirm2 -->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my_confirm2">
		<div class="am-modal-dialog">
			<div class="am-modal-hd" id="confirm2_title">确认提示</div>
			<div class="am-modal-bd" id="confirm2_msg">你，确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	
		<!-- 用户资料查看弹出框 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-popup1">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">查看用户基本资料
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
  		<div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-16 am-u-md-12">
            	<form id="dataForm1" action="" method="POST" class="am-form am-form-horizontal">
  					<div class="am-form-group">
  						<label for="userName" class="am-u-sm-5 am-form-label">账号 </label>
		    			<div class="am-u-sm-6">
		      				<label id="userName" class="am-u-sm-2 am-form-label">aa</label>
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="role" class="am-u-sm-5 am-form-label">角色</label>
		    			<div class="am-u-sm-6">
		      				<label id="role" class="am-u-sm-2 am-form-label">cc</label>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="mobile" class="am-u-sm-5 am-form-label">联系电话</label>
		    			<div class="am-u-sm-6">
		    				<label id="mobile" class="am-u-sm-2 am-form-label">11</label>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="email" class="am-u-sm-5 am-form-label">邮箱</label>
		    			<div class="am-u-sm-6">
		    				<label id="email" class="am-u-sm-2 am-form-label">wwssdfdsafsafsdf1354564356456</label>
		    			</div>
					</div>
  				</form>
          	</div>
      	</div>
    </div>
  </div>
</div>

	<!-- 用户资料修改弹出框 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-popup2">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">修改用户基本资料
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
  		<div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-16 am-u-md-12">
            	<form id="dataForm2" action="" method="POST" class="am-form am-form-horizontal">
  					<div class="am-form-group">
  						<label for="userName" class="am-u-sm-5 am-form-label">账号 </label>
		    			<div class="am-u-sm-6">
		      				<label id="userName"  class="am-u-sm-2 am-form-label"></label>
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="role" class="am-u-sm-5 am-form-label">角色</label>
		    			<div class="am-u-sm-6">
		      				<select id="role" name="role"></select>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="mobile" class="am-u-sm-5 am-form-label">联系电话</label>
		    			<div class="am-u-sm-6">
		    				<input class="am-form-field" type="text" name="mobile" id="mobile" placeholder="输入联系电话" required="required" value="" maxlength="11">
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="email" class="am-u-sm-5 am-form-label">邮箱</label>
		    			<div class="am-u-sm-6">
		    				<input class="am-form-field" type="text" name="email" id="email" placeholder="输入邮箱" required="required" value="">
		    			</div>
					</div>
					<div class="am-form-group">
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript:infoSubmit()"  class="am-btn am-btn-default" value="确定"/>
		    			</div>
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript: void(0)" data-am-modal-close class="am-btn am-btn-default " value="取消"/>
		    			</div>
					</div>
  				</form>
          	</div>
      	</div>
    </div>
  </div>
</div>

	<!-- 用户密码修改弹出框 -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-popup3">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">修改用户密码
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
  		<div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-16 am-u-md-12">
            	<form id="dataForm3" action="" method="POST" class="am-form am-form-horizontal">
            		<input type="hidden" name="id" value="${ADMIN_USER.id}">
  					<div class="am-form-group">
		    			<label for="userName" class="am-u-sm-5 am-form-label">账号</label>
		    			<div class="am-u-sm-6">
		      				<label for="userName" class="am-u-sm-2 am-form-label">${ADMIN_USER.userName}</label>
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="oldPassword" class="am-u-sm-5 am-form-label">旧密码</label>
		    			<div class="am-u-sm-6">
		      				<input type="password" name="oldPassword" id="oldPassword" placeholder="输入你的旧密码" required="required">
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="password" class="am-u-sm-5 am-form-label">密码</label>
		    			<div class="am-u-sm-6">
		      				<input type="password" name="password" id="password" placeholder="输入新密码" required="required">
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="confirmPassword" class="am-u-sm-5 am-form-label">确认密码</label>
		    			<div class="am-u-sm-6">
		      				<input type="password" name="confirmPassword" id="confirmPassword" placeholder="输入确认密码" required="required">
		    			</div>
					</div>
					<div class="am-form-group">
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript:passwordSubmit()"  class="am-btn am-btn-default" value="确定"/>
		    			</div>
		    			<div class="am-u-sm-6">
		    				<input type="button" onclick="javascript: void(0)" data-am-modal-close class="am-btn am-btn-default " value="取消"/>
		    			</div>
					</div>
  				</form>
          	</div>
      	</div>
    </div>
  </div>
</div>
	
<!-- amaze ui 模态窗口 end -->
	<!-- 公共js -->
	<script src="<%=basePath%>/assets/js/jquery.min.js"></script>
	<script src="<%=basePath%>/assets/js/jquery-form.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.min.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.page.js"></script>
	<script src="<%=basePath%>/assets/js/echarts.min.js"></script>
	<script src="<%=basePath%>/assets/js/common.js"></script>
	<script src="<%=basePath%>/assets/js/page.js"></script>
	<script src="<%=basePath%>/assets/js/app.js"></script>
	<script src="<%=basePath%>/js/index.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.datetimepicker.min.js"></script>
	<script src="<%=basePath%>/assets/js/amazeui.datetimepicker.zh-CN.js"></script>
	<script src="<%=basePath%>/js/fileupload/jquery.ui.widget.js"></script>
	<script src="<%=basePath%>/js/fileupload/jquery.fileupload.js"></script>
	<script src="<%=basePath%>/js/fileupload/jquery.iframe-transport.js"></script>
</body>

<shiro:notAuthenticated>
    <script>
        	alert("请重新登录");
        	location.href=getRootPath()+"/views/login.jsp";
        	
    </script>
</shiro:notAuthenticated>
<script>
$(function() {
	 
});

</script>
<style>
#alert_msg{word-break:break-all}
</style>
</html>