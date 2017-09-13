$(function() {

        var $fullText = $('.admin-fullText');
        $('#admin-fullscreen').on('click', function() {
            $.AMUI.fullscreen.toggle();
        });

        $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
            $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
        });

		var dataType = $('body').attr('data-type');
        for (key in pageData) {
            if (key == dataType) {
//                pageData[key]();
            }
        }
    })
    // ==========================
    // 侧边导航下拉列表
    // ==========================

$('.tpl-left-nav-link-list').on('click', function() {
			$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80).end().find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
			$('.am-breadcrumb').children('li').eq(1).children('a').eq(0).text( $(this).children('span').eq(0).text());//设置有则导航栏值
    })
    // ==========================
    // 头部导航隐藏菜单
    // ==========================

$('.tpl-header-nav-hover-ico').on('click', function() {
    $('.tpl-left-nav').toggle();
    $('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
})

/**
 * 给所有a标签添加事件,点击添加选中效果，并将其他选中的效果删除
 */
$("a").click(function(){
	$('a').each(function(){
	   if($(this).attr('href')){
		   $(this).removeClass("active");
	   }
	});
	$(this).addClass("active");
});

var pageData = {
	// ===============================================
    // 后台首页-用户相关报表数据
    // ===============================================
		
    'index': function indexData() {
    	var actList,regList,dayList;
//    	$.ajax({
//			url:getRootPath()+"/getUserStats",
//			async : false
//		}).done(function(dataInfo){
//			$("#regUserYesterDay_count").html(dataInfo.registerUserCountYesterday);
//			$("#activeUserYesterDay_count").html(dataInfo.activeUserCountYesterday);
//			actList = dataInfo.actList; 
//			regList = dataInfo.regList; 
//			dayList = dataInfo.dayList; 
//			
//			//填入数据，开始画图
//			var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));
//	        option = {
//
//	            tooltip: {
//	                trigger: 'axis',
//	            },
//	            legend: {
//	                data: ['注册', '活跃']
//	            },
//	            grid: {
//	                left: '3%',
//	                right: '4%',
//	                bottom: '3%',
//	                containLabel: true
//	            },
//	            xAxis: [{
//	                type: 'category',
//	                boundaryGap: true,
//	                data:JSON.parse(dayList),
//	            }],
//
//	            yAxis: [{
//	                type: 'value'
//	            }],
//	            series: [{
//	                    name: '注册',
//	                    type: 'line',
//	                    data:JSON.parse(regList),
//	                    itemStyle: {
//	                        normal: {
//	                            color: '#59aea2'
//	                        }
//	                    }
//	                },
//	                {
//	                    name: '活跃',
//	                    type: 'line',
//	                    data:JSON.parse(actList),
//	                    itemStyle: {
//	                        normal: {
//	                            color: '#e7505a'
//	                        }
//	                    }
//	                }
//	               
//	            ]
//	        }; 
//	        echartsA.setOption(option);
//			 
//		});

    }
	
}