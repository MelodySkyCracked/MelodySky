/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDirIndex;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIDirIndexListener
extends nsISupports {
    public static final String NS_IDIRINDEXLISTENER_IID = "{fae4e9a8-1dd1-11b2-b53c-8f3aa1bbf8f5}";

    public void onIndexAvailable(nsIRequest var1, nsISupports var2, nsIDirIndex var3);

    public void onInformationAvailable(nsIRequest var1, nsISupports var2, String var3);
}

