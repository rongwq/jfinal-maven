package com.rong.common.bean;

import com.rong.common.util.ObjectUtil;

public class BaseVo<T> {
	public BaseVo() {
	}
	public BaseVo(Object o) {
		merge(o);
	}
	
	@SuppressWarnings("unchecked")
	public T merge(Object o){
		return (T) ObjectUtil.merge(this, o);
//		try {
//			BeanUtils.copyProperties(this, o);
//			return (T) this;
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			throw new RuntimeException(e);
//		}
	}
	
}
