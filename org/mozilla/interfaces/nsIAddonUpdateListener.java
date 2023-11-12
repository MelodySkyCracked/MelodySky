/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdateItem;

public interface nsIAddonUpdateListener
extends nsISupports {
    public static final String NS_IADDONUPDATELISTENER_IID = "{bb86037c-98c1-4c22-8e03-1e4c9fc89a8e}";

    public void onStateChange(nsIUpdateItem var1, short var2, int var3);

    public void onProgress(nsIUpdateItem var1, long var2, long var4);
}

