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
            	<form id="dataForm" action="<%=basePath%>/sysConfig/edit" method="POST" class="am-form am-form-horizontal">
  					<input type="hidden" name="id" value="${sysConfig.id }">
  					<div class="am-form-group">
		    			<label for="configkey" class="am-u-sm-3 am-form-label" style="color:red">key</label>
		    			<div class="am-u-sm-9">
		      				<input class="am-form-field" type="text" name="key" id="key" placeholder="输入key" required="required" value="${sysConfig.key}" <c:if test="${sysConfig.key == 'sharePic'}">readOnly="true"</c:if> <c:if test="${sysConfig.key == 'shareRule'}">readOnly="true"</c:if> >
		    			</div>
					</div>
					<div class="am-form-group">
						<label for="configvalue" class="am-u-sm-3 am-form-label" style="color:red">值</label>
						<div class="am-u-sm-9">
			      			<input class="am-form-field" type="text" name="value" id="text" placeholder="值" required="required" value="${sysConfig.value}">
						</div>
					</div>
					<div class="am-form-group am-form-select">
						<label class="am-u-sm-3 am-form-label" style="color:red">*类型</label>
						<div class="am-u-sm-9">
							<select name="type" class="am-form-field">
								<option <c:if test="${sysConfig.type eq 'app' }">selected="selected"</c:if> value="app">app</option>
								<option <c:if test="${sysConfig.type eq 'system' }">selected="selected"</c:if> value="system">system</option>
							</select>
						</div>
					</div>
					<div class="am-form-group">
		   				<label for="configremark" class="am-u-sm-3 am-form-label">备注</label>
		    			<div class="am-u-sm-9">
		    				<textarea  name="remark" id="remark" rows="5" >${sysConfig.remark}</textarea>
		    			</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:loadRight('<%=basePath %>/sysConfig/list?page=${page}&_id=${_id}&key=${key}&value=${value}')" class="am-btn am-btn-default" value="返回">
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
					loadRight(getRootPath()+"/sysConfig/list");
				}
			}
		});
		return false;
	});
  });
</script>  	
