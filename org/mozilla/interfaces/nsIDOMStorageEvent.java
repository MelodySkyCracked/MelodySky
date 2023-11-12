/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;

public interface nsIDOMStorageEvent
extends nsIDOMEvent {
    public static final String NS_IDOMSTORAGEEVENT_IID = "{fc540c28-8edd-4b7a-9c30-8638289b7a7d}";

    public String getDomain();

    public void initStorageEvent(String var1, boolean var2, boolean var3, String var4);

    public void initStorageEventNS(String var1, String var2, boolean var3, boolean var4, String var5);
}

