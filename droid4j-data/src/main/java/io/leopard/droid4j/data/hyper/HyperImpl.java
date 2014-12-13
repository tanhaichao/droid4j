package io.leopard.droid4j.data.hyper;

import io.leopard.burrow.httpnb.HttpException;
import io.leopard.burrow.httpnb.HttpHeader;
import io.leopard.burrow.httpnb.HttpHeaderGetImpl;
import io.leopard.burrow.httpnb.HttpHeaderPostImpl;
import io.leopard.burrow.httpnb.HttpUpload;
import io.leopard.burrow.httpnb.Httpnb;
import io.leopard.burrow.httpnb.Param;
import io.leopard.burrow.lang.Json;
import io.leopard.burrow.lang.Paging;
import io.leopard.burrow.lang.PagingImpl;
import io.leopard.core.exception.StatusCodeException;
import io.leopard.droid4j.data.UserSession;
import io.leopard.droid4j.log.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class HyperImpl implements Hyper {

	private boolean log;

	// private String loginCookie;
	//
	// public String getLoginCookie() {
	// return loginCookie;
	// }
	//
	// public void setLoginCookie(String loginCookie) {
	// this.loginCookie = loginCookie;
	// }

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	private boolean global;

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public String getAndCheckLoginCookie() {

		String cookie = UserSession.getLoginedCookie();
		if (!global) {
			if (StringUtils.isEmpty(cookie)) {
				NullPointerException e = new NullPointerException("loginCookie怎么会为空?");
				e.printStackTrace();
				throw e;
			}
		}
		return cookie;
	}

	public static Map<String, Object> parseMap(String json) {
		Map<String, Object> map = Json.toMap(json);
		String status = (String) map.get("status");
		if (!"200".equals(status)) {
			String message = (String) map.get("message");
			throw new StatusCodeException(status, message, message);
		}
		return map;
	}

	protected String doGet(String url, Param... params) {
		// System.out.println("HttpJson loginCookie:" + loginCookie);
		// System.out.println("params:" + params);
		HttpHeader header = new HttpHeaderGetImpl(-1);
		for (Param param : params) {
			header.addParam(param);
			// System.out.println("param:" + param.toString());
		}
		header.setCookie(getAndCheckLoginCookie());
		Logger.error(this, "url:" + url);
		String content = Httpnb.execute(url, header);

		// if (this.log) {
		Logger.error(this, "url:" + url + " content:" + content);
		// }

		return content;
	}

	protected String doPost(String url, Param... params) {
		// System.out.println("HttpJson loginCookie:" + loginCookie);
		HttpHeader header = new HttpHeaderPostImpl(-1);
		for (Param param : params) {
			header.addParam(param);
		}
		header.setCookie(getAndCheckLoginCookie());
		System.err.println("cookie:" + getAndCheckLoginCookie());
		return Httpnb.execute(url, header);
	}

	@Override
	public boolean add(String url, Param... params) {
		String json = this.doPost(url, params);
		Map<String, Object> map = parseMap(json);
		return (Boolean) map.get("data");
	}

	@Override
	public boolean update(String url, Param... params) {
		String json = this.doPost(url, params);
		Map<String, Object> map = parseMap(json);
		return (Boolean) map.get("data");
	}

	@Override
	public boolean update2(String url, List<Param> paramList) {
		HttpHeader header = new HttpHeaderPostImpl(-1);
		for (Param param : paramList) {
			header.addParam(param);
		}
		header.setCookie(getAndCheckLoginCookie());
		String json = Httpnb.execute(url, header);
		Map<String, Object> map = parseMap(json);
		return (Boolean) map.get("data");
	}

	@Override
	public boolean delete(String url, Param... params) {
		String json = this.doPost(url, params);
		Map<String, Object> map = parseMap(json);
		return (Boolean) map.get("data");
	}

	@Override
	public <T> T query(String url, Class<T> clazz, Param... params) {
		String json = this.doGet(url, params);
		Map<String, Object> map = parseMap(json);
		String data = Json.toJson(map.get("data"));
		System.out.println("json:" + json);
		return Json.toObject(data, clazz, true);
	}

	@Override
	public <T> T queryForNext(String url, Class<T> clazz, Param... params) {
		String json = this.doGet(url, params);
		Map<String, Object> map = parseMap(json);
		String data = Json.toJson(map.get("data"));
		boolean next = (Boolean) map.get("next");
		T bean = Json.toObject(data, clazz, true);
		try {
			Method method = clazz.getDeclaredMethod("setNext", boolean.class);
			method.invoke(bean, next);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return bean;
	}

	@Override
	public boolean queryForBoolean(String url, Param... params) {
		String json = this.doGet(url, params);
		Map<String, Object> map = parseMap(json);
		return (Boolean) map.get("data");
	}

	@Override
	public <T> List<T> queryForList(String url, Class<T> clazz, Param... params) {
		String json = this.doGet(url, params);
		Map<String, Object> map = parseMap(json);
		String data = Json.toJson(map.get("data"));
		return Json.toListObject(data, clazz, true);
	}

	@Override
	public <T> Paging<T> queryForPaging(String url, Class<T> clazz, Param... params) {
		String json = this.doGet(url, params);
		Map<String, Object> map = parseMap(json);
		String data = Json.toJson(map.get("data"));
		List<T> list = Json.toListObject(data, clazz, true);
		Boolean hasNextPage = (Boolean) map.get("next");
		if (hasNextPage == null) {
			throw new NullPointerException("服务器端接口没有返回next字段.");
		}
		return new PagingImpl<T>(list, hasNextPage);
	}

	@Override
	public boolean remove(Param... params) {
		// throw new NotImplementedException();
		return false;
	}

	@Override
	public long getSessUid() {
		return UserSession.getSessUid();
	}

	@Override
	public String uploadForUrl(String url, String path, Param... params) {
		List<String> fileList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(path)) {
			fileList.add(path);
		}
		String data = this.uploadForData(url, fileList, params);
		return Json.toObject(data, String.class);
	}

	@Override
	public String uploadForData(String url, List<String> fileList, Param... params) {
		List<Entry<String, String>> fileEntryList = new ArrayList<Entry<String, String>>();
		if (fileList != null) {
			for (String file : fileList) {
				Entry<String, String> entry = new SimpleEntry<String, String>("file", file);
				fileEntryList.add(entry);
			}
		}
		HttpHeader header = new HttpHeaderPostImpl(-1);
		header.setCookie(getAndCheckLoginCookie());

		try {
			HttpURLConnection conn = header.openConnection(url);

			HttpUpload.upload(conn, fileEntryList, params);

			String json = Httpnb.execute(conn);
			Map<String, Object> map = parseMap(json);
			return Json.toJson(map.get("data"));
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	// public static String doUpload(String url, String path) {
	// HttpHeader header = new HttpHeaderImpl("POST", 6000);
	// try {
	// HttpURLConnection conn = header.openConnection(url);
	// upload(path, conn);
	// return execute(conn);
	// }
	// catch (IOException e) {
	// throw new HttpException(e, header);
	// }
	// }

	// POST http://olla.laopao.com/share/upload/image.do HTTP/1.1
	// Host: olla.laopao.com
	// Connection: keep-alive
	// Content-Length: 13481
	// Cache-Control: max-age=0
	// Accept:
	// text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
	// Origin: null
	// User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36
	// (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36
	// Content-Type: multipart/form-data;
	// boundary=----WebKitFormBoundaryEvaq8bJriOMUn4F6
	// Accept-Encoding: gzip,deflate,sdch
	// Accept-Language: zh-CN,zh;q=0.8,en;q=0.6
	//
	// ------WebKitFormBoundaryEvaq8bJriOMUn4F6
	// Content-Disposition: form-data; name="file"; filename="test-3-1.jpg"
	// Content-Type: image/jpeg
	//
	//
	//
	//
	// ------WebKitFormBoundaryEvaq8bJriOMUn4F6
	// Content-Disposition: form-data; name="file"; filename="test-3-2.jpg"
	// Content-Type: image/jpeg
	//
	// ------WebKitFormBoundaryEvaq8bJriOMUn4F6--

}
