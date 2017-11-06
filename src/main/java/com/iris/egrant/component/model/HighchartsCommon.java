package com.iris.egrant.component.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *  highcharts常量定义类
 * @author wen
 *
 */
public class HighchartsCommon
{
	public final static String LINE = "1";
	
	public final static String PIE = "2";
	
	public final static String ZHUZHUANG = "3";
	
	/**
	 *  对象深度克隆方法（复制对象obj，类似于值传递，非引用）
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Object cloneObject(Object obj) throws Exception
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(obj);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(
				byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		return in.readObject();
	}
}
