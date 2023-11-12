/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIEmbeddingSiteWindow
extends nsISupports {
    public static final String NS_IEMBEDDINGSITEWINDOW_IID = "{3e5432cd-9568-4bd1-8cbe-d50aba110743}";
    public static final long DIM_FLAGS_POSITION = 1L;
    public static final long DIM_FLAGS_SIZE_INNER = 2L;
    public static final long DIM_FLAGS_SIZE_OUTER = 4L;

    public void setDimensions(long var1, int var3, int var4, int var5, int var6);

    public void getDimensions(long var1, int[] var3, int[] var4, int[] var5, int[] var6);

    public void setFocus();

    public boolean getVisibility();

    public void setVisibility(boolean var1);

    public String getTitle();

    public void setTitle(String var1);

    public long getSiteWindow();
}

