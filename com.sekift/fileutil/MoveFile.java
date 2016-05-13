package com.sekift.fileutil;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 移动文件或目录
 * 
 * @author:sekift
 * @time:2014-7-15 下午04:45:34
 * @version:
 */
public class MoveFile {

	private static final Logger logger = LoggerFactory.getLogger(CopyFile.class);

	/**
	 * 移动文件，不覆盖
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 */
	public static boolean moveFile(String srcFileName, String destFileName) {
		return MoveFile.moveFile(srcFileName, destFileName, false);
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @param overlay
	 * @return
	 */
	public static boolean moveFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			logger.error("[sekiftuitl]移动文件失败：源文件" + srcFileName + "不存在");
			return false;
		} else if (!srcFile.isFile()) {
			logger.error("[sekiftuitl]移动文件失败" + srcFileName + "不是一个文件");
			return false;
		}
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			if (overlay) {
				if (!DeleteFile.delete(destFileName)) {
					logger.error("[sekiftuitl]移动文件失败：删除目标文件" + destFileName + "失败");
					return false;
				}
			} else {
				logger.error("[sekiftuitl]移动文件失败：目标文件" + destFileName + "已存在");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				if (!destFile.getParentFile().mkdirs()) {
					logger.error("[sekiftuitl]移动文件失败：创建目标文件所在目录失败");
					return false;
				}
			}
		}
		if (srcFile.renameTo(destFile)) {
			return true;
		} else {
			logger.error("[sekiftuitl]移动单个文件" + srcFileName + "至" + destFileName + "失败");
			return false;
		}
	}

	/**
	 * 移动目录，不覆盖
	 * 
	 * @param srcDirName
	 * @param destDirName
	 * @return
	 */
	public static boolean moveDirectory(String srcDirName, String destDirName) {
		return MoveFile.moveDirectory(srcDirName, destDirName, false);
	}

	/**
	 * 移动目录
	 * 
	 * @param srcDirName
	 * @param destDirName
	 * @param overlay
	 * @return
	 */
	public static boolean moveDirectory(String srcDirName, String destDirName, boolean overlay) {
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			logger.error("[sekiftuitl]移动目录失败：源目录" + srcDirName + "不存在");
			return false;
		} else if (!srcDir.isDirectory()) {
			logger.error("[sekiftuitl]移动目录失败：" + srcDirName + "不是一个目录");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		if (destDir.exists()) {
			if (overlay) {
				if (!DeleteFile.delete(destDirName)) {
					logger.error("[sekiftuitl]移动目录失败：删除目标目录" + destDirName + "失败");
					return false;
				}
			} else {
				logger.error("[sekiftuitl]移动目录失败：目标目标" + destDirName + "已存在");
				return false;
			}
		} else {
			if (!destDir.mkdirs()) {
				logger.error("[sekiftuitl]移动目录失败：创建目标目录失败");
				return false;
			}
		}
		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = MoveFile.moveFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag) {
					break;
				}
			} else if (files[i].isDirectory()) {
				flag = MoveFile.moveDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			return true;
		} else {
			logger.error("[sekiftuitl]移动目录" + srcDirName + "至" + destDirName + "失败");
			return false;
		}
	}

	public static void main(String[] args) {
		String srcFileName = "D:/temp/test.txt";
		String destFileName = "D:/temp/dierceng/test.txt";
		MoveFile.moveFile(srcFileName, destFileName, true);
//		String srcDir = "D:/temp";
//		String destDir = "D:/tempbak";
//		MoveFile.moveDirectory(srcDir, destDir, true);

	}

}
