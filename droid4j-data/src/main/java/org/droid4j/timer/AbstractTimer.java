package org.droid4j.timer;

/**
 * 定时器.
 * 
 * @author 阿海
 * 
 */
public abstract class AbstractTimer implements Timer {

	private volatile boolean isRun = true;

	private Thread thread;

	public void start() {
		thread = new Thread() {
			@Override
			public void run() {
				while (isRun) {
					try {
						AbstractTimer.this.run();
					}
					catch (Exception e) {
						e.printStackTrace();
					}

					try {
						AbstractTimer.this.sleep();
					}
					catch (InterruptedException e) {

					}
				}
			}
		};
		thread.start();
	}

	@Override
	public void stop() {
		isRun = false;
		thread.interrupt();
	}

}
