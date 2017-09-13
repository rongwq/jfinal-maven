<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
<div class="tpl-portlet-components">
	<div class="portlet-title">
    	<div class="caption font-green bold">
        	<div class="am-btn-toolbar">
                	<div class="am-btn-group am-btn-group-xs">
                    	<shiro:hasPermission name="user-create">  
							<button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/admin/toEdit')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
						</shiro:hasPermission>
                   	</div>
               	</div>
        </div>
    </div>
    <div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12 am-scrollable-horizontal">
            	<form class="am-form am-form-inline" id="queryForm" role="form" action="<%=basePath %>/admin/userList">
            		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
                	<table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
		    			<thead>
		        			<tr>
		        				<th>ID</th>
		        				<th>用户名</th>
		        				<th>角色</th>
		        				<th>邮箱</th>
		        				<th>手机</th>
		        				<th>创建时间</th>
		        				<th>操作</th>
		        			</tr>
		    			</thead>
		    			<tbody>
		    				<c:forEach items="${page.list}" var="item">
			        			<tr>
			        				<td>${item.id }</td>
			            			<td>${item.userName }</td>
			            			<td>${item.role }</td>
			            			<td>${item.email }</td>
			            			<td>${item.mobile }</td>
			            			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			            			<td>
			              				<div class="am-btn-toolbar">
                    						<div class="am-btn-group am-btn-group-xs">
			            						<shiro:hasPermission name="user-update">  
													<button type="button" onclick="javascript:loadRight('<%=basePath %>/admin/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改</button>
												</shiro:hasPermission>
												<shiro:hasPermission name="user-updatePwd"> 
													<c:if test="${ADMIN_USER.userName ne item.userName }">
														<button type="button" data-id="${item.id}" name="backPwdBtn" class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span class="am-icon-copy"></span>还原密码</button>
													</c:if>
												</shiro:hasPermission>
												<shiro:hasPermission name="user-delete">
													<c:if test="${ADMIN_USER.userName ne item.userName }">
														<button type="button" data-id="${item.id}" name="delBtn" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>删除</button>
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
          		</form>
          	</div>
      	</div>
  	</div>
</div>

<script src="<%=basePath %>/js/user.js"></script>