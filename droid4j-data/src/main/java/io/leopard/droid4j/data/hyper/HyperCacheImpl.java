package io.leopard.droid4j.data.hyper;

import io.leopard.burrow.httpnb.Param;
import io.leopard.burrow.lang.Json;
import io.leopard.droid4j.data.UserSession;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.droid4j.cache.FileMap;

public class HyperCacheImpl extends HyperImpl {

	private Map<String, String> data;

	private Map<String, String> getData() {
		if (data == null) {
			File cacheDir = new File(UserSession.getUserDir(isGlobal()), "cache");
			data = new FileMap(cacheDir);
		}
		return data;
	}

	@Override
	public <T> T query(String url, Class<T> clazz, Param... params) {
		String key = this.toKey(params);

		String json = this.getData().get(key);
		if (json != null) {
			return Json.toObject(json, clazz, true);
		}
		T bean = super.query(url, clazz, params);
		this.getData().put(key, Json.toJson(bean));
		return bean;
	}

	@Override
	public <T> List<T> queryForList(String url, Class<T> clazz, Param... parmas) {
		// FIXME ahai 未区分DAO对象.
		String key = this.toKey(parmas);
		String json = this.getData().get(key);
		System.out.println("key:" + key);
		if (json != null) {
			return Json.toListObject(json, clazz);
		}
		List<T> list = super.queryForList(url, clazz, parmas);
		this.getData().put(key, Json.toJson(list));
		return list;
	}

	@Override
	public boolean add(String url, Param... parmas) {
		boolean success = super.add(url, parmas);
		this.remove(parmas);
		return success;
	}

	@Override
	public boolean delete(String url, Param... parmas) {
		boolean success = super.delete(url, parmas);
		this.remove(parmas);
		return success;
	}

	@Override
	public boolean remove(Param... parmas) {
		String key = this.toKey(parmas);
		this.getData().remove(key);
		return true;
	}

	protected String toKey(Param... parmas) {
		StringBuilder sb = new StringBuilder();
		for (Param param : parmas) {
			if (!param.isKey()) {
				continue;
			}
			sb.append(param.getValue().toString()).append(":");
		}
		return sb.toString();
	}
}
