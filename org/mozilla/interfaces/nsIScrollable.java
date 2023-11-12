/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIScrollable
extends nsISupports {
    public static final String NS_ISCROLLABLE_IID = "{919e792a-6490-40b8-bba5-f9e9ad5640c8}";
    public static final int ScrollOrientation_X = 1;
    public static final int ScrollOrientation_Y = 2;
    public static final int Scrollbar_Auto = 1;
    public static final int Scrollbar_Never = 2;
    public static final int Scrollbar_Always = 3;

    public int getCurScrollPos(int var1);

    public void setCurScrollPos(int var1, int var2);

    public void setCurScrollPosEx(int var1, int var2);

    public void getScrollRange(int var1, int[] var2, int[] var3);

    public void setScrollRange(int var1, int var2, int var3);

    public void setScrollRangeEx(int var1, int var2, int var3, int var4);

    public int getDefaultScrollbarPreferences(int var1);

    public void setDefaultScrollbarPreferences(int var1, int var2);

    public void getScrollbarVisibility(boolean[] var1, boolean[] var2);
}

