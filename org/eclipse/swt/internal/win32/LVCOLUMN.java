/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class LVCOLUMN {
    public int mask;
    public int fmt;
    public int cx;
    public long pszText;
    public int cchTextMax;
    public int iSubItem;
    public int iImage;
    public int iOrder;
    public static final int sizeof = OS.LVCOLUMN_sizeof();
}

