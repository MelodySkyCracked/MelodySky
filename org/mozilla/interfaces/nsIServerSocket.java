/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIServerSocketListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIServerSocket
extends nsISupports {
    public static final String NS_ISERVERSOCKET_IID = "{a5b64be0-d563-46bb-ae95-132e46fcd42f}";

    public void init(int var1, boolean var2, int var3);

    public void close();

    public void asyncListen(nsIServerSocketListener var1);

    public int getPort();
}

