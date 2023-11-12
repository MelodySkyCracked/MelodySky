/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer.debug;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler;

public class RuntimeDecompilerAsync
extends RuntimeDecompiler
implements Runnable,
Thread.UncaughtExceptionHandler {
    private final BlockingQueue queue = new LinkedBlockingQueue();
    private final Thread thread = new Thread((Runnable)this, "Decompiler thread");
    private boolean run = true;

    public RuntimeDecompilerAsync(File file) {
        super(file);
        this.thread.setDaemon(true);
        this.thread.setPriority(1);
        this.thread.setUncaughtExceptionHandler(this);
        this.thread.start();
    }

    @Override
    public void decompile(File file) {
        if (this.run) {
            this.queue.offer(file);
        } else {
            super.decompile(file);
        }
    }

    @Override
    public void run() {
        while (this.run) {
            try {
                File file = (File)this.queue.take();
                super.decompile(file);
            }
            catch (InterruptedException interruptedException) {
                this.run = false;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[]{throwable.getClass().getName(), throwable.getMessage()});
        this.flush();
    }

    private void flush() {
        this.run = false;
        File file = null;
        while ((file = (File)this.queue.poll()) != null) {
            this.decompile(file);
        }
    }
}

