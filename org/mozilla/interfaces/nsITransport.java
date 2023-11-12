/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventTarget;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransportEventSink;

public interface nsITransport
extends nsISupports {
    public static final String NS_ITRANSPORT_IID = "{cbb0baeb-5fcb-408b-a2be-9f8fc98d0af1}";
    public static final long OPEN_BLOCKING = 1L;
    public static final long OPEN_UNBUFFERED = 2L;
    public static final long STATUS_READING = 2152398856L;
    public static final long STATUS_WRITING = 2152398857L;

    public nsIInputStream openInputStream(long var1, long var3, long var5);

    public nsIOutputStream openOutputStream(long var1, long var3, long var5);

    public void close(long var1);

    public void setEventSink(nsITransportEventSink var1, nsIEventTarget var2);
}

