/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class WNDCLASS {
    public int style;
    public long lpfnWndProc;
    public int cbClsExtra;
    public int cbWndExtra;
    public long hInstance;
    public long hIcon;
    public long hCursor;
    public long hbrBackground;
    public long lpszMenuName;
    public long lpszClassName;
    public static final int sizeof = OS.WNDCLASS_sizeof();
}

