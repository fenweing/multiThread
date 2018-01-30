package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import exception.CancelException;

public class Memorizer<A, V> implements Computable<A, V> {
	private Computable<A, V> computable;

	private ConcurrentHashMap<A, FutureTask<V>> cache = new ConcurrentHashMap<A, FutureTask<V>>();

	public Memorizer(Computable<A, V> computable) {
		this.computable = computable;
	}

	public V compute(final A arg) throws ExecutionException, InterruptedException {
		while (true) {
			FutureTask<V> f = cache.get(arg);
			if (null == f) {
				FutureTask<V> ft = new FutureTask<V>(new Callable<V>() {
					public V call() throws ExecutionException, InterruptedException {
						return computable.compute(arg);
					}
				});
				f = cache.putIfAbsent(arg, ft);
				if (null == f) {
					f = ft;
					f.run();
				}
			}
			try {
				return f.get();
			} catch (CancelException e) {
				e.printStackTrace();
				cache.remove(arg);
			}
		}
	}
}
