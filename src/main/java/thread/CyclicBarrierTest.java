package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	public final CyclicBarrier cyclicBarrier;
	public int count;
	public final ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<String>();
	public final ConcurrentLinkedQueue<Integer> orderQueue = new ConcurrentLinkedQueue<Integer>();

	public CyclicBarrierTest(int count) {
		this.count = count;
		this.cyclicBarrier = new CyclicBarrier(count, new Runnable() {
			public void run() {
				System.out.println(linkedQueue.toString());
			}
		});
		for (int i = 0; i < count; i++) {
			orderQueue.add(i);
		}
	}

	public void start() {
		for (int i = 0; i < count; i++) {
			new Thread() {
				public void run() {
					int i = orderQueue.poll();
					linkedQueue.add(i + "");
					try {
						sleep(3000l);
						System.out.println(i + " is watting...");
						cyclicBarrier.await();
						System.out.println(i + " is wake up");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	public class Worker {

	}

	public static void main(String[] args) {
		new CyclicBarrierTest(5).start();
	}
}
