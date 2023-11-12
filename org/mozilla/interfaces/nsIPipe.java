/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAsyncInputStream;
import org.mozilla.interfaces.nsIAsyncOutputStream;
import org.mozilla.interfaces.nsIMemory;
import org.mozilla.interfaces.nsISupports;

public interface nsIPipe
extends nsISupports {
    public static final String NS_IPIPE_IID = "{f4211abc-61b3-11d4-9877-00c04fa0cf4a}";

    public void init(boolean var1, boolean var2, long var3, long var5, nsIMemory var7);

    public nsIAsyncInputStream getInputStream();

    public nsIAsyncOutputStream getOutputStream();
}

