function logout() {
	$('#confirm_msg').text("温馨提示：你，确定退出当前登录吗？");
    $('#my_confirm').modal({
      relatedTarget: this,
      onConfirm: function(options) {
    	  location.href=getRootPath()+"/admin/logout";
      }
    });
}

/**
 * 展示查看用户基本信息小页面
 */
function onuserInfo(){
	$.ajax({
		url:getRootPath()+"/admin/userInfo",
   		data:{},
   		dataType:"text",
   		success:function(data){
   			var obj = jQuery.parseJSON(data);
   			$("#my-popup1 #dataForm1 label[id='userName']").text(obj.resultData.userName);
   			$("#my-popup1 #dataForm1 label[id='role']").text(obj.resultData.role);
   			$("#my-popup1 #dataForm1 label[id='mobile']").text(obj.resultData.mobile);
   			$("#my-popup1 #dataForm1 label[id='email']").text(obj.resultData.email);
   			$('#my-popup1').modal({
   				relatedTarget: this,
   			});
   		},error:function(data){
   			alert("展示用户基本信息失败");
   		}
	});
}

/**
 * 展示修改用户基本信息小页面
 */
function onuserInfoEdit(){
	$.ajax({
		url:getRootPath()+"/admin/userInfoToEdit",
   		data:{},
   		dataType:"text",
   		success:function(data){
   			var obj = jQuery.parseJSON(data);
   			$("#my-popup2 #dataForm2 label[id='userName']").text(obj.resultData.user.userName);
   			$("#my-popup2 #dataForm2 #role").empty();
   			for(var i=0;i<obj.resultData.roles.length;i++){
   				if(obj.resultData.roles[i] == obj.resultData.user.role){
   					$("#my-popup2 #dataForm2 #role").append("<option value='"+obj.resultData.roles[i]+"' selected >"+obj.resultData.roles[i]+"</option>");
   				}else{
   					$("#my-popup2 #dataForm2 #role").append("<option value='"+obj.resultData.roles[i]+"'>"+obj.resultData.roles[i]+"</option>");
   				}
   			}
   			$("#my-popup2 #dataForm2 input[id='mobile']").val(obj.resultData.user.mobile);
   			$("#my-popup2 #dataForm2 input[id='email']").val(obj.resultData.user.email);
   			$('#my-popup2').modal({
   				relatedTarget: this,
   			});
   		},error:function(data){
   			alert("展示设置用户基本信息失败");
   		}
	});
}

/**
 * 展示修改用户密码小页面
 */
function onuserpasswordEdit(){
	$('#my-popup3 #dataForm3 #oldPassword').val("");
	$('#my-popup3 #dataForm3 #password').val("");
	$('#my-popup3 #dataForm3 #confirmPassword').val("");
	$('#my-popup3').modal({
		relatedTarget: this,
	});
}

/**
 * 修改用户基本信息小页面确定按钮点击方法
 */
function infoSubmit(){
	$.ajax({
		url:getRootPath()+"/admin/userInfoEdit",
		method:"POST",
		data:$('#dataForm2').formSerialize(),
		success:function(data) {
			alert(data.resultDes);
   			if(data.resultCode == '1'){
				$("#my-popup2 a").click();
			}
		}
	})
}

/**
 * 修改用户秘密小页面确定按钮点击方法
 */
function passwordSubmit(){
	$.ajax({
		url:getRootPath()+"/admin/updatePassword",
		method:"POST",
		data:$('#dataForm3').formSerialize(),
		success:function(data) {
			alert(data.resultDes);
   			if(data.resultCode == '1'){
				$("#my-popup3 a").click();
			}
		}
	})
}