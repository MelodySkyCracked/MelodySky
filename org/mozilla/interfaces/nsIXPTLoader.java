/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXPTLoaderSink;

public interface nsIXPTLoader
extends nsISupports {
    public static final String NS_IXPTLOADER_IID = "{368a15d9-17a9-4c2b-ac3d-a35b3a22b876}";

    public void enumerateEntries(nsILocalFile var1, nsIXPTLoaderSink var2);

    public nsIInputStream loadEntry(nsILocalFile var1, String var2);
}

