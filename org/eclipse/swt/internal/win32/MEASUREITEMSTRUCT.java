/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class MEASUREITEMSTRUCT {
    public int CtlType;
    public int CtlID;
    public int itemID;
    public int itemWidth;
    public int itemHeight;
    public long itemData;
    public static final int sizeof = OS.MEASUREITEMSTRUCT_sizeof();
}

