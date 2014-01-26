package com.bypay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author e430c
 * 获取jdbc connection
 */
public class DbUtil {
	static String url = "jdbc:mysql://192.168.1.130:3306/sub?useUnicode=true&characterEncoding=UTF-8";
	static String username = "root";
	static String password = "root";
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}