<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
  
<div class="tpl-portlet-components">
	<div class="portlet-title">
    	<div class="caption font-green bold">
        	<div class="am-btn-toolbar">
                	<div class="am-btn-group am-btn-group-xs">
                    	<shiro:hasPermission name="config-create">  
				  			<button type="button" id="addBtn" onclick="javascript:loadBack('<%=basePath %>/sysConfig/toEdit')" class="am-btn am-btn-default"><span class="am-icon-plus"></span> 新增</button>
						</shiro:hasPermission>	
                   	</div>
               	</div>
        </div>
    </div>
    <div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12 am-scrollable-horizontal">
        		<!-- 搜索form -->
            	<form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/sysConfig/list">
   					<input type="hidden" id="page" name="page" value="${page.pageNumber}">
					<div class="am-g tpl-amazeui-form">
						<div class="am-u-lg-3">
							<label for="_id" class="am-u-sm-4 am-form-label">id：</label>
							<div class="am-input-group">
								<input type="text" class="am-form-field" name="_id" value="${_id}">
							</div>
						</div>
						<div class="am-u-lg-3">
							<label for="_id" class="am-u-sm-4 am-form-label">key：</label>
							<div class="am-input-group">
								<input type="text" class="am-form-field" name="key" value="${key}">
							</div>
						</div>
						<div class="am-u-lg-3">
							<label for="_id" class="am-u-sm-4 am-form-label">值：</label>
							<div class="am-input-group">
								<input type="text" class="am-form-field" name="value" value="${value}"> 
							</div>
						</div>
						<div class="am-u-lg-3">
								<button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();"> 搜索</button>
						</div>
					</div>
				</form>	
				<table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
		    		<thead>
		        		<tr>
		        			<th width="5%">ID</th>
		        			<th width="5%">类型</th>
		        			<th width="15%">key</th>
		        			<th width="30%">值</th>
		        			<th width="25%">备注</th>
		        			<th width="10%">创建时间</th>
		        			<th width="10%">操作</th>
		        		</tr>
		    		</thead>
		    		<tbody>
		    			<c:forEach items="${page.list}" var="item">
			        		<tr>
			        			<td>${item.id }</td>
			        			<td>${item.type }</td>
			            		<td>${item.key }</td>
			            		<td class="am-text-truncate" title="<c:out value='${item.value }'></c:out>">${item.value }</td>
			            		<td>${item.remark }</td>
			            		<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			            		<td>
			            			<div class="am-btn-toolbar">
                    					<div class="am-btn-group am-btn-group-xs">
			            					<shiro:hasPermission name="config-update">  
												<button type="button" onclick="javascript:loadBack('<%=basePath %>/sysConfig/toEdit?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改</button>
											</shiro:hasPermission>
											<shiro:hasPermission name="config-delete">
												<button type="button" name="delBtn" data-id="${item.id}" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>删除</button>
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
          	</div>
      	</div>
  	</div>
</div>  

<script src="<%=basePath %>/js/sysConfig.js"></script>