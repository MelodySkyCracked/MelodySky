/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsIStreamListenerTee
extends nsIStreamListener {
    public static final String NS_ISTREAMLISTENERTEE_IID = "{fb683e76-d42b-41a4-8ae6-65a6c2b146e5}";

    public void init(nsIStreamListener var1, nsIOutputStream var2);
}

