/**
 * 使用分页需要在页面定义page-当前页，pages-总页数，隐藏域参数
 */
function initPage() {
	var currPage = $("#page").val();
	var pages = $("#pages").val();
	if(pages==0){
		pages = 1;
	}
	dopage(pages,currPage);
}

/**
 * @param allPage 总页数
 * @param currPage 当前页
 */
function dopage(allPage,currPage) {
	//返回的是一个page示例，拥有实例方法
	var $page = $("#pageDiv").page({
		pages : allPage, //页数
		curr : currPage, //当前页 
		type : 'default', //主题
		groups : 10, //连续显示分页数
		prev : '<', //若不显示，设置false即可
		next : '>', //若不显示，设置false即可        
		first : "首页",
		last : "尾页", //false则不显示
		/*
		 * 触发分页后的回调，如果首次加载时后端已处理好分页数据则需要在after中判断终止或在jump中判断first是否为假
		 */
		jump : function(context, first) {
			if(!first){
				$("#page").val(context.option.curr);
				$("#queryForm").submit();
				console.log('当前第：' + context.option.curr + "页");
			}
		}
	});
}
