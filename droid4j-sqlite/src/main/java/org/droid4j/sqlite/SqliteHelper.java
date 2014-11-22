package org.droid4j.sqlite;

public interface SqliteHelper {

	SqliteDatabase getReadableDatabase();

	SqliteDatabase getWritableDatabase();
}
