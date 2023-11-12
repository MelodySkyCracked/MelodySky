/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXULWindow;

public interface nsIWindowMediatorListener
extends nsISupports {
    public static final String NS_IWINDOWMEDIATORLISTENER_IID = "{2f276982-0d60-4377-a595-d350ba516395}";

    public void onWindowTitleChange(nsIXULWindow var1, String var2);

    public void onOpenWindow(nsIXULWindow var1);

    public void onCloseWindow(nsIXULWindow var1);
}

