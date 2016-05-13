package com.sekift.fileutil;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 删除文件或目录的类
 * 
 * @author:sekift
 * @time:2014-7-15 下午03:51:55
 * @version:
 */
public class DeleteFile {

	private static final Logger logger = LoggerFactory.getLogger(DeleteFile.class);

	/**
	 * 删除类
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			logger.error("[sekiftutil]删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {
				return DeleteFile.deleteFile(fileName);
			} else {
				return DeleteFile.deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				logger.error("[sekiftutil]删除单个文件" + fileName + "失败");
				return false;
			}
		} else {
			logger.error("[sekiftutil]删除单个文件" + fileName + "失败");
			return false;
		}
	}

	/**
	 * 删除目录
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDirectory(String dir) {
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists() || (!dirFile.isDirectory())) {
			logger.error("[sekiftutil]删除目录失败" + dir + "目录不存在");
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = DeleteFile.deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} else if (files[i].isDirectory()) {
				flag = DeleteFile.deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			logger.error("[sekiftutil]删除目录失败");
			return false;
		}
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		String file = "D:\\temp\\temp0\\temp1\\temp.txt";
		DeleteFile.deleteFile(file);
		String dir = "D:\\temp\\temp0\\temp1";
		DeleteFile.deleteDirectory(dir);
		dir = "D:\\temp\\temp0";
		DeleteFile.delete(dir);

	}

}
