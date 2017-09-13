package com.rong.api.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.gw.common.exception.g.Exception4View;
import com.gw.common.util.PropertiesUtils;
import com.gw.common.util.RSAUtil;
import com.gw.common.util.RequestUtils;
import com.gw.common.util.StringUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.persist.dao.UsertokenDao;
import com.rong.persist.model.Usertoken;

public class CommonInterceptor implements Interceptor {

	private static final Logger logger = Logger.getLogger(CommonInterceptor.class);

	/**
	 * 控制器操作主逻辑 加入事务操作
	 * 
	 * @param ai
	 * @return
	 */
	private boolean doMain(Invocation ai) {
		try {
			ai.invoke();// 然后调用
		} catch (Exception4View e) {
			BaseRenderJson.returnBaseTemplateObj(ai.getController(), MyErrorCodeConfig.REQUEST_FAIL, e.getMessage());
		}
		return true;
	}

	@Override
	public void intercept(Invocation ai) {
		// 添加跨域
		checkAndSetTrustURL(ai.getController().getRequest(), ai.getController().getResponse());
		Controller controller = ai.getController();
		if (!MyConst.devMode) {
			// 获取当前action
			HttpServletRequest request = controller.getRequest();
			StringBuffer requestURL = request.getRequestURL();
			String url = requestURL.toString();
			String[] temp = url.split("/");
			String actionName = temp[temp.length - 2] + "/" + temp[temp.length - 1];
			actionName = actionName.toLowerCase();
			// logger.info("action : " +actionName);
			// 判断是否公共权限路径
			String[] publicActionsArr = MyAccessConfig.PUBLIC_ACTIONS.toLowerCase().split(",");
			java.util.List<String> publicActionsList = Arrays.asList(publicActionsArr);
			if (publicActionsList.contains(actionName)) {
				doMain(ai);
				return;
			}
			// 验证token和当前操作路径权限
			String token = controller.getPara("token");
			String userId = controller.getPara("userId");
			if (StringUtils.isNullOrEmpty(token) || StringUtils.isNullOrEmpty(userId)) {
				BaseRenderJson.baseRenderObj.returnUserIdErrorObj(controller);
				return;
			} else {
				token = RSAUtil.decrypt(token);
				if (null == token) {
					logger.error("action : " + actionName + "," + token + " token 解密错误. ");
					return;
				}
			}
			MDC.put("userId", userId);// 设置log4j的用户id-不同线程都有自己的MDC的key
			try {
				Usertoken resultToken = UsertokenDao.getNewestByUserId(Integer.parseInt(userId));
				// 判断token
				if (null != resultToken) {
					// 去掉token过期,不判断过期----update by rongwq
					/*
					 * if(resultToken.checkIsExpir()){
					 * BaseRenderJson.baseRenderObj.returnTokenErrorObj(
					 * controller,3); return; }
					 */
					if (!token.equals(resultToken.get("token"))) {
						BaseRenderJson.baseRenderObj.returnTokenErrorObj(controller, 2);
						return;
					}
					if (resultToken.getStatus() == 1) {
						doMain(ai);
						return;
					} else {
						BaseRenderJson.baseRenderObj.returnUserIdErrorObj(controller);
						return;
					}
				} else {
					BaseRenderJson.baseRenderObj.returnUserIdErrorObj(controller);
					return;
				}
			} catch (Exception e) {
				logger.error("[CommonInterceptor] error : " + e.getMessage(), e);
				BaseRenderJson.returnObjectTmplate(controller, "500", null, e.getMessage());
				return;
			}
		} else {
			doMain(ai);
		}
	}

	private static void checkAndSetTrustURL(HttpServletRequest request, HttpServletResponse response) {
		String requestURI = RequestUtils.getRequestIpAddress(request);// 获取客户端主机域
		String trustURLStr = PropertiesUtils.get("write_ip", ""); // 获取信任域
		if (trustURLStr.equals("*")) {
			setResponseHeader(response);
			return;
		}
		boolean ret = false;
		String[] trustURLStrArray = trustURLStr.split(",");
		for (int i = 0; i < trustURLStrArray.length; i++) {
			if (StringUtils.isNullOrEmpty(requestURI)) {
				break;
			}
			if (requestURI.indexOf(trustURLStrArray[i]) != -1) {
				ret = true;
				setResponseHeader(response);
				break;
			}
		}
		if (!ret) {
			logger.error("不受信任的ip：" + requestURI);
		}
	}

	/**
	 * 通过设置响应头里的Header，来指定可以跨域访问的客户端
	 * @param response
	 */
	private static void setResponseHeader(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
		response.setHeader("Access-Control-Expose-Headers", "*");
	}

}
