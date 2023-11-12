/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

public class XPCOMInitializationException
extends RuntimeException {
    private static final long serialVersionUID = -7067350325909231055L;

    public XPCOMInitializationException(String string) {
        super(string);
    }

    public XPCOMInitializationException(Throwable throwable) {
        super(throwable);
    }

    public XPCOMInitializationException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

