package com.rong.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.exception.g.IllegalFileException4View;
import com.rong.common.exception.g.UnexpectedException4View;
import com.rong.common.util.GFtpUtil.FtpFile;

public class FileUtil {
	
	/**
	 * 测试用---不需要拼接字符串  读文件返回字符串
	 * @param filePath
	 * @return
	 */
	public static String xmlToString(String filePath) {
		String str = "";

		File file = new File(filePath);
		try {

			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));

			int size = in.available();

			byte[] buffer = new byte[size];

			in.read(buffer);

			in.close();

			str = new String(buffer, "UTF-8");

		} catch (IOException e) {
			return null;
		}

		return str;
	}
	
	public static void cleanDir(File file){
		if(!file.exists()){
			return ;
		}
		File files[] = file.listFiles();
		for(File f : files){
			if(f.isFile()){
				f.delete();
			}else if(f.isDirectory()){
				cleanDir(f);
				f.delete();
			}
		}
	}
	
	/**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static void readTxtFile(String filePath){
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt.substring(lineTxt.indexOf("{")));
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
    
    /**
     * 根据url来删除图片
     * @param url 
     * @return
     */
    public static boolean deleteFileByUrl(String url) {
    	if (StringUtils.isNullOrEmpty(url)) {
    		return true;
    	}
    	String webPath = PathKit.getWebRootPath();
    	String webRootPath = webPath.substring(0, webPath.lastIndexOf(File.separator));
    	String fileName = url.substring(url.lastIndexOf("/") + 1);
    	String upload = MyConst.upload.substring(MyConst.upload.indexOf("/") + 1, MyConst.upload.lastIndexOf("/"));
    	File file = new File(webRootPath + File.separator + upload + File.separator + fileName);
    	if (file.exists()) {
    		return file.delete();
    	}
    	return true;
    }
    
    //img标签的正则表达式
    public static final Pattern pattern = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
    //src的正则表达式
    public static final Pattern srcPattern = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
    /**
     * 根据对比，删除ueditor中无用的图片
     * @param oldTxt 旧文本，html格式
     * @param newTxt 新文本，html格式
     */
    public static void deleteFromUeditor(String oldTxt, String newTxt) {
    	if (oldTxt == null) {
    		return;
    	}
    	Matcher oldMatcher = pattern.matcher(oldTxt);
    	Matcher newMatcher = pattern.matcher(newTxt);
    	List<String> oldImgs = new ArrayList<String>();
    	List<String> newImgs = new ArrayList<String>();
    	//1、对旧文本进行匹配，检查是否有图片
    	while (oldMatcher.find()) {
    		Matcher srcMatcher = srcPattern.matcher(oldMatcher.group());
    		if (srcMatcher.find()) {
    			oldImgs.add(srcMatcher.group());
    		}
    	}
    	//2、如果原先没有图片，则不用执行删除工作
    	if (oldImgs.size() == 0) {
    		return;
    	}
    	//3、匹配新文本，检查是否有图片
    	while (newMatcher.find()) {
    		newImgs.add(newMatcher.group());
    		Matcher srcMatcher = srcPattern.matcher(newMatcher.group());
    		if (srcMatcher.find()) {
    			newImgs.add(srcMatcher.group());
    		}
    	}
    	//4、删除无用的图片
    	for (String oldImg : oldImgs) {
    		if (newImgs.size() != 0) {
    			for (String newImg : newImgs) {
    				//如果内容相同，则说明是同一张图片，不能删除，跳过此次循环
    				if (newImg.equals(oldImg)) {
    					break;
    				}
    			}
    			deleteImg(oldImg);
    		} else {
    			deleteImg(oldImg);
    		}
    	}
    }
    /**
     * 根据图片的src内容，删除图片
     * @param src
     */
    private static void deleteImg(String src) {
    	String webPath = PathKit.getWebRootPath();
    	webPath = webPath.substring(0, webPath.lastIndexOf(File.separator));
    	String url = src.substring(src.indexOf("\"") + 1, src.lastIndexOf("\""));
    	String imgPath = url.replace("/", File.separator);
    	File img = new File(webPath + imgPath);
    	if (img.exists()) {
    		img.delete();
    	}
    }
    
    public static void match(String str) {
    	Pattern pattern = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
    	Matcher matcher = pattern.matcher(str);
    	while (matcher.find()) {
    		String img = matcher.group();
    		System.out.println(img);
    		Pattern p = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
    		Matcher m = p.matcher(img);
    		while (m.find()) {
    			System.out.println(m.group());
    		}
    	}
    }
    
    /**
     * 上传图片公共方法
     * @param uploadFile 上传的图片
     * @param prefix 需要自定义的前缀名
     * @return 图片的访问路径
     * @throws Exception 压缩图片出错时或者上传到ftp服务器失败时
     */
    public static String upload(UploadFile uploadFile, String prefix) throws Exception {
    	File file = uploadFile.getFile();
    	String fileName = uploadFile.getFileName();
    	String rename = System.currentTimeMillis() + "" + (new Random().nextInt(89999999) + 10000000) ;
    	String pathNameSuffix = ".png";
    	if (fileName.indexOf(".") != -1) {
    		pathNameSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
    	if (prefix != null) {
    		rename = prefix + rename + pathNameSuffix;
    	} else {    		
    		rename = rename + pathNameSuffix;
    	}
    	if (file.length() > 100 * 1024) {
    		ImageResizer.resize(file.getAbsolutePath(), file.getAbsolutePath(), MyConst.thum_width,0);
    	}
    	boolean isSuccess = FtpUtil.uploadFiles(rename, file, null);
    	file.delete();
    	if (!isSuccess) {
    		throw new CommonException(MyErrorCodeConfig.REQUEST_FAIL, "图片上传到ftp服务器失败");
    	}
    	String path = MyConst.imgUrlHead + MyConst.upload + rename;
    	return path;
    }
    
    /**
     * 删除单张图片
     * @param fileName 图片的url名称
     */
    public static void delete(String fileName) {
    	if (fileName.lastIndexOf("/") != -1) {			
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		}
    	FtpUtil.deleteFile(fileName);
    }
    
    /**
     * 删除多张图片
     * @param arrayString 图片url名称数组
     */
    public static void deleteByArrayString(String arrayString) {
    	if (!StringUtils.isNullOrEmpty(arrayString)) {
    		String[] imageArray = arrayString.substring(1, arrayString.length() - 1).split(", ");
    		for (String image : imageArray) {
    			if (!"null".equals(image)) {
    				delete(image);
    			}
    		}
    	}
    }
    
    /**
     * 删除多张图片
     * @param arrayString 图片url名称数组
     */
    public static void deleteMore(String arrayString) {
    	if (!StringUtils.isNullOrEmpty(arrayString)) {
    		String[] imageArray = arrayString.substring(1, arrayString.length()).split(",");
    		for (String image : imageArray) {
    			delete(image.trim());
    		}
    	}
    }

	/**
	 * 批量保存图片到FTp    并返回map集合  图片url
	 * 调用方式：		Map<String, String> imageUrlMap = saveToFtpServer(getFiles());
	 * @param uploadFileList
	 * @return	 问题代码，不用
	 */
	public static Map<String, String> saveToFtpServer2(List<UploadFile> uploadFileList) {
		return saveToFtpServer2(uploadFileList.toArray(new UploadFile[uploadFileList.size()]));
	}

	
	// 问题代码，不用
	public static Map<String, String> saveToFtpServer2(UploadFile... uploadFileArray) {
		Map<String, String> urlmap = new HashMap<String, String>();
		List<FtpFile> fileList = new ArrayList<FtpFile>();
//		int i =0;
		for (UploadFile uploadFile : uploadFileArray) {
			File file = uploadFile.getFile();
//			long fileSize = file.length();
			try {
				if (!uploadFile.getContentType().startsWith("image/")) {
					file.delete();
					throw new IllegalFileException4View("该文件类型不是接受的图片类型");
				}
//				if (fileSize > 100 * 1024) {
//					String filePath = uploadFile.getUploadPath() + File.separator + uploadFile.getFileName();
//					ImageResizer.resizep(filePath, filePath, 0.2);
//				}
				String suffix = file.getName().substring(file.getName().lastIndexOf("."));
				String fileNameOnFtpServer = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
				fileList.add(new FtpFile(file, fileNameOnFtpServer));
				//图片路径保存(key可以自定义，若需要关联其他表)
				urlmap.put("i+1",MyConst.imgUrlHead + MyConst.ftp_uploads + fileNameOnFtpServer);
			} catch (Exception e) {
				throw new UnexpectedException4View("保存图片失败", e);
			}
		}
		GFtpUtil.uploadFiles(MyConst.ftp_uploads, fileList);
		for (FtpFile ftpFile : fileList) {
			ftpFile.getFile().delete();
		}
		return urlmap;
	}

	/**
	 * 多图上传 
	 */
	public static List<String> saveToFtpServer(List<UploadFile> uploadFileList) {
		return saveToFtpServer(uploadFileList.toArray(new UploadFile[uploadFileList.size()]));
	}
	/**
	 * 多图上传 
	 */
	public static List<String> saveToFtpServer(UploadFile... uploadFileArray) {
		List<String> list = new ArrayList<String>(); 
		List<FtpFile> fileList = new ArrayList<FtpFile>();
		for (UploadFile uploadFile : uploadFileArray) {
			File file = uploadFile.getFile();
			try {
				String suffix = file.getName().substring(file.getName().lastIndexOf("."));
				String fileNameOnFtpServer = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
				fileList.add(new FtpFile(file, fileNameOnFtpServer));
				//图片路径保存(key可以自定义，若需要关联其他表)
				list.add(MyConst.imgUrlHead + MyConst.ftp_uploads + fileNameOnFtpServer);
			} catch (Exception e) {
				throw new UnexpectedException4View("保存图片失败", e);
			}
		}
		GFtpUtil.uploadFiles(MyConst.ftp_uploads, fileList);
		for (FtpFile ftpFile : fileList) {
			ftpFile.getFile().delete();
		}
		return list;
	}
	
	
	
    public static void main(String[] args) {
//    	readTxtFile("C:\\Users\\G-emall\\Downloads\\2016-06-13-15");
    	String old = "<p><img title=\"xxx.jpg\" src=\"yyy.jpg\"/><p><a><img title=\"xxx.jpg\"/></a>";
    	match(old);
//    	String url = "/apart_web/ueditor/jsp/upload/image/20160909/1473404865202063027.jpg";
//    	System.out.println(url.replace("/", File.separator));
	}


}
