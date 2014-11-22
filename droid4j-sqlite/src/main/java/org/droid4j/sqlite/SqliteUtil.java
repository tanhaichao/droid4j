package org.droid4j.sqlite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class SqliteUtil {

	public static String getSql(Class<?> clazz, String table) {
		InputStream input = clazz.getResourceAsStream("/sql/" + table + ".sql");
		try {
			return IOUtils.toString(input);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		finally {
			try {
				input.close();
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	public static List<String> listAllSql(Class<?> clazz) {
		URL url = clazz.getResource(".");
		System.out.println("url:" + url);
		String root = clazz.getResource("/sql/").getFile();
		File dir = new File(root);
		List<String> list = new ArrayList<String>();
		for (File file : dir.listFiles()) {
			String sql;
			try {
				sql = FileUtils.readFileToString(file);
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			list.add(sql);
		}
		return list;
	}
}
