$(function() {
	//定义点击要上传图片事件
	$(".picture-file").unbind("click").bind("click",function() {//确认
		$(this).next("input").click();
	});
	//上传图片
	$("input[type=file]").fileupload({
		 url : getRootPath() + "/file/upload",
		 dataType: 'json',
		 change: function(e, data) {
	         if(data.files.length > 1){
	             alert("一次只能选择一张图片上传");
	             return false;
	         }
	     },
	     done: function (e, data) {
	    	 //图片url
	    	 var imgPath = data.result.imgPath;
	    	 //点击上传的input前的a标签
	    	 var element = $(e.target).prev();
	    	 //a标签里的图片数量限制
	    	 var limit = $(element).attr("limit");
	    	 //a标签里的对于图片input的名称
	    	 var name = $(element).attr("name");
	    	
	    	 //要加入的html
	    	 var html = "<a class='picture-file'><img src='" + imgPath + "'><span class='picture-delete'></span>";
	    	 if (name != null) {
	    		 html = html + "<input type='hidden'  name='" + name + "' value='" + imgPath + "'/></a>";
	    		 number = $("input[name='" + name + "']").length;
	    	 } else {
	    		 html = html + "<input type='hidden' name='image' value='" + imgPath +"'/></a>";
	    		 number = $("input[name='image']").length;
	    	 }
	    	 //把html加到前头
	    	 $(e.target).prev().before(html);
	    	 //根据现在的图片数量，判断要不要隐藏添加按钮
	    	 var number = 0;
	    	 if (name != null) {
	    		 number = $("input[name='" + name + "']").length;
	    	 } else {
	    		 number = $("input[name='image']").length;
	    	 }
	    	 if (number >= limit) {
	    		 $(element).hide();
	    	 }
	     }
	});
	//删除图片
	$(document).on("click", ".picture-delete", function() {
		var src = $(this).prev().attr("src");
		var element = this;
		$.ajax({
			url : getRootPath() + "/file/delete",
			data : {"fileName" : src},
			dataType: "text",
			success: function(data) {
				//删除后显示添加按钮，去除当前图片元素
				$(element).parent(".picture-file").siblings("a:last").show();
				$(element).parent(".picture-file").remove();
			}
		});
	});
	
});
//显示图片
//如果是单张图片，images则为字符串，如果是多张图片，images则为数组，name为要指定的显示图片的地方，不填默认为第一个图片区域
function showImage(images, name) {
	var element = null;
	if (name != null) {
		element = $("a[name='" + name + "']");
	} else {
		element = $("a.picture-file");
	}
	var limit = $(element).attr("limit");
	if (!(images instanceof Array)) {
		var html = "<a class = 'picture-file'><img src='" + images + "'><span class ='picture-delete'></span>";
		if (name == null) {
			html = html + "<input type='hidden' name='image' value='" + images + "'/></a>";
		} else {
			html = html + "<input type='hidden' name='" + name + "' value='" + images + "'/></a>";
		}
		$(element).before(html);
		if (limit == 1) {
			$(element).hide();
		}
		
	} else {
		for (var i = 0; i < images.length; i ++) {
			var html = "<a class = 'picture-file'><img src='" + images[i] + "'><span class ='picture-delete'></span>";
			if (name == null) {
				html = html + "<input type='hidden' name='image' value='" + images[i] + "'/></a>";
			} else {
				html = html + "<input type='hidden' name='" + name + "' value='" + images[i] + "'/></a>";
			}
			$(element).before(html);
		}
		if (limit <= images.length) {
			$(element).hide();
		}
	}
}