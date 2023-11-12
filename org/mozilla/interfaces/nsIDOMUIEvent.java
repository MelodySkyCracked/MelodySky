/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAbstractView;
import org.mozilla.interfaces.nsIDOMEvent;

public interface nsIDOMUIEvent
extends nsIDOMEvent {
    public static final String NS_IDOMUIEVENT_IID = "{a6cf90c3-15b3-11d2-932e-00805f8add32}";

    public nsIDOMAbstractView getView();

    public int getDetail();

    public void initUIEvent(String var1, boolean var2, boolean var3, nsIDOMAbstractView var4, int var5);
}

