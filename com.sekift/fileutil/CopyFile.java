package com.sekift.fileutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sekift.CloseUtil;

/**
 * 复制文件或目录的类
 * 
 * @author:sekift
 * @time:2014-7-15 下午03:49:46
 * @version:
 */
public class CopyFile {

	private static final Logger logger = LoggerFactory.getLogger(CopyFile.class);

	/**
	 * 复制文件，不覆盖
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @return
	 */
	public static boolean copyFile(String srcFileName, String destFileName) {
		return CopyFile.copyFile(srcFileName, destFileName, false);
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @param overlay
	 * @return
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			logger.error("[sekiftuitl]复制文件失败：源文件" + srcFileName + "不存在");
			return false;
		} else if (!srcFile.isFile()) {
			logger.error("[sekiftuitl]复制文件失败：源文件" + srcFileName + "不是一个文件");
			return false;
		}
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			if (overlay) {
				if (!DeleteFile.delete(destFileName)) {
					logger.error("[sekiftuitl]复制文件失败：删除目标文件" + destFileName + "失败");
					return false;
				}
			} else {
				logger.error("[sekiftuitl]复制文件失败：目标文件" + destFileName + "已存在");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				if (!destFile.getParentFile().mkdirs()) {
					logger.error("[sekiftuitl]复制文件失败：创建目标文件所在目录失败");
					return false;
				}
			}
		}
		int byteread = 0;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (Exception e) {
			logger.error("[sekiftuitl]复制文件 出错：", e);
			return false;
		} finally {
			CloseUtil.closeSilently(out);
			CloseUtil.closeSilently(in);
		}
	}

	/**
	 * 复制目录，不覆盖
	 * 
	 * @param srcDirName
	 * @param destDirName
	 * @param overlay
	 * @return
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName) {
		return CopyFile.copyDirectory(srcDirName, destDirName, false);
	}

	/**
	 * 复制目录
	 * 
	 * @param srcDirName
	 * @param destDirName
	 * @param overlay
	 * @return
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			logger.error("[sekiftuitl]复制目录失败：源目录" + srcDirName + "不存在");
			return false;
		} else if (!srcDir.isDirectory()) {
			logger.error("[sekiftuitl]复制目录失败：源目录" + srcDirName + "不是一个目录");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		if (destDir.exists()) {
			if (overlay) {
				if (!DeleteFile.delete(destDirName)) {
					logger.error("[sekiftuitl]复制目录失败：删除目标目录" + destDirName + "失败");
					return false;
				}
			} else {
				logger.error("[sekiftuitl]复制目录失败：目标目录" + destDirName + "已存在");
				return false;
			}
		} else {
			if (!destDir.mkdirs()) {
				logger.error("[sekiftuitl]复制目录失败：创建目标目录失败");
				return false;
			}
		}
		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = CopyFile.copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName());
				if (!flag) {
					break;
				}
			}
			if (files[i].isDirectory()) {
				flag = CopyFile.copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			logger.error("[sekiftuitl]复制目录" + srcDirName + "至" + destDirName + "失败！");
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		String srcPath = "C:/temp/tempfile0.txt";
		String destPath = "C:/temp_bak/temp_bak.txt";
		CopyFile.copyFile(srcPath, destPath, true);
		CopyFile.copyFile(srcPath, destPath);
		String srcDir = "C:/temp";
		String destDir = "D:/temp";
		CopyFile.copyDirectory(srcDir, destDir, true);

	}

}
