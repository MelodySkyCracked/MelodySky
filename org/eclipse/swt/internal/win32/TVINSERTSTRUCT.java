/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TVINSERTSTRUCT {
    public long hParent;
    public long hInsertAfter;
    public int mask;
    public long hItem;
    public int state;
    public int stateMask;
    public long pszText;
    public int cchTextMax;
    public int iImage;
    public int iSelectedImage;
    public int cChildren;
    public long lParam;
    public int iIntegral;
    public static final int sizeof = OS.TVINSERTSTRUCT_sizeof();
}

