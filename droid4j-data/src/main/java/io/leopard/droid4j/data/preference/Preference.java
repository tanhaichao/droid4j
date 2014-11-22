package io.leopard.droid4j.data.preference;

/**
 * 偏好设定接口.
 * 
 * @author 阿海
 *
 */
public interface Preference {

	boolean put(String key, Object bean);

	boolean put(String key, String value);

	String get(String key);

	<T> T get(String key, Class<T> clazz);

	boolean remove(String key);
}
