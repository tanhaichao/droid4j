package org.droid4j;

import java.lang.reflect.Field;

public class FieldUtil {

	public static void set(Field field, Object obj, Object value) {
		field.setAccessible(true);

		try {
			field.set(obj, value);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
