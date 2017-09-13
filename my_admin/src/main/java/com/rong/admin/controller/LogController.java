package com.rong.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.model.Admin;
import com.rong.persist.model.AdminLog;
import com.rong.system.service.LogService;
import com.rong.system.service.LogServiceImpl;

/****
 * @Project_Name:	it_web
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.0.0
 * @File_Name:		LogController.java
 * @CreateDate:		2017年1月20日 上午11:11:49
 * @Designer:		rongwq
 * @Desc:			日志管理
 * @ModifyHistory:	
 ****/

public class LogController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	LogService logService = new LogServiceImpl();
	
	//获取服务器日志信息
	public void logList(){
		Calendar cal = Calendar.getInstance();	
		SimpleDateFormat SDF=new SimpleDateFormat("yyyyMM");//格式化时间显示
		String defTime=SDF.format(cal.getTime()); //得到当前日期和时间
		
		try {
			Integer pageNumber = getParaToInt("page");
			String userId = getPara("userId");
			String gwNumber = getPara("gwNumber");
			String logLevel = getPara("logLevel");
			String method = getPara("method");
			String msg = getPara("msg");
			String datetimeStart= getPara("datetimeStart");	
			String datetimeEnd= getPara("datetimeEnd");				

			if(pageNumber==null){
				pageNumber = 1;
			}
			Map<String, Object> parMap = new HashMap<>();
			parMap.put("nowDate", defTime);

			if(!StringUtils.isEmpty(gwNumber)){
				parMap.put("gwNumber", gwNumber);
			}
			if(!StringUtils.isEmpty(logLevel)){
				parMap.put("logLevel", logLevel);
			}
			if(!StringUtils.isEmpty(method)){
				parMap.put("method", method);
			}
			if(!StringUtils.isEmpty(msg)){
				parMap.put("msg", msg);
			}
			if(!StringUtils.isEmpty(userId)){
				parMap.put("userId", userId);
			}
			if(!StringUtils.isEmpty(datetimeStart)){
				String newstr = datetimeStart.replace("-", "");
		        String yMM=newstr.substring(0,6);
				parMap.put("nowDate", yMM);
				parMap.put("datetimeStart", datetimeStart);
			}
			if(!StringUtils.isEmpty(datetimeEnd)){
				parMap.put("datetimeEnd", datetimeEnd);
			}
			Page<Record> page = logService.list(pageNumber, pageSize, parMap);
			setAttr("page", page);
			setAttrs(parMap);
			setAttr("logLevelList", getLogLevelList());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		render("/views/log/logList.jsp");
	}
	
	//获取系统日志信息
	public void adminLogList(){
		Calendar cal = Calendar.getInstance();	
		SimpleDateFormat SDF=new SimpleDateFormat("yyyyMM");//格式化时间显示
		String defTime=SDF.format(cal.getTime()); //得到当前日期和时间
		
		try {
			Integer pageNumber = getParaToInt("page");
			String userId = getPara("userId");
			String logLevel = getPara("logLevel");
			String method = getPara("method");
			String msg = getPara("msg");
			String datetimeStart= getPara("datetimeStart");	
			String datetimeEnd= getPara("datetimeEnd");	

			if(pageNumber==null){
				pageNumber = 1;
			}
			Map<String, Object> parMap = new HashMap<>();
			parMap.put("nowDate", defTime);
			if(!StringUtils.isEmpty(logLevel)){
				parMap.put("logLevel", logLevel);
			}
			if(!StringUtils.isEmpty(method)){
				parMap.put("method", method);
			}
			if(!StringUtils.isEmpty(msg)){
				parMap.put("msg", msg);
			}
			if(!StringUtils.isEmpty(userId)){
				parMap.put("userId", userId);
			}
			if(!StringUtils.isEmpty(datetimeStart)){
				String newstr = datetimeStart.replace("-", "");
		        String yMM=newstr.substring(0,6);
				parMap.put("nowDate", yMM);
				parMap.put("datetimeStart", datetimeStart);
			}
			if(!StringUtils.isEmpty(datetimeEnd)){
				parMap.put("datetimeEnd", datetimeEnd);
			}
			Page<Record> page = logService.adminList(pageNumber, pageSize, parMap);
			setAttr("page", page);
			setAttrs(parMap);
			setAttr("logLevelList", getLogLevelList());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		render("/views/log/adminLogList.jsp");
	}
	
	//获取登录日志信息
	public void loginLogList() {
		try {
			
			Map<String, Object> parMap = new HashMap<>();
			Integer pageNumber = getParaToInt("page",1);
			parMap.put("adminId", getParaToInt("adminId"));
			parMap.put("userName", getPara("userName"));
			parMap.put("createTime", getPara("createTime"));
			keepPara();
			Page<AdminLog> page = logService.loginLogList(pageNumber, pageSize, parMap);
			if(null != page){
				for(AdminLog admin: page.getList() ){
					Admin u =Admin.dao.findById(admin.getAdminId());
					if(null != u){
						admin.put("userName", u.getUserName());
					}
				}
			}
			setAttr("page", page);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		render("/views/log/loginLogList.jsp");
	}
	
	//获取操作日志信息
	public void operateLogList(){
		Calendar cal = Calendar.getInstance();	
		SimpleDateFormat SDF=new SimpleDateFormat("yyyyMM");//格式化时间显示
		String defTime=SDF.format(cal.getTime()); //得到当前日期和时间
		
		try {
			String datetimeStart= getPara("datetimeStart");	
			Integer pageNumber = getParaToInt("page");
			if(pageNumber==null){
				pageNumber = 1;
			}
			Map<String, Object> parMap = new HashMap<>();
			parMap.put("nowDate", defTime);
			parMap.put("msg", getPara("msg"));
			parMap.put("datetimeEnd", getPara("datetimeEnd"));
			if(!StringUtils.isEmpty(datetimeStart)){
				String newstr = datetimeStart.replace("-", "");
		        String yMM=newstr.substring(0,6);
				parMap.put("nowDate", yMM);
				parMap.put("datetimeStart", datetimeStart);
			}

			keepPara();
			Page<Record> page = logService.operateLogList(pageNumber, pageSize, parMap);
			setAttr("page", page);
			setAttr("logLevelList", getLogLevelList());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}		
		render("/views/log/operateLogList.jsp");
	}
	
	public List<String> getLogLevelList(){
		List<String> list=new ArrayList<String>();
		list.add("INFO");
		list.add("WARN");
		list.add("ERROR");
		return list;
	}
}

