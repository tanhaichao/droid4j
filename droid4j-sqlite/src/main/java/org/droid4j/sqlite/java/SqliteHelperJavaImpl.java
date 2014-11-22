package org.droid4j.sqlite.java;

import org.droid4j.sqlite.SqliteDatabase;
import org.droid4j.sqlite.SqliteHelper;

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
