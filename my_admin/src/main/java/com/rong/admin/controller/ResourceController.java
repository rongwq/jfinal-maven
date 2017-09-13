package com.rong.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gw.common.validator.CommonValidatorUtils;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.AdminResource;
import com.rong.system.service.ResourceService;
import com.rong.system.service.ResourceServiceImpl;

/****
 * @Project_Name:	gxt_admin
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		ResourceController.java
 * @CreateDate:		2016年6月8日 下午1:59:06
 * @Designer:		rongwq
 * @Desc:			资源管理
 * @ModifyHistory:	
 ****/

public class ResourceController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	ResourceService resourceService = new ResourceServiceImpl();
	
	public void list(){
		int page = getParaToInt("page", 1);
		String key = getPara("key");
		Integer id = getParaToInt("_id");
		String name = getPara("name");
		keepPara();
		setAttr("page", resourceService.getPage(page,pageSize,key,id,name));
		render("/views/system/resource/list.jsp");
	}
	
	public void toEdit() {
		Integer id = getParaToInt("id");
		if(id!=null){
			AdminResource model = resourceService.getById(id);
			setAttr("resource", model);
		}
		setAttr("menus", resourceService.getMenus());
		keepPara();//保留传递过来的参数
		render("/views/system/resource/edit.jsp");
	}
	
	public void edit() {
		String key = getPara("key");
		String name = getPara("name");
		String remark = getPara("remark");
		Integer pid = getParaToInt("pid");
		Integer id = getParaToInt("id");
		Integer type = getParaToInt("type");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("key", key);
		paramMap.put("name", name);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		if(id==null){
			if (resourceService.getByKey(key)!=null) {
				BaseRenderJson.returnJsonS(this,0,"权限key已经存在");
				return;
			}
			AdminResource model = new AdminResource();
			model.setCreateTime(new Date());
			setModel(key, name, remark, pid, type, model);
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加权限成功："+key+","+name );
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.info("[操作日志]添加权限失败："+key+","+name );
			}
		}else{
			AdminResource model = resourceService.getById(id);
			setModel(key, name, remark, pid, type, model);
			if (model.update()) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]修改权限成功："+key+","+name );
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.info("[操作日志]修改权限失败："+key+","+name );
			}
		}
	}

	private void setModel(String key, String name, String remark, Integer pid, Integer type, AdminResource model) {
		model.setKey(key);
		model.setName(name);
		model.setPid(pid);
		model.setRemark(remark);
		model.setType(type);
	}

	public void delete() {
		Integer id = getParaToInt("id");
		if(resourceService.delete(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除权限成功:id:" + id);
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]删除权限失败:id:" + id);
		}
	}
}

