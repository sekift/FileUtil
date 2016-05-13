package com.sekift.fileutil;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建文件、文件夹和临时文件的类
 * 
 * @author:sekift
 * @time:2014-7-14 下午04:50:02
 */
public class CreateFile {

	private static final Logger logger = LoggerFactory.getLogger(CreateFile.class);

	/**
	 * 创建单个文件
	 * 
	 * @param destFileName
	 * @return
	 */
	public static boolean createFile(String destFileName) {
		java.io.File file = new File(destFileName);
		if (file.exists()) {
			logger.error("创建单个文件" + destFileName + "失败，目标文件已存在");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			logger.error("创建单个文件" + destFileName + "失败，目标文件不能为目录");
			return false;
		}
		if (!file.getParentFile().exists()) {
			logger.error("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				logger.error("创建目标文件所在的目录失败！");
				return false;
			}
		}
		try {
			if (file.createNewFile()) {
				logger.error("创建单个文件" + destFileName + "成功");
				return true;
			} else {
				logger.error("创建单个文件" + destFileName + "失败");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("[sekiftutil]创建文件、文件夹和临时文件的类--创建单个文件 错误：", e);
			return false;
		}

	}

	/**
	 * 创建文件夹
	 * 
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			logger.error("创建单个目录" + destDirName + "失败，目标文件已存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {
			logger.error("创建目录" + destDirName + "成功");
			return true;
		} else {
			logger.error("创建目录" + destDirName + "失败");
			return false;
		}
	}

	/**
	 * 创建临时文件
	 * 
	 * @param prefix
	 * @param suffix
	 * @param dirName
	 * @return
	 */
	public static String createTempFile(String prefix, String suffix, String dirName) {
		File tempFile = null;
		if (dirName == null) {
			try {
				tempFile = File.createTempFile(prefix, suffix);
				return tempFile.getCanonicalPath();
			} catch (IOException e) {
				return "创建临时文件失败！" + e.getMessage();
			}
		} else {
			File dir = new File(dirName);
			if (!dir.exists()) {
				if (CreateFile.createDir(dirName)) {
					return "创建临时文件失败！";
				}
			}
			try {
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("[sekiftutil]创建文件、文件夹和临时文件的类--创建临时文件 错误：", e);
				return null;
			}
		}
	}
}
