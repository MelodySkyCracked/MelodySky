/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIConsoleMessage;
import org.mozilla.interfaces.nsISupports;

public interface nsIConsoleListener
extends nsISupports {
    public static final String NS_ICONSOLELISTENER_IID = "{eaaf61d6-1dd1-11b2-bc6e-8fc96480f20d}";

    public void observe(nsIConsoleMessage var1);
}

