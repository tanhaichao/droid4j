package io.leopard.droid4j.data.ioc;

import io.leopard.droid4j.FieldUtil;
import io.leopard.droid4j.data.hyper.Hyper;
import io.leopard.droid4j.data.preference.Preference;
import io.leopard.droid4j.sqlite.Sqlite;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractInjecter {

	private static Set<String> dataSourceType = new HashSet<String>();

	static {
		dataSourceType.add(Hyper.class.getName());
		dataSourceType.add(Sqlite.class.getName());
		dataSourceType.add(Preference.class.getName());
	}

	public <T> T inject(T bean) {

		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			boolean isAutowired = this.isAutowired(field);
			if (!isAutowired) {
				continue;
			}
			inject(bean, field);
		}
		return bean;
	}

	protected abstract boolean isAutowired(Field field);

	protected void inject(Object bean, Field field) {
		// System.err.println("inject:" + bean + " " + field);
		field.setAccessible(true);
		// System.out.println("inject:" + field.getType());
		Class<?> fieldType = field.getType();

		Object value;
		if (!dataSourceType.contains(fieldType.getName())) {
			value = this.getBean(field);
			if (value != null) {
				FieldUtil.set(field, bean, value);
				return;
			}
		}
		// System.out.println("inject:" + field);
		value = this.onCreateBean(bean, field);

		if (value != null) {
			FieldUtil.set(field, bean, value);
			this.onSave(field, value);
		}
	}

	protected abstract Object onCreateBean(Object bean, Field field);

	/**
	 * 从缓存中获取Bean.
	 * 
	 * @param type
	 * @param source
	 * @return
	 */

	protected Object getBean(Field field) {
		String key = this.getBeanKey(field);
		return BeanFactory.getBean(key);
	}

	/**
	 * 将bean存入缓存.
	 * 
	 * @param type
	 * @param source
	 * @param value
	 */
	protected void onSave(Field field, Object value) {
		String key = this.getBeanKey(field);
		BeanFactory.put(key, value);
	}

	protected abstract String getBeanKey(Field field);
}
