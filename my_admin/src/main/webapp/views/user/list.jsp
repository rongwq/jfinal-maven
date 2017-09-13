<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
 <script src="<%=basePath %>/assets/js/common.js"></script>
  
<script>
var basePath = "<%=basePath %>";

function resetPassword(id) {
    $('#my-prompt').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/user/resetPassword",
           		data:{"id":id, "password":options.data},
           		dataType:"text",
           		success:function(data){
           			var obj = jQuery.parseJSON(data);
           			alert(obj.resultDes);
           			if(obj.resultCode == '1'){
           				doQuery();
           			}
           		}
    		});
       },
       onCancel: function() {
         
       }
    });   
}

function setEnable2(cBox, pid, userState){
	modp("/user/setEnable", {id:pid, _switch:!(userState == 1 )});
}

function refresh(data){
	alert(data.resultDes);
	$("#queryForm").submit();
	if(data.resultCode==1){
	}
}

function modp(action, param){

	mod(function(){
		$.post(basePath + action, param, refresh, "json");
	});
}

function mod(onConfirm){
	$("#my-confirm").modal({
		relatedTarget: this,
		onConfirm: function(){
			$("#my-confirm").modal("close");
			onConfirm();
		},
		onCancel:function(){
			$("#my-confirm").modal("close");
			$("#queryForm").submit();
		}
	});
}

$(function() {
	$("#queryForm").submit(function() {
		$(this).ajaxSubmit({
			data:$('#queryForm').formSerialize(),
			success:function(data) {
				$(".admin-content").html(data);
			}
		});
		return false;
	});
});


</script>
<div class="tpl-portlet-components">
	
    <form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/user/getUserList">
   					<input type="hidden" id="page" name="page" value="${page.pageNumber}">
					<div class="am-g tpl-amazeui-form">
					<div class="am-u-lg-4">
						<label for="_id" class="am-u-sm-4 am-form-label">用户名称：</label>
							<div class="am-input-group">
								<input type="text" class="am-form-field" name="userName" value="${userName}"> 							
							</div>	
					</div>				
						
						<div class="am-u-lg-4 am-u-end">
							<label for="_id" class="am-u-sm-4 am-form-label">手机号码：</label>
							<div class="am-input-group">
									<input type="text" class="am-form-field" name="mobile" value="${mobile}">
							</div>
						</div>
							<div class="am-u-lg-3 am-u-end">
								<button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();"> 搜索</button>
							</div>
					</div>	
				</form>	
    <div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12 am-scrollable-horizontal">
                	<table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
		    			<thead>
		        			<tr>
		        				<th>ID</th>
		        				<th>用户名</th>
		        				<th>手机号码</th>
		        				<th>登录时间</th>
		        				<th>操作</th>
		        			</tr>
		    			</thead>
		    			<tbody>
		    				<c:forEach items="${page.list}" var="item">
			        			<tr>
			        				<td>${item.id }</td>
			            			<td>${item.userNickName }</td>
			            			<td>${item.mobile }</td>
			            			<td><fmt:formatDate value="${item.registerDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			            			<td>
			              				<div class="am-btn-toolbar">
                    						<div class="am-btn-group am-btn-group-xs">
			            						
												<shiro:hasPermission name="member-user-resetpassword"> 
														<button type="button" onclick="resetPassword( ${item.id })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">重置密码</button>				
												</shiro:hasPermission>
												<shiro:hasPermission name="member-user-onoff">
														<c:if test="${item.userState == 1 }">	
														<button type="submit" onclick="setEnable2(this, ${item.id }, ${item.userState })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">停用</button>
													</c:if>
													<c:if test="${item.userState == 0 }">	
														<button type="submit" onclick="setEnable2(this, ${item.id }, ${item.userState })" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">启用</button>
													</c:if>
												</shiro:hasPermission>
						    				</div>
						  				</div>
									</td>
			        			</tr>    		
		    				</c:forEach>
		    			</tbody>
					</table>
                    <div class="am-cf">
                    	<div class="am-fr">
                        	<!-- 分页使用 -->
    						<div id="pageDiv"></div>
							<input type="hidden" id="pages" name="pages" value="${page.totalPage}">        
                        </div>
                    </div>
                    <hr>
         
          		
          		<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
  					<div class="am-modal-dialog">
    					<div class="am-modal-hd">重置密码</div>
   							 <div class="am-modal-bd">
     							请输入6-12位由字母和数字组合成的登录密码
      						<input type="text" class="am-modal-prompt-input" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\@\.\_\-\=\+\(\)\*\&\^\%\$\#\!\~]/g,'')" maxlength="11">
   							 </div>
     					<div class="am-modal-footer">
      						<span class="am-modal-btn" data-am-modal-cancel>取消</span>
     						<span class="am-modal-btn" data-am-modal-confirm>提交</span>
   						</div>
  					</div>
				</div>
				
				<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
  		<div class="am-modal-dialog">
    		<div class="am-modal-hd">喜客温馨提示：</div>
    		<div class="am-modal-bd">你，确定要执行此次操作吗？
    	</div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
          	</div>
      	</div>
  	</div>
</div>

<script src="<%=basePath %>/js/user.js"></script>