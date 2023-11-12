/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMEventListener
extends nsISupports {
    public static final String NS_IDOMEVENTLISTENER_IID = "{df31c120-ded6-11d1-bd85-00805f8ae3f4}";

    public void handleEvent(nsIDOMEvent var1);
}

