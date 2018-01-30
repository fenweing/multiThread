package thread;

import java.util.concurrent.atomic.AtomicInteger;

public class SychronizedLock {
	private String name;
	private AtomicInteger integer = new AtomicInteger();

	public synchronized String getString() {
		Thread.currentThread().yield();
		return name;
	}
}
