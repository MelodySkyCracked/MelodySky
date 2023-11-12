/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class MENUITEMINFO {
    public int cbSize;
    public int fMask;
    public int fType;
    public int fState;
    public int wID;
    public long hSubMenu;
    public long hbmpChecked;
    public long hbmpUnchecked;
    public long dwItemData;
    public long dwTypeData;
    public int cch;
    public long hbmpItem;
    public static final int sizeof = OS.MENUITEMINFO_sizeof();
}

