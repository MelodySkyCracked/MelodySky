/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIXULSortService
extends nsISupports {
    public static final String NS_IXULSORTSERVICE_IID = "{bfd05261-834c-11d2-8eac-00805f29f371}";

    public void sort(nsIDOMNode var1, String var2, String var3);
}

