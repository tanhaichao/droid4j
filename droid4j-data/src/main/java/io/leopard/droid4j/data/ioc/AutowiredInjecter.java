package io.leopard.droid4j.data.ioc;

import java.lang.reflect.Field;

public class AutowiredInjecter extends AbstractInjecter {

	@Override
	protected Object onCreateBean(Field field) {
		Class<?> clazz = field.getType();
		Object bean;
		try {
			bean = clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		new SourceInjecter().inject(bean);
		new AutowiredInjecter().inject(bean);
		return bean;
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
