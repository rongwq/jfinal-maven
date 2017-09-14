package com.rong.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.rong.common.bean.MyConst;

public class GFtpUtil {

	private static final Logger logger = Logger.getLogger(GFtpUtil.class);

	public static void uploadFile(String ftp_host, Integer ftp_port, String ftp_username, String ftp_pwd,
			String ftp_uploads, List<FtpFile> fileList) {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		try {
			ftpClient.connect(ftp_host, ftp_port);
			ftpClient.login(ftp_username, ftp_pwd);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("连接FTP服务器失败");
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.makeDirectory(ftp_uploads);
			ftpClient.changeWorkingDirectory(ftp_uploads);
			ftpClient.enterLocalPassiveMode();
			for (FtpFile ftpFile : fileList) {
				boolean result = ftpClient.storeFile(ftpFile.getFileNameOnFtpServer(), new FileInputStream(ftpFile.getFile()));
				logger.info(ftpFile.getFile().getName() + (result ? " 上传成功" : " 上传失败"));
			}
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void uploadFiles(String dir, List<FtpFile> fileList) {
		try {
			uploadFile(MyConst.ftp_host, MyConst.ftp_port, MyConst.ftp_username, MyConst.ftp_pwd, dir, fileList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param filename
	 *            要删除的文件名称
	 * @return
	 */
	public static boolean deleteFile(String filename) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		try {
			// 连接FTP服务器
			ftpClient.connect(MyConst.ftp_host, MyConst.ftp_port);
			// 登录FTP服务器
			ftpClient.login(MyConst.ftp_username, MyConst.ftp_pwd);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(MyConst.ftp_uploads);
			ftpClient.dele(filename);
			ftpClient.logout();
			flag = true;
			logger.info("删除图片成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除图片失败");
		} finally {
			if (ftpClient.isConnected()) {
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
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器文件目录
	 * @param filename
	 *            文件名称
	 * @param localpath
	 *            下载后的文件路径
	 * @return
	 */
	public static boolean downloadFile(String filename, String localpath) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(MyConst.ftp_host, MyConst.ftp_port);
			ftpClient.login(MyConst.ftp_username, MyConst.ftp_pwd);
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			ftpClient.changeWorkingDirectory(MyConst.ftp_uploads);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (filename.equalsIgnoreCase(file.getName())) {
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
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return flag;
	}

	public static class FtpFile {
		File file;
		String fileNameOnFtpServer;

		public FtpFile(File file, String fileNameOnFtpServer) {
			super();
			this.file = file;
			this.fileNameOnFtpServer = fileNameOnFtpServer;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public String getFileNameOnFtpServer() {
			return fileNameOnFtpServer;
		}

		public void setFileNameOnFtpServer(String fileNameOnFtpServer) {
			this.fileNameOnFtpServer = fileNameOnFtpServer;
		}

	}
}
