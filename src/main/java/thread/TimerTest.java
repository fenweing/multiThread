package thread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TimerTest {
	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 1);
		Thread.sleep(1);
		System.out.println("==");
		timer.schedule(new ThrowTask(), 1);
		System.out.println("==2");
		Thread.sleep(2);
		DelayQueue<DelayTast> delayQueue = new DelayQueue<TimerTest.DelayTast>();

	}

	public static class DelayTast implements Delayed {

		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			return 0;
		}

		public long getDelay(TimeUnit unit) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public static class ThrowTask extends TimerTask {
		@Override
		public void run() {
			throw new RuntimeException();
		}

	}
}
