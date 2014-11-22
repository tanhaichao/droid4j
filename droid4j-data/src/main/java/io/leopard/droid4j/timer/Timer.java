package io.leopard.droid4j.timer;

public interface Timer {

	void sleep() throws InterruptedException;

	void run();

	void start();

	void stop();

}
