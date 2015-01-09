package io.leopard.droid4j.data.hyper;

import io.leopard.burrow.httpnb.Param;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.lang.Paging;
import io.leopard.burrow.lang.PagingImpl;
import io.leopard.droid4j.FileMap;
import io.leopard.droid4j.data.UserSession;
import io.leopard.droid4j.log.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

public class HyperCacheImpl extends HyperImpl {

	private Map<String, String> data;
	private String className;

	public HyperCacheImpl(String className) {
		this.className = className;
	}

	private Map<String, String> getData() {
		if (data == null) {
			File cacheDir = new File(UserSession.getUserDir(isGlobal()), "cache");
			File cacheSubDir = new File(cacheDir, this.getCacheSubDir());
			data = new FileMap(cacheSubDir);
		}
		return data;
	}

	protected String getCacheSubDir() {
		StringBuilder sb = new StringBuilder();
		if (!isGlobal()) {
			sb.append(UserSession.getSessUid()).append("_");
		}
		sb.append(className);
		return sb.toString();
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
	public <T> List<T> queryForList(String url, Class<T> clazz, Param... params) {
		String key = this.toKey(params);
		String json = this.getData().get(key);
		Logger.error(this, "queryForList key:" + key + " url:" + url);
		if (json != null) {
			return Json.toListObject(json, clazz);
		}
		List<T> list = super.queryForList(url, clazz, params);
		this.getData().put(key, Json.toJson(list));
		return list;
	}

	@Override
	public <T> Paging<T> queryForPaging(String url, Class<T> clazz, Param... params) {
		// String key = this.toKey(params);
		// String json = this.getData().get(key);
		// Logger.error(this, "queryForList key:" + key + " url:" + url);
		// if (json != null) {
		// return Json.toListObject(json, clazz);
		// }
		// Paging<T> paging = super.queryForPaging(url, clazz, parmas);
		// this.getData().put(key, Json.toJson(list));
		// return paging;
		//
		//
		// String json = this.doGet(url, params);
		// Map<String, Object> map = parseMap(json);
		// String data = Json.toJson(map.get("data"));
		// List<T> list = Json.toListObject(data, clazz, true);
		// Boolean hasNextPage = (Boolean) map.get("next");
		// if (hasNextPage == null) {
		// throw new NullPointerException("服务器端接口没有返回next字段.");
		// }
		// return new PagingImpl<T>(list, hasNextPage);

		throw new NotImplementedException();
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

	@Override
	public boolean clean() {
		data.clear();
		return true;
	}

	protected String toKey(Param... parmas) {
		StringBuilder sb = new StringBuilder();
		sb.append("cache-");
		for (Param param : parmas) {
			if (!param.isKey()) {
				continue;
			}
			sb.append(param.getValue().toString()).append("_");
		}
		return sb.toString();
	}
}
