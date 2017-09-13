package com.rong.persist.base;

import java.lang.reflect.Field;

public class BaseServiceImpl<T> {

	public BaseDao<T> baseDao = null;
	
	@SuppressWarnings("unchecked")
	public BaseDao<T> getDao() {
		if (baseDao != null) {
			return baseDao;
		}
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				if ("dao".equals(field.getName())) {
					field.setAccessible(true);
					baseDao = (BaseDao<T>)field.get(this);
					break;
				}
			}
			return baseDao;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	public T findById(Integer id) {
		return getDao().findById(id);
	}
	
	public boolean deleteById(Integer id) {
		return getDao().deleteById(id);
	}
	*/
}
