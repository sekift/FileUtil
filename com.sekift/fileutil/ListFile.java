package com.sekift.fileutil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取文件夹下文件列表的类
 * 
 * @author:sekift
 * @time:2014-7-15 上午11:48:01
 * @version:
 */
public class ListFile {

	private static final Logger logger = LoggerFactory.getLogger(ListFile.class);

	/**
	 * 定义后缀类
	 * 
	 * @author:sekift
	 * @time:2014-7-15 下午02:06:17
	 * @version:
	 */
	static class MyFilenameFilter implements FilenameFilter {
		private String suffix = "";

		public MyFilenameFilter(String surfix) {
			this.suffix = surfix;
		}

		public boolean accept(File dir, String name) {
			if (new File(dir, name).isFile()) {
				return name.endsWith(suffix);
			} else {
				return true;
			}
		}
	}

	/**
	 * 获取文件夹下全部文件
	 * 
	 * @param dirName
	 * @return
	 */
	public static List<String> listAllFiles(String dirName) {
		List<String> list = new ArrayList<String>();
		if (!dirName.endsWith(File.separator)) {
			dirName = dirName + File.separator;
		}
		File dirFile = new File(dirName);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			list.add("List 失败找不到目录：" + dirName);
			logger.error("[sekiftutil]获取文件夹下文件列表的类--获取文件夹下全部文件 出错"+"\r\n List 失败找不到目录：" + dirName);
			return list;
		}
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				list.add(files[i].getAbsolutePath());
			} else if (files[i].isDirectory()) {
				list.addAll(ListFile.listAllFiles(files[i].getAbsolutePath()));
			}
		}
		return list;
	}

	/**
	 * 获取文件夹下文件
	 * 
	 * @param dirName
	 * @param deep
	 *            -- 表示深度，0代表当前目录下的文件，1表示1层，以此类推
	 * @return
	 */
	public static List<String> listFiles(String dirName, int deep) {
		List<String> list = new ArrayList<String>();
		if (!dirName.endsWith(File.separator)) {
			dirName = dirName + File.separator;
		}
		File dirFile = new File(dirName);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			list.add("List 失败找不到目录：" + dirName);
			logger.error("[sekiftutil]获取文件夹下文件列表的类--获取文件夹下文件 出错"+"\r\n List 失败找不到目录：" + dirName);
			return list;
		}
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile() || deep == 0) {
				if (files[i].isFile()) {
					list.add(files[i].getAbsolutePath());
				}
			} else if (files[i].isDirectory()) {
				list.addAll(ListFile.listFiles(files[i].getAbsolutePath(), deep - 1));
			}
		}
		return list;
	}

	
	public static void listFilesByFilenameFilter(FilenameFilter filter, String dirName) {
		if (!dirName.endsWith(File.separator)) {
			dirName = dirName + File.separator;
		}
		File dirFile = new File(dirName);
		if (!dirFile.exists() || (!dirFile.isDirectory())) {
			System.out.println("List失败找不到目录：" + dirName);
			return;
		}
		File[] files = dirFile.listFiles(filter);
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				System.out.println(files[i].getAbsolutePath() + " \t是文件");

			} else if (files[i].isDirectory()) {
				System.out.println(files[i].getAbsolutePath() + " \t是目录");
				ListFile.listFilesByFilenameFilter(filter, files[i].getAbsolutePath());
			}
		}
	}

}
