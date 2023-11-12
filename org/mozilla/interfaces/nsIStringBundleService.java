/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIStringBundle;
import org.mozilla.interfaces.nsISupports;

public interface nsIStringBundleService
extends nsISupports {
    public static final String NS_ISTRINGBUNDLESERVICE_IID = "{d85a17c0-aa7c-11d2-9b8c-00805f8a16d9}";

    public nsIStringBundle createBundle(String var1);

    public nsIStringBundle createExtensibleBundle(String var1);

    public String formatStatusMessage(long var1, String var3);

    public void flushBundles();
}

