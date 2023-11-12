/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIAboutModule
extends nsISupports {
    public static final String NS_IABOUTMODULE_IID = "{692303c0-2f83-11d3-8cd0-0060b0fc14a3}";

    public nsIChannel newChannel(nsIURI var1);
}

