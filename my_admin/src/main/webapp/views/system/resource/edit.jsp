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
            	<form id="dataForm" action="<%=basePath%>/resource/edit" method="POST" class="am-form am-form-horizontal">
  					<input type="hidden" name="id" value="${resource.id }">
  					<div class="am-form-group">
		    			<label for="userName" class="am-u-sm-3 am-form-label">key</label>
		    			<div class="am-u-sm-9">
		      				<input class="am-form-field" type="text" name="key" id="key" placeholder="输入key" required="required" value="${resource.key}">
		    			</div>
					</div>
					<div class="am-form-group">
						<label for="password" class="am-u-sm-3 am-form-label">名称</label>
						<div class="am-u-sm-9">
			      			<input class="am-form-field" type="text" name="name" id="text" placeholder="名称" required="required" value="${resource.name}">
						</div>
					</div>
  					<div class="am-form-group">
		    			<label for="role" class="am-u-sm-3 am-form-label">父节点</label>
		    			<div class="am-u-sm-9">
		      				<select name="pid" id="pid">
		      					<option value="" >根目录</option>
		      					<c:forEach items="${menus}" var="item">	
			      					<option value="${item.id}" <c:if test="${resource.pid == item.id }">selected</c:if>>${item.name}:${item.key}</option>			      		      
		      					</c:forEach>
		      				</select>
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="role" class="am-u-sm-3 am-form-label">类型</label>
		    			<div class="am-u-sm-9">
		      				<select name="type" id="type">
		      					<option value="1" <c:if test="${resource.type == 1 }">selected</c:if>>目录</option>
		     					<option value="0" <c:if test="${resource.type == 0 }">selected</c:if>>子节点</option>
		      				</select>
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="roleName" class="am-u-sm-3 am-form-label">备注</label>
		    			<div class="am-u-sm-9">
		    				<textarea  name="remark" id="remark" rows="5" >${resource.remark}</textarea>
		    			</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:loadRight('<%=basePath %>/resource/list?page=${page}&_id=${_id}&key=${key}&name=${name}')" class="am-btn am-btn-default" value="返回">
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
					loadRight(getRootPath()+"/resource/list");
				}
			}
		});
		return false;
	});
  });
</script>  	
