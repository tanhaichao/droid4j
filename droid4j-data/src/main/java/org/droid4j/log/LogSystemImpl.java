package org.droid4j.log;

public class LogSystemImpl implements ILog {

	@Override
	public void debug(String tag, String message) {
		System.out.println("tag:" + tag + " message:" + message);

	}

	@Override
	public void info(String tag, String message) {
		System.out.println("tag:" + tag + " message:" + message);

	}

	@Override
	public void warn(String tag, String message) {
		System.err.println("tag:" + tag + " message:" + message);
	}

	@Override
	public void error(String tag, String message) {
		System.err.println("tag:" + tag + " message:" + message);

	}

}
