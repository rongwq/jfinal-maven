package com.rong.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.rong.common.bean.MyConst;

public class FtpUtil {

	private static final Logger logger = Logger.getLogger(FtpUtil.class);

   /**
    * 上传文件（可供Action/Controller层使用）
    * @param ftp_host 
    * @param ftp_port
    * @param ftp_username
    * @param ftp_pwd
    * @param ftp_uploads
    * @param fileName
    * @param inputStream
    * @return
    */
	 public static boolean uploadFile( String ftp_host,Integer ftp_port, String ftp_username, String ftp_pwd, String ftp_uploads, String fileName, InputStream inputStream){
		 boolean flag = false;
		     FTPClient ftpClient = new FTPClient();
		     ftpClient.setControlEncoding("UTF-8");
		 try {
		     //连接FTP服务器
		      ftpClient.connect(ftp_host,ftp_port);
		     //登录FTP服务器
		      ftpClient.login(ftp_username,ftp_pwd);
		      //是否成功登录FTP服务器
		      int replyCode = ftpClient.getReplyCode();
			  if(!FTPReply.isPositiveCompletion(replyCode)){
				  logger.info("上传失败，未连接到FTP");
			      return flag;
			   }		
		      ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		      ftpClient.makeDirectory(ftp_uploads);
		      ftpClient.changeWorkingDirectory(ftp_uploads);
		      ftpClient.enterLocalPassiveMode();
		      ftpClient.setControlEncoding("UTF-8");
		      boolean result = ftpClient.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"), inputStream);
		      logger.info(fileName+",上传结果:"+result);
		      inputStream.close();
		      ftpClient.logout();
		      flag = true;
		 } catch (Exception e) {
			 e.printStackTrace();
			 logger.info("上传失败"+e);
		 } finally{
			 if(ftpClient.isConnected()){
				 try {
					 ftpClient.disconnect();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
	 	return flag;
	 }
	
	
	 
	/**
	 * 上传文件
	 * @param fileName 文件名称
	 * @param file 文件
	 * @param type 文件类型
	 * @return
	 */
	  public static boolean uploadFiles(String fileName,File file, String type){
		  boolean flag = false;
		  try {
			  InputStream inputStream = new FileInputStream(file);
			  String path = "";
			  if ("file".equals(type)) {
				  path = MyConst.ftp_files;
			  } else if ("ueditor".equals(type)) {
				  path = MyConst.ftp_ueditor;
			  } else {
				  path = MyConst.ftp_uploads;
			  }
			  flag = uploadFile(MyConst.ftp_host,MyConst.ftp_port, MyConst.ftp_username, MyConst.ftp_pwd, path, fileName, inputStream); 
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  return flag;
	  }
	  

	/**
	 * 删除文件
	 * @param filename
	 * @return
	 */
	  public static boolean deleteFile(String filename){
		boolean flag = false;
		  FTPClient ftpClient = new FTPClient();
		  try {
			  //连接FTP服务器
			  ftpClient.connect(MyConst.ftp_host, MyConst.ftp_port);
			  //登录FTP服务器
			  ftpClient.login(MyConst.ftp_username, MyConst.ftp_pwd);
			  //验证FTP服务器是否登录成功
			  int replyCode = ftpClient.getReplyCode();
			  if(!FTPReply.isPositiveCompletion(replyCode)){
				  return flag;
		       }
			  //切换FTP目录
			  ftpClient.changeWorkingDirectory(MyConst.ftp_uploads);
			  ftpClient.dele(filename);
			  ftpClient.logout();
			  flag = true;
			  logger.info("删除图片成功");
		  } catch (Exception e) {
			  e.printStackTrace();
			  logger.info("删除图片失败");
		  } finally{
			  if(ftpClient.isConnected()){
				  try {
					  ftpClient.logout();
				  } catch (IOException e) {
		   
				  }
			  }
		  }
		  	return flag;
	}
	  
	  /**
	  * 下载文件
	  * @param hostname FTP服务器地址
	  * @param port FTP服务器端口号
	  * @param username FTP登录帐号
	  * @param password FTP登录密码
	  * @param pathname FTP服务器文件目录
	  * @param filename 文件名称
	  * @param localpath 下载后的文件路径
	  * @return
	  */
	  public static boolean downloadFile(String filename, String localpath){
		  boolean flag = false;
		  FTPClient ftpClient = new FTPClient();
		  try {
			  //连接FTP服务器
			//  ftpClient.connect(MyConst.imgUrlHead);
			  ftpClient.connect(MyConst.ftp_host, MyConst.ftp_port);
			  //登录FTP服务器
			  ftpClient.login(MyConst.ftp_username, MyConst.ftp_pwd);
			  //验证FTP服务器是否登录成功
			  int replyCode = ftpClient.getReplyCode();
			  if(!FTPReply.isPositiveCompletion(replyCode)){
				  return flag;
			  }
			  //切换FTP目录
			  ftpClient.changeWorkingDirectory(MyConst.ftp_uploads);
			  FTPFile[] ftpFiles = ftpClient.listFiles();
			  for(FTPFile file : ftpFiles){
				  if(filename.equalsIgnoreCase(file.getName())){
					  File localFile = new File(localpath + "/" + file.getName());
					  OutputStream os = new FileOutputStream(localFile);
					  ftpClient.retrieveFile(file.getName(), os);
					  os.close();
				  }
		  }
			  ftpClient.logout();
			  flag = true;
		  } catch (Exception e) {
			  e.printStackTrace();
		  } finally{
			  if(ftpClient.isConnected()){
				  try {
					  ftpClient.logout();
				  } catch (IOException e) {
		   
				  }
			  }
		  }
		  	return flag;
	  }
}
