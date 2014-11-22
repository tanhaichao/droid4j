package org.droid4j.data;

import java.io.File;

import org.droid4j.sqlite.SqliteHelper;

public class UserSession {

	private static String loginedCookie;

	public static void setLoginedInfo(long uid, String loginedCookie) {
		UserSession.uid = uid;
		UserSession.loginedCookie = loginedCookie;
	}

	public static String getLoginedCookie() {
		return loginedCookie;
	}

	protected static long uid;

	public static long getUid() {
		return uid;
	}

	public static long getSessUid() {
		if (uid <= 0) {
			throw new NullPointerException("uid未设置.");
		}
		return uid;
	}

	private static SqliteHelper sqliteHelper;

	public static void setSqliteHelper(SqliteHelper sqliteHelper) {
		UserSession.sqliteHelper = sqliteHelper;
	}

	public static SqliteHelper getSqliteHelper() {
		if (sqliteHelper == null) {
			throw new NullPointerException("sqliteHelper未初始化.");
		}
		return sqliteHelper;
	}

	private static File rootDir;

	public static void setRootDir(File rootDir) {
		UserSession.rootDir = rootDir;
	}

	public static File getRootDir() {
		if (rootDir == null) {
			throw new NullPointerException("rootDir未初始化.");
		}
		return rootDir;
	}

	public static File getUserDir(boolean global) {
		if (global) {
			return rootDir;
		}

		long sessUid = getSessUid();
		return new File(rootDir, Long.toString(sessUid));
	}
}
