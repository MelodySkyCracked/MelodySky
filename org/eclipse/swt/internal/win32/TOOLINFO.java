/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TOOLINFO {
    public int cbSize;
    public int uFlags;
    public long hwnd;
    public long uId;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public long hinst;
    public long lpszText;
    public long lParam;
    public long lpReserved;
    public static int sizeof = OS.TOOLINFO_sizeof();
}

