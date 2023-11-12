/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAbstractView;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMUIEvent;

public interface nsIDOMXULCommandEvent
extends nsIDOMUIEvent {
    public static final String NS_IDOMXULCOMMANDEVENT_IID = "{f9fa8205-a988-4828-9228-f3332d5475ac}";

    public boolean getCtrlKey();

    public boolean getShiftKey();

    public boolean getAltKey();

    public boolean getMetaKey();

    public nsIDOMEvent getSourceEvent();

    public void initCommandEvent(String var1, boolean var2, boolean var3, nsIDOMAbstractView var4, int var5, boolean var6, boolean var7, boolean var8, boolean var9, nsIDOMEvent var10);
}

