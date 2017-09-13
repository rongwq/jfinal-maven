package com.gw.common.util;

import java.util.Calendar;
import java.util.List;

public class CommonUtil {

	public static String createGwGroupId(Integer groupId){
		//月份+年份+该群的序号
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String year =  String.valueOf(cal.get(Calendar.YEAR)).substring(2);
		String gwGroupId = month+year+groupId.toString();
		 return gwGroupId;
	}
	
	public static String createGwGroupName(String groupName){
		Long now = System.currentTimeMillis();
		StringBuffer sb = new StringBuffer();
		sb.append(groupName);
		sb.append(now);
		return sb.toString();
	}
		
	/**
	 * 讲list转换成带,分隔的字符串
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	/**
	 * 重写线程等待方法
	 * @param time
	 */
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 默认等待2秒
	 */
	public static void sleep(){
		sleep(3000);
	}

}
