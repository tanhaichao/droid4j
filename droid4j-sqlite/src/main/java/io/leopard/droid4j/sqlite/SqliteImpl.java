package io.leopard.droid4j.sqlite;

import io.leopard.commons.utility.StringUtil;
import io.leopard.droid4j.sqlite.builder.InsertBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import android.database.Cursor;

public class SqliteImpl implements Sqlite {

	private SqliteHelper helper;

	public void setHelper(SqliteHelper helper) {
		this.helper = helper;
	}

	@Override
	public <T> T query(String sql, Class<T> clazz) {
		SqliteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		return this.query(cursor, clazz);
	}

	@Override
	public <T> T query(String sql, Class<T> clazz, Object... params) {
		SqliteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, toSelectionArgs(params));

		return this.query(cursor, clazz);
	}

	protected <T> T query(Cursor cursor, Class<T> clazz) {
		// System.out.println("cursor:" + cursor);
		if (!cursor.moveToFirst()) {
			return null;
		}
		// String name = cursor.getString(0);
		// System.out.println("name:" + name);
		try {
			return this.orm(cursor, clazz);
		}
		catch (Exception e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	protected String[] toSelectionArgs(Object... params) {
		String[] selectionArgs = new String[params.length];
		for (int i = 0; i < selectionArgs.length; i++) {
			// TODO ahai 这里要做数据类型转换
			selectionArgs[i] = params[i].toString();
		}
		return selectionArgs;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> clazz, Object... params) {
		SqliteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, toSelectionArgs(params));
		return this.queryForList(cursor, clazz);

	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> clazz) {
		SqliteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(sql, null);
		return this.queryForList(cursor, clazz);
	}

	protected <T> List<T> queryForList(Cursor cursor, Class<T> clazz) {
		System.err.println("cursor count:" + cursor.getCount());
		if (!cursor.moveToFirst()) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		do {
			try {
				T bean = this.orm(cursor, clazz);
				list.add(bean);
			}
			catch (Exception e) {
				throw new DataAccessException(e.getMessage(), e);
			}
		}
		while (cursor.moveToNext());
		return list;

	}

	protected <T> T orm(Cursor cursor, Class<T> clazz) throws Exception {
		T bean = clazz.newInstance();
		int count = cursor.getColumnCount();
		// System.err.println("getColumnCount:" + count);
		for (int i = 0; i < count; i++) {
			String columnName = cursor.getColumnName(i);
			String varName = this.toVarName(columnName);
			Object value = this.get(cursor, i);
			String setMethodName = "set" + StringUtil.firstCharToUpperCase(varName);
			// System.err.println("setMethodName:" + setMethodName);
			Method method = this.getMethod(clazz, setMethodName);
			method.setAccessible(true);
			Class<?> type = method.getParameterTypes()[0];

			try {
				if (type.equals(Date.class)) {
					Long time = (Long) value;
					value = new Date(time);
				}
				else if (type.equals(boolean.class)) {
					Long flag = (Long) value;
					value = !(flag == 0);
				}
				else if (type.equals(int.class) || type.equals(Integer.class)) {
					int num = ((Long) value).intValue();
					value = num;
				}
			}
			catch (Exception e) {
				System.err.println("method:" + setMethodName + " columnName:" + columnName + " value:" + value);
				throw e;
			}

			try {
				method.invoke(bean, value);
			}
			catch (IllegalArgumentException e) {
				System.err.println("method:" + setMethodName + " type:" + type.getName() + " value:" + value.getClass().getName() + " value2:" + value);
				throw e;
			}
		}
		return bean;
	}

	protected Method getMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new NoSuchMethodException(methodName);
	}

	protected String toVarName(String columnName) {
		return columnName;
	}

	protected Object get(Cursor cursor, int columnIndex) {
		int type = cursor.getType(columnIndex);
		if (type == Cursor.FIELD_TYPE_INTEGER) {
			return cursor.getLong(columnIndex);
		}
		else if (type == Cursor.FIELD_TYPE_FLOAT) {
			return cursor.getFloat(columnIndex);
		}
		else if (type == Cursor.FIELD_TYPE_STRING) {
			return cursor.getString(columnIndex);
		}
		// else if (type == Types.DATE) {
		// throw new NotImplementedException("不支持日期类型.");
		// }
		else {
			throw new UnsupportedOperationException("未知数据类型[" + type + "." + columnIndex + "]");
		}
	}

	@Override
	public boolean insertObject(String keyFieldName, Object key, Object value) {
		throw new NotImplementedException();
	}

	@Override
	public boolean insert(InsertBuilder builder) {
		SqliteDatabase db = helper.getWritableDatabase();
		// int updatedCount = this.getJdbcTemplate().update(sql,
		// param.getParameters());
		String sql = builder.getSql();
		Object[] bindArgs = builder.getParam().getArgs();
		db.execSQL(sql, bindArgs);

		System.out.println("sql:" + sql);
		System.out.println("bindArgs:" + StringUtils.join(bindArgs, ","));
		return true;
	}

	@Override
	public boolean updateForBool(String sql, Object... params) {
		Object[] selectionArgs = new Object[params.length];
		for (int i = 0; i < selectionArgs.length; i++) {
			if (params[i] instanceof Date) {
				selectionArgs[i] = ((Date) params[i]).getTime();
			}
			else {
				selectionArgs[i] = params[i].toString();
			}
		}

		SqliteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql, selectionArgs);
		// db.close();
		// TODO ahai 这里死活返回true
		return true;
	}

	@Override
	public Integer queryForInteger(String sql, Object... params) {
		SqliteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, toSelectionArgs(params));
		if (!cursor.moveToFirst()) {
			return null;
		}
		return cursor.getInt(0);

	}

	@Override
	public Long queryForLong(String sql, Object... params) {
		SqliteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(sql, toSelectionArgs(params));
		if (!cursor.moveToFirst()) {
			return null;
		}
		return cursor.getLong(0);

	}

	@Override
	public String queryForString(String sql, Object... params) {
		SqliteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, toSelectionArgs(params));
		if (!cursor.moveToFirst()) {
			return null;
		}
		return cursor.getString(0);
	}

	@Override
	public List<String> queryForStrings(String sql, Object... params) {
		SqliteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, toSelectionArgs(params));
		if (!cursor.moveToFirst()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		do {
			list.add(cursor.getString(0));
		}
		while (cursor.moveToNext());
		return list;
	}

	protected boolean loadTable(InputStream input) {
		String sql;
		try {
			sql = IOUtils.toString(input);
			input.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		System.out.println("sql:" + sql);

		String tableName = parseTableName(sql);

		String dropTableSql = "drop table if exists " + tableName + ";";

		SqliteDatabase db = helper.getWritableDatabase();
		db.execSQL(dropTableSql);
		db.execSQL(sql);
		return true;
	}

	protected String parseTableName(String sql) {
		String regex = "CREATE TABLE `([a-z_0-9]+)`";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		if (m.find()) {
			return m.group(1);
		}
		System.err.println("error table name,sql:" + sql);
		throw new IllegalArgumentException("解析表名出错.");
	}

	@Override
	public boolean loadTable(Object bean, String tableName) {
		InputStream input = bean.getClass().getResourceAsStream("/sql/" + tableName + ".sql");
		return this.loadTable(input);
	}

}
