/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class MENUBARINFO {
    public int cbSize;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public long hMenu;
    public long hwndMenu;
    public boolean fBarFocused;
    public boolean fFocused;
    public static final int sizeof = OS.MENUBARINFO_sizeof();
}

