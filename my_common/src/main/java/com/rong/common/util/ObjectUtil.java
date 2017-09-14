package com.rong.common.util;

import org.apache.commons.beanutils.BeanUtils;

import com.rong.common.exception.g.UnexpectedException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 可以将map的值设置到指定的对象，通过map key 与对象字段名一一对应设值。<br>
 * 只支持基本数据类型及其包装类型，String类型，BigDecimal类型。
 * 
 * @author 倪志耿
 *
 */
public class ObjectUtil {
	public interface IVO<T> {
		public T merge(Object vo);
	}

	public static <T> T copyProperties(T vo, Object o) {
		try {
			BeanUtils.copyProperties(vo, o);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new UnexpectedException("VO automatic assembly failure", e);
		}
		return vo;
	}

	/**
	 * 可以将map的值设置到指定的对象，通过map key 与对象字段名一一对应设值。<br>
	 * 只支持基本数据类型及其包装类型，String类型，BigDecimal类型。
	 * 
	 * @param vo
	 * @param map
	 */
	public static <T> T merge(T vo, Map<String, ? extends Object> map) {
		try {
			return mymerge(vo, map);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new UnexpectedException("VO automatic assembly failure", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T merge(T vo, Object vob) {
		try {
			if (vob instanceof Map)
				return merge(vo, (Map<String, ? extends Object>) vob);
			return mymerge(vo, vob);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new UnexpectedException("VO automatic assembly failure", e);
		}
	}

	private static <T> T mymerge(T vo, Map<String, ? extends Object> map)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		for (Field field : vo.getClass().getDeclaredFields()) {
			Object valueFromMap = map.get(field.getName());
			if (valueFromMap != null)
				setValue(vo, field, valueFromMap);
		}
		return vo;
	}

	private static <T> T mymerge(T vo, Object vob)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Class<? extends Object> vobClazz = vob.getClass();
		for (Field field : vo.getClass().getDeclaredFields()) {
			try {
				Field vobField = vobClazz.getDeclaredField(field.getName());
				vobField.setAccessible(true);
				Object vobFieldValue = vobField.get(vob);
				if (vobFieldValue != null)
					setValue(vo, field, vobFieldValue);
			} catch (NoSuchFieldException e) {
				// Just let it go
			}
		}
		return vo;
	}

	private static void setValue(Object vo, Field voField, Object valueFromMap)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		voField.setAccessible(true);
		Class<?> voFieldType = voField.getType();
		if (valueFromMap.getClass().isAssignableFrom(voFieldType)) {
			voField.set(vo, valueFromMap);
			return;
		}
		
		if(voFieldType.isPrimitive()){
			
		}

		if (voFieldType.equals(String.class)) {
			voField.set(vo, valueFromMap.toString());
			return;
		}

		if (voFieldType.equals(BigDecimal.class)) {
			if (valueFromMap == null || "".equals(valueFromMap))
				return;
			if (valueFromMap instanceof BigDecimal)
				voField.set(vo, valueFromMap);
			else
				voField.set(vo, new BigDecimal(String.valueOf(valueFromMap)));
			return;
		}

		try {
			Method method = voFieldType.getDeclaredMethod("valueOf", String.class);
			String string = valueFromMap.toString();
			if (!string.equals(""))
				voField.set(vo, method.invoke(null, string));
		} catch (NoSuchMethodException e) {
			// Just let it go
		}

	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, SecurityException, InvocationTargetException, NoSuchFieldException {
		Map<String, Object> m = new HashMap<>();
		m.put("str", "caca");
		m.put("inte", "50");
		m.put("str2", new BigDecimal("45.02"));
		m.put("dou", 13.5);
		m.put("dou2", "12.012");
		m.put("bd", "100.2");
		m.put("bd2", 500);
		m.put("mvo", "mvoo");
		m.put("boo", true);
		Mvo vo = new Mvo();
//		ObjectUtil.merge(vo, m);
		ObjectUtil.merge(vo, new Object(){
//			boolean boo = true;
//			int cc = 2;
		});
//		 BeanUtils.copyProperties(vo, m);
		//
		for (Field field : vo.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			System.out.println(field.getName() + " = " + field.get(vo));
		}

	}

	public static class Mvo implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String str;
		Integer inte;
		String str2;
		Double dou;
		Double dou2;
		BigDecimal bd;
		BigDecimal bd2;
		boolean boo;
		int cc = 1;

		// Mvo mvo;
		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}

		public Integer getInte() {
			return inte;
		}

		public void setInte(Integer inte) {
			this.inte = inte;
		}

		public String getStr2() {
			return str2;
		}

		public void setStr2(String str2) {
			this.str2 = str2;
		}

		public Double getDou() {
			return dou;
		}

		public void setDou(Double dou) {
			this.dou = dou;
		}

		public Double getDou2() {
			return dou2;
		}

		public void setDou2(Double dou2) {
			this.dou2 = dou2;
		}

		public BigDecimal getBd() {
			return bd;
		}

		public void setBd(BigDecimal bd) {
			this.bd = bd;
		}

		public BigDecimal getBd2() {
			return bd2;
		}

		public void setBd2(BigDecimal bd2) {
			this.bd2 = bd2;
		}

		/**
		 * @return the boo
		 */
		public boolean isBoo() {
			return boo;
		}

		/**
		 * @param boo the boo to set
		 */
		public void setBoo(boolean boo) {
			this.boo = boo;
		}

	}
}
