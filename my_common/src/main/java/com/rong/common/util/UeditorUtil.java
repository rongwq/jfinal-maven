package com.rong.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.jfinal.kit.PathKit;
import com.rong.common.bean.MyConst;

/*****
 *@Project_Name:apart_common
 * @Copyright:	Copyright © 2012-2016 G-emall Technology Co.,Ltd
 * @Version:	1.0.0.1
 * @File_Name:	UeditorUtil.java
 * @CreateDate:	2016年10月2日 上午10:51:05
 * @Designer:	zhenghongbin
 * @Desc:			
 * @ModifyHistory:

*****/
public class UeditorUtil {
	/**
	 * 初始化时更改ueditor的图片访问名的前缀
	 */
	public static void init() {
		String webPath = PathKit.getWebRootPath();
		//ueditor路径
		String ueditorPath = webPath + File.separator + "ueditor" + File.separator + "jsp" + File.separator + "config.json";
		//临时文件
		String temporary = webPath + File.separator + "ueditor" + File.separator + "jsp" + File.separator + "temporary.json";
		File file = new File(ueditorPath);
		File temFile = new File(temporary);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		if (file.exists()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				writer = new BufferedWriter(new FileWriter(temFile));
				String str = null;
				while ((str = reader.readLine()) != null) {
					//找到图片访问的前缀行
					if (str.indexOf("imageUrlPrefix") > 0) {
						//添加请求头部
						int index = str.indexOf("/");
						str = str.substring(0, index) + MyConst.ueditorHead + str.substring(index);
					}
					writer.write(str);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		//删除旧的ueditor文件
		file.delete();
		//把临时文件更名为ueditor文件
		temFile.renameTo(file);
	}
}
