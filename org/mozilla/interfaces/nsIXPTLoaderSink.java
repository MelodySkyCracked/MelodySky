/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIXPTLoaderSink
extends nsISupports {
    public static final String NS_IXPTLOADERSINK_IID = "{6e48c500-8682-4730-add6-7db693b9e7ba}";

    public void foundEntry(String var1, int var2, nsIInputStream var3);
}

