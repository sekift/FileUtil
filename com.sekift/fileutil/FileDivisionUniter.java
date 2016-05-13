package com.sekift.fileutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.sekift.CloseUtil;

/**
 * 分割与合并文件
 * 
 * @author:sekift
 * @time:2014-7-15 下午04:29:50
 * @version:
 */
public class FileDivisionUniter {

	public static final String SUFFIX = ".pp";

	/**
	 * 分割文件
	 * 
	 * @param fileName
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String[] divide(String fileName, long size) {
		File inputFile = new File(fileName);
		isFileExists(inputFile);

		int number = getDivideNumber(inputFile, size);
		String[] outFileNames = new String[number];

		File parentFile = inputFile.getParentFile();
		File outFile = null;
		FileInputStream in = null;
		long inEndIndex = 0;
		int inBeginIndex = 0;

		try {
			in = new FileInputStream(inputFile);
			for (int outFileIndex = 0; outFileIndex < number; outFileIndex++) {
				outFile = new File(parentFile, inputFile.getName() + outFileIndex + SUFFIX);
				FileOutputStream out = new FileOutputStream(outFile);
				inEndIndex += size;
				inEndIndex = (inEndIndex > inputFile.length()) ? inputFile.length() : inEndIndex;
				for (; inBeginIndex < inEndIndex; inBeginIndex++) {
					out.write(in.read());
				}
				out.close();
				outFileNames[outFileIndex] = outFile.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtil.closeSilently(in);
		}
		return outFileNames;
	}

	/**
	 * 查看文件是否存在
	 * @param inputFile
	 */
	private static void isFileExists(File inputFile) {
		if (!inputFile.exists() || (!inputFile.isFile())) {
			try {
				throw new Exception("指定文件不存在");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取分割个数
	 * 
	 * @param inputFile
	 * @param size
	 * @return
	 */
	private static int getDivideNumber(File inputFile, long size) {
		if (size <= 0) {
			size = inputFile.length() / 2;
		}
		int number = (inputFile.length() % size != 0) ? (int) (inputFile.length() / size + 1) : (int) (inputFile
				.length() / size);
		return number;
	}

	/**
	 * 合并文件
	 * 
	 * @param fileNames
	 * @param TargetFileName
	 * @return
	 * @throws Exception
	 */
	public static String unite(String[] fileNames, String TargetFileName) {
		File inFile = null;
		File outFile = new File(TargetFileName);
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(outFile);
			for (int i = 0; i < fileNames.length; i++) {
				inFile = new File(fileNames[i]);
				FileInputStream in = new FileInputStream(inFile);
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtil.closeSilently(out);
		}
		return outFile.getAbsolutePath();
	}

	public static void main(String[] args) throws Exception {
		String fileName = "D:\\temp\\cust_search_keyword_seg.sql";
		long size = 10 * 1024 * 1024;
		String[] fileNames = FileDivisionUniter.divide(fileName, size);
		System.out.println("分割文件" + fileName + "结果：");
		for (int i = 0; i < fileNames.length; i++) {
			System.out.println(fileNames[i]);
		}
		// String newFileName = "D:\\tempbak\\newTemp.xls";
		// System.out.println("合并文件结果：" + FileDivisionUniter.unite(fileNames,
		// newFileName));

	}

}
