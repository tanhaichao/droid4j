package io.leopard.droid4j;

import io.leopard.commons.utility.AssertUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NotImplementedException;

public class FileMap implements Map<String, String> {

	private File dir;

	public FileMap(File dir) {
		this.dir = dir;

		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	@Override
	public int size() {
		return dir.listFiles().length;
	}

	@Override
	public boolean isEmpty() {
		return this.size() <= 0;
	}

	@Override
	public boolean containsKey(Object key) {
		File file = new File(dir, toStringKey(key));
		return file.exists();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new NotImplementedException();
	}

	private String toStringKey(Object key) {
		String key1 = key.toString();
		AssertUtil.assertNotEmpty(key1, "参数key不能为空.");
		return key1;
	}

	@Override
	public String get(Object key) {
		File file = new File(dir, toStringKey(key));
		try {
			return FileUtils.readFileToString(file);
		}
		catch (IOException e) {
			return null;
		}
	}

	@Override
	public String put(String key, String value) {
		AssertUtil.assertNotEmpty(key, "参数key不能为空.");
		File file = new File(dir, key);
		try {
			FileUtils.writeStringToFile(file, value);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return value;
	}

	@Override
	public String remove(Object key) {
		File file = new File(dir, toStringKey(key));
		FileUtils.deleteQuietly(file);
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		throw new NotImplementedException();
	}

	@Override
	public void clear() {
		try {
			FileUtils.forceDelete(this.dir);
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Set<String> keySet() {
		throw new NotImplementedException();
	}

	@Override
	public Collection<String> values() {
		throw new NotImplementedException();
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		throw new NotImplementedException();
	}

}
