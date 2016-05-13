package com.sekift.fileutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sekift.CloseUtil;

/**
 * 读取文件内容的类
 * 
 * @author:sekift
 * @time:2014-7-14 下午05:40:49
 * @version:1.0.0
 */

public class ReadFromFile {

	private static final Logger logger = LoggerFactory.getLogger(ReadFromFile.class);

	/**
	 * 以字节为单位读取文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFileByBytes(String fileName) {
		String str = "";
		InputStream inputStream = null;
		try {
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			inputStream = new FileInputStream(fileName);
			// ReadFromFile.showAvailableBytes(inputStream);
			while ((byteread = inputStream.read(tempbytes)) != -1) {
				str += new String(tempbytes, 0, byteread, "gb2312");
			}

			return str;
		} catch (Exception e) {
			logger.error("[sekiftutil]以字节为单位读取文件内容 出错:", e);
			return "以字节为单位读取文件内容 出错:" + e;
		} finally {
			CloseUtil.closeSilently(inputStream);
		}
	}

	/**
	 * 以字符为单位读取文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFileByChars(String fileName) {
		Reader reader = null;
		String str = "";
		try {
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			while ((charread = reader.read(tempchars)) != -1) {
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
					str += new String(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							str += String.valueOf(tempchars[i]);
						}
					}
				}
			}
			return str;
		} catch (Exception e) {
			logger.error("[sekiftutil]以字符为单位读取文件内容 出错:", e);
			return "以字符为单位读取文件内容 出错:" + e;
		} finally {
			CloseUtil.closeSilently(reader);
		}

	}

	/**
	 * 以行为单位读取文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader bufferedReader = null;
		String str = "";
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
			bufferedReader = new BufferedReader(isr);
			String tempString = null;
			int line = 1;
			while ((tempString = bufferedReader.readLine()) != null) {
				str += tempString + "\r\n";
				line++;
			}

			return str;
		} catch (IOException e) {
			logger.error("[sekiftutil]以行为单位读取文件内容 出错:", e);
			return "[sekiftutil]以行为单位读取文件内容 出错:" + e;
		} finally {
			CloseUtil.closeSilently(bufferedReader);
		}
	}

	/**
	 * 随机读取文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFileByRandomAcccess(String fileName) {
		RandomAccessFile randomFile = null;
		String str = "";
		try {
			randomFile = new RandomAccessFile(fileName, "r");
			long fileLength = randomFile.length();
			int beginIndex = (fileLength > 4) ? 4 : 0;
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			while ((byteread = randomFile.read(bytes)) != -1) {
				str += new String(bytes, 0, byteread, "gb2312");

			}
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("[sekiftutil]随机读取文件内容 出错:", e);
			return "[sekiftutil]随机读取文件内容 出错:" + e;
		} finally {
			CloseUtil.closeSilently(randomFile);
		}
	}

}
