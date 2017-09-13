package com.rong.persist.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.gw.common.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class BaseDao<T> {

	public Field daoField = null;
	private static String tableName = null;
	
	public Field getDao() {
		if (daoField != null) {
			return daoField;
		}
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType para = (ParameterizedType)type;
		Type[] types = para.getActualTypeArguments();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (types[0] == field.getType()) {
				daoField = field;
				daoField.setAccessible(true);
				break;
			}
		}
		return daoField;
	}
	
	/**
	 * 获取泛型的表名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getTableName() {
		if (tableName != null) {
			return tableName;
		}
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType para = (ParameterizedType)type;
		Type[] types = para.getActualTypeArguments();
		String allName = types[0].toString();
		allName = allName.substring(allName.lastIndexOf(" ") + 1);
		try {
			Class persist = Class.forName(allName);
			Field field = persist.getField("TABLE");
			return field.get(persist).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 单表查询时调用，只返回指定字段的单条数据
	 * 只适用于简单的sql查询语句，条件筛选暂时只能是等于
	 * 类似：select id, money, gwNumber from yzg_discount_orde where id = ? and gwNumber = ? order by createTime desc
	 * @param paraMap 筛选条件，如id = 1，以map形式保存
	 * @param orderBy 排序字段
	 * @param isDesc 是否倒序，true为倒序，false为顺序
	 * @param results 需要查询的字段
	 * @return
	 */
	public Record getRecordByParam(Map<String, Object> paraMap, String orderBy, Boolean isDesc, String... results) {
		if (results.length == 0) {
			return null;
		}
		StringBuilder select = new StringBuilder("select");
		for (int i = 0; i < results.length; i ++) {
			select.append(" " + results[i] + ",");
		}
		select = select.delete(select.length() - 1, select.length());
		select.append(" from " + getTableName());
		StringBuilder where = new StringBuilder(" where");
		Object[] objArr = new Object[paraMap.size()];
		Set<Entry<String, Object>> set = paraMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : set) {
			where.append(" " + entry.getKey() + " = ? and");
			objArr[index++] = entry.getValue();
		}
		where = where.delete(where.length() - 4, where.length());
		String sql = select.toString() + where.toString();
		if (!StringUtils.isNullOrEmpty(orderBy)) {
			if (isDesc) {
				sql = sql + " order by " + orderBy + " desc";
			} else {
				sql = sql + " order by " + orderBy;
			}
		}
		return Db.findFirst(sql, objArr);
	}
	/**
	 * 单表查询时调用，返回实体类的单条数据
	 * 只适用于简单的sql查询语句，条件筛选暂时只能是等于
	 * 类似：select * from yzg_discount_orde where id = ? and gwNumber = ? order by createTime desc
	 * @param paraMap 筛选条件，如id = 1，以map形式保存
	 * @param orderBy 排序字段
	 * @param isDesc 是否倒序，true为倒序，false为顺序
	 * @return
	 */
	public T findFirst(Map<String, Object> paraMap, String orderBy, Boolean isDesc) {
		StringBuilder select = new StringBuilder("select * from ");
		select.append(getTableName());
		StringBuilder where = new StringBuilder(" where");
		Object[] objArr = new Object[paraMap.size()];
		Set<Entry<String, Object>> set = paraMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : set) {
			where.append(" " + entry.getKey() + " = ? and");
			objArr[index++] = entry.getValue();
		}
		where = where.delete(where.length() - 4, where.length());
		String sql = select.toString() + where.toString();
		if (!StringUtils.isNullOrEmpty(orderBy)) {
			if (isDesc) {
				sql = sql + " order by " + orderBy + " desc";
			} else {
				sql = sql + " order by " + orderBy;
			}
		}
		return findFirst(sql, objArr);
	}
	
	
	/**
	 * 返回Record
	 * 类似：select * from yzg_discount_orde where id = ? and gwNumber = ? order by createTime desc
	 * @param paraMap
	 * @param orderBy
	 * @param isDesc
	 * @return    record
	 */
	public Record findFirstRecord(Map<String, Object> paraMap, String orderBy, Boolean isDesc) {
		StringBuilder select = new StringBuilder("select * from ");
		select.append(getTableName());
		StringBuilder where = new StringBuilder(" where");
		Object[] objArr = new Object[paraMap.size()];
		Set<Entry<String, Object>> set = paraMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : set) {
			where.append(" " + entry.getKey() + " = ? and");
			objArr[index++] = entry.getValue();
		}
		where = where.delete(where.length() - 4, where.length());
		String sql = select.toString() + where.toString();
		if (!StringUtils.isNullOrEmpty(orderBy)) {
			if (isDesc) {
				sql = sql + " order by " + orderBy + " desc";
			} else {
				sql = sql + " order by " + orderBy;
			}
		}
		return Db.findFirst(sql, objArr);
	}
	
	/**
	 * 单表查询时调用，返回指定字段的多条数据
	 * 只适用于简单的sql查询语句，条件筛选暂时只能是等于
	 * 类似：select id, money, gwNumber from yzg_discount_orde where id = ? and gwNumber = ? order by createTime desc
	 * @param paraMap 筛选条件，如id = 1，以map形式保存
	 * @param orderBy 排序字段
	 * @param isDesc 是否倒序，true为倒序，false为顺序
	 * @param results 需要查询的字段
	 * @return
	 */
	public List<Record> getListByParam(Map<String, Object> paraMap, String orderBy, Boolean isDesc, String... results) {
		if (results.length == 0) {
			return null;
		}
		StringBuilder select = new StringBuilder("select");
		for (int i = 0; i < results.length; i ++) {
			select.append(" " + results[i] + ",");
		}
		select = select.delete(select.length() - 1, select.length());
		select.append(" from " + getTableName());
		StringBuilder where = new StringBuilder(" where");
		Object[] objArr = new Object[paraMap.size()];
		Set<Entry<String, Object>> set = paraMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : set) {
			where.append(" " + entry.getKey() + " = ? and");
			objArr[index++] = entry.getValue();
		}
		where = where.delete(where.length() - 4, where.length());
		String sql = select.toString() + where.toString();
		if (!StringUtils.isNullOrEmpty(orderBy)) {
			if (isDesc) {
				sql = sql + " order by " + orderBy + " desc";
			} else {
				sql = sql + " order by " + orderBy;
			}
		}
		return Db.find(sql, objArr);
	}
	
	/**
	 * 单表查询调用，返回实体类的多条数据
	 * 只适用于简单的sql查询语句，条件筛选暂时只能是等于
	 * 类似：select * from yzg_discount_orde where id = ? and gwNumber = ? order by createTime desc
	 * @param paraMap 筛选条件，如id = 1，以map形式保存
	 * @param orderBy 排序字段
	 * @param isDesc 是否倒序，true为倒序，false为顺序
	 * @return
	 */
	public List<T> find(Map<String, Object> paraMap, String orderBy, Boolean isDesc) {
		StringBuilder select = new StringBuilder("select * from ");
		select.append(getTableName());
		StringBuilder where = new StringBuilder(" where ");
		Object[] objArr = new Object[paraMap.size()];
		Set<Entry<String, Object>> set = paraMap.entrySet();
		int index = 0;
		for (Entry<String, Object> entry : set) {
			where.append(" " + entry.getKey() + " = ?");
			objArr[index++] = entry.getValue();
		}
		where = where.delete(where.length() - 4, where.length());
		String sql = select.toString() + where.toString();
		if (!StringUtils.isNullOrEmpty(orderBy)) {
			if (isDesc) {
				sql = sql + " order by " + orderBy + " desc";
			} else {
				sql = sql + " order by " + orderBy;
			}
		}
		return find(sql, objArr);
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Integer id) {
		try {			
			Method method = getDao().getType().getMethod("findById", Object.class);
			Object obj = getDao().get(this);
			Object result = method.invoke(obj, id);
			return (T)result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(Model<?> model) {
		return model.save();
	}
	
	public boolean update(Model<?> model) {
		return model.update();
	}
	
	public boolean delete(Model<?> model) {
		return model.delete();
	}
	
	public boolean deleteById(Integer id) {
		try {
			Method method = getDao().getType().getMethod("deleteById", Object.class);
			Object obj = getDao().get(this);
			Object result = method.invoke(obj, id);
			return (boolean)result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String sql) {
		try {
			Method method = getDao().getType().getMethod("find", String.class);
			Object obj = getDao().get(this);
			Object result = method.invoke(obj, sql);
			return (List<T>)result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String sql, Object... paras) {
		try {
			Method method = getDao().getType().getMethod("find", String.class, Object[].class);
			Object obj = getDao().get(this);
			Object result = method.invoke(obj, sql, paras);
			return (List<T>)result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findFirst(String sql) {
		try {
			Method method = getDao().getType().getMethod("findFirst", String.class);
			Object obj = getDao().get(this);
			Object result = method.invoke(obj, sql);
			return (T)result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findFirst(String sql, Object... paras) {
		try {
			Method method = getDao().getType().getMethod("findFirst", String.class, Object[].class);
 			Object obj = getDao().get(this);
			Object result = method.invoke(obj, sql, paras);
			return (T)result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
