//js获取项目根路径，如： http://localhost:8080/gxt_admin
function getRootPath(){
    //获取当前网址，如： http://localhost:8080/gxt_admin/views/index.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： gxt_admin/views/index.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/gxt_admin
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

function initQueryForm(){
	$("#queryForm").submit(function() {
		$.ajax({
			url : $('#queryForm').attr("action"),
			data :$('#queryForm').formSerialize()
		}).done(function(data) {
			$("#right").html("");
			$("#right").html(data);
		});
		return false;
	});
}

function doQuery(){
	$("#page").val('1');
	$("#queryForm").submit();	
}

function loadRight(url,menu2){
	$("#menu2").attr("href",url);
	$("#menu2").text(menu2);//头部导航栏设置文字
	if(url.indexOf("?")>-1){
		url = url+"&t="+Date.parse(new Date());
	}else{
		url = url+"?t="+Date.parse(new Date());
	}
	$.ajax({
		url:url
	}).done(function(data){
		$("#right").html("");
		$("#right").html(data);
	});
}

function loadBack(url){
	$.ajax({
		type: 'post',
		url:url,
		data:$('#queryForm').formSerialize()
	}).done(function(data){
		$("#right").html(data);
	});
}

/**
 * 弹出框调用
 * @param msg
 */
function myalert(msg){
	$('#alert_msg').text(msg);
	$('#my_alert').modal();
}
