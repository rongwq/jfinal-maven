package com.rong.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jfinal.kit.PathKit;

public class RobotAddressUtil {
	//用于缓存地理地址信息
	private static List<String[]> list = null;
	
	/**
	 * 获取随机地理地址
	 * @return
	 */
	public static String getRamdomAddress() {
		if (list == null) {
			list = init();
		}
		Random random = new Random();
		//随机获取一条信息
		Integer pIndex = random.nextInt(list.size() - 1);
		String[] arr = list.get(pIndex);
		//如果是直辖市的话，则不需要再拿后一位
		if (arr.length == 1) {
			return arr[0];
		}
		Integer cIndex = random.nextInt(arr.length - 1);
		//如果是这条信息的第一个元素，说明是省份，则往后推一位
		if (cIndex == 0) {
			cIndex = cIndex + 1;
		}
		return arr[0] + arr[cIndex];
	}
	
	public static void initList() {
		list = init();
	}

	/**
	 * 通过文件，初始化缓存信息
	 * @return
	 */
	private static List<String[]> init() {
		//项目在tomcat里的布置路径
		String webPath = PathKit.getWebRootPath();
		//文件路径
		String addressTxt = webPath + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "address.txt";
		File file = new File(addressTxt);
		BufferedReader reader = null;
		List<String[]> list = new ArrayList<String[]>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = reader.readLine()) != null) {
				//获取每一行的数据
				str = str.replace("\"", "").trim();
				str = str.substring(1, str.length() - 1);
				String[] arr = str.split(",");
				list.add(arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
