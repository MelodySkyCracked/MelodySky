/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIKeygenThread
extends nsISupports {
    public static final String NS_IKEYGENTHREAD_IID = "{8712a243-5539-447c-9f47-8653f40c3a09}";

    public void startKeyGeneration(nsIObserver var1);

    public void userCanceled(boolean[] var1);
}

