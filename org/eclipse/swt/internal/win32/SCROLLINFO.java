/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SCROLLINFO {
    public int cbSize;
    public int fMask;
    public int nMin;
    public int nMax;
    public int nPage;
    public int nPos;
    public int nTrackPos;
    public static final int sizeof = OS.SCROLLINFO_sizeof();
}

