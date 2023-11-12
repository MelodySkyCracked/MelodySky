/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIInputStreamPump
extends nsIRequest {
    public static final String NS_IINPUTSTREAMPUMP_IID = "{400f5468-97e7-4d2b-9c65-a82aecc7ae82}";

    public void init(nsIInputStream var1, long var2, long var4, long var6, long var8, boolean var10);

    public void asyncRead(nsIStreamListener var1, nsISupports var2);
}

