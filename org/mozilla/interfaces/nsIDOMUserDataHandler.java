/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIVariant;

public interface nsIDOMUserDataHandler {
    public static final String NS_IDOMUSERDATAHANDLER_IID = "{5470deff-03c9-41b7-a824-e3225266b343}";
    public static final int NODE_CLONED = 1;
    public static final int NODE_IMPORTED = 2;
    public static final int NODE_DELETED = 3;
    public static final int NODE_RENAMED = 4;

    public void handle(int var1, String var2, nsIVariant var3, nsIDOMNode var4, nsIDOMNode var5);
}

