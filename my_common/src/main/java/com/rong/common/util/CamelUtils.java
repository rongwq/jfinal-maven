package com.rong.common.util;

import org.apache.camel.Exchange;

public class CamelUtils {

	@SuppressWarnings("unchecked")
	public static <T> T extractBody(Exchange exchange , Class<T> clazz){
		Object object = exchange.getIn().getBody();
		
		if(object == null) return null;
		
		if (clazz != null && !clazz.isAssignableFrom(object.getClass())) 
			return null;
		else 
			return (T) object;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T extractHeader(Exchange exchange , String name , Class<T> clazz){
		Object header = exchange.getIn().getHeader(name);
		
		if(header == null) return null;
		
		if(clazz != null && !clazz.isAssignableFrom(header.getClass()))
			return null;
		else 
			return (T) header;
	}
	
	public static void setHeader(Exchange exchange , String name , Object value){
		exchange.getIn().setHeader(name, value);
	}
	
	public static void setBody(Exchange exchange , Object body){
		exchange.getIn().setBody(body);
	}
	
	public static void clear(Exchange exchange){
		exchange.getIn().setBody(null);
	}
}
