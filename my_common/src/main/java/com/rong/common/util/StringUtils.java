package com.rong.common.util;


public class StringUtils {
	public static boolean isNullOrEmpty(String str) {
		return null == str || 0 == str.trim().length();
	}
	
	public static boolean isNullOrEmpty(Object str) {
		return null == str || isNullOrEmpty((String) str);
	}

    /**
     * 用于经纬度  取小数点后6位
     * 保留小数点后i位  如：传入参数 1234567891.1234567891 ，6  返回结果为1234567891.123456
     *
     * @param s 要截取的字符串
     * @param i 保留小数后几位
     * @return
     */
    public static double stringToDouble(String s, int i) {
        int index = s.indexOf(".");
        String start = s.substring(0, index);
        String end = s.substring(index, i + index + 1);
        return Double.parseDouble(start + end);
    }

}
