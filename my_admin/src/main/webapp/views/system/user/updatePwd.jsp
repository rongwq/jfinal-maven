<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>
<div class="tpl-portlet-components">
    <div class="tpl-block">
    	<div class="am-g">
        	<div class="am-u-sm-12 am-u-md-6">
            	<div class="am-btn-toolbar">
                	<div class="am-btn-group am-btn-group-xs">
                    	
                   	</div>
               	</div>
           	</div>   
      	</div>
        <div class="am-g">
        	<div class="am-u-sm-12">
            	<form id="dataForm" action="<%=basePath%>/admin/updatePassword" method="POST" class="am-form am-form-horizontal">
  					<input type="hidden" name="id" value="${ADMIN_USER.id}">
  					<div class="am-form-group">
		    			<label for="userName" class="am-u-sm-2 am-form-label">账号</label>
		    			<div class="am-u-sm-10">
		      				<label for="userName" class="am-u-sm-2 am-form-label">${ADMIN_USER.userName}</label>
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="oldPassword" class="am-u-sm-2 am-form-label">旧密码</label>
		    			<div class="am-u-sm-10">
		      				<input type="password" name="oldPassword" id="oldPassword" placeholder="输入你的旧密码" required="required">
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="password" class="am-u-sm-2 am-form-label">密码</label>
		    			<div class="am-u-sm-10">
		      				<input type="password" name="password" id="password" placeholder="输入新密码" required="required">
		    			</div>
					</div>
  					<div class="am-form-group">
		   				<label for="confirmPassword" class="am-u-sm-2 am-form-label">确认密码</label>
		    			<div class="am-u-sm-10">
		      				<input type="password" name="confirmPassword" id="confirmPassword" placeholder="输入确认密码" required="required">
		    			</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-10 am-u-sm-offset-2">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:loadRight('<%=basePath %>/admin/userList?page=${page }')" class="am-btn am-btn-default" value="返回">
	  					</div>
  					</div>
  				</form>
          	</div>
      	</div>
  	</div>
</div>
    
<script>
  $(function() {
    $("#dataForm").submit(function() {
		$(this).ajaxSubmit({
			method:"POST",
			data:$('#dataForm').formSerialize(),
			success:function(data) {
				alert(data.resultDes);
       			if(data.resultCode == '1'){
					loadRight(getRootPath()+"/admin/userList");
				}
			}
		});
		return false;
	});
  });
</script>  	
