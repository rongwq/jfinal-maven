<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
<link rel="stylesheet" href="<%=basePath %>/components/ztree/zTreeStyle/zTreeStyle.css" type="text/css">

<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12">
            	<form id="dataForm" role="form" action="<%=basePath %>/role/savePermissions" class="am-form">
					<div class="am-panel am-panel-default">
  						<input type="hidden" name="roleId" value="${role.id}"/>
  						<input type="hidden" name="resources" id="resources"/>
  						<div class="am-panel-bd">
  							<div class="am-form-group">
								<ul id="mytree" class="ztree"></ul>
							</div>
							<div class="am-form-group">
								<div class="am-u-sm-10 am-u-sm-offset">
		  							<input type="submit" class="am-btn am-btn-primary" value="提交">
		  							<input type="button" onclick="javascript:loadRight('<%=basePath %>/role/list')" class="am-btn am-btn-default" value="返回">
	  							</div>
  							</div>
  						</div>
					</div>
 				</form>	
          	</div>
      	</div>
  	</div>
</div>

<script type="text/javascript" src="<%=basePath %>/components/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<SCRIPT type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},callback: {
				onCheck: onCheck
			}
		};

		var zNodes = <%= request.getAttribute("mytree") %>;
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("mytree");
			zTree.setting.check.chkboxType =  { "Y" : "ps", "N" : "ps" };
		}
		
		function onCheck(e, treeId, treeNode) {
			//alert(treeNode.id+'=='+treeNode.name)
		}
		
		function getCheckNodeIds(){
			var zTree = $.fn.zTree.getZTreeObj("mytree");
			var checkNodes = zTree.getCheckedNodes(true);
			var checkNodeIds = "";
			for(var i=0;i<checkNodes.length;i++){
				if(i==0){
					checkNodeIds += checkNodes[i].id;
				}else{
					checkNodeIds += ","+checkNodes[i].id;
				}
			}
			return checkNodeIds;
		}
		
		$(function() {
			$.fn.zTree.init($("#mytree"), setting, zNodes);
			setCheck();
		    $("#dataForm").submit(function() {
			$("#resources").val(getCheckNodeIds());
				$(this).ajaxSubmit({
					method:"POST",
					data:$('#dataForm').formSerialize(),
					success:function(data) {
						alert(data.resultDes);
						if(data.resultCode=="1"){
							loadRight(getRootPath()+"/role/list");
						}
					}
				});
				return false;
			});
		  });
	</SCRIPT>
