package com.sekift.fileutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sekift.CloseUtil;

/**
 * 从键盘输入数据
 * @author:sekift
 * @time:2014-8-14 上午09:40:09
 * @version:1.1.5
 */
public class ReadFromSystem {

	public static Object systemIn(){
		String str;
		BufferedReader buf;
		buf = new BufferedReader(new InputStreamReader(System.in));
		Object r=null;
		System.out.print("请输入你的数据：");
		try {
			str = buf.readLine();
			r = (Object)str;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.closeSilently(buf);
		}
		return r;
	}
}
