/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIControllerCommandTable;
import org.mozilla.interfaces.nsISupports;

public interface nsIControllerContext
extends nsISupports {
    public static final String NS_ICONTROLLERCONTEXT_IID = "{47b82b60-a36f-4167-8072-6f421151ed50}";

    public void init(nsIControllerCommandTable var1);

    public void setCommandContext(nsISupports var1);
}

