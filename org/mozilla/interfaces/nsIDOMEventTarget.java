/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMEventTarget
extends nsISupports {
    public static final String NS_IDOMEVENTTARGET_IID = "{1c773b30-d1cf-11d2-bd95-00805f8ae3f4}";

    public void addEventListener(String var1, nsIDOMEventListener var2, boolean var3);

    public void removeEventListener(String var1, nsIDOMEventListener var2, boolean var3);

    public boolean dispatchEvent(nsIDOMEvent var1);
}

