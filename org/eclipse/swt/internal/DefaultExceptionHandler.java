/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import java.util.function.Consumer;

public class DefaultExceptionHandler {
    public static final Consumer RUNTIME_EXCEPTION_HANDLER = DefaultExceptionHandler::lambda$static$0;
    public static final Consumer RUNTIME_ERROR_HANDLER = DefaultExceptionHandler::lambda$static$1;

    private static void lambda$static$1(Error error) {
        throw error;
    }

    private static void lambda$static$0(RuntimeException runtimeException) {
        throw runtimeException;
    }
}

