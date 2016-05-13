package com.sekift.fileutil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取properties文件
 * 
 * @author:Administrator
 * @time:2015-5-11 下午03:53:58
 * @version:
 */
public class PropertiesFileRead {

	/**
	 * 使用getResourceAsStream方法读取properties文件
	 * 如果是在resources则不用再写resources名。
	 */
	public static void readPropFileByGetResourceAsStream() {
		InputStream in1 = PropertiesFileRead.class.getClassLoader().getResourceAsStream("log4j.properties");
		Properties p = new Properties();
		try {
			p.load(in1);

			System.out.println(p.getProperty("log4j.appender.fileout.File"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用InputStream流读取properties文件
	 * 相对路径
	 */
	public static void readPropFileByInputStream() {
		InputStream in1 = null;
		try {
			in1 = new BufferedInputStream(new FileInputStream("resources/log4j.properties"));
			 Properties p = new Properties();
			 p.load(in1);
			 System.out.println(p.getProperty("log4j.appender.fileout.File"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {
		readPropFileByGetResourceAsStream();
		readPropFileByInputStream();
	}
}
