package com.rong.common.util;

import java.io.File;

import com.jfinal.kit.PathKit;

/*****
 *@Project_Name:apart_common
 * @Copyright:	Copyright © 2012-2016 G-emall Technology Co.,Ltd
 * @Version:	1.0.0.1
 * @File_Name:	UnionUtil.java
 * @CreateDate:	2016年10月3日 下午5:59:37
 * @Designer:	zhenghongbin
 * @Desc:			
 * @ModifyHistory:

*****/
public class UnionPayUtil {

	public static String signPath = "";
	public static String encPath = "";
	public static String dirPath = "";
	
	/**
	 * 在初始化的时候，计算出银联证书的位置参数
	 */
	public static void init() {
		String acpSign = null;
		String acpEnc = null;
		//项目的tomcat路径
		String webPath = PathKit.getWebRootPath();
		//银联证书存放路径
		String acpDir = webPath + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "sdk";
		File dir = new File(acpDir);
		//遍历所有文件，获取签名证书和敏感信息证书的名称
		File[] files = dir.listFiles();
		for (File acpFile : files) {
			String name = acpFile.getName();
			if (name.endsWith("cer")) {
				if (name.indexOf("enc") > 0) {
					acpEnc = name;
				}
			} else if (name.endsWith("pfx")) {
				acpSign = name;
			}
		}
		//赋值
		dirPath = acpDir + File.separator;
		signPath = acpDir + File.separator + acpSign;
		encPath = acpDir + File.separator + acpEnc;
	}
}
