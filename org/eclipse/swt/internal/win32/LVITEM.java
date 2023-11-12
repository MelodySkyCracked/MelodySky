/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class LVITEM {
    public int mask;
    public int iItem;
    public int iSubItem;
    public int state;
    public int stateMask;
    public long pszText;
    public int cchTextMax;
    public int iImage;
    public long lParam;
    public int iIndent;
    public int iGroupId;
    public int cColumns;
    public long puColumns;
    public static final int sizeof = OS.LVITEM_sizeof();
}

