package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * cyclicBarrier.await();次数到了配置值后往下走；
 * new CyclicBarrier（count,runable）runable的执行线程是最后一个触发await（）并达到次数的线程
 */
public class CyclicBarrierTest {
    public final CyclicBarrier cyclicBarrier;
    public int count;
    public final ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<String>();
    public final ConcurrentLinkedQueue<Integer> orderQueue = new ConcurrentLinkedQueue<Integer>();
    private Integer times = 2;
    private Integer currentTimes = 1;
    private AtomicInteger order = new AtomicInteger(0);

    public CyclicBarrierTest(int count) {
        this.count = count;
        this.cyclicBarrier = new CyclicBarrier(count, new Runnable() {
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println("thread-" + threadName);
                if (++currentTimes <= times) {
                    start();
                    System.out.println("current:" + currentTimes);
                }
            }
        });
    }

    public void start() {
        for (int i = 0; i < count; i++) {
            new Thread() {
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    try {
                        sleep(5000L);
                        int i = order.addAndGet(1);
                        System.out.println(threadName + "-" + i + " is watting...");
                        cyclicBarrier.await();
                        System.out.println(threadName + "-" + i + " is wake up");
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
