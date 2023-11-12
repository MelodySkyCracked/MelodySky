/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TBBUTTONINFO {
    public int cbSize;
    public int dwMask;
    public int idCommand;
    public int iImage;
    public byte fsState;
    public byte fsStyle;
    public short cx;
    public long lParam;
    public long pszText;
    public int cchText;
    public static final int sizeof = OS.TBBUTTONINFO_sizeof();
}

