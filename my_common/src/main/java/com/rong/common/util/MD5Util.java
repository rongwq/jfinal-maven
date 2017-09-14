package com.rong.common.util;

import java.security.MessageDigest;

/****
 * @Project_Name:	yzg_common
 * @Copyright:		Copyright © 2012-2017 G-emall Technology Co.,Ltd
 * @Version:		    1.0.0.1
 * @File_Name:		MD5Util.java
 * @CreateDate:		2017年2月23日 下午1:54:00
 * @Designer:		    zhenghongbin
 * @Desc:			 MD5加密工具类
 * @ModifyHistory:	 
 ****/
public class MD5Util {

	/**
	 * 对数据源进行MD5加密
	 * @param src 数据源
	 * @return 返回加密后的值
	 */
	public static String getMD5(byte[] src) {
		StringBuilder result = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src);
			for (byte b : md.digest()) {
				result.append(Integer.toString(b >>> 4 & 0xF, 16)).append(Integer.toString(b & 0xF, 16));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}
