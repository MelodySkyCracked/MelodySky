/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

public class XPCOMException
extends RuntimeException {
    public long errorcode;
    private static final long serialVersionUID = 198521829884000593L;

    public XPCOMException() {
        this(2147500037L, "Unspecified internal XPCOM error");
    }

    public XPCOMException(String string) {
        this(2147500037L, string);
    }

    public XPCOMException(long l2) {
        this(l2, "Internal XPCOM error");
    }

    public XPCOMException(long l2, String string) {
        super(string + "  (0x" + Long.toHexString(l2) + ")");
        this.errorcode = l2;
    }
}

