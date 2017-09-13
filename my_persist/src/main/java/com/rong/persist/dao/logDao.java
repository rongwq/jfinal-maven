package com.rong.persist.dao;

import java.util.Map;

import com.gw.common.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.model.Admin;
import com.rong.persist.model.AdminLog;

public class logDao {
	private AdminLog dao = AdminLog.dao;
	private AdminDao adminDao = new AdminDao();

	public Page<Record> list(int pageNumber, int pageSize, Map<String, Object> parMap) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");

		if (parMap != null) {
			if (parMap.containsKey("userId")) {
				sqlWhere.append(" and userId = '").append(parMap.get("userId")).append("'");

			}
			if (parMap.containsKey("logLevel")) {
				sqlWhere.append(" and log_level = '").append(parMap.get("logLevel")).append("'");

			}
			if (parMap.containsKey("method")) {
				sqlWhere.append(" and method = '").append(parMap.get("method")).append("'");

			}
			if (parMap.containsKey("msg")) {
				sqlWhere.append(" and msg like '%").append(parMap.get("msg")).append("%'");

			}
			if (parMap.containsKey("datetimeStart")) {
				sqlWhere.append(" and create_time >= '").append(parMap.get("datetimeStart")).append("'");

			}
			if (parMap.containsKey("datetimeEnd")) {
				sqlWhere.append(" and create_time <= '").append(parMap.get("datetimeEnd")).append("'");

			}
		}

		String sqlExceptSelect = "from itlog_" + parMap.get("nowDate").toString() + sqlWhere.toString()
				+ " order by create_time desc";
		Page<Record> logList = Db.use("log").paginate(pageNumber, pageSize, sqlSelect, sqlExceptSelect);

		return logList;
	}

	public Page<Record> adminList(int pageNumber, int pageSize, Map<String, Object> parMap) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");

		if (parMap != null) {
			if (parMap.containsKey("userId")) {
				sqlWhere.append(" and userId = '").append(parMap.get("userId")).append("'");

			}
			if (parMap.containsKey("logLevel")) {
				sqlWhere.append(" and log_level = '").append(parMap.get("logLevel")).append("'");

			}
			if (parMap.containsKey("method")) {
				sqlWhere.append(" and method = '").append(parMap.get("method")).append("'");

			}
			if (parMap.containsKey("msg")) {
				sqlWhere.append(" and msg like '%").append(parMap.get("msg")).append("%'");

			}
			if (parMap.containsKey("datetimeStart")) {
				sqlWhere.append(" and create_time >= '").append(parMap.get("datetimeStart")).append("'");

			}
			if (parMap.containsKey("datetimeEnd")) {
				sqlWhere.append(" and create_time <= '").append(parMap.get("datetimeEnd")).append("'");

			}

		}

		String sqlExceptSelect = "from itadminlog_" + parMap.get("nowDate").toString() + sqlWhere.toString()
				+ " order by create_time desc";
		Page<Record> logList = Db.use("log").paginate(pageNumber, pageSize, sqlSelect, sqlExceptSelect);

		return logList;
	}

	public Page<AdminLog> loginLogList(int pageNo, int pageSize, Map<String, Object> parMap) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");
		Admin u = null;
		if (parMap != null) {
			if (parMap.get("adminId") != null) {
				sqlWhere.append(" and adminId= ").append(parMap.get("adminId"));
			}

			if (!StringUtils.isNullOrEmpty(parMap.get("userName"))) {
				u = adminDao.getByUserName(parMap.get("userName").toString());
				if (null != u) {
					sqlWhere.append(" and adminId= " + u.getId() + " ");
				}

			}
			if (!StringUtils.isNullOrEmpty(parMap.get("createTime"))) {
				sqlWhere.append(" and createTime like '").append(parMap.get("createTime")).append("%'");
				;
			}
		}
		String sqlExceptSelect = "from " + AdminLog.TABLE + sqlWhere.toString() + " order by createTime desc";
		Page<AdminLog> adminLogPage = dao.paginate(pageNo, pageSize, sqlSelect, sqlExceptSelect);
		return adminLogPage;
	}

	public Page<Record> operateLogList(int pageNumber, int pageSize, Map<String, Object> parMap) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1 and  msg like '[操作日志]%' ");

		if (parMap != null) {
			if (!StringUtils.isNullOrEmpty(parMap.get("userId"))) {
				sqlWhere.append(" and userId = '").append(parMap.get("userId")).append("'");
			}
			if (!StringUtils.isNullOrEmpty(parMap.get("msg"))) {
				sqlWhere.append(" and msg like '%").append(parMap.get("msg")).append("%'");
			}
			if (!StringUtils.isNullOrEmpty(parMap.get("datetimeStart"))) {
				sqlWhere.append(" and create_time > '").append(parMap.get("datetimeStart")).append("'");
			}
			if (!StringUtils.isNullOrEmpty(parMap.get("datetimeEnd"))) {
				sqlWhere.append(" and create_time < '").append(parMap.get("datetimeEnd")).append("'");
			}
		}

		String sqlExceptSelect = "from itadminlog_" + parMap.get("nowDate").toString() + sqlWhere.toString()
				+ " order by create_time desc";
		Page<Record> logList = Db.use("log").paginate(pageNumber, pageSize, sqlSelect, sqlExceptSelect);

		return logList;
	}
}
