package io.leopard.droid4j.log;

import android.util.Log;

public class LogAndroidImpl implements ILog {

	@Override
	public void debug(String tag, String message) {
		Log.d(tag, message);
	}

	@Override
	public void info(String tag, String message) {
		Log.i(tag, message);
	}

	@Override
	public void warn(String tag, String message) {
		Log.w(tag, message);
	}

	@Override
	public void error(String tag, String message) {
		Log.e(tag, message);
	}

}
