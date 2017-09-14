package com.rong.common.util;

import java.util.Collection;

public class CollectionUtil {
	public static String join(Collection<?> collection, String s) {
		String result = "";
		for (Object object : collection) {
			result += "," + object;
		}
		return result.length() > 0 ? result.substring(1) : result;
	}
}
