/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIWebBrowserStream
extends nsISupports {
    public static final String NS_IWEBBROWSERSTREAM_IID = "{86d02f0e-219b-4cfc-9c88-bd98d2cce0b8}";

    public void openStream(nsIURI var1, String var2);

    public void appendToStream(byte[] var1, long var2);

    public void closeStream();
}

