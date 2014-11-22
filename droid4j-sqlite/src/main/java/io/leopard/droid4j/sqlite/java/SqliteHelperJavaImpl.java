package io.leopard.droid4j.sqlite.java;

import io.leopard.droid4j.sqlite.SqliteDatabase;
import io.leopard.droid4j.sqlite.SqliteHelper;

public class SqliteHelperJavaImpl implements SqliteHelper {

	private SqliteDatabaseJavaImpl database = new SqliteDatabaseJavaImpl();

	@Override
	public SqliteDatabase getReadableDatabase() {
		return database;
	}

	@Override
	public SqliteDatabase getWritableDatabase() {
		return database;
	}

}
