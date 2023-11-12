/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISHEntry;
import org.mozilla.interfaces.nsISupports;

public interface nsISHContainer
extends nsISupports {
    public static final String NS_ISHCONTAINER_IID = "{65281ba2-988a-11d3-bdc7-0050040a9b44}";

    public int getChildCount();

    public void addChild(nsISHEntry var1, int var2);

    public void removeChild(nsISHEntry var1);

    public nsISHEntry getChildAt(int var1);
}

