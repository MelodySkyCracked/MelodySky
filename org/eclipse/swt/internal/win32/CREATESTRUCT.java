/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class CREATESTRUCT {
    public long lpCreateParams;
    public long hInstance;
    public long hMenu;
    public long hwndParent;
    public int cy;
    public int cx;
    public int y;
    public int x;
    public int style;
    public long lpszName;
    public long lpszClass;
    public int dwExStyle;
    public static final int sizeof = OS.CREATESTRUCT_sizeof();
}

