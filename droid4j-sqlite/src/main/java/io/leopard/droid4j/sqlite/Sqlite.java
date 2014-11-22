package io.leopard.droid4j.sqlite;

import io.leopard.droid4j.sqlite.builder.InsertBuilder;

import java.util.List;

public interface Sqlite {

	boolean insertObject(String keyFieldName, Object key, Object value);

	boolean insert(InsertBuilder builder);

	<T> T query(String sql, Class<T> clazz);

	<T> T query(String sql, Class<T> clazz, Object... params);

	<T> List<T> queryForList(String sql, Class<T> clazz);

	<T> List<T> queryForList(String sql, Class<T> clazz, Object... params);

	boolean updateForBool(String sql, Object... params);

	Integer queryForInteger(String sql, Object... params);

	Long queryForLong(String sql, Object... params);

	String queryForString(String sql, Object... params);

	List<String> queryForStrings(String sql, Object... params);

	boolean loadTable(Object bean, String filename);
}
