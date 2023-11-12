/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAbstractView;
import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsIDOMUIEvent;

public interface nsIDOMMouseEvent
extends nsIDOMUIEvent {
    public static final String NS_IDOMMOUSEEVENT_IID = "{ff751edc-8b02-aae7-0010-8301838a3123}";

    public int getScreenX();

    public int getScreenY();

    public int getClientX();

    public int getClientY();

    public boolean getCtrlKey();

    public boolean getShiftKey();

    public boolean getAltKey();

    public boolean getMetaKey();

    public int getButton();

    public nsIDOMEventTarget getRelatedTarget();

    public void initMouseEvent(String var1, boolean var2, boolean var3, nsIDOMAbstractView var4, int var5, int var6, int var7, int var8, int var9, boolean var10, boolean var11, boolean var12, boolean var13, int var14, nsIDOMEventTarget var15);
}

