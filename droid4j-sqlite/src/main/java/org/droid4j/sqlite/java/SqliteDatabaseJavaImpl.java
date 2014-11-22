package org.droid4j.sqlite.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.droid4j.sqlite.SqliteDatabase;

import android.database.Cursor;

public class SqliteDatabaseJavaImpl implements SqliteDatabase {

	@Override
	public Cursor rawQuery(String sql, String[] selectionArgs) {
		try {
			return this.query(sql, selectionArgs);
		}
		catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected Cursor query(String sql, String[] args) throws SQLException {
		System.out.println("sql:" + sql + " args:" + StringUtils.join(args, ","));
		Connection conn = Sqlite4jTemplate.getConnection();
		// sql = "select * from share";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				int parameterIndex = i + 1;
				if (args[i] == null) {
					throw new NullPointerException("数据[" + parameterIndex + "]不能为null.");
				}
				if (args[i] instanceof String) {
					pstmt.setString(parameterIndex, (String) args[i]);
					// System.out.println("args[i]:" + args[i]);
				}
				else {
					throw new IllegalArgumentException("未知数据类型[" + args[i].getClass().getName() + "].");
				}
			}
		}
		ResultSet rs = pstmt.executeQuery();
		Cursor cursor = this.toCursor(rs);
		rs.close();
		pstmt.close();
		conn.close();
		return cursor;
	}

	protected Cursor toCursor(ResultSet rs) throws SQLException {
		CursorJavaImpl cursor = new CursorJavaImpl();
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		for (int i = 0; i < count; i++) {
			int columnIndex = i + 1;
			String name = meta.getColumnLabel(columnIndex);
			int type = meta.getColumnType(columnIndex);
			cursor.addType(i, type, name);
		}
		cursor.printMetaInfo();
		while (rs.next()) {
			Map<Integer, Object> row = new HashMap<Integer, Object>();
			for (int i = 0; i < count; i++) {
				int columnIndex = i + 1;
				Object value = rs.getObject(columnIndex);
				row.put(i, value);
			}
			System.err.println("row:" + row);
			cursor.addRow(row);
		}
		return cursor;
	}

	@Override
	public void execSQL(String sql, Object[] bindArgs) {
		try {
			this.executeUpdate(sql, bindArgs);
		}
		catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void execSQL(String sql) {
		try {
			this.executeUpdate(sql, null);
		}
		catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected int executeUpdate(String sql, Object[] args) throws SQLException {
		System.out.println("sql:" + sql + " args:" + StringUtils.join(args, ","));
		Connection conn = Sqlite4jTemplate.getConnection();
		// Statement statement = conn.createStatement();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				int parameterIndex = i + 1;
				if (args[i] == null) {
					throw new NullPointerException("数据[" + parameterIndex + "]不能为null.");
				}
				if (args[i] instanceof String) {
					pstmt.setString(parameterIndex, (String) args[i]);
				}
				else if (args[i] instanceof Long) {
					pstmt.setLong(parameterIndex, (Long) args[i]);
				}
				else if (args[i] instanceof Integer) {
					pstmt.setInt(parameterIndex, (Integer) args[i]);
				}
				else {
					throw new IllegalArgumentException("未知数据类型[" + args[i].getClass().getName() + "].");
				}
			}
		}
		int updateCount = pstmt.executeUpdate();
		System.err.println("updateCount:" + updateCount);
		pstmt.close();
		conn.close();
		return updateCount;
	}

}
