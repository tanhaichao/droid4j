package org.droid4j.sqlite.builder;

import org.droid4j.sqlite.StatementParameter;

public interface SqlBuilder {

	/**
	 * 获取SQL语句.
	 * 
	 * @return
	 */
	String getSql();

	/**
	 * 获取参数
	 * 
	 * @return
	 */
	StatementParameter getParam();
}
