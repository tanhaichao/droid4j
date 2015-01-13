package io.leopard.droid4j.data.ioc;

import io.leopard.core.exception.UnknownException;
import io.leopard.droid4j.data.UserSession;
import io.leopard.droid4j.data.hyper.Hyper;
import io.leopard.droid4j.data.hyper.HyperCacheImpl;
import io.leopard.droid4j.data.hyper.HyperImpl;
import io.leopard.droid4j.data.preference.Preference;
import io.leopard.droid4j.data.preference.PreferenceImpl;
import io.leopard.droid4j.sqlite.Sqlite;
import io.leopard.droid4j.sqlite.SqliteImpl;

import java.lang.reflect.Field;

/**
 * 数据源注入.
 * 
 * @author 阿海
 * 
 */
public class SourceInjecter extends AbstractInjecter {

	protected Hyper onHyper(Object bean, Field field, Source source) {
		HyperImpl hyper;
		if (source.cache()) {
			String className = bean.getClass().getSimpleName();
			hyper = new HyperCacheImpl(className);
		}
		else {
			hyper = new HyperImpl();
		}
		// hyper.setLoginCookie(UserSession.getLoginedCookie());
		hyper.setLog(source.log());
		hyper.setGlobal(source.global());
		return hyper;
	}

	protected Sqlite onSqlite(Object bean, Field field, Source source) {
		SqliteImpl sqlite = new SqliteImpl();
		sqlite.setHelper(UserSession.getSqliteHelper());
		return sqlite;
	}

	protected Preference onPreference(Object bean, Field field, Source source) {
		PreferenceImpl preference = new PreferenceImpl();
		preference.setLog(source.log());
		preference.setGlobal(source.global());
		// preference.init();
		return preference;
	}

	@Override
	protected Object onCreateBean(Object bean, Field field) {
		// System.err.println("onCreateBean:" + bean + " " + field);
		Class<?> type = field.getType();
		Source source = field.getAnnotation(Source.class);

		Object value;
		if (Hyper.class.equals(type)) {
			value = this.onHyper(bean, field, source);
		}
		else if (Sqlite.class.equals(type)) {
			value = this.onSqlite(bean, field, source);
		}
		else if (Preference.class.equals(type)) {
			value = this.onPreference(bean, field, source);
		}
		else {
			throw new UnknownException("未知类型[" + type.getName() + "].");
		}
		return value;
	}

	@Override
	protected String getBeanKey(Field field) {
		Source source = field.getAnnotation(Source.class);
		String key = field.getType().getName();
		if (source.global()) {
			key += ":global";
		}
		if (source.cache()) {
			key += ":cache";
		}
		return key;
	}

	@Override
	protected boolean isAutowired(Field field) {
		Source source = field.getAnnotation(Source.class);
		return source != null;
	}

}
