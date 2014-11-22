package io.leopard.droid4j.sqlite;

import android.database.Cursor;

public interface SqliteDatabase {

	public Cursor rawQuery(String sql, String[] selectionArgs);

	public void execSQL(String sql, Object[] bindArgs);

	public void execSQL(String sql);

	// public void close();
}
