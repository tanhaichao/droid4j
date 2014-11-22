package org.droid4j.data.ioc;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

	private static Map<String, Object> data = new ConcurrentHashMap<String, Object>();

	public static void put(String key, Object bean) {
		data.put(key, bean);
	}

	public static Object getBean(String key) {
		return data.get(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		Iterator<Object> iterator = data.values().iterator();

		while (iterator.hasNext()) {
			Object bean = iterator.next();
			if (bean.getClass().equals(clazz)) {
				return (T) bean;
			}
		}
		// throw new NullPointerException("找不到Bean[" + clazz.getName() + "].");
		return instance(clazz);
	}

	@SuppressWarnings("unchecked")
	protected static <T> T instance(Class<T> clazz) {
		System.err.println("instance:" + clazz);
		Object bean;
		try {
			bean = clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		new AutowiredInjecter().inject(bean);
		new SourceInjecter().inject(bean);
		put(clazz.getName(), bean);
		return (T) bean;
	}
}
