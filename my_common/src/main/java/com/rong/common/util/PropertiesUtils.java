package com.rong.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.rong.common.bean.MyConst;

/**
 * properties文件读取
 * @author rongwq
 *	
 */
public class PropertiesUtils {
	
	private static Properties conf = null;//Properties Object
	public static String filePath = "/conf.properties";
	
	public static void load(String fileName){
		conf = new Properties();//Create a Properties object
        InputStream in = Gutil.classLoader().getResourceAsStream(fileName);
        if(in == null){
        	in = PropertiesUtils.class.getResourceAsStream(fileName);
        }
        try {
			conf.load(in);//Load the file Properties to flow to the object
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key,String defaultVal){
		try {
			if(conf==null || conf.isEmpty()){
				load(filePath);
			}
			return conf.getProperty(key);//Through the key specific get attribute value
		} catch (Exception e) {
			return defaultVal;
		}
	}
	
	public static void loadFile(String path){
		try {
			conf = new Properties();//Create a Properties object
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			conf.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtils.get("RUNNING_MODE",null));
	}
	
	public static boolean LoadRunConfigProperty() {
		int mode = Integer.parseInt(PropertiesUtils.get("RUNNING_MODE","1"));
		switch (mode) {
		case MyConst.RUNNING_MODE_DEV_SERVER:
			PropertiesUtils.load("/config_dev.txt");
			break;

		case MyConst.RUNNING_MODE_TEST_SERVER:
			PropertiesUtils.load("/config_test.txt");
			break;

		case MyConst.RUNNING_MODE_ONLINE_SERVER:
			PropertiesUtils.load("/config_online.txt");
			break;

		}
		return true;
	}
}
