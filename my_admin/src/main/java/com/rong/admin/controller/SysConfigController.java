package com.rong.admin.controller;

import java.util.Date;

import org.apache.log4j.Logger;

import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.AdminSysConfig;
import com.rong.system.service.SysConfigService;
import com.rong.system.service.SysConfigServiceImpl;

public class SysConfigController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	SysConfigService sysConfigService=new SysConfigServiceImpl();
	
	public void list(){
		int page = getParaToInt("page", 1);
		Integer id = getParaToInt("_id");
		String key = getPara("key");
		String value = getPara("value");
		keepPara();
		setAttr("page", sysConfigService.list(page,pageSize,id,key,value));
		render("/views/system/sysConfig/list.jsp");
	}
	

	public void toEdit() {
		Integer id = getParaToInt("id");
		if(id!=null){
			AdminSysConfig model = sysConfigService.getById(id);
			setAttr("sysConfig", model);
		}
		keepPara();//保留传递过来的参数
		render("/views/system/sysConfig/edit.jsp");
	}
	
	public void edit(){
		Integer id = getParaToInt("id");
		String key = getPara("key");
		String value = getPara("value");
		String remark = getPara("remark");
		String type = getPara("type");
		if(id==null){
			if (sysConfigService.getByKey(key)!=null) {
				BaseRenderJson.returnJsonS(this,0,"配置key已经存在");
				return;
			}
			AdminSysConfig model = new AdminSysConfig();
			setModel(key,value,remark,model);
			model.setType(type);
			model.setCreateTime(new Date());
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加配置成功：" + key+","+value );
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.error("[操作日志]添加配置失败：" + key+","+value );
			}
		}else{
			AdminSysConfig model = sysConfigService.getById(id);
			setModel(key,value,remark,model);
			model.setType(type);
			if (model.update()) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]"+getUser().getUserName()+ "修改配置成功：" + model.getValue());
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.info("[操作日志]"+getUser().getUserName()+ "修改配置失败：" + model.getValue());
			}
		}
	}
	
	public void delete() {
		Integer id = getParaToInt("id");
		if(sysConfigService.delete(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]"+getUser().getUserName()+ "删除配置成功:id:" + id);
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]"+getUser().getUserName()+ "删除配置失败:id:" + id);
		}
	}
	
	private void setModel(String key,String value,String remark,AdminSysConfig model){
		model.setKey(key);
		model.setValue(value);
		model.setRemark(remark);
	}
	
//	public void refresh(){
//		SysConfigService confService = new SysConfigServiceImpl();
//		boolean result = confService.refreshConf();
//		BaseRenderJson.returnOpareObj(this, result);
//	}


}
