package com.sekift.fileutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sekift.CloseUtil;

/**
 * 写入内容到文档的类
 * 
 * @author:sekift
 * @time:2014-7-15 上午11:03:46
 * @version:
 */
public class WriteToFile {

	private static final Logger logger = LoggerFactory.getLogger(WriteToFile.class);

	/**
	 * 利用Byte写入文件
	 * 或者直接使用commom-io的FileUtils.writeStringToFile(...);
	 * 
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static String writeFileByBytes(String fileName, String content) {
//		java.io.File file = new File(fileName);
//		FileOutputStream outputStream = null;
//		try {
//			outputStream = new FileOutputStream(file);
//			byte[] bytes = content.getBytes();
//			outputStream.write(bytes);
//			return returnResult(file.getAbsolutePath(), "成功");
//		} catch (IOException e) {
//			logger.error("[sekiftutil]利用Byte写入文件 出错：", e);
//			return returnResult(file.getAbsolutePath(), "失败") + e;
//		} finally {
//			CloseUtil.closeSilently(outputStream);
//		}
		File file=new File(fileName);
		try {
			FileUtils.writeStringToFile(file, content);
		} catch (IOException e) {
			e.printStackTrace();
			return "失败";
		}
		return "成功";
	}

	/**
	 * 利用Char写入文件
	 * 
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static String writeFileByChars(String fileName, String content) {
		File file = new File(fileName);
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write(content);
			return returnResult(file.getAbsolutePath(), "成功");
		} catch (IOException e) {
			logger.error("[sekiftutil]利用Char写入文件 出错：", e);
			return returnResult(file.getAbsolutePath(), "失败") + e;
		} finally {
			CloseUtil.closeSilently(writer);
		}
	}

	/**
	 * 利用Line写入文件
	 * 
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static String writeFileByLines(String fileName, String content) {
		File file = new File(fileName);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			writer.println(content);
			writer.flush();
			return returnResult(file.getAbsolutePath(), "成功");
		} catch (IOException e) {
			logger.error("[sekiftutil]利用Line写入文件 出错：", e);
			return returnResult(file.getAbsolutePath(), "失败") + e;
		} finally {
			CloseUtil.closeSilently(writer);
		}
	}

	private static String returnResult(String path, String result) {
		return "写文件" + path + result;
	}

}
