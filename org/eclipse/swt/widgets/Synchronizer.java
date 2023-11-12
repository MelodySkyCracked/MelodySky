/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.RunnableLock;

public class Synchronizer {
    Display display;
    final ConcurrentLinkedQueue messages = new ConcurrentLinkedQueue();
    Thread syncThread;
    static final int GROW_SIZE = 4;
    static final int MESSAGE_LIMIT = 64;
    static final boolean IS_COCOA = "cocoa".equals(SWT.getPlatform());
    static final boolean IS_GTK = "gtk".equals(SWT.getPlatform());

    public Synchronizer(Display display) {
        this.display = display;
    }

    void moveAllEventsTo(Synchronizer synchronizer) {
        ArrayList arrayList = new ArrayList();
        ConcurrentLinkedQueue concurrentLinkedQueue = synchronizer.messages;
        ArrayList arrayList2 = arrayList;
        Objects.requireNonNull(arrayList2);
        concurrentLinkedQueue.removeIf(arrayList2::add);
        ConcurrentLinkedQueue concurrentLinkedQueue2 = this.messages;
        ConcurrentLinkedQueue concurrentLinkedQueue3 = synchronizer.messages;
        Objects.requireNonNull(concurrentLinkedQueue3);
        concurrentLinkedQueue2.removeIf(concurrentLinkedQueue3::add);
        synchronizer.messages.addAll(arrayList);
    }

    void addLast(RunnableLock runnableLock) {
        boolean bl = this.messages.isEmpty();
        this.messages.add(runnableLock);
        if (bl) {
            this.display.wakeThread();
        }
    }

    protected void asyncExec(Runnable runnable) {
        if (runnable == null && !IS_GTK && !IS_COCOA) {
            this.display.wake();
            return;
        }
        this.addLast(new RunnableLock(runnable));
    }

    boolean isMessagesEmpty() {
        return this.messages.isEmpty();
    }

    void releaseSynchronizer() {
        this.display = null;
        this.messages.clear();
        this.syncThread = null;
    }

    RunnableLock removeFirst() {
        return (RunnableLock)this.messages.poll();
    }

    boolean runAsyncMessages() {
        return this.runAsyncMessages(false);
    }

    boolean runAsyncMessages(boolean bl) {
        boolean bl2 = false;
        do {
            RunnableLock runnableLock;
            if ((runnableLock = this.removeFirst()) == null) {
                return bl2;
            }
            bl2 = true;
            RunnableLock runnableLock2 = runnableLock;
            synchronized (runnableLock2) {
                this.syncThread = runnableLock.thread;
                this.display.sendPreEvent(0);
                try {
                    runnableLock.run(this.display);
                }
                catch (Throwable throwable) {
                    runnableLock.throwable = throwable;
                    SWT.error(46, runnableLock.throwable);
                    if (this.display != null && !this.display.isDisposed()) {
                        this.display.sendPostEvent(0);
                    }
                    this.syncThread = null;
                    runnableLock.notifyAll();
                }
                if (this.display != null && !this.display.isDisposed()) {
                    this.display.sendPostEvent(0);
                }
                this.syncThread = null;
                runnableLock.notifyAll();
            }
        } while (bl);
        return bl2;
    }

    protected void syncExec(Runnable runnable) {
        RunnableLock runnableLock = null;
        Object object = Device.class;
        synchronized (Device.class) {
            block17: {
                block16: {
                    if (this.display == null || this.display.isDisposed()) {
                        SWT.error(45);
                    }
                    if (!this.display.isValidThread()) {
                        if (runnable == null) {
                            this.display.wake();
                            // ** MonitorExit[var3_3] (shouldn't be in output)
                            return;
                        }
                        runnableLock = new RunnableLock(runnable);
                        runnableLock.thread = Thread.currentThread();
                        this.addLast(runnableLock);
                    }
                    // ** MonitorExit[var3_3] (shouldn't be in output)
                    if (runnableLock != null) break block17;
                    if (runnable != null) {
                        this.display.sendPreEvent(0);
                        try {
                            runnable.run();
                        }
                        catch (RuntimeException runtimeException) {
                            this.display.getRuntimeExceptionHandler().accept(runtimeException);
                            if (this.display != null && !this.display.isDisposed()) {
                                this.display.sendPostEvent(0);
                            }
                        }
                        catch (Error error) {
                            this.display.getErrorHandler().accept(error);
                            if (this.display == null || this.display.isDisposed()) break block16;
                            this.display.sendPostEvent(0);
                        }
                        if (this.display != null && !this.display.isDisposed()) {
                            this.display.sendPostEvent(0);
                        }
                    }
                }
                return;
            }
            object = runnableLock;
            synchronized (object) {
                boolean bl = false;
                while (!runnableLock.done()) {
                    try {
                        runnableLock.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        bl = true;
                    }
                }
                if (bl) {
                    Thread.currentThread().interrupt();
                }
                if (runnableLock.throwable != null) {
                    SWT.error(46, runnableLock.throwable);
                }
            }
            return;
        }
    }
}

