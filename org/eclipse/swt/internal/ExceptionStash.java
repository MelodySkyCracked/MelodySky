/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import org.eclipse.swt.widgets.Display;

public class ExceptionStash
implements AutoCloseable {
    Throwable storedThrowable;

    public void stash(Throwable throwable) {
        try {
            Display display = Display.getCurrent();
            if (display != null) {
                if (throwable instanceof RuntimeException) {
                    display.getRuntimeExceptionHandler().accept((RuntimeException)throwable);
                    return;
                }
                if (throwable instanceof Error) {
                    display.getErrorHandler().accept((Error)throwable);
                    return;
                }
            }
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
        }
        if (this.storedThrowable != null) {
            this.storedThrowable.addSuppressed(throwable);
        } else {
            this.storedThrowable = throwable;
        }
    }

    @Override
    public void close() {
        if (this.storedThrowable == null) {
            return;
        }
        Throwable throwable = this.storedThrowable;
        this.storedThrowable = null;
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
    }
}

