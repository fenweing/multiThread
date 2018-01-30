package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.sun.org.apache.bcel.internal.generic.NEW;

import exception.CancelException;
import thread.Computable;
import thread.Memorizer;
import thread.SychronizedLock;

public class Test {
	// public final int x, y;
	static boolean ready;
	static int i;
	static String name = "zs";
	public static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
	private static final int threadCount = 3;
	public static final Executor exec = Executors.newFixedThreadPool(threadCount);
	// static {
	// System.out.println(name);
	// }

	// public Test(int x, int y) {
	// this.x = x;
	// this.y = y;
	// // TODO Auto-generated constructor stub
	// }

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		// new Test().instructRerange();
		// ready = true;
		// i = 49;
		// User user = new User();
		// user.setAge(24);
		// user.setName("zs");
		// user.setSex("women");
		// User user2 = new User();
		// user2.setAge(25);
		// user2.setName("lise");
		// user2.setSex("man");
		// User user3 = new User();
		// user3.setAge(24);
		// user3.setName("zs");
		// user3.setSex("women");
		// AtomicReference<User> atomicReference = new AtomicReference<User>(user);
		// System.out.println(atomicReference.get().getName());
		// System.out.println(atomicReference.compareAndSet(user3, user2));
		// System.out.println(atomicReference.get().getName());
		// AtomicInteger atomicInteger = new AtomicInteger();
		// atomicInteger.set(11);
		// atomicInteger.compareAndSet(11, 12);
		// System.out.println(atomicInteger.get());
		// ReentrantLock reentrantLock = new ReentrantLock(true);
		// reentrantLock.lockInterruptibly();
		// // final java.util.List<String> list = new ArrayList<String>();
		// final java.util.List<String> list = new CopyOnWriteArrayList<String>();
		// for (int i = 0; i < 100; i++) {
		// list.add("a" + i);
		// }
		// list.add("a");
		// list.add("b");
		// final java.util.List<String> list2 = Collections.synchronizedList(list);
		// new Thread(new Runnable() {
		// public void run() {
		// // synchronized (list2) {
		// Iterator<String> iterator = list.iterator();
		// while (iterator.hasNext()) {
		// System.out.println(iterator.next());
		// }
		// // }
		// }
		// }).start();
		// new Thread() {
		// @Override
		// public void run() {
		// int k = 0;
		// // synchronized (list2) {
		// Iterator<String> iterator = list.iterator();
		// while (iterator.hasNext()) {
		// System.out.println(k++);
		// iterator.next();
		// iterator.remove();
		// }
		// // }
		// }
		// }.start();
		// final Test test = new Test();
		// new Thread(new Runnable() {
		//
		// public void run() {
		// // TODO Auto-generated method stub
		// try {
		// test.get();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }).start();
		// Thread.sleep(500l);
		// System.out.println(test.subget(i));
		// Thread thread = new Thread() {
		// public void run() {
		// // try {
		// // System.out.println(blockingQueue.take());
		// System.out.println("fs");
		// try {
		// sleep(1000l);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// // } catch (InterruptedException e) {
		// // e.printStackTrace();
		// //// Thread.currentThread().interrupt();
		// // System.out.println("interrupted");
		// // }
		// }
		// };
		// thread.start();
		// // thread.start();
		// // interruptTest(thread);
		// Thread.sleep(3000l);
		// // thread.interrupt();
		// // blockingQueue.clear();
		// System.out.println("removed");
		// Memorizer<String, String> cache = new Memorizer<String, String>(new
		// ExpensiveComputor());
		// for (int i = 0; i < 10; i++) {
		// System.out.println(cache.compute("fdsafdsafdsfadfasfsadfds"));
		// }
		// Runnable runnable = new Runnable() {
		// public void run() {
		// System.out.println("in run");
		// }
		// };
		// runnable.run();
		// for (int i = 0; i < 100; i++) {
		// blockingQueue.put(i + "");
		// Runnable runnable = new Runnable() {
		// public void run() {
		// try {
		// Thread.sleep(3000l);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// System.out.println("in " + blockingQueue.poll());
		// }
		// };
		// System.out.println("add in " + i);
		// exec.execute(runnable);
		// }
		// Executor executor = Executors.newCachedThreadPool();
		// executor.execute(new Runnable() {
		// public void run() {
		// System.out.println("ff");
		// }
		// });
		// BigInteger bigInteger = BigInteger.ONE;
		// System.out.println(bigInteger.nextProbablePrime());
		TestRunnable testRunnable = new TestRunnable();
		Thread thread = new Thread(testRunnable);
		thread.start();
		Thread thread2 = new Thread(testRunnable);
		thread2.start();
		thread.join();
		thread2.join();
		testRunnable.getQueue().forEach(item -> System.out.println(item));
	}

	public static class TestRunnable implements Runnable {
		private final List<String> list = new ArrayList();

		public void run() {
			try {
				Thread.sleep(3000);
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (this) {
				list.add("hello world");
			}
		}

		public synchronized ArrayList getQueue() {
			return new ArrayList(list);
		}
	}

	public static void interruptTest(Thread thread) {
		thread.start();
	}

	public void instructRerange() {
		new Thread(new Runnable() {
			public void run() {
				if (!ready) {
					Thread.yield();
				}
				System.out.println(i);
			}
		}).start();
	}

	public synchronized String subget(int i) throws InterruptedException {
		return i + "hs";
	}

	public synchronized void get() throws InterruptedException {
		Thread.sleep(3000l);
		System.out.println(subget(12));
		Thread.sleep(1000l);
	}

	public static class ExpensiveComputor implements Computable<String, String> {
		public String compute(String arg) throws ExecutionException, InterruptedException {
			byte[] bytes = null;

			try {
				System.out.println("in compute");
				bytes = MessageDigest.getInstance("MD5").digest(arg.getBytes());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new CancelException(123, e.getMessage());
			}
			return new String(bytes);
		}
	}
}
