/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsISimpleStreamListener
extends nsIStreamListener {
    public static final String NS_ISIMPLESTREAMLISTENER_IID = "{a9b84f6a-0824-4278-bae6-bfca0570a26e}";

    public void init(nsIOutputStream var1, nsIRequestObserver var2);
}

