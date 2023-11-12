/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableRegion
extends nsISupports {
    public static final String NS_ISCRIPTABLEREGION_IID = "{82d8f400-5bde-11d3-b033-b27a62766bbc}";

    public void init();

    public void setToRegion(nsIScriptableRegion var1);

    public void setToRect(int var1, int var2, int var3, int var4);

    public void intersectRegion(nsIScriptableRegion var1);

    public void intersectRect(int var1, int var2, int var3, int var4);

    public void unionRegion(nsIScriptableRegion var1);

    public void unionRect(int var1, int var2, int var3, int var4);

    public void subtractRegion(nsIScriptableRegion var1);

    public void subtractRect(int var1, int var2, int var3, int var4);

    public boolean isEmpty();

    public boolean isEqualRegion(nsIScriptableRegion var1);

    public void getBoundingBox(int[] var1, int[] var2, int[] var3, int[] var4);

    public void offset(int var1, int var2);

    public boolean containsRect(int var1, int var2, int var3, int var4);
}

