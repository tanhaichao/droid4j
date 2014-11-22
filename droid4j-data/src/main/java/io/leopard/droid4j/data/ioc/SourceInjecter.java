package io.leopard.droid4j.data.ioc;

import io.leopard.core.exception.UnknownException;
import io.leopard.droid4j.data.UserSession;
import io.leopard.droid4j.data.hyper.Hyper;
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

	protected Hyper onHyper(Field field, Source source) {
		HyperImpl hyper;
		if (source.cache()) {// FIXME ahai cache未实现
			hyper = new HyperImpl();// HyperCacheImpl();
		}
		else {
			hyper = new HyperImpl();
		}
		// hyper.setLoginCookie(UserSession.getLoginedCookie());
		hyper.setLog(source.log());
		hyper.setGlobal(source.global());
		return hyper;
	}

	protected Sqlite onSqlite(Field field, Source source) {
		SqliteImpl sqlite = new SqliteImpl();
		sqlite.setHelper(UserSession.getSqliteHelper());
		return sqlite;
	}

	protected Preference onPreference(Field field, Source source) {
		PreferenceImpl preference = new PreferenceImpl();
		preference.setLog(source.log());
		preference.setGlobal(source.global());
		// preference.init();
		return preference;
	}

	@Override
	protected Object onCreateBean(Field field) {
		Class<?> type = field.getType();
		Source source = field.getAnnotation(Source.class);

		Object value;
		if (Hyper.class.equals(type)) {
			value = this.onHyper(field, source);
		}
		else if (Sqlite.class.equals(type)) {
			value = this.onSqlite(field, source);
		}
		else if (Preference.class.equals(type)) {
			value = this.onPreference(field, source);
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
