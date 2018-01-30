package thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorTest {
	public static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
	private static final int threadCount = 3;
//	public static final Executor exec = Executors.newFixedThreadPool(threadCount);
	public static final Executor exec = Executors.newCachedThreadPool();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			blockingQueue.put(i + "");
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						Thread.sleep(3000l);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("in " + blockingQueue.poll());
				}
			};
			System.out.println("add in " + i);// put all runnable instance in executor regardless the executor size,sort
												// and waiting operator is in
												// executor
			exec.execute(runnable);
		}
	}
}
