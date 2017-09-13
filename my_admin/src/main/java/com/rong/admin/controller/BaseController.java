/**
 * 后台控制器基类
 */
package com.rong.admin.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.gw.common.exception.ParamIsEmpty;
import com.gw.common.exception.ParamNotFound;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.bean.ResultBean;
import com.rong.persist.model.Admin;
import com.rong.persist.model.AdminLog;

public class BaseController extends Controller {
	public static final int pageSize = 10;
	public Field serviceField = null;
	
	public Field getService() {
		if (serviceField != null) {
			return serviceField;
		}
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if ("service".equals(field.getName())) {
				serviceField = field;
				serviceField.setAccessible(true);
				break;
			}
		}
		return serviceField;
	}
	
	public String path() {
		String name = this.getClass().getSimpleName();
		name = name.substring(0, name.indexOf("Controller"));
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		return "/views/" + name;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void list() {
		try {
			Integer pageNumber = getParaToInt("page", 1);
			Map<String, Object> paraMap = getParas();
			Class service = getService().getType();
			Method method = service.getMethod("query", Integer.class, Integer.class, Map.class);
			Object obj = getService().get(this);
			Object result = method.invoke(obj, pageNumber, pageSize, paraMap);
			Page<Record> page = (Page<Record>)result;
			setAttr("page", page);
			paraMap.remove("page");
			setAttrs(paraMap);
			render(path() + "/list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void toEdit() {
		try {
			Integer id = getParaToInt("id");
			if (id != null) {
				Class service = getService().getType();
				Method method = service.getMethod("findById", Integer.class);
				Object obj = getService().get(this);
				Object result = method.invoke(obj, id);
				setAttr("bean", result);
			}
			render(path() + "/edit.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Admin getUser(){
		Admin u = (Admin) getSessionAttr(Admin.SESSION_KEY);
		return u;
	}
	
	public Map<String, Object> getParas(){
		Map<String, String[]> paraMap = getParaMap();
		Map<String, Object> result = new HashMap<>();
		for (Map.Entry<String, String[]> entry : paraMap.entrySet()) {
			result.put(entry.getKey(), entry.getValue()[0]);
		}
		return result;
	}
	
	public void renderJudge(boolean succ){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", succ ? 1 : 0);
		renderJson(jsonObject.toString());
	}

	public void renderJudge(boolean succ, String msg){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", succ ? 1 : 0);
		jsonObject.addProperty("msg", msg);
		renderJson(jsonObject.toString());
	}

	public void renderJudge(boolean succ, String msg, Object data){
		renderJson(new ResultBean(succ ? "1" : "0", msg, data, null));
	}
	
	public void paramCheck(String names) throws ParamNotFound, ParamIsEmpty{
		String[] nameArray = names.split(",");
		for (String name : nameArray) {
			if (!isParaExists(name)) {
				ParamNotFound paramNotExists = new ParamNotFound(String.format("param [%s] not found", name));
				paramNotExists.setParam(name);
				throw paramNotExists;
			}else {
				if (StringUtils.isEmpty(getPara(name))) {
					ParamIsEmpty paramIsEmpty = new ParamIsEmpty(String.format("param [%s] is empty", name));
					paramIsEmpty.setParam(name);
					throw paramIsEmpty;
				}
			}
		}
	}
	
	public void saveLog(String content){
		AdminLog l = new AdminLog();
		l.setAdminId(getUser().getId());
		l.setContent(content);
		l.setCreateTime(new Date());
		l.save();
	}
	
	public void succ(Object data) {
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, data, "success");
	}

	public void succ() {
		succ(null);
	}
	
	public void fail(String message) {
		BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.REQUEST_FAIL, message);
	}
}
