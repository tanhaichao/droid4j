package org.droid4j.timer;

public abstract class MilliSecondTimer extends AbstractTimer {

	protected long time;

	public MilliSecondTimer(long time) {
		this.time = time;
	}

	@Override
	public void sleep() throws InterruptedException {
		Thread.sleep(time);
	}
}
