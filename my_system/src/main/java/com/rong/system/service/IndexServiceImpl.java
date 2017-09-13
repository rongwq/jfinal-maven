package com.rong.system.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.model.User;

/****
 * @Project_Name:	xk_admin
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		IndexServiceImpl.java
 * @CreateDate:		2017年8月3日 
 * @Designer:		chentt
 * @Desc:			
 * @ModifyHistory:	
 ****/

public class IndexServiceImpl implements IndexService{

	@Override
	public Record getIndexUserValue() {
		StringBuffer sql = new StringBuffer("select"); 
		sql.append(" (select count(*) from "+User.TABLE+" u where TO_DAYS(now()) - TO_DAYS(u.registerDate) = 1) registerUserCountYesterday,");
		return Db.findFirst(sql.toString());
	}
	
}

