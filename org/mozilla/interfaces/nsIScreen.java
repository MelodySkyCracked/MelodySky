/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIScreen
extends nsISupports {
    public static final String NS_ISCREEN_IID = "{f728830e-1dd1-11b2-9598-fb9f414f2465}";

    public void getRect(int[] var1, int[] var2, int[] var3, int[] var4);

    public void getAvailRect(int[] var1, int[] var2, int[] var3, int[] var4);

    public int getPixelDepth();

    public int getColorDepth();
}

