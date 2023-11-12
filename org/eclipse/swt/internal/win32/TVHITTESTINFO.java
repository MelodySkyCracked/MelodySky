/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TVHITTESTINFO {
    public int x;
    public int y;
    public int flags;
    public long hItem;
    public static int sizeof = OS.TVHITTESTINFO_sizeof();
}

