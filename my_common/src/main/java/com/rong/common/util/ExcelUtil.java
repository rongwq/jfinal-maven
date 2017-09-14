package com.rong.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.kit.PathKit;

public class ExcelUtil {
	/**
	 * 创建一个excel表格
	 * @param sheetName sheet页名
	 * @param colNames 每一列的列名数组
	 * @return
	 */
	public static HSSFWorkbook create(String sheetName, String[] colNames) {
		//创建一个webbook，对应一个Excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet页
		HSSFSheet sheet = wb.createSheet(sheetName);
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);  
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle(); 
		//创建一个居中格式 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheet.setDefaultColumnWidth(12);
		sheet.setColumnWidth(0, 36 * 256); 
		sheet.setColumnWidth(2, 15 * 256); 
		sheet.setColumnWidth(3, 20 * 256); 
		//定义表头每一列的列名
		HSSFCell cell = null;
		for (int i = 0; i < colNames.length; i ++) {
			cell = row.createCell(i);
			cell.setCellValue(colNames[i]);  
			cell.setCellStyle(style);
		}
		return wb;
	}
	
	/**
	 * 创建一个excel表格，作用于批次单下载的单元格
	 * @param sheetName sheet页名
	 * @param colNames 每一列的列名数组
	 * @return
	 */
	public static HSSFWorkbook createBatchExcel(String sheetName, String[] colNames){
		//创建一个webbook，对应一个Excel文档
				HSSFWorkbook wb = new HSSFWorkbook();
				//在webbook中添加一个sheet,对应Excel文件中的sheet页
				HSSFSheet sheet = wb.createSheet(sheetName);
				//在sheet中添加表头第0行
				HSSFRow row = sheet.createRow((int) 0);  
				//创建单元格，并设置值表头 设置表头居中  
				HSSFCellStyle style = wb.createCellStyle(); 
				//创建一个居中格式 
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				sheet.setColumnWidth(0, 10 * 256); 
				sheet.setColumnWidth(1, 12 * 256); 
				sheet.setColumnWidth(2, 18 * 256); 
				sheet.setColumnWidth(3, 30 * 256); 
				sheet.setColumnWidth(4, 15 * 256); 
				sheet.setColumnWidth(5, 70 * 256); 
				//定义表头每一列的列名
				HSSFCell cell = null;
				for (int i = 0; i < colNames.length; i ++) {
					cell = row.createCell(i);
					cell.setCellValue(colNames[i]);  
					cell.setCellStyle(style);
				}
				return wb;
	}
	
	/**
	 * 创建一个excel表格，作用于申请提单下载的单元格
	 * @param sheetName sheet页名
	 * @param colNames 每一列的列名数组
	 * @return
	 */
	public static HSSFWorkbook createWithdrawalsOrderExcel(String sheetName, String[] colNames){
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet页
		HSSFSheet sheet = wb.createSheet(sheetName);
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);  
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle(); 
		//创建一个居中格式 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheet.setColumnWidth(0, 11 * 256); 
		sheet.setColumnWidth(1, 11 * 256); 
		sheet.setColumnWidth(2, 12 * 256); 
		sheet.setColumnWidth(3, 18 * 256); 
		sheet.setColumnWidth(4, 18 * 256); 
		sheet.setColumnWidth(5, 12 * 256); 
		sheet.setColumnWidth(6, 12 * 256); 
		sheet.setColumnWidth(7, 12 * 256); 
		sheet.setColumnWidth(8, 15 * 256); 
		//定义表头每一列的列名
		HSSFCell cell = null;
		for (int i = 0; i < colNames.length; i ++) {
			cell = row.createCell(i);
			cell.setCellValue(colNames[i]);  
			cell.setCellStyle(style);
		}
		return wb;
	}
	
	/**
	 * 创建一个excel表格，作用于批次单中申请提单下载的单元格
	 * @param sheetName sheet页名
	 * @param colNames 每一列的列名数组
	 * @return
	 */
	public static HSSFWorkbook createBatchWithdrawalsOrderExcel(String sheetName, String[] colNames){
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet页
		HSSFSheet sheet = wb.createSheet(sheetName);
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);  
		//创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle(); 
		//创建一个居中格式 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheet.setColumnWidth(0, 11 * 256); 
		sheet.setColumnWidth(1, 11 * 256); 
		sheet.setColumnWidth(2, 11 * 256); 
		sheet.setColumnWidth(3, 12 * 256); 
		sheet.setColumnWidth(4, 18 * 256); 
		sheet.setColumnWidth(5, 18 * 256); 
		sheet.setColumnWidth(6, 12 * 256); 
		sheet.setColumnWidth(7, 12 * 256); 
		sheet.setColumnWidth(8, 12 * 256); 
		sheet.setColumnWidth(9, 15 * 256); 
		//定义表头每一列的列名
		HSSFCell cell = null;
		for (int i = 0; i < colNames.length; i ++) {
			cell = row.createCell(i);
			cell.setCellValue(colNames[i]);  
			cell.setCellStyle(style);
		}
		return wb;
	}
	
	/**
	 * 输出生成excel表格
	 * @param wb 创建的excel表格
	 * @param excelName excel表格名称
	 */
	public static void outputExcel(HSSFWorkbook wb, String excelName) {
		String webPath = PathKit.getWebRootPath();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(webPath + File.separator + excelName);
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
