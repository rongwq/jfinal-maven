package com.rong.admin.config;

import org.apache.log4j.MDC;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.g.Exception4View;
import com.rong.common.util.RequestUtils;
import com.rong.persist.model.Admin;

/*****
 * @Copyright:	Copyright © 2012-2017 G-emall Technology Co.,Ltd
 * @Version:	1.0.0.1
 * @File_Name:	CommonInterceptor.java
 * @CreateDate:	2017年1月3日 下午6:07:59
 * @Designer:	rongwq
 * @Desc:			
 * @ModifyHistory:

*****/
public class CommonInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		try {
			myIntercept(ai);
		} catch (Exception4View e) {
			BaseRenderJson.returnBaseTemplateObj(ai.getController(), MyErrorCodeConfig.REQUEST_FAIL, e.getMessage());
		}
	}

	private void myIntercept(Invocation ai) {
		Controller controller = ai.getController();
		String url = controller.getRequest().getRequestURL().toString();
		String[] temp = url.split("/");
		//验证码部分不需要做判断
		if ("captcha".equals(temp[temp.length - 1]) || "login".equals(temp[temp.length - 1]) || temp.length == 4) {
			ai.invoke();
			return;
		}
		//判断登陆后权限
		Admin u = controller.getSessionAttr(Admin.SESSION_KEY);
		if(u!=null){
			MDC.put("userId", u.getUserName());//设置log4j的用户id-不同线程都有自己的MDC的key
			MDC.put("ip",RequestUtils.getRequestIpAddress(controller.getRequest()));
			ai.invoke();
		} else {
			controller.redirect("/");
		}
		return;
	}

}
