/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIBaseWindow
extends nsISupports {
    public static final String NS_IBASEWINDOW_IID = "{046bc8a0-8015-11d3-af70-00a024ffc08c}";

    public void initWindow(long var1, long var3, int var5, int var6, int var7, int var8);

    public void create();

    public void destroy();

    public void setPosition(int var1, int var2);

    public void getPosition(int[] var1, int[] var2);

    public void setSize(int var1, int var2, boolean var3);

    public void getSize(int[] var1, int[] var2);

    public void setPositionAndSize(int var1, int var2, int var3, int var4, boolean var5);

    public void getPositionAndSize(int[] var1, int[] var2, int[] var3, int[] var4);

    public void repaint(boolean var1);

    public long getParentWidget();

    public void setParentWidget(long var1);

    public long getParentNativeWindow();

    public void setParentNativeWindow(long var1);

    public boolean getVisibility();

    public void setVisibility(boolean var1);

    public boolean getEnabled();

    public void setEnabled(boolean var1);

    public boolean getBlurSuppression();

    public void setBlurSuppression(boolean var1);

    public long getMainWidget();

    public void setFocus();

    public String getTitle();

    public void setTitle(String var1);
}

