package com.rong.system.service;

import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.model.AdminLog;

public interface LogService {
	public Page<Record> list(int pageNumber,int pageSize,Map<String,Object> parMap);
	
	public Page<Record> adminList(int pageNumber,int pageSize,Map<String,Object> parMap);

	public Page<AdminLog> loginLogList(int pageNo, int pageSize, Map<String,Object> parMap) ;
	
	public Page<Record> operateLogList(int pageNumber,int pageSize,Map<String,Object> parMap) ;
}
