package com.ce.cechat.utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CE Chen
 *
 * 线程池管理类
 */
public class ThreadPools {

    private volatile static ThreadPools sThreadPools;


    //-----------------线程池相关配置及初始化 Start-----------------

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work

    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE =
            new LinkedBlockingQueue<Runnable>(128);

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger COUNT = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + COUNT.getAndIncrement());
        }
    };

    private static final ExecutorService THREAD_POOL_EXECUTOR;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                POOL_WORK_QUEUE, THREAD_FACTORY);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    //-----------------线程池相关配置及初始化 End-----------------


    private ThreadPools() {

    }

    public static ThreadPools newInstance() {
        if (sThreadPools == null) {
            synchronized (ThreadPools.class) {
                if (sThreadPools == null) {
                    sThreadPools = new ThreadPools();
                }
            }
        }
        return sThreadPools;
    }

    public void execute(Runnable task) {
        THREAD_POOL_EXECUTOR.execute(task);
    }

    public Future<?> submit(Runnable task) {
        return THREAD_POOL_EXECUTOR.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return THREAD_POOL_EXECUTOR.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return THREAD_POOL_EXECUTOR.submit(task, result);
    }

    public void shutdown() {
        THREAD_POOL_EXECUTOR.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return THREAD_POOL_EXECUTOR.shutdownNow();
    }

}
