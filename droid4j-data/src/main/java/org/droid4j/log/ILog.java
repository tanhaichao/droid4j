package org.droid4j.log;

public interface ILog {
	void debug(String tag, String message);

	void info(String tag, String message);

	void warn(String tag, String message);

	void error(String tag, String message);
}
