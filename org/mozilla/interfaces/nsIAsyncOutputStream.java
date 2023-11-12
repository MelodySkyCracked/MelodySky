/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventTarget;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsIOutputStreamCallback;

public interface nsIAsyncOutputStream
extends nsIOutputStream {
    public static final String NS_IASYNCOUTPUTSTREAM_IID = "{10dc9c94-8aff-49c6-8af9-d7fdb7339dae}";
    public static final long WAIT_CLOSURE_ONLY = 1L;

    public void closeWithStatus(long var1);

    public void asyncWait(nsIOutputStreamCallback var1, long var2, long var4, nsIEventTarget var6);
}

