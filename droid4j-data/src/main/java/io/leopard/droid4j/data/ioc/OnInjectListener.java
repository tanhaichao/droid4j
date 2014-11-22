package io.leopard.droid4j.data.ioc;

import java.lang.reflect.Field;

public interface OnInjectListener {
	void onInject(Class<?> type, Source source, Field field);
}