package io.github.maxwell_nc.imageloader.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池控制器
 */
public class ThreadPoolController {

	/**
	 * 线程池
	 */
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);

	/**
	 * 获取线程池
	 * @return 线程池
	 */
	public static Executor getThreadPool() {
		return threadPool;
	}

}
