/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISidebar
extends nsISupports {
    public static final String NS_ISIDEBAR_IID = "{577cb745-8caf-11d3-aaef-00805f8a4905}";

    public void addPanel(String var1, String var2, String var3);

    public void addPersistentPanel(String var1, String var2, String var3);

    public void addSearchEngine(String var1, String var2, String var3, String var4);
}

