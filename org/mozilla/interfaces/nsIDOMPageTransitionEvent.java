/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;

public interface nsIDOMPageTransitionEvent
extends nsIDOMEvent {
    public static final String NS_IDOMPAGETRANSITIONEVENT_IID = "{b712418b-376f-4f75-b156-5d9ad99fe51f}";

    public boolean getPersisted();

    public void initPageTransitionEvent(String var1, boolean var2, boolean var3, boolean var4);
}

