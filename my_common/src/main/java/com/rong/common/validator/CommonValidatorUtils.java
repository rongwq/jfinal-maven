package com.rong.common.validator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;

public class CommonValidatorUtils {

	public static boolean requiredValidate(String key, String value, Controller controller){
		if(StringUtils.isEmpty(value)){
			BaseRenderJson.returnParaErrorObj(controller,key);
			return true;
		}
		return false;
	}
	
	public static boolean requiredValidate(Map<String,Object> map, Controller controller){
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if(entry.getValue() instanceof  String){
				if(StringUtils.isEmpty(String.valueOf(entry.getValue()))){
					BaseRenderJson.returnParaErrorObj(controller,entry.getKey());
					return true;
				}
			}else{
				if(null==entry.getValue()){
					BaseRenderJson.returnParaErrorObj(controller,entry.getKey());
					return true;
				}
			}

		}
		return false;
	}
}
