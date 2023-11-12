/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMWindowCollection
extends nsISupports {
    public static final String NS_IDOMWINDOWCOLLECTION_IID = "{a6cf906f-15b3-11d2-932e-00805f8add32}";

    public long getLength();

    public nsIDOMWindow item(long var1);

    public nsIDOMWindow namedItem(String var1);
}

