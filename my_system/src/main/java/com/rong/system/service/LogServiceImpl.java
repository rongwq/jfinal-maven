package com.rong.system.service;

import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.dao.logDao;
import com.rong.persist.model.AdminLog;

public class LogServiceImpl implements LogService{
	private logDao dao = new logDao();
	
	@Override
	public Page<Record> list(int pageNumber, int pageSize, Map<String, Object> parMap) {
		return dao.list(pageNumber, pageSize, parMap);
	}

	@Override
	public Page<Record> adminList(int pageNumber, int pageSize, Map<String, Object> parMap){
		return dao.adminList(pageNumber, pageSize, parMap);
	}

	@Override
	public Page<AdminLog> loginLogList(int pageNo, int pageSize,Map<String,Object> parMap){
		return dao.loginLogList(pageNo, pageSize, parMap);
	}

	@Override
	public Page<Record> operateLogList(int pageNumber, int pageSize, Map<String, Object> parMap) {
		return dao.operateLogList(pageNumber, pageSize, parMap);
	}

}
