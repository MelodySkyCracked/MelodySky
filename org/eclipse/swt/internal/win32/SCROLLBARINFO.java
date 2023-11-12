/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public class SCROLLBARINFO {
    public int cbSize;
    public RECT rcScrollBar = new RECT();
    public int dxyLineButton;
    public int xyThumbTop;
    public int xyThumbBottom;
    public int reserved;
    public int[] rgstate = new int[6];
    public static final int sizeof = OS.SCROLLBARINFO_sizeof();
}

