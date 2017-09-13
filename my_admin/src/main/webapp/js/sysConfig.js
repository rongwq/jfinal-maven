/**
 * 系统配置管理
 */

$(function() {
	initPage();//初始化分页
	initQueryForm();
	initDelete();
	initRefreshBtn();
});

/**
 * 初始化删除事件
 */
function initDelete(){
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除该配置？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/sysConfig/delete",
	           		data:{"id":$link.data("id")},
	           		dataType:"text",
	           		success:function(data){
	           			$('#my_confirm').modal("close");
	           			var obj = jQuery.parseJSON(data);
	           			alert(obj.resultDes);
	           			if(obj.resultCode == '1'){
	           				doQuery();
	           			}
	           		}
	           	})
	        }
	      });
	    });
}

/**
 * 初始化还原密码事件
 */
function initRefreshBtn(){
	$("button[name='refreshBtn']").on('click', function() {
		$('#confirm2_msg').text("是否刷新系统配置，刷新后应用到整个系统配置");
	      $('#my_confirm2').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/sysConfig/refresh",
	           		data:{"id":$link.data("id")},
	           		dataType:"text",
	           		success:function(data){
	           			$('#my_confirm2').modal("close");
	           			var obj = jQuery.parseJSON(data);
	           			alert(obj.resultDes);
	           			if(obj.resultCode == '1'){
	           				doQuery();
	           			}
	           		}
	           	})
	        }
	      });
	    });
}









