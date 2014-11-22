package io.leopard.droid4j.log;

import io.leopard.burrow.lang.Json;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;
import org.droid4j.log.ILog;

public class Logger {

	private static ILog logger = null;
	static {
		String logImplClassName;
		boolean isAndroid = isAndroid();
		if (isAndroid) {
			logImplClassName = "org.droid4j.log.LogAndroidImpl";
		}
		else {
			logImplClassName = "org.droid4j.log.LogSystemImpl";
		}

		try {
			Class<?> clazz = Class.forName(logImplClassName);
			logger = (ILog) clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected static boolean isAndroid() {
		String className = "android.util.Log";
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			return false;
		}
		Constructor<?> c;
		try {
			c = clazz.getConstructor();
		}
		catch (Exception e) {
			return false;
		}

		try {
			c.setAccessible(true);
			c.newInstance();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			if ("Stub!".equals(e.getMessage())) {
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void debug(Object bean, Object data) {
		debug(bean, Json.toJson(data));
	}

	public static void debug(Object bean, String message) {
		// Log.d(bean.getClass().getSimpleName(), message);
		debug(bean.getClass().getSimpleName(), message);
	}

	public static void debug(String tag, String message) {
		logger.debug(tag, message);
	}

	public static void info(Object bean, Object data) {
		info(bean, Json.toJson(data));
	}

	public static void info(Object bean, String message) {
		// Log.i(bean.getClass().getSimpleName(), message);
		info(bean.getClass().getSimpleName(), message);
	}

	public static void info(String tag, String message) {
		logger.info(tag, message);
	}

	public static void warn(Object bean, String message) {
		warn(bean.getClass().getSimpleName(), message);
	}

	public static void warn(String tag, String message) {
		logger.warn(tag, message);
	}

	public static void error(Object bean, Object data) {
		error(bean, Json.toJson(data));
	}

	public static void error(Object bean, Exception e) {
		String message = e.getMessage();
		if (StringUtils.isEmpty(message)) {
			message = e.toString();
		}
		e.printStackTrace();
		error(bean.getClass().getSimpleName(), message);
	}

	public static void error(Object bean, String message) {
		error(bean.getClass().getSimpleName(), message);
	}

	public static void error(String tag, String message) {
		// AssertUtil.assertNotEmpty(message, "参数message不能为空.");
		if (StringUtils.isEmpty(message)) {
			throw new IllegalArgumentException("参数message不能为空.");
		}
		// Log.e(tag, message);
		logger.error(tag, message);
	}
}
