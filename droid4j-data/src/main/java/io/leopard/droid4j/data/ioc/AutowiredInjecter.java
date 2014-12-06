package io.leopard.droid4j.data.ioc;

import java.lang.reflect.Field;

public class AutowiredInjecter extends AbstractInjecter {

	@Override
	protected Object onCreateBean(Object bean, Field field) {
		Class<?> clazz = field.getType();
		Object result;
		try {
			result = clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		new SourceInjecter().inject(result);
		new AutowiredInjecter().inject(result);
		return result;
	}

	@Override
	protected String getBeanKey(Field field) {
		return field.getType().getName();
	}

	@Override
	protected boolean isAutowired(Field field) {
		Autowired autowired = field.getAnnotation(Autowired.class);
		return autowired != null;
	}

}
