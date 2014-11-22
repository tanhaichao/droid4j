package org.droid4j.data.preference;

import io.leopard.burrow.lang.Json;

import java.io.File;

import org.droid4j.cache.FileMap;
import org.droid4j.data.UserSession;

public class PreferenceImpl implements Preference {

	private FileMap map;

	private boolean global;
	private boolean log;

	public PreferenceImpl() {

	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public FileMap getFileMap() {
		if (map != null) {
			return map;
		}
		File baseDir = UserSession.getUserDir(global);
		File dir = new File(baseDir, "preference");
		map = new FileMap(dir);
		return map;
	}

	@Override
	public boolean put(String key, Object bean) {
		String json = Json.toJson(bean);
		return this.put(key, json);
	}

	@Override
	public boolean put(String key, String value) {
		getFileMap().put(key, value);
		return true;
	}

	@Override
	public String get(String key) {
		return this.getFileMap().get(key);
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		String json = this.get(key);
		return Json.toObject(json, clazz);
	}

	@Override
	public boolean remove(String key) {
		getFileMap().remove(key);
		return true;
	}

}
