/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class LVHITTESTINFO {
    public int x;
    public int y;
    public int flags;
    public int iItem;
    public int iSubItem;
    public static int sizeof = OS.LVHITTESTINFO_sizeof();
}

