<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
  
 <div class="tpl-portlet-components">
 	<div class="portlet-title">
    	<div class="caption font-green bold">
        	<div class="am-btn-toolbar">
                	<div class="am-btn-group am-btn-group-xs">
                    	<shiro:hasPermission name="role-create">  
		  					<button type="button" id="addBtn" onclick="javascript:loadRight('<%=basePath %>/role/toEdit')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
						</shiro:hasPermission>	
                   	</div>
               	</div>
        </div>
    </div>
    <div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12 am-scrollable-horizontal">
            	<form class="am-form am-form-inline" id="queryForm" role="form" action="<%=basePath %>/role/list">
            		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
            		<table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
		    			<thead>
		        			<tr>
		        				<th>ID</th>
		        				<th>角色名称</th>
		        				<th>备注</th>
		        				<th>创建日期</th>
		        				<th>操作</th>
		        			</tr>
		    			</thead>
		    			<tbody>
		    				<c:forEach items="${list}" var="item">
			        			<tr>
			        				<td>${item.id }</td>
			            			<td>${item.rname }</td>
			            			<td>${item.remark }</td>
			            			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
			            			<td>
			            				<div class="am-btn-toolbar">
                    						<div class="am-btn-group am-btn-group-xs">
			            						<shiro:hasPermission name="role-update">  
													<button type="button" onclick="javascript:loadRight('<%=basePath %>/role/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改</button>
												</shiro:hasPermission>
												<shiro:hasPermission name="role-delete">
													<button type="button" name="delBtn" data-id="${item.id}" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>删除</button>
												</shiro:hasPermission>
												<shiro:hasPermission name="role-permissions"> 
													<button type="button" onclick="javascript:loadRight('<%=basePath %>/role/toPermissions?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span class="am-icon-copy"></span>权限管理</button>
												</shiro:hasPermission>
											</div>
										</div>
									</td>
			        			</tr>    		
		    				</c:forEach>
		    			</tbody>
					</table>
            	</form>
          	</div>
      	</div>
  	</div>
</div>

<script src="<%=basePath %>/js/role.js"></script>