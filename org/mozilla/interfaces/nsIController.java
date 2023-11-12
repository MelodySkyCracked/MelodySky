/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIController
extends nsISupports {
    public static final String NS_ICONTROLLER_IID = "{d5b61b82-1da4-11d3-bf87-00105a1b0627}";

    public boolean isCommandEnabled(String var1);

    public boolean supportsCommand(String var1);

    public void doCommand(String var1);

    public void onEvent(String var1);
}

