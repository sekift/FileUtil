package com.sekift.fileutil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author:sekift
 * @time:2014-7-15 下午05:12:20
 * @version:
 */
public class SerializeObject {

	static class MyClass implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7766377606517551777L;
		private int a, b;
		private transient int c;
		private static int d;

		public MyClass() {
		}

		public MyClass(int a, int b, int c, int d) {
			this.a = a;
			this.b = b;
			this.c = c;
			MyClass.d = d;
		}

		public String toString() {
			return this.a + " " + this.b + " " + this.c + " " + MyClass.d;
		}
	}

	public static void serialize(String fileName) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject("Today:");
		out.writeObject(new Date());
		MyClass my1 = new MyClass(5, 6, 7, 8);
		out.writeObject(my1);
		out.close();
	}

	public static void deserialize(String fileName) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		String today = (String) (in.readObject());
		System.out.println(today);
		Date date = (Date) (in.readObject());
		System.out.println(date.toString());
		MyClass my1 = (MyClass) (in.readObject());
		System.out.println(my1.toString());
		in.close();
	}

	public static void main(String[] args) throws Exception {
		String fileName = "D:\\temp\\Myclass.ser";
		SerializeObject.serialize(fileName);
		SerializeObject.deserialize(fileName);

	}

}
