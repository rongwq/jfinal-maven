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
        <div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-12 am-u-md-9">
            	<form id="dataForm" action="<%=basePath%>/admin/edit" method="POST" class="am-form am-form-horizontal">
  					<input type="hidden" name="id" value="${user.id }">
  					<div class="am-form-group">
  						<label for="userName" class="am-u-sm-3 am-form-label">账号 </label>
		    			<div class="am-u-sm-9">
		     				<c:if test="${empty user}">
		      					<input class="am-form-field" type="text" name="userName" id="userName" placeholder="输入账号" required="required" value="${user.userName}">
		      				</c:if>
		      				<c:if test="${!empty user}">
		     					<input type="hidden" name="userName" value="##">
		     					<input type="hidden" name="password" value="##">
		      					<label for="userName" class="am-u-sm-2 am-form-label">${user.userName}</label>
		      				</c:if>	
		    			</div>
					</div>
					<c:if test="${empty user}">
						<div class="am-form-group">
			    			<label for="password" class="am-u-sm-3 am-form-label">密码</label>
			    				<div class="am-u-sm-9">
			      					<input class="am-form-field" type="password" name="password" id="password" placeholder="输入密码" required="required" value="${user.password}">
			    				</div>
						</div>
		 			</c:if>
  					<div class="am-form-group">
		   				<label for="role" class="am-u-sm-3 am-form-label">角色</label>
		    			<div class="am-u-sm-9">
		      				<select name="role" id="role" required="required">
		      					<c:forEach items="${roles}" var="item">			     
			      					<option value="${item}" <c:if test="${user.role == item }">selected</c:if>>${item}</option>			      		      
		      					</c:forEach>
		      				</select>
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="mobile" class="am-u-sm-3 am-form-label">联系电话</label>
		    			<div class="am-u-sm-9">
		      				<input class="am-form-field" type="text" name="mobile" id="mobile" placeholder="输入联系电话" required="required" value="${user.mobile}" maxlength="11">
		    			</div>
					</div>
		
					<div class="am-form-group">
		    			<label for="email" class="am-u-sm-3 am-form-label">邮箱</label>
		    			<div class="am-u-sm-9">
		      				<input class="am-form-field" type="text" name="email" id="email" placeholder="输入邮箱" required="required" value="${user.email}">
		    			</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
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
