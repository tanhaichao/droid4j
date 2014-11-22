package io.leopard.droid4j.util;

public class RunnableUtil {

	// public static void sleep(long time) {
	// try {
	// Thread.sleep(time);
	// }
	// catch (InterruptedException e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	// }

	public static void run(Runnable runnable) {
		new Thread(runnable).start();
	}

	public static void run(final Runnable runnable, final Runnable callback) {
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				runnable.run();
				callback.run();
			}
		};
		new Thread(runner).start();
	}
}
