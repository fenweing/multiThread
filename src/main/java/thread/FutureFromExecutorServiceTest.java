package thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureFromExecutorServiceTest {
	private final static ExecutorService execService = Executors.newCachedThreadPool();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Future<String> future = fromExecutor();
		// try {
		// System.out.println(future.get(3000, TimeUnit.MILLISECONDS));
		// } catch (TimeoutException e) {
		// System.out.println("ops, time out!");
		// future.cancel(true);
		// e.printStackTrace();
		// }
		// CompletionService<String> completionService = fromCompletionService();
		// for (int i = 0; i < 6; i++) {
		// System.out.println("before take");
		// Future<String> future2 = completionService.take();
		// try {
		// System.out.println(future2.get(1000, TimeUnit.MILLISECONDS));
		// } catch (TimeoutException e) {
		// System.out.println("ops, time out!");
		// future2.cancel(true);
		// e.printStackTrace();
		// }
		// }
		Map<String, Object> map = fromInvokeAll();
		List<Future<String>> futures = (List<Future<String>>) map.get("futures");
		List<Callable<String>> callables = (List<Callable<String>>) map.get("callables");
		for (Future tempFuture : futures) {
			try {
				try {
					System.out.println(tempFuture.get(3000l, TimeUnit.MILLISECONDS));
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			} catch (CancellationException e) {
				System.out.println("time out");
				e.printStackTrace();
			}
		}

	}

	// there is no timeout parameter in submit method,single future`s time limit is
	// called in future`s methos get(timeoutNumber,timeUnit)
	public static Future<String> fromExecutor() {
		Future<String> future = execService.submit(new Callable<String>() {
			public String call() throws Exception {
				// Thread.sleep(4000l);
				for (int i = 0; i < 5; i++) {
					System.out.println("in common executor call");
				}
				return "common executor return";
			}
		});
		// future.get(12, TimeUnit.NANOSECONDS);
		return future;
	}

	// future would be pushed in blockQueue of completionService only if the future
	// has been completed.The method submit of completionService itself has no right
	// to limit timeout paramter of each future,actually,main function of
	// completionService is sort block completed future by blockQueue which is an
	// important part of completionService
	public static CompletionService<String> fromCompletionService() {
		CompletionService<String> completionService = new ExecutorCompletionService<String>(execService);
		for (int j = 0; j < 5; j++) {
			completionService.submit(new Callable<String>() {
				public String call() throws Exception {
					Thread.sleep(4000);
					System.out.println("in completionServie call");
					return "completionServie return";
				}
			});
		}
		return completionService;
	}

	// this is a timeout invoker of a list of 'future',because their timeout
	// property is handled by invokeAll.If one future is timeout,it would throw a
	// CancellationException,it emerge in future.get() is getting a
	// CancellationException.
	// NOTE: if a callable is timeout in invokeAll method,its return value is
	// fixed(CancellationException),that`s to say,although you set timeout parameter
	// in future`s get method,the parameter would be ignored because there is a
	// property state in future,which will be set to a value(6) to indicate current
	// future is invoked by invokeAll method and return value is
	// CancellationException
	public static Map<String, Object> fromInvokeAll() throws InterruptedException {
		List<Callable<String>> callables = new ArrayList<Callable<String>>();
		for (int i = 0; i < 5; i++) {
			callables.add(new Callable<String>() {
				public String call() throws Exception {
					Thread.sleep(3000l);
					System.out.println("in invokeAll call");
					return "invokeAll return";
				}
			});
		}
		List<Future<String>> futures = execService.invokeAll(callables, 2000, TimeUnit.MILLISECONDS);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("futures", futures);
		map.put("callables", callables);
		return map;
	}
}
