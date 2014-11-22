package org.droid4j.timer;

public abstract class SecondTimer extends MilliSecondTimer {

	public SecondTimer(int second) {
		super(second * 1000L);
	}
}
