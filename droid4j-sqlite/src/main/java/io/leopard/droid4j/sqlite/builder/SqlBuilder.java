package io.leopard.droid4j.sqlite.builder;

import io.leopard.droid4j.sqlite.StatementParameter;

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
