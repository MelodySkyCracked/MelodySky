/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class WINDOWPOS {
    public long hwnd;
    public long hwndInsertAfter;
    public int x;
    public int y;
    public int cx;
    public int cy;
    public int flags;
    public static final int sizeof = OS.WINDOWPOS_sizeof();
}

