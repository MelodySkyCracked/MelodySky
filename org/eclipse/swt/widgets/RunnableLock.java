/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.widgets.Display;

class RunnableLock {
    Runnable runnable;
    Thread thread;
    Throwable throwable;

    RunnableLock(Runnable runnable) {
        this.runnable = runnable;
    }

    boolean done() {
        return this.runnable == null || this.throwable != null;
    }

    void run(Display display) {
        if (this.runnable != null) {
            try {
                this.runnable.run();
            }
            catch (RuntimeException runtimeException) {
                display.getRuntimeExceptionHandler().accept(runtimeException);
            }
            catch (Error error) {
                display.getErrorHandler().accept(error);
            }
        }
        this.runnable = null;
    }
}

