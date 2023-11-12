/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDebug
extends nsISupports {
    public static final String NS_IDEBUG_IID = "{3bf0c3d7-3bd9-4cf2-a971-33572c503e1e}";

    public void assertion(String var1, String var2, String var3, int var4);

    public void warning(String var1, String var2, int var3);

    public void _break(String var1, int var2);

    public void abort(String var1, int var2);
}

