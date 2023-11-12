/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMMutationEvent
extends nsIDOMEvent {
    public static final String NS_IDOMMUTATIONEVENT_IID = "{8e440d86-886a-4e76-9e59-c13b939c9a4b}";
    public static final int MODIFICATION = 1;
    public static final int ADDITION = 2;
    public static final int REMOVAL = 3;

    public nsIDOMNode getRelatedNode();

    public String getPrevValue();

    public String getNewValue();

    public String getAttrName();

    public int getAttrChange();

    public void initMutationEvent(String var1, boolean var2, boolean var3, nsIDOMNode var4, String var5, String var6, String var7, int var8);
}

