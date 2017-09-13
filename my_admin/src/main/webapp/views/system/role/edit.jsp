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
            	<form id="dataForm" action="<%=basePath%>/role/edit" method="POST" class="am-form am-form-horizontal">
  					<input type="hidden" name="id" value="${role.id }">
  					<div class="am-form-group">
		    			<label for="roleName" class="am-u-sm-3 am-form-label">角色名称</label>
		    			<div class="am-u-sm-9">
		      				<c:if test="${empty role}">
		      					<input class="am-form-field" type="text" name="rname" id="rname" placeholder="输入角色名称" required="required" value="${role.rname}">
		      				</c:if>
		      				<c:if test="${!empty role}">
		      					<input type="hidden" name="rname" id="rname" value="${role.rname}">
		      					<label for="rname" class="am-form-label">${role.rname}</label>
		      				</c:if>	
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="roleName" class="am-u-sm-3 am-form-label">备注</label>
		    			<div class="am-u-sm-9">
		    				<textarea  name="remark" id="remark" rows="5" >${role.remark}</textarea>
		    			</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:loadRight('<%=basePath %>/role/list')" class="am-btn am-btn-default" value="返回">
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
					loadRight(getRootPath()+"/role/list");
				}
			}
		});
		return false;
	});
  });
</script>  	
