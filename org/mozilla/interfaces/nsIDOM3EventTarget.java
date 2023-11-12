/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventGroup;
import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOM3EventTarget
extends nsISupports {
    public static final String NS_IDOM3EVENTTARGET_IID = "{3e9c01a7-de97-4c3b-8294-b4bd9d7056d1}";

    public void addGroupedEventListener(String var1, nsIDOMEventListener var2, boolean var3, nsIDOMEventGroup var4);

    public void removeGroupedEventListener(String var1, nsIDOMEventListener var2, boolean var3, nsIDOMEventGroup var4);

    public boolean canTrigger(String var1);

    public boolean isRegisteredHere(String var1);
}

