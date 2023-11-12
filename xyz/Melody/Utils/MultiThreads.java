/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 */
package xyz.Melody.Utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiThreads {
    private static final ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("MelodySky %d").build());
    private static final ScheduledExecutorService runnableExecutor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1);

    public static void runAsync(Runnable runnable) {
        MultiThreads.submit(runnable);
    }

    public static void runAsync(Runnable ... runnableArray) {
        for (Runnable runnable : runnableArray) {
            MultiThreads.runAsync(runnable);
        }
    }

    public static Future submit(Runnable runnable) {
        return executorService.submit(runnable);
    }

    public static void schedule(Runnable runnable, long l2, TimeUnit timeUnit) {
        MultiThreads.submitScheduled(runnable, l2, timeUnit);
    }

    public static ScheduledFuture submitScheduled(Runnable runnable, long l2, TimeUnit timeUnit) {
        return runnableExecutor.schedule(runnable, l2, timeUnit);
    }
}

