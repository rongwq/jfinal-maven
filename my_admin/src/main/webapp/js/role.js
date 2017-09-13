/**
 * 角色管理
 */

$(function() {
	initQueryForm();
	initDelete();
});

/**
 * 初始化删除事件
 */
function initDelete(){
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除该角色？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/resource/delete",
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