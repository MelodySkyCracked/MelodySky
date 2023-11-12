/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class DRAWITEMSTRUCT {
    public int CtlType;
    public int CtlID;
    public int itemID;
    public int itemAction;
    public int itemState;
    public long hwndItem;
    public long hDC;
    public int left;
    public int top;
    public int bottom;
    public int right;
    public long itemData;
    public static final int sizeof = OS.DRAWITEMSTRUCT_sizeof();
}

