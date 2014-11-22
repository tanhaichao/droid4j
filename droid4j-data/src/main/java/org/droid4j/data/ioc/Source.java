package org.droid4j.data.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 数据源.
 * @author 阿海
 *
 */
public @interface Source {

	/**
	 * 是否全局数据.
	 * 
	 * @return
	 */
	boolean global() default false;

	/**
	 * 是否启用缓存.
	 * 
	 * @return
	 */
	boolean cache() default false;

	/**
	 * 是否开启log.
	 * 
	 * @return
	 */
	boolean log() default false;
}
