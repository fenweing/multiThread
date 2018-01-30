package thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CloseLockTest {
	static List<String> list = new ArrayList<String>();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// timeTask(5);
		futureTaskTest();
	}

	public static void task() {
		list.add("task element");
		System.out.println("in task");
	}

	public static void futureTaskTest() throws InterruptedException {
		FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
			public String call() throws InterruptedException {
				task();
				Thread.sleep(3000l);
				return "in call";
			}
		});
		Thread thread = new Thread(futureTask);
		thread.start();
		System.out.println("before get");
		try {
			System.out.println(futureTask.get());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw launderThrowable(e);
		}
		System.out.println(list.get(0));
	}

	public static RuntimeException launderThrowable(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else if (t instanceof Error) {
			// return (Error) t;
			return new RuntimeException();
		} else {
			throw new IllegalStateException("unchecked", t);
		}
	}

	public static void timeTask(int nThreads) {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
			new Thread() {
				public void run() {
					try {
						startGate.await();
						sleep(1000);
						task();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						endGate.countDown();
					}
				}
			}.start();
		}
		Long start = System.currentTimeMillis();
		startGate.countDown();
		try {
			endGate.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println((System.currentTimeMillis() - start));
	}
}
