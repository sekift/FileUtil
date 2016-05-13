package com.sekift.fileutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sekift.CloseUtil;

/**
 * 压缩与解压的类
 * 
 * @author:sekift
 * @time:2014-7-15 上午10:08:17
 * @version:
 */
public class CompressFile {

	private static final Logger logger = LoggerFactory.getLogger(CompressFile.class);

	/**
	 * 压缩文件
	 * 
	 * @param baseDirName
	 * @param fileName
	 * @param targetFileName
	 * @return String
	 */
	public static String zipFile(String baseDirName, String fileName, String targetFileName) {
		ZipOutputStream out = null;
		if (baseDirName == null) {
			return "解压失败，根目录不存在：" + baseDirName;
		}
		java.io.File baseDir = new File(baseDirName);
		if (!baseDir.exists() || (!baseDir.isDirectory())) {
			return "压缩失败，根目录不存在：" + baseDirName;
		}
		String baseDirPath = baseDir.getAbsolutePath();
		File targetFile = new File(targetFileName);
		try {
			out = new ZipOutputStream(new FileOutputStream(targetFile));
			if (fileName.equals("*")) {
				CompressFile.dirToZip(baseDirPath, baseDir, out);
			} else {
				File file = new File(baseDir, fileName);
				if (file.isFile()) {
					CompressFile.fileToZip(baseDirPath, file, out);
				} else {
					CompressFile.dirToZip(baseDirPath, file, out);
				}
			}
			return "压缩文件成功，目标文件名：" + targetFileName;
		} catch (IOException e) {
			logger.error("[sekiftutil]压缩与解压的类--压缩文件 出错：", e);
			return "压缩文件夹 出错" + e;
		} finally {
			CloseUtil.closeSilently(out);
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param zipFileName
	 * @param targetBaseDirName
	 * @return
	 */
	public static String upZipFile(String zipFileName, String targetBaseDirName) {
		FileOutputStream fos = null;
		InputStream is = null;
		if (!targetBaseDirName.endsWith(File.separator)) {
			targetBaseDirName += File.separator;
		}
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			ZipEntry entry = null;
			String entryName = null;
			String targetFileName = null;
			byte[] buffer = new byte[4096];
			int bytes_read;
			Enumeration<?> entrys = zipFile.entries();
			while (entrys.hasMoreElements()) {
				entry = (ZipEntry) entrys.nextElement();
				entryName = entry.getName();
				targetFileName = targetBaseDirName + entryName;
				if (entry.isDirectory()) {
					new File(targetFileName).mkdirs();
					continue;
				} else {
					new File(targetFileName).getParentFile().mkdirs();
				}
				File targetFile = new File(targetFileName);
				fos = new FileOutputStream(targetFile);
				is = (InputStream) zipFile.getInputStream(entry);
				while ((bytes_read = is.read(buffer)) != -1) {
					fos.write(buffer, 0, bytes_read);
				}
			}
			return "解压文件成功！";
		} catch (IOException err) {
			logger.error("[sekiftutil]压缩与解压的类--解压文件 出错：", err);
			return "解压文件失败：" + err;
		} finally {
			CloseUtil.closeSilently(is);
			CloseUtil.closeSilently(fos);
		}
	}

	/**
	 * 压缩文件夹
	 * 
	 * @param baseDirPath
	 * @param dir
	 * @param out
	 */
	private static String dirToZip(String baseDirPath, File dir, ZipOutputStream out) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files.length == 0) {
				ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));
				try {
					out.putNextEntry(entry);
					out.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("[sekiftutil]压缩与解压的类--解压文件夹 出错：", e);
					return "压缩文件夹 出错：" + e;
				}
				return null;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					CompressFile.fileToZip(baseDirPath, files[i], out);
				} else {
					CompressFile.dirToZip(baseDirPath, files[i], out);
				}
			}
		}
		return null;
	}

	/**
	 * 压缩文件
	 * 
	 * @param baseDirPath
	 * @param file
	 * @param out
	 */
	private static String fileToZip(String baseDirPath, File file, ZipOutputStream out) {
		FileInputStream in = null;
		ZipEntry entry = null;

		byte[] buffer = new byte[4096];
		int bytes_read;
		if (file.isFile()) {
			try {
				in = new FileInputStream(file);
				entry = new ZipEntry(getEntryName(baseDirPath, file));
				out.putNextEntry(entry);
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}
				out.closeEntry();
				return file.getAbsolutePath() + "被添加到ZIP文件中";
			} catch (IOException e) {
				logger.error("[sekiftutil]压缩与解压的类--压缩文件 出错：", e);
				return "压缩文件出错" + e;
			} finally {
				CloseUtil.closeSilently(in);
			}
		}
		return null;
	}

	private static String getEntryName(String baseDirPath, File file) {
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath += File.separator;
		}
		String filePath = file.getAbsolutePath();
		if (file.isDirectory()) {
			filePath += "/";
		}
		int index = filePath.indexOf(baseDirPath);
		return filePath.substring(index + baseDirPath.length());
	}

}
