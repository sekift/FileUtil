package com.sekift.fileutil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sekift.CloseUtil;

/**
 * 往文件中添加字段的类
 * 
 * @author:sekift
 * @time:2014-7-14 下午04:43:02
 * @vertion 1.0.0
 */
public class AppendToFile {
	private static final Logger logger = LoggerFactory.getLogger(AppendToFile.class);

	/**
	 * 用Access添加的方法
	 * 
	 * @param fileName
	 * @param content
	 * @return 
	 */
	public static String appendWithAccess(String fileName, String content) {
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(fileName, "rw");
			long fileLength = randomAccessFile.length();
			randomAccessFile.seek(fileLength);
			randomAccessFile.writeBytes(content);

			return "追加内容成功！";
		} catch (Exception e) {
			logger.error("[sekiftutil]用Access添加的方法:",e);
			return "追加内容失败！";
		} finally {
			CloseUtil.closeSilently(randomAccessFile);
		}
	}

	/**
	 * 用writer添加的方法
	 * 
	 * @param fileName
	 * @param content
	 * @return 
	 */
	public static String appendWithWriter(String fileName, String content) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName, true);
			writer.write(content);
			
			return "追加内容成功！";
		} catch (IOException e) {
			logger.error("[sekiftutil]用writer添加的方法:" + e);
			
			return "追加内容失败！";
		} finally {
			CloseUtil.closeSilently(writer);
		}
	}

}
