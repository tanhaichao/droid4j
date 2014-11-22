package org.droid4j.sqlite.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sqlite4jTemplate {

	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:/tmp/test.db");
			// Statement statement = conn.createStatement();
			// conn.close(); // 结束数据库的连接
			return conn;
		}
		catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
