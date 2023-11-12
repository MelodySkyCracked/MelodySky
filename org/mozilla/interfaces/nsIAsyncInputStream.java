/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventTarget;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIInputStreamCallback;

public interface nsIAsyncInputStream
extends nsIInputStream {
    public static final String NS_IASYNCINPUTSTREAM_IID = "{15a15329-00de-44e8-ab06-0d0b0d43dc5b}";
    public static final long WAIT_CLOSURE_ONLY = 1L;

    public void closeWithStatus(long var1);

    public void asyncWait(nsIInputStreamCallback var1, long var2, long var4, nsIEventTarget var6);
}

