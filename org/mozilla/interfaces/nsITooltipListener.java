/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITooltipListener
extends nsISupports {
    public static final String NS_ITOOLTIPLISTENER_IID = "{44b78386-1dd2-11b2-9ad2-e4eee2ca1916}";

    public void onShowTooltip(int var1, int var2, String var3);

    public void onHideTooltip();
}

