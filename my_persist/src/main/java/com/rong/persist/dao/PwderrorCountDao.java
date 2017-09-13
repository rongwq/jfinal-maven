package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.PwderrorCount;



public class PwderrorCountDao extends BaseDao<PwderrorCount> {
	private PwderrorCount dao= PwderrorCount.dao;
	public PwderrorCount getByUserId(Integer userId){
		return dao.findFirst("select * from "+PwderrorCount.TABLE+" where userId=?", userId);
	}
	public void delete(){
		String sql = "delete from "+PwderrorCount.TABLE;
		Db.update(sql);
		
	}
	
}
