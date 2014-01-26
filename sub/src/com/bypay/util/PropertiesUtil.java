package com.bypay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author e430c 读取配置文件
 */
public class PropertiesUtil {
	private static Properties p = new Properties();
	static {
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("com/bypay/config/jdbc.properties");
			p.load(inputStream);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static String getValue(String key) {
		return p.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(getValue("jdbc.driver"));
	}
}
